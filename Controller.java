import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import java.util.*;
/*************************************************************
* The Virtual Memory Management Manager a.k.a Controller
* Sends messages to the tables with any new updates dictacted
* by the file input.
* Points the GUI to the right tables to update its contents.
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
  private JButton next, runf, runc, exit;

  /** The array of action buttons. */
  private JButton [] userActionBtns;

  /*************************************************
  * Instantiates the tables, GUI, and buttons to be
  * used for user actions.
  *************************************************/
  public Controller (SystemGUI pGUI, Tables pTable, Queue<Integer[]> pInput) {

    gui = pGUI;
    table = pTable;
    input = pInput;
    userActionBtns = gui.sendButtons();
    next = userActionBtns[0];
    runf = userActionBtns[1];
    runc = userActionBtns[2];
    exit = userActionBtns[3];
    addCommandListeners();
  }

  /*******************************************
  * Adds the action listeners for the buttons.
  ********************************************/
  private void addCommandListeners() {
    next.addActionListener(this);
    runf.addActionListener(this);
    runc.addActionListener(this);
    exit.addActionListener(this);
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
  * Instructs the GUI to display the latest state of
  * the frame table i.e. physical memory.
  * @param fault color the frame based on fault kind.
  * @param frame is the frame that needs coloring.
  *************************************************/
  public void updateFrameTable (boolean fault, int frame) {
    gui.displayFrameTable(table.passFrameTable(), fault, frame);
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
  *******************************************/
  public void updateStats () {
    gui.displayStats(table.passPCBArray());
  }

  /********************************************************************
  * Pops input from the queue to be read when the user presses next.
  * Tells the controller whether the read caused a page fault.
  * Otherwise the LRU Queue is updated with the least recently
  * used frame if the page is already in memory.
  * @param queue is the input queue.
  * @return 1 if we're done reading input, -1 if there was a page
  * fault, 0 if the page was already in memory.
  *******************************************************************/
  private int readNext ( Queue<Integer[]> myQ ) {

    // Check if we have input.
    Integer [] pair = new Integer[2];
    if ( ( pair = myQ.poll() ) == null ) { // Error checking
      return 1; // Input is done.
    }

    // Send the input to check for a page fault.
    int procNum =  pair[0];
    int page = pair[1];
    if ( isFault(procNum, page) ) {
      return -1; // Page fault occured.
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
    int freeFrame;

    if ( table.checkPageInTable( procNum, pageNum) ) { // Check in memory

      // Handles the case of a page being already in memory.
      this.handlePageInMemory(procNum, pageNum);

      // The page is in memory. No page fault.
      return false;

    } else if ( (freeFrame = table.checkFreeFrame()) >= 0 ) {

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
  this.updateFrameTable(false, -1);

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
  int [] victimInfo = table.pickVictim();

  int victimFrame = victimInfo[0];

  // Notify the GUI that a victim was picked.
  this.notifyGUI(victimInfo);

  // Send the victim a message to update their page table.
  int [] replacementPair = table.searchVictimPair(victimFrame);
  int pid = replacementPair[0];
  int page = replacementPair[1];
  table.updatePageTable(true, pid, page, victimFrame);

  // Send a message to the replacing process to update their page table.
  table.updatePageTable(false, procNum, pageNum, victimFrame);

  // Update the frame table.
  table.updateFrameTable(victimFrame, procNum, pageNum);

  // Add the reference frame to LRU Queue as a replacement candidate.
  table.addCandidateFrame(victimFrame);

  // Update the page/frame table on the GUI.
  this.updatePageTable(procNum);
  this.updateFrameTable(true, victimFrame);

}

/***************************************************
* Notifies the GUI that a page replacement occured.
* GUI Updates who was the victim on the replacement.
* @param vic is info about the victim that was picked.
****************************************************/
public void notifyGUI( int [] vic ) {
  gui.displayVictim(vic);
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


@Override
public void actionPerformed(ActionEvent e) {

  // Get the source of event;
  JButton click = (JButton) e.getSource();

  if ( click.equals(next) ) {

    int check = this.readNext(input);
    if ( check > 0 ) { // Inputs are done, deactivate buttons
      this.updateStats();
      this.disableButtons(userActionBtns);
    }

  } else if ( click.equals(runf) ) {

    int check;
    // Keep reading until we're done with input or a page fault occurs.
    while ( ( check = this.readNext(input)) >= 0 ) {
      if (check < 0 ) {
        break;
      } else if ( check > 0 ) { // Input ended while searching for page fault.
        this.updateStats();
        this.disableButtons(userActionBtns);
        break;
      }
    }
  } else if ( click.equals(runc) ) {

    int check;
    while ( ( check = this.readNext(input)) > -2 ) {
      if ( check == 1 ){
        this.updateStats();
        this.disableButtons(userActionBtns);
        break;
      }
    }
  } else if ( click.equals(exit) ) {
    System.exit(0);
  }
}
}
