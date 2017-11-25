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

  /** The buttons for handling user actions. */
  private JButton next, runf, runc;

  /** The array of action buttons. */
  private JButton [] userActionBtns;

  /** The size of the buttons. */
  private final int bSize  = 30;

  /*************************************************
  * Instantiates the tables, GUI, and buttons to be
  * used for user actions.
  *************************************************/
  public Controller (SystemGUI pGUI, Tables pTable ) {

    gui = pGUI;
    table = pTable;
    userActionBtns = gui.sendButtons();
    next = userActionBtns[0];
    runf = userActionBtns[1];
    runc = userActionBtns[2];
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
  public void updateReference (int pid, String ref) {
    gui.displayReference(pid, ref);
  }

  /*******************************************
  * Instructs the GUI to display final stats.
  * @param pcbArray is the array of PCBs.
  *******************************************/
  public void updateStats () {
    gui.displayStats(table.passPCBArray());
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if ( e.getSource() instanceof JButton && e.getSource() == next ) {

    } else if ( e.getSource() instanceof JButton && e.getSource() == runf) {

    } else if ( e.getSource() instanceof JButton && e.getSource() == runc ) {

    }
  }
}
