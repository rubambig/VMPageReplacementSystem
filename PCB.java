import java.util.*;
import java.util.ArrayList;
/************************************************************
* A Process Control Block (PCB) data structure for a process
* @author Gloire Rubambiza
* @since 11/12/2017
************************************************************/
public class PCB {

  /** The PID for this process. */
  private int pid;

  /** The number of memory references for this process. */
  private int memref;

  /** The page table for this process. */
  private Hashtable<Integer, Integer> pageTable;

  /** The initial capacity of the page table. */
  private int capacity = 64;

  /*************************************************
  * Instantiates a PCB for a process with (minimal)
  * useful information
  * The process will have 64 pages of 1KB each
  * @param pid the process ID
  * @param table the page table for this process
  ***********************************************/
  public PCB (int PID) {
    this.pid = PID;
    this.memref = 0;
    this.pageTable = new Hashtable<Integer, Integer>(capacity);
  }

  /************************************************************
  * Updates page table of the given process with new entries.
  * @param inTable tells where the page is being added/replaced
  * @param pid is the PID of the process.
  * @param page is the page to be added/replaced in the table.
  *************************************************************/
  public void updateTable(boolean inTable, int page, int frame) {
    if ( !(inTable) ) { // Adding for the first time
      this.pageTable.put(page,frame);
    } else { // Removing it from the table
      this.pageTable.remove(page,frame);
    }
  }

  /***********************************************
  * Updates the references count for the process.
  ***********************************************/
  public void updateRefCount () {
    this.memref++;
  }

  /*********************************************
  * Prints the current state of the page table.
  * Code help on enumerating over a map obtained from:
  * https://stackoverflow.com/questions/2216311/how-do-i-enumerate-the-keys-and
  * -values-of-a-hashtable
  *********************************************/
  public void printTable() {
    System.out.println("Page     Frame");
    for ( Integer key : this.pageTable.keySet() ) {
      System.out.println(key + "    ->    " + this.pageTable.get(key) );
    }
  }

  /**********************************************************
   * Reports the total number of references for this process.
   **********************************************************/
   public int getTotalReferences () {
     return this.memref;
   }

  /************************************************
   * Reports the size of the PCB's page table
   * at the completion of each memory reference run.
   * @return the size of the page table currently.
  ************************************************/
  public int getPageSize () {
    return this.pageTable.size();
  }

  /***********************************
   * Reports the PID for the process.
   **********************************/
   public int getPID () {
     return this.pid;
   }

}
