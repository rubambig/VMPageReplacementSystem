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
    int i, j = 1;

    // Creates each new PCB
    for ( i = 0; i < max; i++ ) {
      this.pcbTable[i] = new PCB (j);
      j++;
    }
  }

  /************************************************************
  * Updates page table of the given process with new entries.
  * @param inTable tells where the page is being added/replaced
  * @param pid is the PID of the process.
  * @param page is the page to be added/replaced in the table.
  * @return true if the update was successful, false otherwise.
  *************************************************************/
  public void updatePCB(boolean inTable, int pid, int page, int frame) {
    int i;
    for ( i = 0; i < 10; i++ ) {
      if ( this.pcbTable[i].getPID() == pid ) {
        this.pcbTable[i].updateTable(inTable, page, frame);
      }
    }
  }

  /***********************************************
  * Updates the references count for the process.
  * @param pid is the PID of the process.
  ***********************************************/
  public void updatePCBRefCount ( int pid ) {
    int i;
    for ( i = 0; i < max; i++ ) {
      if ( this.pcbTable[i].getPID() == pid ) {
        this.pcbTable[i].updateRefCount();
      }
    }
  }

  /********************************************************
  * Prints the current state of the PCB's page table.
  * @param pid is the PID of the process.
  *********************************************************/
  public void printPageTable( int pid ) {
    System.out.println("Page Table for Process " + pid);
    int i;
    for ( i = 0; i < max; i++ ) {
      if ( this.pcbTable[i].getPID() == pid ) {
        this.pcbTable[i].printTable();
      }
    }
    System.out.println();
  }

  /*****************************************************
  * Prints the total memory references for each process.
  ******************************************************/
  public void printStats () {
    int i = 0;
    for (i = 0; i < max; i++) {
      int pid = this.pcbTable[i].getPID();
      //System.out.println("Got process #" + pid + "and i is " + i + "\n");
      int total = this.pcbTable[i].getTotalReferences();
      System.out.println("Process " + pid + ", Total Refs: " + total + "\n");
    }
  }

  /**
  * TO-DO
  * - Update the page table of a process if a page fault occurs
  * - Send the current state of a PCB as requested by controller
  * - Report the size of the page table for a process after each input run (pageTable.size)
  * - Report the totla number of memory references made by each process
  */

}
