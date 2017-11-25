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

  /** The label for the page table. **/
  private static JLabel pidLabel;

  /** The max size of a page table. **/
  private final static int max  = 64;

  /***************************************
  * Instantiates the page table state panel.
  ****************************************/
  public PageTableStatePanel () {

    super();

    // Create the main components
    int i;
    pageTableStatePanel = new JPanel();
    pidLabel = new JLabel("Process Table: P ", SwingConstants.CENTER);
    pairs = new JLabel[max];

    // Add the components
    add(pidLabel);
    for ( i = 0 ; i < max ; i++ ) {
      pairs[i] = new JLabel("", SwingConstants.CENTER);
      add(pairs[i]);
    }


    // Define the layout i.e. everything will be dropped into a box.
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    setVisible(true);
  }

  /****************************************************
  * Builds a page table based on the size of the table.
  * @param table is the page table for the process.
  * @param pid is the PID of the process.
  ****************************************************/
  public void redrawTable ( Hashtable<Integer,Integer> table, int pid ) {

    // Relabel the page table
    pidLabel.setText("Process Table: P" + pid + "\n");

    // Reset the text
    resetText();

    // Build the table based on size
    int counter = 0;
    for ( Integer key : table.keySet() ) {
      String onePair = "Page " + key + " -----> " + "Frame " + table.get(key);
      pairs[counter].setText(onePair);
      counter++;
    }

    //pageTableStatePanel.revalidate();
    //pageTableStatePanel.repaint();
  }

  /****************************************************
  * Resets the text in all the labels of the table.
  * This ensures any changes made by a page table that
  * was bigger than the current one are not kept on the
  * panel.
  ****************************************************/
  private void resetText () {
    int i;
    for ( i = 0; i < max; i++ ) {
      pairs[i].setText("");
    }
  }

  /**
  * TO-DO
  * Needs access to the page table of the given process
  */

}
