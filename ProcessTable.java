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

  /***********************************************
  * Updates the page fault count for the process.
  * @param pid is the PID of the process.
  ***********************************************/
  public void updatePCBFaultCount ( int pid ) {
    int i;
    for ( i = 0; i < max; i++ ) {
      if ( this.pcbTable[i].getPID() == pid ) {
        this.pcbTable[i].updateFaults();
      }
    }
  }

  /********************************************************
  * Prints the current state of the PCB's page table.
  * @param pid is the PID of the process.
  *********************************************************/
  public void printPageTable( int pid ) {
    System.out.println("Page Table : Process " + pid);
    int i;
    for ( i = 0; i < max; i++ ) {
      if ( this.pcbTable[i].getPID() == pid ) {
        this.pcbTable[i].printTable();
      }
    }
    System.out.println();
  }

  /******************************************
  * Prints the final stats for each process.
  *******************************************/
  public void printStats () {
    System.out.println("-------------------------------------------------");
    System.out.println("Final Stats\n");
    System.out.println("Proc   Refs  Faults");
    int i = 0;

    for (i = 0; i < max; i++) {
      int pid = this.pcbTable[i].getPID();
      int totalRefs = this.pcbTable[i].getTotalReferences();
      int totalFaults = this.pcbTable[i].getTotalPageFaults();
      System.out.println(pid + "\t" + totalRefs + "\t" + totalFaults);
    }
  }

  /*******************************************
  * Returns a reference to the array of PCBs.
  * @return an array of PCBs.
  ********************************************/
  public PCB [] getPCBArray () {
    return this.pcbTable;
  }
}
