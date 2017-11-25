import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
/*********************************************************
* The Virtual Memory Management Manager a.k.a Controller
* Listens for actions on the user GUI.
* Sends messages to the process table and frame table with
* any new updates dictacted by the file input.
* Handles the controller communicating with process and
* frame tables.
* Sends any updates the viewer about the state of the
* process currently making memory references.
* @author Gloire Rubambiza
* @since 11/22/2017
**********************************************************/
public class Main {

  public static void main (String[] args) {

    Tables tbl = new Tables();
    SystemGUI gui = new SystemGUI(args[0]);
    Controller ctrl = new Controller(gui, tbl);
    String filename = args[0];
    try  {
      BufferedReader reader = new BufferedReader(new FileReader(filename));
      String line  = null;

      while ( (line = reader.readLine() ) != null) {
        int procNum = Integer.parseInt(line.substring(1,2));
        System.out.println("Process : " + procNum);
        String pageRef = line.substring(4,10);
        int pageNum = Integer.parseInt(line.substring(4,10),2);
        System.out.println("Page referenced: " + pageNum + "\n");

        // Check if there is a free frame.
        int freeFrame = tbl.checkFreeFrame();

        if ( tbl.checkPageInTable(procNum, pageNum) ) { // Check in memory
          ctrl.updateReference(procNum, pageRef);

          // Print message to the user and updated reference count.
          System.out.println("The page is already in physical memory!");
          tbl.updateProcessRefCount(procNum);

          // Search which frame is associated with the process/page pair.
          int frameOfInterest = (tbl.searchAssociatedFrame(procNum, pageNum))[0];

          // Add the reference frame to LRU Queue.
          tbl.addCandidateFrame(frameOfInterest);

        } else if ( freeFrame >= 0) { // Check for free frames.
          ctrl.updateReference(procNum, pageRef);

          tbl.updateProcessFaultCount(procNum);
          tbl.updateProcessRefCount(procNum);
          tbl.updateFrameTable(freeFrame, procNum, pageNum);
          tbl.updatePageTable(false, procNum, pageNum, freeFrame);

          // Controller actions.
          ctrl.updatePageTable(procNum);
          ctrl.updateFrameTable();

          // Add the reference frame to LRU Queue.
          tbl.addCandidateFrame(freeFrame);

        } else { // Find a victim and replace them.
          ctrl.updateReference(procNum, pageRef);

          tbl.updateProcessFaultCount(procNum);
          tbl.updateProcessRefCount(procNum);
          System.out.println("PAGE FAULT!!");

          // Find the victim
          int victim = tbl.pickVictim();

          // Send the victim a message to update their page table.
          int [] replacementPair = tbl.searchVictimPair(victim);
          int pid = replacementPair[0];
          int page = replacementPair[1];
          tbl.updatePageTable(true, pid, page, victim);


          // Send a message to the replacing process to update their page table.
          tbl.updatePageTable(false, procNum, pageNum, victim);

          // Update the frame table.
          tbl.updateFrameTable(victim, procNum, pageNum);

          ctrl.updatePageTable(procNum);
          ctrl.updateFrameTable();

          // Add the reference frame to LRU Queue
          tbl.addCandidateFrame(victim);
        }

        // Inspect the frame/page table as it currently stands.
        System.out.println("Physical Memory \n");
        System.out.println("Frame# ProcID  Page#");
        tbl.printFrameTableState();
        ctrl.updateFrameTable();
        tbl.printPageTableState(procNum);

      }
    } catch ( IOException e) {
      System.err.println("Could not find input file.");
      e.printStackTrace();
      System.exit(1);
    }

    // Report final statistics
    ctrl.updateStats();
    tbl.printFinalStats();
  }
}
