import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import java.util.*;
/*************************************************************
* The Virtual Memory Management Manager a.k.a Controller
* Sends messages to the tables with any new updates dictacted
* by the file input.
* Points the GUI to the right tables to update any new updates.
* @author Gloire Rubambiza
* @since 11/22/2017
**************************************************************/
public class Controller implements ActionListener {

  /** The GUI to be displayed. */
  private SystemGUI gui;

  /** The table object that holds the frame and page table. */
  private Tables table;

  /** The Queue of inputs read from the file. */
  private Queue<Integer[]> input;

  /** The buttons for handling user actions. */
  private JButton next, runf, runc;

  /** The array of action buttons. */
  private JButton [] userActionBtns;

  /** The size of the buttons. */
  private final int bSize  = 30;

  private boolean locker;

  /*************************************************
  * Instantiates the tables, GUI, and buttons to be
  * used for user actions.
  *************************************************/
  public Controller (SystemGUI pGUI, Tables pTable, Queue<Integer[]> pInput,
                      boolean myLock ) {

    gui = pGUI;
    table = pTable;
    input = pInput;
    locker = myLock;
    userActionBtns = gui.sendButtons();
    next = userActionBtns[0];
    runf = userActionBtns[1];
    runc = userActionBtns[2];
    addCommandListeners();
  }

  /*******************************************
  * Adds the action listeners for the buttons.
  ********************************************/
  private void addCommandListeners() {
    next.addActionListener(this);
    runf.addActionListener(this);
    runc.addActionListener(this);
  }

  /**********************************************
  * Instructs the GUI to display the latest page
  * table for a given process.
  * @param pid is the PID of the process.
  **********************************************/
  public void updatePageTable (int pid) {
    gui.displayPageTable(table.passProcessTable(pid), pid);
  }

  /**************************************************
  * Gets a reference to the frame table i.e. current
  * physical state of memory.
  * Passes it to the GUI.
  * @return an array of frames.
  *************************************************/
  public void updateFrameTable () {
    gui.displayFrameTable(table.passFrameTable());
  }

  /*********************************************
  * Instructs the GUI to display the latest page
  * reference for a given process.
  * @param pid is the PID of the process.
  * @param ref is the page that was referenced.
  **********************************************/
  public void updateReference (int pid, int ref) {
    gui.displayReference(pid, ref);
  }

  /*******************************************
  * Instructs the GUI to display final stats.
  * @param pcbArray is the array of PCBs.
  *******************************************/
  public void updateStats () {
    gui.displayStats(table.passPCBArray());
  }

  /********************************************************************
  * Pops on input from the queue to be read when the user presses next.
  * Tells the controller whether the read caused a page fault.
  * Otherwise the LRU Queue is updated with the least recently
  * used page if the page is already in memory.
  * @param queue is the input queue.
  * @return 1 if we're done reading input, -1 if there was a page
  * fault, 0 if the page was already in memory.
  *******************************************************************/
  private int readNext ( Queue<Integer[]> myQ ) {

    // Check if we have input.
    Integer [] pair = new Integer[2];
    if ( ( pair = myQ.poll() ) == null ) { // Error checking
      System.out.println("The inputs are done!\n");
      return 1;
    }

    // Send the input to check for a page fault.
    int procNum =  pair[0];
    int page = pair[1];
    if ( isFault(procNum, page) ) {
      return -1;
    } else {
      return 0;
    }
  }

