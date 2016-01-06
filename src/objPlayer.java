

public class objPlayer extends objEntity
{
	public enum TurnPhase{NOTMYTURN, ITEMSARRANGE, KICKDOOR, LOOT, CHARITY, FIGHT}
	private TurnPhase myTurnPhase;
	private String name;
	private boolean sex;
	private int level;
	private MunchkinHand hand;
	private MunchkinGroup cardsInPlay;
	private MunchkinGroup carriedCards;
	private int freeHandCounter, footgearCounter, armorCounter, classCounter;
	private objGameLogic environment;
	public objPlayer(String name, boolean sex, int handX, int handY, objGameLogic envi)
	{
		setFreeHandCounter(2);
		setFootgearCounter(1);
		setArmorCounter(1);
		setClassCounter(1);
		this.name=name;
		level=1;
		cardsInPlay=new MunchkinGroup();
		carriedCards=new MunchkinGroup();
		this.sex=sex;
		hand=new MunchkinHand(handX, handY, 0);
		environment=envi;
		drawTreasure(4);
		drawDoor(4);
		myTurnPhase=TurnPhase.NOTMYTURN;
	}



	public int getLevel()
	{
		return level;
	}
	public int levelUp(int amount)
	{
		if(amount>-level)return level+=amount;
		else throw new IllegalArgumentException();
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
		if(myTurnPhase==TurnPhase.NOTMYTURN) myTurnPhase=TurnPhase.ITEMSARRANGE;
		else throw new IllegalStateException("nie mo¿esz zacz¹æ tury jeœli jest twoja tura");
	}
	public void kickOpenDoor() throws IllegalStateException
	{
		if(myTurnPhase==TurnPhase.ITEMSARRANGE)
		{
			myTurnPhase=TurnPhase.KICKDOOR;
			objCard temp= environment.showDoorCard();
			switch (temp.getSecondaryType())
			{
			case MONSTER:
				environment.getPlayedCards().remove(environment.getPlayedCards().size()-1);
				myTurnPhase=TurnPhase.FIGHT;
				objMonster monst=new objMonster(temp);
				environment.setCurrentFight(new objFight(monst, this, environment));
				environment.getEffectHandler().handleEffect(temp.getSecondaryType(),temp.getEffect(0), monst);
				break;
			case DISASTER:
				break;
			case OTHER:
				hand.addCard(temp);
				environment.getPlayedCards().remove(environment.getPlayedCards().size()-1);
				break;
			default:
				throw new IllegalStateException();
			}
		}
		else throw new IllegalStateException("z³a kolejnoœæ faz tury");
	}
	public void lookForTrouble(objCard temp)
	{
		if(myTurnPhase==TurnPhase.KICKDOOR)
		{
			if(temp.getSecondaryType()==objCard.SecondaryType.MONSTER)
			{
				myTurnPhase=TurnPhase.FIGHT;
				objMonster monst=new objMonster(temp);
				environment.setCurrentFight(new objFight(monst, this,environment));
				environment.getEffectHandler().handleEffect(temp.getSecondaryType(),temp.getEffect(0), monst);
			}
			else throw new IllegalArgumentException();
		}
		else throw new IllegalStateException();
	}
	public void lootRoom()
	{
		if(myTurnPhase==TurnPhase.KICKDOOR)
		{
			myTurnPhase=TurnPhase.LOOT;
			this.drawDoor(1);
		}
		else throw new IllegalStateException();
	}
	public void charity()
	{
		if(myTurnPhase==TurnPhase.FIGHT||myTurnPhase==TurnPhase.LOOT)
		{
			myTurnPhase=TurnPhase.CHARITY;
		}
	}
	public void endTour()
	{
		if(myTurnPhase==TurnPhase.CHARITY&&hand.size()<=5)
		{
			myTurnPhase=TurnPhase.NOTMYTURN;
		}
		else throw new IllegalStateException();
	}
	public void endImmediately()
	{
		myTurnPhase=TurnPhase.NOTMYTURN;
	}
	public void playCard(int cardNr, objEntity target)
	{
		objCard temp =hand.getCard(cardNr);
		switch (temp.getSecondaryType())
		{
		case ARMOR:
			break;
		case BOOTS:
			break;
		case DISASTER:

			discardCardfromHand(cardNr);
			break;
		case HAT:
			break;
		case ITEMENCHANCER:
			break;
		case MONSTER:
			break;
		case OTHER:
			break;
		case OTHERITEM:
			break;
		case WEAPON:
			break;
		default:
			break;

		}
	}
	public MunchkinGroup getCardsInPlay()
	{
		return cardsInPlay;
	}
	public void discardCardfromHand(int index)
	{
		environment.discardCard(hand.removeCard(index));
	}
	public TurnPhase getMyTurnPhase()
	{
		return myTurnPhase;
	}
	public MunchkinGroup getCarriedCards()
	{
		return carriedCards;
	}



	public int getFreeHandCounter()
	{
		return freeHandCounter;
	}



	public void setFreeHandCounter(int freeHandCounter)
	{
		this.freeHandCounter = freeHandCounter;
	}



	public int getFootgearCounter()
	{
		return footgearCounter;
	}



	public void setFootgearCounter(int footgearCounter)
	{
		this.footgearCounter = footgearCounter;
	}
	public int getArmorCounter()
	{
		return armorCounter;
	}
	public void setArmorCounter(int armorCounter)
	{
		this.armorCounter = armorCounter;
	}
	public int getClassCounter()
	{
		return classCounter;
	}
	public void setClassCounter(int classCounter)
	{
		this.classCounter = classCounter;
	}



}
