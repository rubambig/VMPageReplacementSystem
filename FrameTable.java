import java.util.*;
/************************************************************
* A frame table data structure
* Keeps track of process and page is associated with frames.
* Physical memory is specified to be 16 KB with 1KB frames.
* There are 16 total frames in physical memory.
* The frame table also keeps track of its free frames.
* @author Gloire Rubambiza
* @since 11/12/2017
************************************************************/
public class FrameTable {

  /** The array of frames in physical memory. */
  private Frame [] frameTable;

  /** The max amount of frames to be created. */
  private int max = 16;

  /** The list of free frames in the system. */
  private boolean [] freeFrameList;

  /** The Queue for deciding which frame to vacate for a new page reference. */
  private Queue<Integer> kicker;

  /*************************************
  * Instantiates an array of 16 frames
  *************************************/
  public FrameTable () {
    this.frameTable = new Frame [max];
    this.freeFrameList = new boolean [max];
    kicker = new LinkedList<Integer>();
    int i;

    // Creates each new frame
    for ( i = 0; i < max; i++ ) {
      this.frameTable[i] = new Frame ();
      this.freeFrameList[i] = true;
    }
  }

  /******************************************************
  * Checks if the page is already in the table.
  * @param pid is the process that owns the page.
  * @param page the page that was referenced.
  * @return the presence of the page in the frame table.
  ******************************************************/
  public boolean checkPage(int pPid, int pPage){
    int i, pid, page;
    for ( i = 0; i < max; i++ ) {
      pid = this.frameTable[i].getPID();
      page = this.frameTable[i].getPage();
      if ( (pid == pPid) && (page == pPage) ) {
        return true;
      }
    }
    return false;
  }

  /**************************************************
  * Checks for empty spots in the frame table.
  * @return a free frame number if one is available,
  *  -1 if the frame table is empty
  *************************************************/
  public int getFreeFrame() {
    int i;
    for ( i = 0; i < max; i++) {
      if ( this.freeFrameList[i] ) {
        this.freeFrameList[i] = false;
        return i;
      }
    }
    return -1;
  }

  /***************************************************
  * Inserts a new frame table entry.
  * @param frame is the frame that will be associated
  * with the process/page pair
  * @param process is the process that owns the page.
  * @param page is the page to be inserted.
  **************************************************/
  public void insertFrameEntry(int frame, int process, int page) {
    int i;
    for ( i = 0; i < max; i++ ) {
      if ( i == frame ) {
        this.frameTable[i].updateProcess(process);
        this.frameTable[i].updatePage(page);
      }
    }
  }

  /**************************************************
  * Inspects the frame table as it currently stands.
  **************************************************/
  public void printCurrentState() {
    int i;
    for ( i = 0; i < max; i++ ) {
      int pid = this.frameTable[i].getPID();
      int page = this.frameTable[i].getPage();
      System.out.println(i + "\t" + pid + "\t" + page + "\n");
    }
  }

  /***************************************
  * Adds a frame to the replacement queue.
  ****************************************/
  public void addCandidate(int frame) {
    this.kicker.add(frame);
  }

  /*****************************************************************
  * Searches for which frame is associated with a PID/page pair.
  * @param pid is the PID of the process owning the frame.
  * @param page is the page located at the frame.
  * @param frame is the frame that may need replacement.
  * @return the frame number associated with the PID/page pair.
  /****************************************************************/
  public int [] searchPotentialReplacement(int pPid, int pPage) {
    int i;
    int [] interestArray = new int [3];
    for ( i = 0; i < max; i++ ) {
      int pid = this.frameTable[i].getPID();
      int page = this.frameTable[i].getPage();
      if ( (pid == pPid) && (page == pPage) ) {
        interestArray[0] = i;
        interestArray[1] = pid;
        interestArray[2] = page;
        return interestArray;
      }
    }
    return interestArray;
  }

  /*****************************************************
  * Reports the PID/page pair associated with
  * a victim frame.
  * @param frame is the frame associated with the pair.
  * @return the PID and page of the victims.
  *****************************************************/
  public int [] searchVictims (int frame ) {
    int i;
    int [] pair = new int [2];
    for ( i = 0; i < max ; i++ ) {
      if ( i == frame ) {
        int pid = this.frameTable[i].getPID();
        int page = this.frameTable[i].getPage();
        pair[0] = pid;
        pair[1] = page;
        return pair;
      }
    }
    return pair;
  }

  /********************************************
  * Picks a candidate for page replacement.
  * Removes it from the head of the queue.
  * @return the frame that needs to be updated.
  *********************************************/
  public int pickLRUCandidate () {
    int vic = this.kicker.remove();
    System.out.println("Picking victim frame: " + vic + "\n");
    return vic;
  }

  /**
  * TO-DO
  * - Keep track of which frame/page pair was least recently used
  * - Use LRU replacement and reupdate the page table of a process kicked out
  * - Check if any frames are free before bringing a new page in
  * - Update the free frame list after bringing a new page in
  * - Update the frame table(new PID, new page) when a new page is brought in
  */

}
