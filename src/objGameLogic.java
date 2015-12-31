import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;


public class objGameLogic {

	private MunchkinHand reka = new MunchkinHand(100,100);
	private objCreateAppletImage createImage = new objCreateAppletImage();
    private	Image[][] imgCards = new Image[4][13];
    private Image[] imgCardBack = new Image[6];
    private Image CharacterImage ;
    protected objInstruction currentInstruction, dragPaintInstruction; 
	public objGameLogic()
	{
		currentInstruction	= new objInstruction(1,1);
		importPictures();
		przygotujTalie();
	}	
	private void przygotujTalie()
	{
		for(int i = 0 ;i<=64;i++)
		{
			objCard karta = new objCard(i,imgCardBack[1]);
			reka.addCard(karta);
		}
	}
	private void importPictures ()
	{
		
		String colour = "";
		//Array to store all the card back images

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
			
		
	
			imgCardBack[card]= createImage.getImage(this, "images/ks (1).jpg", 2000000).getScaledInstance(72, 96, Image.SCALE_DEFAULT);
			
		//	mt.addImage(imgCardBack[card], 0);
			
		}
	
		CharacterImage= createImage.getImage(this, "images/munchkinPostac.png", 2000000).getScaledInstance(150, 150, Image.SCALE_DEFAULT);
	
	

	}
	public MunchkinHand getHand()
	{
		return reka;
	}
    public Integer getPlayerHandPositionX(int Player)
    {
    	return currentInstruction.getPlayerHandPositionX(Player-1);
    }
    public Integer getPlayerHandPositionY(int Player)
    {
    	return currentInstruction.getPlayerHandPositionY(Player-1);
    }
    
    public Image getCharacterImage()
    {
    	return CharacterImage;
    }
    

}
