import java.util.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

/*********************************************************
* The Virtual Memory Management Manager a.k.a Controller
* Listens for actions on the user GUI.
* Sends messages to the process table and frame table with
* any new updates dictacted by the file input.
* Maintains one process/frame table to handle page faults.
* Sends any updates the viewer about the state of the
* process currently making memory references.
* @author Gloire Rubambiza
* @since 11/14/2017
**********************************************************/
public class Controller {

  /* The process table for this controller i.e.  this run of m references */
  private ProcessTable processTable;

  /* The frame table for this controller */
  private FrameTable frameTable;

  /*******************************************************
  * Instantiates a controller for frame and process table.
  * To be used as a unified data structure that initiates
  * all the actions based on input passed from main.
  ********************************************************/
  public Controller () {
    this.processTable = new ProcessTable();
    this.frameTable = new FrameTable();
  }

  /***********************************************
  * Updates the references count for the process.
  * @param pid is the PID of the process.
  ***********************************************/
  public void updateProcessRefCount ( int pid ) {
    this.processTable.updatePCBRefCount(pid);
  }
  /******************************************************
  * Checks if the page is already in the table.
  * @param pid is the process that owns the page.
  * @param page the page that was referenced.
  * @return the presence of the page in the frame table.
  ******************************************************/
  public boolean checkPageInTable(int pid, int page) {
    return this.frameTable.checkPage(pid, page);
  }

  /**********************************************************
  * Updates page table of the given process with new entries.
  * @param exits tells where the page is already in the table.
  * @param pid is the PID of the process.
  * @param page is the page to be added/replaced in the table.
  ************************************************************/
  public void updatePageTable(boolean exists, int pid, int page, int frame) {
    this.processTable.updatePCB(exists, pid, page, frame);
  }

  /****************************************************
  * Checks if there are any empty frames in the table.
  ****************************************************/
  public int checkFreeFrame() {
    return this.frameTable.getFreeFrame();
  }

  /************************************************************
  * Searches for which frame is associated with a PID/page pair.  
  * @return the frame number associated with the PID/page pair.
  /************************************************************/
  public int searchAssociatedFrame(int pid, int page) {
    return this.frameTable.searchFrame(pid, page);
  }

  /***************************************
  * Adds a frame to the replacement queue.
  ****************************************/
  public void addCandidateFrame(int frame) {
    this.frameTable.addCandidate(frame);
  }


  /*****************************************************
  * Updates the frame table with a new frame as space
  * allows or page faults occurs.
  * @param frame is the frame table entry to be updated
  *****************************************************/
  public void updateFrameTable(int frame, int process, int page) {
    this.frameTable.insertFrameEntry(frame, process, page);
  }

  /**********************************************
  * Prints the current state of the frame table.
  ***********************************************/
  public void printFrameTableState() {
    this.frameTable.printCurrentState();
  }

  /********************************************************
  * Prints the current state of the given PCB's page table.
  * @param pid is the PID of the process.
  *********************************************************/
  public void printPageTableState( int pid ) {
    this.processTable.printPageTable(pid);
  }

  /*****************************************************
  * Prints the total memory references for each process.
  ******************************************************/
  public void printFinalStats () {
    this.processTable.printStats();
  }


  public static void main (String[] args) {

    Controller ctl = new Controller();
    String filename = args[0];
    try  {
      BufferedReader reader = new BufferedReader(new FileReader(filename));
      String line  = null;

      while ( (line = reader.readLine() ) != null) {
        int procNum = Integer.parseInt(line.substring(1,2));
        System.out.println("Process : " + procNum + "\n");
        int pageNum = Integer.parseInt(line.substring(4,10),2);
        System.out.println("Page referenced: " + pageNum + "\n");

        // Check if there is a free frame.
        int freeFrame = ctl.checkFreeFrame();

        if ( ctl.checkPageInTable(procNum, pageNum) ) {
	  // Print message to the user. 
          System.out.println("The page is already in physical memory!\n");
          ctl.updateProcessRefCount(procNum);
          
          // Search which frame is associated with the process/page pair
          int frameOfInterest = ctl.searchAssociatedFrame(procNum, pageNum);
          System.out.println("The frame you want is " + frameOfInterest + "\n");
          
          // Add the reference frame to LRU Queue
	  ctl.addCandidateFrame(frameOfInterest);
      
        } else if ( freeFrame >= 0) { // Check for free frames

          ctl.updateProcessRefCount(procNum);
          ctl.updateFrameTable(freeFrame, procNum, pageNum);
          ctl.updatePageTable(false, procNum, pageNum, freeFrame);

        } else { // Find a victim and replace them
          ctl.updateProcessRefCount(procNum);
          System.out.println("Page not in memory and No more free frames\n");
        }

        // Inspect the frame/page table as it currently stands.
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
