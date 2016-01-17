import java.awt.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;


public class objGameLogic
{


	private ArrayList<GameEventListener> listeners = new ArrayList<GameEventListener>();
	private objPlayer[] players = new objPlayer[4];
	public enum GameState{PLAY, OVER}
	private GameState state;
	private objCreateAppletImage createImage = new objCreateAppletImage();
    private	Image[][] imgCards = new Image[3][95];
    private Image[] imgCardBack = new Image[3];
    private Image CharacterImage ;
    private Image ButtonImage ;
    private Image PressedButtonImage ;
    private Image Door ;
    private Image Treasure ;
    private Image Seal ;
    private Image Chest ;
    private MunchkinGroup sealDeck, treasureDeck, doorDeck;
    private MunchkinGroup treasureDiscard,doorDiscard;
    private MunchkinGroup openedSeals;
    protected objInstruction currentInstruction, dragPaintInstruction;
    private Vector<objPlayedCard> playedCards;
    private objFight currentFight;
    private int playersNumber;
    private objEffectHandler effectHandler;
    private int currPlayer;
    private int playingPlayer;

	public objGameLogic()
	{
		sealDeck=new MunchkinGroup();
		treasureDeck=new MunchkinGroup();
		doorDeck=new MunchkinGroup();
		doorDiscard=new MunchkinGroup();
		treasureDiscard=new MunchkinGroup();
		openedSeals=new MunchkinGroup();
		currentInstruction	= new objInstruction(1,1);
		playedCards= new Vector<objPlayedCard>();
		currPlayer=0;
	//	DatabaseConnection temp=new DatabaseConnection();
	//	temp.importCards();
		importPictures();
		newGame(4);
	}

