import java.util.*;
/******************************************************************
* A process table data structure
* Keeps track of 10 processes currently running in the background.
* @author Gloire Rubambiza
* @since 11/12/2017
******************************************************************/
public class ProcessTable {

  /** The array of PCBs in the ready and running states. */
  private PCB [] pcbTable;

  /** The max amount of PCBs to be created. */
  private int max = 10;

  /*************************************************
  * Instantiates an array of 10 PCBs to start with.
  * Initializes all the PCBs with null page tables
  ************************************************/
  public ProcessTable () {
    this.pcbTable = new PCB [max];
    int i;

    // Creates each new PCB
    for ( i = 0; i < max; i++ ) {
      this.pcbTable[i] = new PCB (i);
    }
  }

  /**
  * TO-DO
  * - Check if a given page is already in memory (associated with a frame)
  * - Update the page table of a process if a page fault occurs
  * - Send the current state of a PCB as requested by controller
  * - Report the size of the page table for a process after each input run (pageTable.size)
  * - Report the totla number of memory references made by each process
  */

}
