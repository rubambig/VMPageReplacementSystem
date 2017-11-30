import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;

import java.awt.Font;
import java.awt.Color;
/*********************************************
* The panel for the final statistics after an
* input run.
* Displays the stats for each process.
* @author Gloire Rubambiza
* @since 11/22/2017
*********************************************/
public class FinalStatsPanel extends JPanel {

  /** Objects of the class are now serializable. */
	private static final long serialVersionUID = 1L;

  /** The font for the stats. */
	private static final Font NORMAL_FONT =
			new Font("Cooper Black", Font.PLAIN, 18);

  /** The font for the header. */
  private static final Font HEADER_FONT =
    	new Font("Cooper Black", Font.BOLD, 20);

  /** LIGHT_GOLD color. */
  private static final java.awt.Color LIGHT_GOLD =
    new java.awt.Color(255, 153, 51);

  /** LIGHT_GOLD color. */
  private static final java.awt.Color CAMO_GREEN =
  new java.awt.Color(153, 153, 0);

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
    header = new JLabel("Proc |  Refs  |  Faults", SwingConstants.CENTER);
    statPanel = new JPanel();
    pairs = new JLabel[max];
    statLabel = new JLabel("<html>Final Statistics<br>", SwingConstants.CENTER);

    // Define the layout i.e. everything will be dropped into a box.
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    // Customize the panel title.
    add(statLabel);
    statLabel.setFont(HEADER_FONT);
    add(header);
    setStandards(header);

    int i;
    for ( i = 0; i < max; i++ ) {
      pairs[i] = new JLabel("", SwingConstants.CENTER);
      setStandards(pairs[i]);
      add(pairs[i]);
    }

    setVisible(true);
  }

  /********************************************
  * Sets some aesthetics for the panel.
  * @param field is the label to be customized
  ********************************************/
  private void setStandards ( JLabel field) {
    field.setFont(NORMAL_FONT);
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
      //Set the color based on the process number.
      pairs[i].setForeground( customizeFont(pid) );
    }
  }

  /**************************************
  * Customizes the font of the label based
  * on the process number.
  * @param pid is the process id.
  ***************************************/
  private Color customizeFont( int pid ) {
    Color myColor = Color.BLACK;
    switch ( pid ) {
      case 1: myColor = LIGHT_GOLD;
              break;
      case 2: myColor = Color.BLUE;
              break;
      case 3: myColor = Color.CYAN;
              break;
      case 4: myColor = CAMO_GREEN;
              break;
      case 5: myColor = Color.GREEN;
              break;
      case 6: myColor = Color.MAGENTA;
              break;
      case 7: myColor = Color.ORANGE;
              break;
      case 8: myColor = Color.PINK;
              break;
      case 9: myColor = Color.YELLOW;
              break;
      default: myColor = Color.BLACK;
              break;
    }
    return myColor;
  }
}
