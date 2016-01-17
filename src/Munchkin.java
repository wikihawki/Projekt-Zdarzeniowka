import java.awt.*;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
public class Munchkin extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
   private static MunchkinGUI munchkin; 
   private static  JFrame frame = new JFrame("Munchkin"); 
	public static void main (String[] args)
	{
		try
		{
		  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		
		  UIManager.put("OptionPane.background",new ColorUIResource(255, 214, 158));
		  UIManager.put("Panel.background",new ColorUIResource(255, 214, 158));

		}
		catch(Exception e){}// if it fails, your program might look ugly but still work
       
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         munchkin = new MunchkinGUI();
        frame.setContentPane(munchkin.createGUI(frame));
        frame.add(munchkin.createGUI(frame));
        frame.setSize(970,730);
        frame.setResizable(false);
        frame.setVisible(false);  
        frame.pack(); 
        JFrame MenuFrame = new JFrame("Munchkin Menu"); 
        MunchkinGUI munchkinMenu;  munchkinMenu = new MunchkinGUI();
        MenuFrame.setContentPane(munchkin.createMenuGui(frame));
        MenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MenuFrame.setSize(645,600);
        MenuFrame.setResizable(false);
        MenuFrame.setVisible(true);  
        MenuFrame.pack(); 
        
	}
	
	public void StartNewGame(ArrayList<String> listaGraczy)
	{
		munchkin.StartNewGame(listaGraczy);
		
	}

	

}
