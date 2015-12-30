import java.awt.*;
import javax.swing.JFrame;
public class Munchkin {
	public void main (String[] args)
	{
		JFrame.setDefaultLookAndFeelDecorated(true); //Make it look nice
        JFrame frame = new JFrame("Munchkin"); //Title
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
       // spiderSolitareGUI spiderWindow = new spiderSolitareGUI();
       // frame.setContentPane(spiderWindow.createGUI(frame));
        
        frame.setSize(970,700);
        frame.setResizable(false);
        frame.setVisible(true);  
        frame.pack();       
	}

}
