import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;


public class MunchkinGUI {
	private MunchkinWindow mainWindow;
	private MunchkinMenu mainMenu;
	private MediaTracker mt;
	public MunchkinGUI()
	{
		
	}
	
	
	
	
	public Container createGUI (JFrame mainApp)
	{
		JPanel panRoot = new JPanel(new BorderLayout());
		
		mainWindow = new MunchkinWindow(this);
		mainWindow.setSize(new Dimension(970, 700));
		mainWindow.setBackground(new Color(231, 218, 167));
		
		mt = new MediaTracker(mainApp);
		JLabel lab1 = new JLabel("User Name", JLabel.CENTER);
	//	panRoot.setLayout(new FlowLayout()); 

	   
		
		panRoot.add(mainWindow, BorderLayout.CENTER);
		
		return panRoot;
		
	}
	
	
	public Container createMenuGui (JFrame mainApp)
	{
		JPanel panRoot = new JPanel(new BorderLayout());
		
		mainMenu = new MunchkinMenu(this);
		mainMenu.setSize(new Dimension(645,600));
		mainMenu.setBackground(new Color(255, 214, 158));
		
		mt = new MediaTracker(mainApp);
		JLabel lab1 = new JLabel("User Name", JLabel.CENTER);
	//	panRoot.setLayout(new FlowLayout()); 

	   
		
		panRoot.add(mainMenu, BorderLayout.CENTER);
		
		return panRoot;
		
	}
	
	public void StartNewGame(ArrayList<String> listaGraczy)
	{
		mainWindow.StartNewGame(listaGraczy);
	}
	
	


}
