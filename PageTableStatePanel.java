import java.util.Hashtable;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
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

  /** Objects of the class are now serializable. */
  private static final long serialVersionUID = 1L;

  /** The font for most text in the GUI. */
	private static final Font NORMAL_FONT =
			new Font("Cooper Black", Font.PLAIN, 10);

  /** The font for most text in the GUI. */
  private static final Font HEADER_FONT =
    	new Font("Cooper Black", Font.PLAIN, 20);

  /** The GV blue color. */
  private static final java.awt.Color LAKER_BLUE =
      new java.awt.Color(0, 101, 164);

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
    pidLabel = new JLabel("Process Table: P", SwingConstants.CENTER);
    pairs = new JLabel[max];

    // Add and customize the components
    add(pidLabel);
    pidLabel.setFont(HEADER_FONT);
    pidLabel.setBackground(LAKER_BLUE);
    for ( i = 0 ; i < max ; i++ ) {
      pairs[i] = new JLabel("", SwingConstants.CENTER);
      setStandards(pairs[i]);
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
    pidLabel.setText("Process Table: P" + pid );

    // Reset the text
    resetText();

    // Build the table based on size
    int counter = 0;
    for ( Integer key : table.keySet() ) {
      String onePair = "Page " + key + " -----> " + "Frame " + table.get(key);
      pairs[counter].setText(onePair);
      counter++;
    }
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

  /********************************************
  * Sets some aesthetics for the panel.
  * @param field is the label to be customized
  ********************************************/
  private void setStandards ( JLabel field) {
    field.setFont(NORMAL_FONT);
  }
}
