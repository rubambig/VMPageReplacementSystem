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

  /******************************************************
  * Checks if the page is already in the table.
  * @param pid is the process that owns the page.
  * @param page the page that was referenced.
  * @return the presence of the page in the frame table.
  ******************************************************/
  public boolean checkPageInTable(int pid, int page){
    return this.frameTable.checkPage(pid, page);
  }

  /****************************************************
  * Checks if there are any empty frames in the table.
  ****************************************************/
  public int checkFreeFrame() {
    return this.frameTable.getFreeFrame();
  }

  /*****************************************************
  * Updates the frame table with a new frame as space
  * allows or page faults occurs.
  * @param frame is the frame table entry to be updated
  *****************************************************/
  public void updateFrameTable(int frame, int process, int page) {
    this.frameTable.insertFrameEntry(frame, process, page);
  }

  /************************************************
  * Prints the current state of the frame table.
  ***********************************************/
  public void printState() {
    this.frameTable.sendCurrentState();
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

        // Save the new memory reference
        int freeFrame = ctl.checkFreeFrame();

        if ( ctl.checkPageInTable(procNum, pageNum) ) {
          System.out.println("The page is already in physical memory!\n");
        } else if ( freeFrame >= 0) {
          ctl.updateFrameTable(freeFrame, procNum, pageNum);
        } else { // No free frames
          System.out.println("Page not in memory and No more free frames\n");
        }

        // Inspect the frame table as it currently stands.
        System.out.println("Frame# ProcID  Page#");
        ctl.printState();

      }
    } catch ( IOException e) {
      System.err.println("Could not find input file.");
      e.printStackTrace();
      System.exit(1);
    }
  }
}
