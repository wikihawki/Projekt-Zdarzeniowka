import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;

import com.sun.glass.ui.Window.Level;
import com.sun.istack.internal.logging.Logger;


public class MunchkinGUI {
	private MunchkinWindow mainWindow;
	private MediaTracker mt;
	private String[] strCardBack = {"Sonic","BelgiumFlag","DutchMouse","FindingNemo","UKFlag","VanessaMae"}; 
	
	
	
	
	
	
	public void MunchkinGUI()
	{
		JFrame frame = new JFrame("LayeredPaneDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = (JComponent) dodajPlansze(frame);
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
	}
	
	
	
	
	
	
	public Container dodajPlansze (JFrame mainApp)
	{
		JPanel panRoot = new JPanel(new BorderLayout());
		
		mainWindow = new MunchkinWindow(this);
		mainWindow.setSize(new Dimension(970, 700));
		mainWindow.setBackground(new Color(231, 218, 167));
		
		mt = new MediaTracker(mainApp);
	
		
		panRoot.add(mainWindow, BorderLayout.CENTER);
		
		return panRoot;
	
	}
}
