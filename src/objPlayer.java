

public class objPlayer
{
	public enum turnPhase{NOTMYTURN, ITEMSARRANGE, KICKDOOR, LOOKFOR, LOOT,CHARITY}
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
	public int levelUp(int amount)
	{
		return level+=amount;
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
		else throw new IllegalStateException("nie mo¿esz zacz¹æ tury jeœli jest twoja tura");
	}
	public void kickOpenDoor() throws IllegalStateException
	{
		if(myTurnPhase==turnPhase.ITEMSARRANGE)
		{
			myTurnPhase=turnPhase.KICKDOOR;
			objDoorCard temp= environment.showDoorCard();
			switch (temp.getType())
			{
			case MONSTER:
				environment.getPlayedCards().removeLastCard();
				environment.setCurrentFight(new objFight(new objMonster(temp.getName(), temp.getLevel(), temp.getReward(), temp.getTreasures(), temp.getSecondaryEffect()), this));
				environment.getEffectHandler().handleEffect(objCard.Type.MONSTER, temp.getEffect(), environment.getPlayerIndex(this));
				break;
			case DISASTER:

				break;
			case OTHER:
				hand.addCard(temp);
				environment.getPlayedCards().removeLastCard();
				break;
			default:
				break;
			}


		}
		else throw new IllegalStateException("z³a kolejnoœæ faz tury");
	}
	public void lookForTrouble(objDoorCard temp)
	{
		if(myTurnPhase==turnPhase.KICKDOOR)
		{
			myTurnPhase=turnPhase.LOOKFOR;
			if(temp.getType()==objCard.Type.MONSTER)
			{
				environment.setCurrentFight(new objFight(new objMonster(temp.getName(), temp.getLevel(), temp.getReward(), temp.getTreasures(), temp.getSecondaryEffect()), this));
				environment.getEffectHandler().handleEffect(objCard.Type.MONSTER, temp.getEffect(), environment.getPlayerIndex(this));
			}
			else throw new IllegalArgumentException();
		}
		else throw new IllegalStateException();
	}
	public void lootRoom()
	{
		if(myTurnPhase==turnPhase.KICKDOOR)
		{
			myTurnPhase=turnPhase.LOOT;
			this.drawDoor(1);
		}
		else throw new IllegalStateException();
	}
	public void Charity()
	{
		if(myTurnPhase==turnPhase.KICKDOOR||myTurnPhase==turnPhase.LOOKFOR||myTurnPhase==turnPhase.LOOT)
		{
			myTurnPhase=turnPhase.CHARITY;
		}
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
