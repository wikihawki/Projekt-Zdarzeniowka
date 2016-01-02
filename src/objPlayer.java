

public class objPlayer
{
	public enum turnPhase{NOTMYTURN, ITEMSARRANGE, KICKDOOR, FIGHT, LOOKFOR, LOOT,CHARITY}
	private turnPhase myTurnPhase;
	private String name;
	private boolean sex;
	private int level;
	private MunchkinHand hand;
	private MunchkinGroup cardsInPlay;
	private int freeHandCounter, footgearCounter, armorCounter, classCounter;
	private objGameLogic environment;
	public objPlayer(String name, boolean sex, int handX, int handY, objGameLogic envi)
	{
		freeHandCounter=2;
		footgearCounter=1;
		armorCounter=1;
		classCounter=1;
		this.name=name;
		level=1;
		this.sex=sex;
		hand=new MunchkinHand(handX, handY, 0);
		environment=envi;
		drawTreasure(4);
		drawDoor(4);
		myTurnPhase=turnPhase.NOTMYTURN;
	}
	


	public int getLevel()
	{
		return level;
	}
	public int levelUp()
	{
		return ++level;
	}
	public int levelDown()
	{
		return --level;
	}
	public boolean changeSex()
	{
		sex=!sex;
		return sex;
	}
	public boolean getSex()
	{
		return sex;
	}
	public String getName()
	{
		return name;
	}
	public MunchkinHand getHand()
	{
		return hand;
	}
	public void drawTreasure(int amount)
	{
		for(int i=0;i<amount;i++)hand.addCard(environment.getTreasureDeck().removeLastCard());
	}
	public void drawDoor(int amount)
	{
		for(int i=0;i<amount;i++)hand.addCard(environment.getDoorDeck().removeLastCard());
	}
	public void beginTurn() throws IllegalStateException
	{
		if(myTurnPhase==turnPhase.NOTMYTURN) myTurnPhase=turnPhase.ITEMSARRANGE;
		else throw new IllegalStateException("nie mo�esz zacz�� tury je�li jest twoja tura");
	}
	public void kickOpenDoor() throws IllegalStateException
	{
		if(myTurnPhase==turnPhase.ITEMSARRANGE)
		{
			myTurnPhase=turnPhase.KICKDOOR;
			objDoorCard temp= environment.showDoorCard();
			
			
		}
		else throw new IllegalStateException("z�a kolejno�� faz tury");
	}
	public void playCard(int cardNr)
	{
		objCard temp =hand.getCard(cardNr);
		if(temp.getClass()==objTreasureCard.class)
		{
			
		}
		else
		{
			if(temp.getType()==objCard.Type.OTHER ||temp.getType()==objCard.Type.DISASTER)
			{
				
			}
		}
	}
	public MunchkinGroup getCardsInPlay() {
		return cardsInPlay;
	}


	public turnPhase getMyTurnPhase() {
		return myTurnPhase;
	}
	
}