  /*****************************************************************
  * Handles the given input and takes care of page faults as needed.
  * @param procNum is the processing reading in a page.
  * @param pageNum is the page being read.
  * @return true if the update caused a page fault, false otherwise.
  *****************************************************************/
  private boolean isFault ( int procNum, int pageNum ) {

    // Check if there is a free frame.
    int freeFrame = table.checkFreeFrame();

    if ( table.checkPageInTable( procNum, pageNum) ) { // Check in memory

      // Handles the case of a page being already in memory.
      this.handlePageInMemory(procNum, pageNum);

      // The page is in memory. No page fault.
      return false;

    } else if ( freeFrame >= 0 ) { // Check if there are free frames.

      // Handles the case of a page fault with free frames available.
      this.handleFreeFrameAvailable(procNum, pageNum, freeFrame);

      // The page is not in memory. It's a page fault, but we have free frames.
      return true;

    } else { // We actually have to kick a process' page out of memory.

    // Handles the case of a page fault with replacement.
    this.handlePageReplacement(procNum, pageNum);

    // The page is not in memory. It's a page fault, a replacement occured.
    return true;
  }
}

/****************************************************
* Handles the case of a page already being in memory
* after the input is read.
* @param pid is the PID of the process.
* @param page is the page already in memory.
*****************************************************/
private void handlePageInMemory( int pid, int page ) {

  // Update the referenced page on the GUI.
  this.updateReference(pid, page);

  // Update the reference count.
  table.updateProcessRefCount(pid);

  // Search which frame is associated with the process/page pair.
  int frameOfInterest = (table.searchAssociatedFrame(pid, page))[0];

  // Add the reference frame to LRU Queue as a replacement candidate.
  table.addCandidateFrame(frameOfInterest);

  // Display the page table for the process of interest.
  this.updatePageTable(pid);

}

/****************************************************
* Handles the case of a page fault with free frames
* being available.
* @param pid is the PID of the process.
* @param page is the page already in memory.
* @param freeFrame is the frame that is available.
*****************************************************/
private void handleFreeFrameAvailable( int pid, int page, int freeFrame ) {
  // Update the referenced page on the GUI.
  this.updateReference(pid, page);

  // Update all the tables with the right info regarding the page fault.
  table.updateProcessFaultCount(pid);
  table.updateProcessRefCount(pid);
  table.updateFrameTable(freeFrame, pid, page);
  table.updatePageTable(false, pid, page, freeFrame);

  // Add the reference frame to LRU Queue as a replacement candidate.
  table.addCandidateFrame(freeFrame);

  // Display the page table for the process of interest.
  this.updatePageTable(pid);

  // Display the frame table with the new updates.
  this.updateFrameTable();

  System.out.println("Page fault, there are free frames\n");
}

/****************************************************
* Handles the case of replacing a page when a page fault
* occurs and there are no free frame availables.
* Picks a victim and updates the tables accordingly.
* @param procNum is the PID of the process.
* @param pageNum is the page already in memory.
*****************************************************/
private void handlePageReplacement( int procNum, int pageNum ) {

  // Update the referenced page on the GUI.
  this.updateReference(procNum, pageNum);

  // Increment reference and fault counts for the process.
  table.updateProcessFaultCount(procNum);
  table.updateProcessRefCount(procNum);

  // Find a victim.
  int victim = table.pickVictim();

  // Send the victim a message to update their page table.
  int [] replacementPair = table.searchVictimPair(victim);
  int pid = replacementPair[0];
  int page = replacementPair[1];
  table.updatePageTable(true, pid, page, victim);

  // Send a message to the replacing process to update their page table.
  table.updatePageTable(false, procNum, pageNum, victim);

  // Update the frame table.
  table.updateFrameTable(victim, procNum, pageNum);

  // Add the reference frame to LRU Queue as a replacement candidate.
  table.addCandidateFrame(victim);

  // Update the page/frame table on the GUI.
  this.updatePageTable(procNum);
  this.updateFrameTable();

  System.out.println("Page fault, victim was replaced!\n");
}

/******************************************************
* Disables all the buttons when the input is done.
* @param btns is the collection of user action buttons.
*******************************************************/
private void disableButtons( JButton[] btns ) {

  int i;
  for ( i = 0; i < 3; i++ ) {
    btns[i].setEnabled(false);
  }
}

/************************************
* Notifies the GUI that we are done.
************************************/
public synchronized void notifyEnd() {
  this.locker = true;
  notifyAll();
}


@Override
public void actionPerformed(ActionEvent e) {

  if ( e.getSource() instanceof JButton && e.getSource() == next ) {

    int check = this.readNext(input);
    if ( check < 0 ) { // Page fault.
      // Maybe a popup to say it's a page fault;
    } else if ( check > 0 ) { // Inputs are done, deactivate buttons
      this.updateStats();
      this.disableButtons(userActionBtns);
    }

  } else if ( e.getSource() instanceof JButton && e.getSource() == runf) {

    int check = this.readNext(input);

    // Keep reading until we're done with input or a page fault occurs.
    if ( check < 0 ) { // We fault at the beginning.
      // Popup saying it was a page fault?
    } else { // We keep reading until we hit a page fault.
      while ( check >= 0 ) {
        check = this.readNext(input);
        if (check < 0 ) {
          // Popup saying it was a page fault?
          break;
        } else if ( check > 0 ) { // Input ended while searching for page fault.
          this.updateStats();
          this.disableButtons(userActionBtns);
          break;
        }
        System.out.println("Trying to find the next fault!\n");
      }
    }
  } else if ( e.getSource() instanceof JButton && e.getSource() == runc ) {

    int check;
    while ( ( check = this.readNext(input)) > -2 ) {
      if ( check == 1 ){
        this.updateStats();
        this.disableButtons(userActionBtns);
        break;
      }
      System.out.println("Skipping until completion\n");
    }
  }

  /**
  * - TO-DO
  * - Change the color of the frame that is changed during a fault
  * - Report the size of a page table as we are running
  * - Different threads for each file?
  */
}
}
