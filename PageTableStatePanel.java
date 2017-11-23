import java.util.Hashtable;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
/*************************************************
* The panel for the page table of a process.
* Displays the current state of the page table.
* Dynamically repaints itself based on the size
* of the process' page table. 
* @author Gloire Rubambiza
* @since 11/22/2017
**************************************************/
public class PageTableStatePanel extends JPanel {

  /** The panel for the current page table state. */
  private JPanel pageTableStatePanel;

  /** The array  of page/frame pairs. */
  private JLabel [] pairs;

  /** The label for the page table **/
  private static JLabel pidLabel;

  /***************************************
  * Instantiates the page table state panel.
  ****************************************/
  public PageTableStatePanel () {

    super();

    // Create the main components
    pageTableStatePanel = new JPanel();
    pidLabel = new JLabel("Process table for process: ", SwingConstants.CENTER);

    // Define the layout i.e. everything will be dropped into a box.
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    // Add the components
    add(pidLabel);

    setVisible(true);
  }
 
  /****************************************************
  * Builds a page table based on the size of the table.
  * @param table is the page table for the process.
  * @param pid is the PID of the process. 
  ****************************************************/
  public void redrawTable ( Hashtable<Integer,Integer> table, int pid ) { 
    
    // Relabel the page table
    pidLabel.setText("Process table for process: " + pid);

    // Build the table based on size
    int counter = 0, size = table.size();
    pairs = new JLabel[size];
    for ( Integer key : table.keySet() ) {
      String onePair = "Page: " + key + " -> " + "Frame: " + table.get(key) + "PID: " + pid;
      pairs[counter] = new JLabel(onePair, SwingConstants.CENTER);
      add(pairs[counter]);
      counter++;
    }
    
    revalidate();
    repaint();
  }

  /**
  * TO-DO
  * Needs access to the page table of the given process
  */

}
