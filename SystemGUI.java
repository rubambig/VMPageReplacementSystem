import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Color;
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
    
    /**
     * TO-DO
     * 
     */
    
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
    memory.setBackground(Color.BLUE);
    pageTable.setBackground(LAKER_BLUE);
    stats.setBackground(Color.RED);
    commands.setBackground(Color.GREEN);
   }

   /***************************************
   * Passes the page table to be displayed.
   * @param pTable is the page table 
   ****************************************/
   public void displayTable( Hashtable<Integer, Integer> pTable, int pid) { 
     pageTable.redrawTable(pTable, pid);
   }
    
}
