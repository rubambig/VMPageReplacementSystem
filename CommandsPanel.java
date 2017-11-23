import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
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

  /** The size of the buttons. */
  private final int bSize  = 100;

  /*******************************************************
  * Instantiates the buttons to be used for user actions.
  *******************************************************/
  public CommandsPanel () {

    super(); 

    command = new JPanel();
 
    next = new JButton("Next Input");

    runf = new JButton("Run to Fault");

    runc = new JButton("Run to Completion");

    setBtnSize();

    // Define the layout i.e. everything will be dropped into a box.
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    // Add the components.
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
}
