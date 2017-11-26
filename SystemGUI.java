import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Hashtable;
/***************************************************
* The main frame for the state of the entire system.
* Provides a centralized view of the system.
* Communicates with the controller to display the
* VM Replacement System as it current stands.
* @author Gloire Rubambiza
* @since 11/22/2017
**************************************************/
public class SystemGUI extends JFrame {

  /** Objects of the class are now serializable. */
	private static final long serialVersionUID = 1L;

  /** Panel for the frame table */
  private PhysicalStatePanel memory;

  /** Panel for the page table */
  private PageTableStatePanel pageTable;

  /** Panel for the commands. */
  private CommandsPanel commands;

  /** The array of action buttons. */
  private JButton [] userActionBtns;

  /** Panel for the statistics. */
  private FinalStatsPanel stats;

  /** The GV blue color. */
  private static final java.awt.Color LAKER_BLUE =
  new java.awt.Color(0, 101, 164);

  /** The dimension for the grid. */
  private final int dim = 2;

  /*****************************************************
  * Instantiates all the panels representing the system.
  * @param filename is the input file
  *****************************************************/
  public SystemGUI(String filename) {

    super();

    memory = new PhysicalStatePanel();

    pageTable = new PageTableStatePanel();

    stats = new FinalStatsPanel();

    commands = new CommandsPanel();

    setTitle("VM Replacement System, input file: " + filename);

    setSize(600, 900);

    setLayout(new GridLayout(dim,dim));

    add(memory);
    add(pageTable);
    add(commands);
    add(stats);


    setBoundaries ();

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }

  /**********************************************
  * Retrieves the action buttons and passes them
  * to the controller to add listeners.
  * @param
  **********************************************/
  public JButton [] sendButtons () {
    userActionBtns = commands.sendButtons();
    return userActionBtns;
  }

  /*********************************************
  * Makes the background more visible for
  * ease of customization.
  *********************************************/
  private void setBoundaries () {
    memory.setBackground(LAKER_BLUE);
    //pageTable.setBackground(LAKER_BLUE);
    stats.setBackground(LAKER_BLUE);
    //commands.setBackground(Color.GREEN);
  }

  /***************************************
  * Passes the page table to be displayed.
  * @param pTable is the page table
  ****************************************/
  public void displayPageTable( Hashtable<Integer, Integer> pTable, int pid) {
    pageTable.redrawTable(pTable, pid);
  }

  /*****************************************************
  * Gets a reference to the frame table i.e. current
  * physical state of memory.
  * Passes the frame table to the physical state panel.
  * @return an array of frames.
  *****************************************************/
  public void displayFrameTable (Frame [] fTable) {
    memory.redrawTable(fTable);
  }

  /**************************************************
  * Passes the memory reference to the commands panel.
  * @param pid is the process that made a reference.
  * @param ref is the page that was referenced.
  **************************************************/
  public void displayReference ( int pid, int ref ) {
    commands.setReference(pid, ref);
  }

  /*********************************************
  * Passes the array of PCBs to the stats panel.
  * @param pcbArray is the array of PCBs.
  *********************************************/
  public void displayStats ( PCB [] pcbArray ) {
    stats.displayFinalStats(pcbArray);
  }

}
