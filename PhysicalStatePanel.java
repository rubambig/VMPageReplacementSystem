import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.BorderLayout;

/*************************************************
* The panel for the current physical memory state.
* Displays the current state of the frame table.
* Notifies the user whether the page is in memory
* or when a page fault occurs.
* @author Gloire Rubambiza
* @since 11/20/2017
**************************************************/

public class PhysicalStatePanel extends JPanel {

  /** Objects of the class are now serializable. */
	private static final long serialVersionUID = 1L;

  /** A panel for the current physical state. */
  private JPanel physicalStatePanel;

  /** A table of PID/page pairs. */
  private JLabel [] pairs;

  /** The label for the frame table **/
  private JLabel pageState;

  /** The number of rows in the frame table. */
  private int rowMax = 16;

  /** The font for most text in the GUI. */
	private static final Font NORMAL_FONT =
			new Font("Cooper Black", Font.PLAIN, 15);

  /** The font for most text in the GUI. */
  private static final Font HEADER_FONT =
    	new Font("Cooper Black", Font.PLAIN, 20);

  /** Creates a border constant. */
  private static final Border CREATE_EMPTY_BORDER =
    	BorderFactory.createEmptyBorder(3, 3, 3, 3);

  /** The GV blue color. */
  private static final java.awt.Color LAKER_BLUE =
      new java.awt.Color(0, 101, 164);

  /***************************************
  * Instantiates the physical state panel.
  ****************************************/
  public PhysicalStatePanel () {

    super();

    // Create the objects
    physicalStatePanel = new JPanel();
    pageState = new JLabel("<html>Physical Memory State<br>", SwingConstants.CENTER);

    // Make it pretty
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

  /**
  * TO-DO
  * - Repaint the frame table when new changes occur
  * - Take the frame table w/o calling the frametable class directly
  /***********************************************
  * Repaints the frame table as new changes occur.
  * @param table is the frame table.
  ************************************************/
  public void redrawTable ( Frame[] table ) {

    // Repaint the frame table
    int i;
    for ( i = 0; i < rowMax; i++ ) {
      int pid = table[i].getPID();
      int page = table[i].getPage();
      String display = "Frame " + i + " P" + pid + "  Page " + page;
      pairs[i].setText(display);
    }

    revalidate();
    repaint();
  }

  /********************************************
  * Sets some aesthetics for the panel.
  * @param field is the label to be customized
  ********************************************/
  private void setStandards ( JLabel field) {
    field.setFont(NORMAL_FONT);
    field.setBorder(CREATE_EMPTY_BORDER);
  }
}
