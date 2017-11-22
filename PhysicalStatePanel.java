import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;

import java.awt.Color;
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

  /** A panel for the current physical state. */
  private JPanel physicalStatePanel;

  /** A table of PID/page pairs. */
  private JLabel [] pairs;

  /** The label for the frame table **/
  private JLabel pageState;

  /** The number of rows in the frame table. */
  private int rowMax = 16;

  /***************************************
  * Instantiates the physical state panel.
  ****************************************/
  public PhysicalStatePanel () {

    super();

    // Create the objects
    physicalStatePanel = new JPanel();
    pageState = new JLabel("Physical Memory State", SwingConstants.CENTER);

    // Make it pretty
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    // Add the components
    add(physicalStatePanel);
    pairs = new JLabel[rowMax];
    int i;
    for ( i = 0; i < rowMax; i++ ) {
      pairs[i] = new JLabel("XXXXX", SwingConstants.CENTER);
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
    String display = "";
    for ( i = 0; i < rowMax; i++ ) {
      int pid = table[i].getPID();
      int page = table[i].getPage();
      display = "Frame " + i + "\tP " + pid + "\tPage " + page;
      pairs[i].setText(display);
    }
   
    revalidate();
    repaint();
  }
  /**
  * TO-DO
  * Needs access to the array of frames
  */
}
