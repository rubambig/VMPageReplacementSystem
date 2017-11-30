import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.awt.Color;
import java.awt.GridLayout;
/*************************************************
* The panel for the user commands.
* Communicates with the GUI to set ActionListeners
* in the controller.
* Displays the LRU victim and memory reference.
* @author Gloire Rubambiza
* @since 11/22/2017
**************************************************/
public class CommandsPanel extends JPanel {

  /** Objects of the class are now serializable. */
  private static final long serialVersionUID = 1L;

  /** The font for user commands the GUI. */
  private static final Font NORMAL_FONT =
  new Font("Cooper Black", Font.BOLD, 20);

  /** The font for user commands the GUI. */
  private static final Font VICTIM_FONT =
  new Font("Cooper Black", Font.BOLD, 15);

  /** The panel for the commands. */
  private JPanel command;

  /** The buttons for handling user actions. */
  private JButton next, runf, runc, exit;

  /** The label for the memory reference. */
  private JLabel ref;

  /** The label for the LRU victim. */
  private JLabel victim;

  /** The dimensions for the grid. */
  private final int col = 1, row = 6;

  /*******************************************************
  * Instantiates the buttons to be used for user actions.
  *******************************************************/
  public CommandsPanel () {

    super();

    command = new JPanel();

    victim = new JLabel("Latest LRU Victim:");

    ref = new JLabel("Mem References");

    next = new JButton("Next Input");

    runf = new JButton("Run to Next Fault");

    runc = new JButton("Run to Completion");

    exit = new JButton("Exit");

    setStandards();

    // Define the layout to be a grid.
    setLayout(new GridLayout(row,col));

    // Add the components.
    add(victim);
    add(ref);
    add(next);
    add(runf);
    add(runc);
    add(exit);

    setVisible(true);
  }

  /*******************************************
  * Sends the action buttons to the GUI to be
  * passed to the controller.
  * @return an array of the action buttons.
  ********************************************/
  public JButton [] sendButtons () {
    JButton [] actionButtons = new JButton[4];
    actionButtons[0] = next;
    actionButtons[1] = runf;
    actionButtons[2] = runc;
    actionButtons[3] = exit;
    return actionButtons;
  }

  /**************************************
  * Sets some aesthetics for the panel.
  ***************************************/
  private void setStandards () {

    Border line = BorderFactory.createLineBorder(Color.WHITE, 2, true);
    victim.setFont(VICTIM_FONT);
    victim.setBorder(line);
    ref.setFont(NORMAL_FONT);
    next.setFont(NORMAL_FONT);
    next.setForeground(Color.GREEN);
    runf.setFont(NORMAL_FONT);
    runf.setForeground(Color.ORANGE);
    runc.setFont(NORMAL_FONT);
    exit.setFont(NORMAL_FONT);
    exit.setForeground(Color.RED);
  }

  /*******************************************
  * Updates the latest LRU victim.
  * @param vic is info about the victim that was picked.
  ********************************************/
  public void setVictim( int [] vic ) {
    int frame = vic[0];
    int pid = vic[1];
    int page = vic[2];
    victim.setText("LRU Victim:" +  " P" + pid + " Page " + page );
    victim.setForeground(Color.RED);
  }

  /**************************************************
  * Resets the label for the memory reference
  * based on the input.
  * @param pid is the process that made a reference.
  * @param page is the page that was referenced.
  **************************************************/
  public void setReference ( int pid, int page ) {
    ref.setText("P" + pid + " referenced page " + page);
  }
}
