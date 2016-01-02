import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;


public class objGameLogic
{
	
	private ArrayList<objPlayer> players = new ArrayList<objPlayer>();
	private objCreateAppletImage createImage = new objCreateAppletImage();
    private	Image[][] imgCards = new Image[4][13];
    private Image[] imgCardBack = new Image[6];
    private Image CharacterImage ;
    private MunchkinGroup sealDeck, treasureDeck, doorDeck;
    private MunchkinGroup treasureDiscard,doorDiscard;
    protected objInstruction currentInstruction, dragPaintInstruction; 
    private MunchkinGroup playedCards;
	public objGameLogic()
	{
		sealDeck=new MunchkinGroup();
		treasureDeck=new MunchkinGroup();
		doorDeck=new MunchkinGroup();
		doorDiscard=new MunchkinGroup();
		treasureDiscard=new MunchkinGroup();
		currentInstruction	= new objInstruction(1,1);
		playedCards= new MunchkinGroup();
		importPictures();
		newGame();
	}
	
	public void newGame()
	{
		przygotujTalie();
		setupPlayers();
	}
	private void przygotujTalie()
	{
		for(int i = 0 ;i<=64;i++)
		{
			objCard karta = new objSealCard(objCard.Type.SEAL,imgCardBack[1], null, null, i);
			sealDeck.addCard(karta);
			karta = new objTreasureCard(objCard.Type.ARMOR,imgCardBack[1], null, null, i, i, i, i);
			treasureDeck.addCard(karta);
			karta = new objDoorCard(objCard.Type.MONSTER,imgCardBack[1], null, null, i, i, i);
			doorDeck.addCard(karta);
		}
	}
	private void setupPlayers()
	{
		for(int i =0 ; i<4;i++)
		{
			
			players.add(new objPlayer(null,true,currentInstruction.getPlayerHandPositionX(i),currentInstruction.getPlayerHandPositionY(i),this));
		}
	}
    private void importPictures ()
	{
		
		for (int suit = 0; suit < 4; suit++) //Loop 4 times (for each suit)
		{
			
			switch (suit) //Inspect current suit number
			{
				
				case 0:	
						break; //Have to put break to stop it executing the other statements
				case 1: 
						break;
				case 2: 
						break;
				case 3: 
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
    
    public objDoorCard showDoorCard()
	{
		objDoorCard temp=(objDoorCard) doorDeck.getCard(doorDeck.size()-1);
		playedCards.addCard(temp);
		return temp;
	}
	public MunchkinHand getHand(int player)
	{
		return players.get(player).getHand();
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
	public MunchkinGroup getSealDeck() {
		return sealDeck;
	}
	public MunchkinGroup getTreasureDeck() {
		return treasureDeck;
	}
	public MunchkinGroup getDoorDeck() {
		return doorDeck;
	}
	public MunchkinGroup getPlayedCard() {
		return playedCard;
	}
    

}
