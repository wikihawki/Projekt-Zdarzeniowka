import java.awt.*;
import javax.swing.*;


public class MunchkinGUI {

	private objCreateAppletImage createImage = new objCreateAppletImage();
	private MunchkinWindow mainWindow;
	private MediaTracker mt;
	private String[] strCardBack = {"Sonic","BelgiumFlag","DutchMouse","FindingNemo","UKFlag","VanessaMae"};
	public Container createGUI (JFrame mainApp)
	{
		
		JPanel panRoot = new JPanel(new BorderLayout());
		
		mainWindow = new MunchkinWindow();
		mainWindow.setSize(new Dimension(970, 700));
		mainWindow.setBackground(new Color(231, 218, 167));
		
		mt = new MediaTracker(mainApp);
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
				
				case 0:	colour = "club";
						break; //Have to put break to stop it executing the other statements
				case 1: colour = "spade";
						break;
				case 2: colour = "heart";
						break;
				case 3: colour = "diamond";
						break;
						
			}
			
			for (int rank = 0; rank < 13; rank++) //Loop 13 times (for ace - king)
			{
				
				//title = colour + Integer.toString(rank + 1); //Current title is the current suit + the rank number + 1
				imgCards[suit][rank] = createImage.getImage(this, "images/" + colour + (rank + 1) + ".gif", 5000);
				//System.out.print("\nGot card");
				mt.addImage(imgCards[suit][rank], 0);
				
			}
			
		}
		
		for (int card = 0; card < 6; card++) //Loop the number of card back images being supplied
		{
			
			imgCardBack[card] = createImage.getImage(this, "images/cardBack" + strCardBack[card] + ".gif", 10000);
			mt.addImage(imgCardBack[card], 0);
			
		}
		
		try
		{
			mt.waitForID(0);
		}
		catch (InterruptedException e)
		{
		}

		mainWindow.setupCards(imgCards, imgCardBack);
	
		
	}
	
}
