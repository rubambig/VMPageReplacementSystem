import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
/*************************************************
* The panel for the user commands.
* Communicates with the GUI to set ActionListeners
* in the controller. 
* @author Gloire Rubambiza
* @since 11/22/2017
**************************************************/
public class CommandsPanel extends JPanel {

  /** The panel for the commands. */
  private JPanel command;

  /** The buttons for handling user actions. */
  private JButton next, runf, runc;

  /** The label for the memory reference. */
  private JLabel ref;

  /** The size of the buttons. */
  private final int bSize  = 50;

  /** The dimensions for the grid. */
  private final int col = 1, row = 4;

  /*******************************************************
  * Instantiates the buttons to be used for user actions.
  *******************************************************/
  public CommandsPanel () {

    super(); 

    command = new JPanel();
 
    next = new JButton("Next Input");

    runf = new JButton("Run to Next Page Fault");

    runc = new JButton("Run to Completion");

    ref = new JLabel("Mem References will appear here");

    setBtnSize();

    // Define the layout to be a grid. 
    setLayout(new GridLayout(row,col));

    // Add the components.
    add(ref);
    add(next);
    add(runf);
    add(runc);

    setVisible(true);
    
  }
  
  /*******************************************
  * Sends the action buttons to the GUI to be 
  * passed to the controller.
  * @return an array of the action buttons. 
  ********************************************/
  public JButton [] sendButtons () {
    JButton [] actionButtons = new JButton[3];
    actionButtons[0] = next;
    actionButtons[1] = runf;
    actionButtons[2] = runc;
    return actionButtons;
  }

  /********************************
  * Sets the size for the buttons.
  ********************************/
  private void setBtnSize () {
   next.setSize(bSize, bSize);
   runf.setSize(bSize, bSize);
   runc.setSize(bSize, bSize);
  }

  /**************************************************
  * Resets the label for the memory reference
  * based on the input.
  * @param pid is the process that made a reference. 
  * @param page is the page that was referenced.
  **************************************************/
  public void setReference ( int pid, String page ) { 
    ref.setText("P" + pid + " referenced page " + page);
  }
}