	public void newGame(int amount)
	{
		state=GameState.PLAY;
		playersNumber=amount;
		importCards();
		effectHandler=new objEffectHandler(this);
		setupPlayers(amount);
		players[currPlayer].beginTurn();
	}
	private void importCards()
	{
		DatabaseConnection connection=new DatabaseConnection();
		MunchkinGroup cards= new MunchkinGroup();
		cards.addStack(connection.importCards());
		objCard card;
		while(cards.size()>0)
		{
			card=cards.removeLastCard();
			if(card.getType()==objCard.Type.DOOR)
			{
				card.setImgCard( imgCardBack[0]);
				doorDeck.addCard(card);
			}
			if(card.getType()==objCard.Type.TREASURE)
			{
				card.setImgCard( imgCardBack[1]);
				treasureDeck.addCard(card);
			}
			if(card.getType()==objCard.Type.SEAL)
			{
				card.setImgCard( imgCardBack[1]);
				sealDeck.addCard(card);
			}
		}
		sealDeck.suffle();
		treasureDeck.suffle();
		doorDeck.suffle();

	}
	private void setupPlayers(int amount)
	{
		for(int i =0 ; i<amount;i++)
		{
			StringBuilder sb = new StringBuilder();
			sb.append("Player ");
			sb.append(i+1);
			String strI = sb.toString();
			players[i]=new objPlayer(strI,true,this,i);
			players[i].addListener(effectHandler);
		}
	}
	private void importPictures ()
	{


			for (int rank = 1; rank <= 92; rank++) //Loop 13 times (for ace - king)


			{

				//title = colour + Integer.toString(rank + 1); //Current title is the current suit + the rank number + 1
				imgCards[0][rank] = createImage.getImage(this, "images/karta ("+rank+").jpg", 200000).getScaledInstance(72, 96, Image.SCALE_DEFAULT);
			
		        //	mt.addImage(imgCards[suit][rank], 0);

			}

			for (int rank = 1; rank < 76; rank++) //Loop 13 times (for ace - king)


			{

				//title = colour + Integer.toString(rank + 1); //Current title is the current suit + the rank number + 1
				imgCards[1][rank] = createImage.getImage(this, "images/karta ("+(92+rank)+").jpg", 200000).getScaledInstance(72, 96, Image.SCALE_DEFAULT);
				
		        //	mt.addImage(imgCards[suit][rank], 0);

			}


			for (int rank = 1; rank <= 12; rank++) //Loop 13 times (for ace - king)


			{

				//title = colour + Integer.toString(rank + 1); //Current title is the current suit + the rank number + 1
				imgCards[2][rank] = createImage.getImage(this, "images/karta ("+(168+rank)+").jpg", 6000000).getScaledInstance(150,150, Image.SCALE_DEFAULT);;
				
		        //	mt.addImage(imgCards[suit][rank], 0);

			}


			imgCardBack[0]= createImage.getImage(this, "images/kd Back.jpg", 200000).getScaledInstance(72, 96, Image.SCALE_DEFAULT);
			imgCardBack[1]= createImage.getImage(this, "images/ks Back.jpg", 200000).getScaledInstance(72, 96, Image.SCALE_DEFAULT);
			Door= createImage.getImage(this, "images/kd Back.jpg", 200000).getScaledInstance(120,147, Image.SCALE_DEFAULT);
			Treasure= createImage.getImage(this, "images/ks Back.jpg", 200000).getScaledInstance(120,147, Image.SCALE_DEFAULT);
			Seal= createImage.getImage(this, "images/seal back.jpg",600000).getScaledInstance(150,150, Image.SCALE_DEFAULT);

		//	mt.addImage(imgCardBack[card], 0);



		CharacterImage= createImage.getImage(this, "images/munchkinPostac.png", 200000).getScaledInstance(150, 150, Image.SCALE_DEFAULT);
		ButtonImage= createImage.getImage(this, "images/Buttons/buttonLong_beige.png", 1000).getScaledInstance(150, 49, Image.SCALE_DEFAULT);
		PressedButtonImage= createImage.getImage(this, "images/Buttons/buttonLong_beige_pressed.png", 1000).getScaledInstance(150, 49, Image.SCALE_DEFAULT);
        Chest =createImage.getImage(this, "images/Ekwipunek/treasure-chest-md.png", 100000).getScaledInstance(190, 130, Image.SCALE_DEFAULT);
	}
    public void resolveStackTopCard()
    {
    	objPlayedCard temp=playedCards.get(playedCards.size()-1);
    	effectHandler.handleEffect(temp.getPlayedCard().getSecondaryType(), temp.getPlayedCard().getEffect(0), temp.getTarget());
    //	effectHandler.handleEffect(temp.getPlayedCard().getSecondaryType(), temp.getPlayedCard().getEffect(1), temp.getTarget());
    }
    public void addCardToStack(objCard card, objEntity target, objPlayer player)
    {
    	playedCards.add(new objPlayedCard(card, target,player));
    }
    public objCard showDoorCard()
	{
		objCard temp=drawDoor();
		if(temp.getSecondaryType()==objCard.SecondaryType.DISASTER)
		{
			if(effectHandler.getTargetClass(temp.getSecondaryType(), temp.getEffect(0))==objPlayer.class)playedCards.add(new objPlayedCard(temp,players[currPlayer],players[currPlayer]));
			else playedCards.add(new objPlayedCard(temp,null,players[currPlayer]));
			resolveStackTopCard();
		}
		return temp;
	}

