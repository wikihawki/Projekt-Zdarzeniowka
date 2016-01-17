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
	private JFrame menuframe;
	private JFrame gameframe;
	public MunchkinGUI()
	{
		
	}
	
	
	
	
	public Container createGUI (JFrame mainApp)
	{
		JPanel panRoot = new JPanel(new BorderLayout());
		gameframe=mainApp;
	    mainWindow = new MunchkinWindow(this);
		mainWindow.setSize(new Dimension(970, 700));
		mainWindow.setBackground(new Color(231, 218, 167));
		JLabel lab1 = new JLabel("User Name", JLabel.CENTER);
	//	panRoot.setLayout(new FlowLayout()); 

	   
		
		panRoot.add(mainWindow, BorderLayout.CENTER);
		
		return panRoot;
		
	}
	
	
	public Container createMainGUI (JFrame mainApp)
	{
		JPanel panRoot = new JPanel(new BorderLayout());
		gameframe=mainApp;
		MunchkinWindow mainWindow2 = new MunchkinWindow(this);
		mainWindow2.setSize(new Dimension(970, 700));
		mainWindow2.setBackground(new Color(231, 218, 167));
		JLabel lab1 = new JLabel("User Name", JLabel.CENTER);
	//	panRoot.setLayout(new FlowLayout()); 

	   
		
		panRoot.add(mainWindow2, BorderLayout.CENTER);
		
		return panRoot;
		
	}
	

	
	public Container createMenuGui (JFrame mainApp)
	{
		JPanel panRoot = new JPanel(new BorderLayout());
		menuframe=mainApp;
		mainMenu = new MunchkinMenu(this);
		mainMenu.setSize(new Dimension(645,600));
		mainMenu.setBackground(new Color(255, 214, 158));

		JLabel lab1 = new JLabel("User Name", JLabel.CENTER);
	//	panRoot.setLayout(new FlowLayout()); 

	   
		
		panRoot.add(mainMenu, BorderLayout.CENTER);
		
		return panRoot;
		
	}
	
	public void StartNewGame(ArrayList<String> listaGraczy)
	{
		
		menuframe.setVisible(false);
		gameframe.setVisible(true);
		mainWindow.StartNewGame(listaGraczy);
	}
	
	


}
