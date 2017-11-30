import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

import java.awt.Color;
import java.awt.Font;
import java.awt.BorderLayout;
/*************************************************
* The panel for the current physical memory state.
* Displays the current state of the frame table.
* @author Gloire Rubambiza
* @since 11/20/2017
**************************************************/
public class PhysicalStatePanel extends JPanel {

  /** Objects of the class are now serializable. */
  private static final long serialVersionUID = 1L;

  /** A panel for the current physical state. */
  private JPanel physicalStatePanel;

  /** An array of PID/page pairs. */
  private JLabel [] pairs;

  /** The label for the frame table **/
  private JLabel pageState;

  /** The number of rows in the frame table. */
  private int rowMax = 16;

  /** The font for most text in the GUI. */
  private static final Font NORMAL_FONT =
  new Font("Cooper Black", Font.PLAIN, 14);

  /** The font for most text in the GUI. */
  private static final Font HEADER_FONT =
  new Font("Cooper Black", Font.BOLD, 20);

  /** Creates a border constant. */
  private static final Border CREATE_EMPTY_BORDER =
  BorderFactory.createEmptyBorder(3, 3, 3, 3);

  /** The GV blue color. */
  private static final java.awt.Color LAKER_BLUE =
  new java.awt.Color(0, 101, 164);

  /** LIGHT_GOLD color. */
  private static final java.awt.Color LIGHT_GOLD =
  new java.awt.Color(255, 153, 51);

  /** LIGHT_GOLD color. */
  private static final java.awt.Color CAMO_GREEN =
  new java.awt.Color(153, 153, 0);

  /***************************************
  * Instantiates the physical state panel.
  ****************************************/
  public PhysicalStatePanel () {

    super();

    // Create the objects.
    physicalStatePanel = new JPanel();
    String title = "<html>Physical Memory State<br>";
    pageState = new JLabel(title, SwingConstants.CENTER);

    // Set the layout to drop everything into a box.
    setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

    // Customize the header.
    add(pageState);
    pageState.setBackground(LAKER_BLUE);
    pageState.setFont(HEADER_FONT);

    pairs = new JLabel[rowMax];
    int i;
    for ( i = 0; i < rowMax; i++ ) {
      pairs[i] = new JLabel("", SwingConstants.CENTER);
      setStandards(pairs[i]);
      add(pairs[i]);
    }

    setVisible(true);
  }

  /***********************************************
  * Repaints the frame table as new changes occur.
  * @param table is the frame table.
  * @param fault is the kind of fault.
  * @param frame is frame that potentiall needs to be
  * colored for the user.
  ************************************************/
  public void redrawTable ( Frame[] table, boolean fault, int frame ) {

    // Repaint the frame table
    resetFont(pairs);
    int i;
    for ( i = 0; i < rowMax; i++ ) {
      int pid = table[i].getPID();
      int page = table[i].getPage();
      String display = "Frame " + i + " P" + pid + "  Page " + page;
      pairs[i].setText(display);

      //Set the color based on the process number.
      pairs[i].setForeground( customizeFont(pid) );
    }
    if ( fault ) { // A hard fault occured
      pairs[frame].setForeground(Color.RED);
    }
  }

  /********************************************
  * Sets some aesthetics for the panel.
  * @param field is the label to be customized
  ********************************************/
  private void setStandards ( JLabel field) {
    field.setFont(NORMAL_FONT);
    field.setBorder(CREATE_EMPTY_BORDER);
  }

  /*******************************************
  * Resets the font all the frames.
  * @param fArray is the array of frame labels.
  ********************************************/
  private void resetFont ( JLabel [] fArray ) {
    int i;
    for ( i = 0; i < rowMax; i++ ) {
      fArray[i].setForeground(Color.BLACK);
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