    public void discardCard(objCard card)
    {
    	if(card.getType()==objCard.Type.TREASURE)treasureDiscard.addCard(card);
    	if(card.getType()==objCard.Type.DOOR)doorDiscard.addCard(card);
    }
	public MunchkinHand getHand(int player)
	{
		return players[player].getHand();
	}
    public Integer getPlayerHandPositionX(int Player)
    {
    	return currentInstruction.getPlayerHandPositionX(Player-1);
    }
    public Integer getPlayerHandPositionY(int Player)
    {
    	return currentInstruction.getPlayerHandPositionY(Player-1);
    }
    public void openSeal()
    {
    	objCard temp=sealDeck.removeLastCard();
    	openedSeals.addCard(temp);
    	effectHandler.handleEffect(objCard.SecondaryType.SEAL, temp.getEffect(0),getCurrentPlayer());
    	fireEvent(GameEvent.EventType.SEALOPEN, null);
    	if(openedSeals.size()==7)
    	{
    		fireEvent(GameEvent.EventType.SEVENTHSEAL, null);
    		state=GameState.OVER;
    	}
    }
    public void openSeal(objPlayer opener)
    {
    	objCard temp=sealDeck.removeLastCard();
    	openedSeals.addCard(temp);
    	effectHandler.handleEffect(objCard.SecondaryType.SEAL, temp.getEffect(0),opener);
    	effectHandler.handleEffect(objCard.SecondaryType.SEAL, temp.getEffect(1),opener);
    	fireEvent(GameEvent.EventType.SEALOPEN, null);
    	if(openedSeals.size()==7)state=GameState.OVER;
    	{
    		fireEvent(GameEvent.EventType.SEVENTHSEAL, null);
    		state=GameState.OVER;
    	}
    }
    public void openSeal(int i)
    {
    	objCard temp=sealDeck.removeCard(i);
    	openedSeals.addCard(temp);
    	effectHandler.handleEffect(objCard.SecondaryType.SEAL, temp.getEffect(0),getCurrentPlayer());
    	effectHandler.handleEffect(objCard.SecondaryType.SEAL, temp.getEffect(1),getCurrentPlayer());
    	fireEvent(GameEvent.EventType.SEALOPEN, null);
    	if(openedSeals.size()==7)state=GameState.OVER;
    	{
    		fireEvent(GameEvent.EventType.SEVENTHSEAL, null);
    		state=GameState.OVER;
    	}
    }
    public void closeSeal()
    {
    	if(openedSeals.size()>0)
    	{
	    	sealDeck.addCard(openedSeals.removeLastCard());
	    	sealDeck.suffle();
	    	if(openedSeals.size()>0)effectHandler.handleEffect(objCard.SecondaryType.SEAL, openedSeals.getLastCard().getEffect(1), null);
	    	fireEvent(GameEvent.EventType.SEALCLOSED,null);
    	}
    }
    public MunchkinGroup getOpenedSeals()
    {
    	return openedSeals;
    }
    public void returnDoorsToDeck(Vector<objCard> cards)
    {
    	doorDeck.addStack(cards);
    	doorDeck.suffle();
    }
    public void returnDoorsToDeck(objCard card)
    {
    	doorDeck.addCard(card);
    	doorDeck.suffle();
    }
    public void returnTreasuresToDeck(Vector<objCard> cards)
    {
    	treasureDeck.addStack(cards);
    	treasureDeck.suffle();
    }
    public void returnTreasuresToDeck(objCard card)
    {
    	treasureDeck.addCard(card);
    	treasureDeck.suffle();
    }
    public Image getCharacterImage()
    {
    	return CharacterImage;
    }
    public objCard drawTreasure()
    {
    	if(treasureDeck.size()==0)
    	{
    		if(treasureDiscard.size()>0)
    		{
    			MunchkinGroup help=treasureDiscard;
    			treasureDiscard=treasureDeck;
    			treasureDeck=help;
    		}
    		else return null;
    	}
    	return treasureDeck.removeLastCard();
    }
    public objCard drawDoor()
    {
    	if(doorDeck.size()==0)
    	{
    		if(doorDiscard.size()>0)
    		{
    			MunchkinGroup help=doorDiscard;
    			doorDiscard=doorDeck;
    			doorDeck=help;
    		}
    		else return null;
    	}
    	return doorDeck.removeLastCard();
    }
    public Image getCardImage(objCard karta,int cardId)
    {
    	if(karta.getType()==objCard.Type.DOOR)
    	{
    		return imgCards[0][karta.getIdNr()];
    	}else if(karta.getType()==objCard.Type.TREASURE)
    	{

    		return imgCards[1][karta.getIdNr()-92];
    	}
    	else{
    		return imgCards[2][karta.getIdNr()-168];
    	}
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
	public Vector<objPlayedCard> getPlayedCards() {
		return playedCards;
	}

	public objFight getCurrentFight() {
		return currentFight;
	}

	public void setCurrentFight(objFight currentFight) {
		this.currentFight = currentFight;
	}
	public objPlayer getPlayer(int index)
	{
		return players[index];
	}
	public int getPlayerIndex(objPlayer player)
	{
		int n=-1;
		for(int i=0; i<4;i++)
		{
			if(player==players[i])n=i;
		}
		return n;
	}

	public objEffectHandler getEffectHandler() {
		return effectHandler;
	}
	public synchronized void addListener(GameEventListener listener)
	{
		listeners.add(listener);
	}
	public synchronized void removeListener(GameEventListener listener)
	{
	    listeners.remove(listener);
	}
	private synchronized void fireEvent(GameEvent.EventType type, objEntity target)
	{
	    GameEvent event = new GameEvent(this, type, target);
	    Iterator<GameEventListener> i = listeners.iterator();
	    while(i.hasNext())
	    {
	    	i.next().gameEventOccurred(event);;
	    }
	}
	public void win(objPlayer winner)
	{
		if(winner.getLevel()>=10)state=GameState.OVER;
	}
	public GameState getState()
	{
		return state;
	}
	public int getPlayersNumber() {
		return playersNumber;
	}
    public objPlayer getCurrentPlayer()
    {
    	return players[currPlayer];
    }

    public int isMouseOnCharacter(int x,int y)
	{
    	int[] tmp=getNextPlayerId(getCurrentPlayer().getPlayerId());

			if ((x >= 220&& x <= 340 )&& (y >= 447 && y <= 597)) //Check if mouse is in this column's card area
			{
				return tmp[3];

	        }else
	        if((x >= 100&& x <= 250 )&& (y >= 130 && y <= 270))
	        {
	        	return tmp[0];
	        }else
		    if((x >= 630&& x <= 740 )&& (y >= 100 && y <= 250))
		    {
		        return tmp[1];
		    }else
	        if((x >= 715&& x <= 850 )&& (y >= 400 && y <= 530))
		     {
			   return tmp[2];
		     }

		return -1;
	}
    public Image getDoorImage()
    {
    	return Door;
    }
    public Image getTreasureImage()
    {
    	return Treasure;
    }
    public void nextPlayer()
    {
    	if(currPlayer<playersNumber-1)currPlayer++;
    	else currPlayer=0;
    	players[currPlayer].beginTurn();
    }
     public Image getSealImage()
    {
    	return Seal;
    }
     public Image getCardImage(int cardType , int cardId)
	    {
	    	return imgCards[cardType][cardId];
	    }
     public Image getButtonImage()
     {
    	 return ButtonImage;
     };
     public int isAboveFocusedPlayerButton(int x, int y)
     {
    	 int tmp =0;
    	 for(int i =0 ;i<=1;i++)
    	 {
    		 for(int j =0 ;j<=1;j++)
        	 { tmp++;
    			 if ((x >= 435+ j%2*150&& x <= 580+ j%2*150 )&& (y >= 495+ i%2*50 && y <= 535+ i%2*50)) //Check if mouse is in this column's card area
    				{

    					return tmp;

    		        }
        	 }

    	 }


    	 return 0;
     }
     public Image getPressedButtonImage()
     {
    	 return PressedButtonImage;
     };
     public boolean isAboveButton(int x , int y)
     {
    	 if ((x >= 435&& x <= 580 )&& (y >= 495 && y <= 535)) //Check if mouse is in this column's card area
			{
				return true;

	        }
    	 return false;
     }
     protected int[] getNextPlayerId(int currentPlayerId)
     {


     	switch(currentPlayerId)
     	{
     	case 0:
     		int[] myIntArray1 = {1,2,3,0};
     		 return  myIntArray1 ;

     	case 1:
     		int[] myIntArray2 = {2,3,0,1};
    		 return  myIntArray2 ;

     	case 2:
     		int[] myIntArray3 = {3,0,1,2};
    		 return  myIntArray3 ;

     	case 3:
     		int[] myIntArray4 = {0,1,2,3};
    		 return  myIntArray4 ;
     	default :
     		break;

     	}

 		int[] myIntArray4 = {0,1,2};
		 return  myIntArray4 ;

     }
     public int isAboveStack(int x , int y)
     {
    	 if ((x >= 215&& x <= 335 )&& (y >= 290 && y <= 440)) //Check if mouse is in this column's card area
			{

			return 1;
	        }else
	        if((x >= 365&& x <= 485 )&& (y >= 290 && y <= 440))
	        {
	        return 2;
	        }else
		    if((x >= 520&& x <= 660 )&& (y >= 290 && y <= 435))
		    {
		    return 3;
		    }
    	 return 0;
     }
   public int isAboveRun(int x,int y)
   {
	 
 	 if((x >= 410&& x <= 550)&& (y >= 300&& y <= 350))
 	 {
 		 
        return 5;
 	 }else
 	 if((x >= 410&& x <= 550)&& (y >= 375&& y <= 415))
 	 {
 	
 	 return 6;
 	 }
 	 return 0;
   }
	public int getPlayingPlayer() {
		return playingPlayer;
	}

	public void setPlayingPlayer(int playingPlayer) {
		this.playingPlayer = playingPlayer;
	}
	public Image getChestImage()
	{return Chest;

	}
}
