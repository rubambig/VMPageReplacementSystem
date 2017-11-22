import java.util.*;
/*********************************************************
* The Table Manager. 
* Maintains one process/frame table to handle page faults.
* @author Gloire Rubambiza
* @since 11/14/2017
**********************************************************/
public class Tables {

  /* The process table for this controller i.e.  this run of m references */
  private ProcessTable processTable;

  /* The frame table for this controller */
  private FrameTable frameTable;

  /*******************************************************
  * Instantiates a controller for frame and process table.
  * To be used as a unified data structure that initiates
  * all the actions based on input passed from main.
  ********************************************************/
  public Tables () {
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

  /***********************************************
  * Updates the page fault count for a process.
  * @param pid is the PID of the process.
  ***********************************************/
  public void updateProcessFaultCount ( int pid ) {
    this.processTable.updatePCBFaultCount(pid);
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
  * If the page exists, the function is used to remove the
  * entry for a process whose frame has been chose as a
  * victim. Otherwise, we're inserting the frame/page pair.
  * @param exists tells where the page is already in the table.
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
  * The function is used when the page is already in memory or
  * a potential replacement.
  * @return the triple of PID,page, and frame number;
  /************************************************************/
  public int [] searchAssociatedFrame(int pid, int page) {
    return this.frameTable.searchPotentialReplacement(pid, page);
  }

  /***************************************
  * Adds a frame to the replacement queue.
  ****************************************/
  public void addCandidateFrame(int frame) {
    this.frameTable.addCandidate(frame);
  }

  /********************************************
  * Picks a victim for  LRU page replacement.
  * @return the frame that needs to be updated.
  *********************************************/
  public int pickVictim () {
    return this.frameTable.pickLRUCandidate();
  }

  /*******************************************
  * Reports the PID/page pair associated with
  * a victim frame.
  * @param frame is the frame associated with the pair
  * @return the PID and page of the victims
  ********************************************/
  public int [] searchVictimPair (int frame ) {
    return this.frameTable.searchVictim(frame);
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

}
