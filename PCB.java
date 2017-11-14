import java.util.*;
/************************************************************
* A Process Control Block (PCB) data structure for a process
* @author Gloire Rubambiza
* @since 11/12/2017
************************************************************/
public class PCB {

  /** The PID for this process. */
  private int pid;

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
    this.pageTable = new Hashtable<Integer, Integer>(capacity);
  }

  /****************************************
   * Update the page table for the process.
  ***************************************/
  public void updateTable (int page, int frame) {
    this.pageTable.put(page,frame);
  }

  /************************************************
   * Reports the size of the PCB's page table 
   * at the completion of each memory reference run
   * @return the size of the page table currently
  ************************************************/
  public int pageSize () {
    return this.pagetTable.size();
  }

}
