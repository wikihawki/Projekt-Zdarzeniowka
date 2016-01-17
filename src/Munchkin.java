import java.awt.*;
import javax.swing.JFrame;
import javax.swing.UIManager;
public class Munchkin extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main (String[] args)
	{
		try
		{
		  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception e){}// if it fails, your program might look ugly but still work
        JFrame frame = new JFrame("Munchkin"); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MunchkinGUI munchkin;  munchkin = new MunchkinGUI();
        frame.setContentPane(munchkin.createGUI(frame));
        frame.add(munchkin.createGUI(frame));
        frame.setSize(970,730);
        frame.setResizable(false);
        frame.setVisible(false);  
        frame.pack(); 
        JFrame MenuFrame = new JFrame("Munchkin Menu"); 
        MunchkinGUI munchkinMenu;  munchkinMenu = new MunchkinGUI();
        MenuFrame.setContentPane(munchkin.createMenuGui(frame));
        MenuFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        MenuFrame.setSize(645,600);
        MenuFrame.setResizable(false);
        MenuFrame.setVisible(true);  
        MenuFrame.pack(); 
        
	}
	
	

	

}
