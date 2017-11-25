import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
/*********************************************
* The panel for the final statistics after an
* input run.
* Displays the stats for each process.
* @author Gloire Rubambiza
* @since 11/22/2017
*********************************************/
public class FinalStatsPanel extends JPanel {

  /** The number of total processes to be displayed. */
  private int max = 10;

  /** The table header. */
  private JLabel header;

  /** The final statistics panel. */
  private JPanel statPanel;

  /** The array of pid/references/faults triplets. */
  private static JLabel [] pairs;

  /** The final statistics label. */
  private JLabel statLabel;

  public FinalStatsPanel () {
    super();

    // Create the components
    header = new JLabel("Proc   Refs   Faults", SwingConstants.CENTER);
    statPanel = new JPanel();
    pairs = new JLabel[max];
    statLabel = new JLabel("Final Statistics", SwingConstants.CENTER);

    // Define the layout i.e. everything will be dropped into a box.
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    // Add the components.
    add(statLabel);
    add(header);
    int i;
    for ( i = 0; i < max; i++ ) {
      pairs[i] = new JLabel("Stats will be displayed here", SwingConstants.CENTER);
      add(pairs[i]);
    }

    setVisible(true);

  }

  /******************************************
  * Displays the final stats for the system.
  * @param pcbtable is the array of PCBs.
  ******************************************/
  public void displayFinalStats ( PCB [] pcbtable ) {

    int i;
    String display = "";
    for ( i = 0; i < max; i++ ) {
      int pid = pcbtable[i].getPID();
      int totalRefs = pcbtable[i].getTotalReferences();
      int totalFaults = pcbtable[i].getTotalPageFaults();
      display = pid + "    |    " + totalRefs + "    |    " + totalFaults;
      pairs[i].setText(display);
    }
  }
}
