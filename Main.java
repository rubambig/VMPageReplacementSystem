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

    Tables ctl = new Tables();
    SystemGUI gui = new SystemGUI(args[0]);
    Controller ctrl = new Controller(gui, ctl);
    String filename = args[0];
    try  {
      BufferedReader reader = new BufferedReader(new FileReader(filename));
      String line  = null;

      while ( (line = reader.readLine() ) != null) {
        int procNum = Integer.parseInt(line.substring(1,2));
        System.out.println("Process : " + procNum);
        int pageNum = Integer.parseInt(line.substring(4,10),2);
        System.out.println("Page referenced: " + pageNum + "\n");

        // Check if there is a free frame.
        int freeFrame = ctl.checkFreeFrame();

        if ( ctl.checkPageInTable(procNum, pageNum) ) { // Check in memory

          // Print message to the user and updated reference count.
          System.out.println("The page is already in physical memory!");
          ctl.updateProcessRefCount(procNum);

          // Search which frame is associated with the process/page pair.
          int frameOfInterest = (ctl.searchAssociatedFrame(procNum, pageNum))[0];

          // Add the reference frame to LRU Queue.
          ctl.addCandidateFrame(frameOfInterest);

        } else if ( freeFrame >= 0) { // Check for free frames.

          ctl.updateProcessFaultCount(procNum);
          ctl.updateProcessRefCount(procNum);
          ctl.updateFrameTable(freeFrame, procNum, pageNum);
          ctl.updatePageTable(false, procNum, pageNum, freeFrame);
          ctrl.updatePageTable(procNum);

          // Add the reference frame to LRU Queue.
          ctl.addCandidateFrame(freeFrame);

        } else { // Find a victim and replace them.

          ctl.updateProcessFaultCount(procNum);
          ctl.updateProcessRefCount(procNum);
          System.out.println("PAGE FAULT!!");

          // Find the victim
          int victim = ctl.pickVictim();

          // Send the victim a message to update their page table.
          int [] replacementPair = ctl.searchVictimPair(victim);
          int pid = replacementPair[0];
          int page = replacementPair[1];
          ctl.updatePageTable(true, pid, page, victim);
          ctrl.updatePageTable(procNum);

          // Send a message to the replacng process to update their page table.
          ctl.updatePageTable(false, procNum, pageNum, victim);

          // Update the frame table.
          ctl.updateFrameTable(victim, procNum, pageNum);

          // Add the reference frame to LRU Queue
          ctl.addCandidateFrame(victim);
        }

        // Inspect the frame/page table as it currently stands.
        System.out.println("Physical Memory \n");
        System.out.println("Frame# ProcID  Page#");
        ctl.printFrameTableState();
        ctl.printPageTableState(procNum);

      }
    } catch ( IOException e) {
      System.err.println("Could not find input file.");
      e.printStackTrace();
      System.exit(1);
    }

    // Report final statistics
    ctl.printFinalStats();
  }
}
