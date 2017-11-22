import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.BorderLayout;
/***************************************************
* The main frame for the state of the entire system.
* Provides a centralized view of the system. 
* Communicates with the controller to display the 
* VM Replacement System as it current stands. 
* @author Gloire Rubambiza
* @since 11/22/2017
**************************************************/
public class SystemGUI extends JFrame {
    
    /**
     * TO-DO
     * 
     */
    
    /** Panel for the frame table */
    private PhysicalStatePanel memory;
    
    /** Panel for the page table */
    private PageTableStatePanel pagetable;
    
    /** Panel for the commands. */
    
    /** Panel for the statistics. */
    private FinalStatsPanel stats;
     
    public SystemGUI(String filename) {
        
        super();
        
        memory = new PhysicalStatePanel();
        
        pagetable = new PageTableStatePanel();

        stats = new FinalStatsPanel();
        
        setTitle("VM Replacement System, input file: " + filename);
        
        setSize(1000, 1000);
        
        setLayout(new BorderLayout());
        
        add(memory, BorderLayout.CENTER);
        add(pagetable, BorderLayout.EAST);
        add(stats, BorderLayout.SOUTH);
        //add(clientList, BorderLayout.WEST);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);  
    }  
    
}
