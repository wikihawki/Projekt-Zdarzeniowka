import java.awt.*;
import javax.swing.JFrame;
public class Munchkin extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main (String[] args)
	{
		JFrame.setDefaultLookAndFeelDecorated(true); //Make it look nice
        JFrame frame = new JFrame("Munchkin"); //Title
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       MunchkinGUI munchkin = new MunchkinGUI();
       frame.setContentPane(munchkin.createGUI(frame));
        
        
        frame.setSize(970,700);
        frame.setResizable(false);
        frame.setVisible(true);  
        frame.pack();       
	}

}
