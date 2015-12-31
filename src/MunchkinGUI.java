import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;


public class MunchkinGUI {

	private objCreateAppletImage createImage = new objCreateAppletImage();
	private MunchkinWindow mainWindow;
	//private MediaTracker mt;
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
		
		//mt = new MediaTracker(mainApp);
		importPictures();
		
		panRoot.add(mainWindow, BorderLayout.CENTER);
		
		return panRoot;
		
	}
	
	
	private void importPictures ()
	{
		
		String colour = "";
		Image[][] imgCards = new Image[4][13];
		Image[] imgCardBack = new Image[6]; //Array to store all the card back images
				
		for (int suit = 0; suit < 4; suit++) //Loop 4 times (for each suit)
		{
			
			switch (suit) //Inspect current suit number
			{
				
				case 0:	colour = "";
						break; //Have to put break to stop it executing the other statements
				case 1: colour = "";
						break;
				case 2: colour = "";
						break;
				case 3: colour = "";
						break;
						
			}
			
			for (int rank = 0; rank < 13; rank++) //Loop 13 times (for ace - king)
			{
				
				//title = colour + Integer.toString(rank + 1); //Current title is the current suit + the rank number + 1
				imgCards[suit][rank] = createImage.getImage(this, "images/ks (1).jpg", 2000000);
				//System.out.print("\nGot card");
			//	mt.addImage(imgCards[suit][rank], 0);
				
			}
			
		}

		for (int card = 0; card < 6; card++) //Loop the number of card back images being supplied
		{
			
			try {
				imgCardBack[card] =  ImageIO.read(new File("C:/Users/Wiktor/Desktop/Java projekty/Projekt-Zdarzeniowka/src/images/ks (1).jpg"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			imgCardBack[card].getScaledInstance(71, 96, Image.SCALE_DEFAULT);
			//mt.addImage(imgCardBack[card], 0);
			
		}
	/*	
		try
		{
			mt.waitForID(0);
		}
		catch (InterruptedException e)
		{
		}
		*/
		imgCardBack[1].getScaledInstance(71, 96, Image.SCALE_SMOOTH);
		mainWindow.setCardBackImage(imgCardBack[1]);
		mainWindow.newGame();

	}
	
}
