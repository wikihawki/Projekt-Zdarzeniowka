import java.awt.*;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
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
		
		  UIManager.put("OptionPane.background",new ColorUIResource(255, 214, 158));
		  UIManager.put("Panel.background",new ColorUIResource(255, 214, 158));

		}
		catch(Exception e){}// if it fails, your program might look ugly but still work
		
		
		
        JFrame frame = new JFrame("Munchkin"); 
        JFrame MenuFrame = new JFrame("Munchkin Menu"); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MunchkinGUI munchkin;  munchkin = new MunchkinGUI();
        
        
        frame.add(munchkin.createGUI(frame));
      //  frame.setContentPane(munchkin.createMainGUI(frame));
       
        frame.setSize(970,730);
        frame.setResizable(false);
        frame.setVisible(false);  
        frame.pack(); 
        MenuFrame.setContentPane(munchkin.createMenuGui(MenuFrame));
        //MenuFrame.add(munchkin.createMenuGui(MenuFrame));
        MenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MenuFrame.setSize(645,600);
        MenuFrame.setResizable(false);
        MenuFrame.setVisible(true);  
        MenuFrame.pack(); 
       
     
        
	}
	
	

	

}
