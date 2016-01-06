import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;


public class MunchkinGUI {
	private MunchkinWindow mainWindow;
	private MediaTracker mt;
	private String[] strCardBack = {"Sonic","BelgiumFlag","DutchMouse","FindingNemo","UKFlag","VanessaMae"}; 
	public void MunchkinGUI()
	{
		
	}
	
	
	
	
	public Container createGUI (JFrame mainApp)
	{
		JPanel panRoot = new JPanel(new BorderLayout());
		
		mainWindow = new MunchkinWindow();
		mainWindow.setSize(new Dimension(970, 700));
		mainWindow.setBackground(new Color(231, 218, 167));
		
		mt = new MediaTracker(mainApp);
	
		
		panRoot.add(mainWindow, BorderLayout.CENTER);
		
		return panRoot;
		
	}
	
	
	

}
