import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;


public class objPlayer extends objEntity
{
	public enum TurnPhase{NOTMYTURN, ITEMSARRANGE, KICKDOOR, LOOT, CHARITY, FIGHT}
	private int PlayerId;
	private TurnPhase myTurnPhase;
	private List<GameEventListener> listeners = new ArrayList<GameEventListener>();
	private String name;
	private boolean sex;
	private int level;
	private MunchkinHand hand;
	private MunchkinGroup cardsInPlay;
	private MunchkinGroup carriedCards;
	private int freeHandCounter, footgearCounter, armorCounter, classCounter,headgearCounter;
	private objGameLogic environment;
	private int levelUpsCounter;
	private int money;
	public objPlayer(String name, boolean sex, objGameLogic envi,int MiejsceWKolejce)
	{
		this.PlayerId=MiejsceWKolejce;
		setFreeHandCounter(2);
		setFootgearCounter(1);
		setArmorCounter(1);
		setClassCounter(1);
		this.name=name;
		level=1;
		cardsInPlay=new MunchkinGroup();
		carriedCards=new MunchkinGroup();
		this.sex=sex;
		hand=new MunchkinHand( 0);
		environment=envi;
		drawTreasure(4);
		drawDoor(4);
		levelUpsCounter=0;
		money=0;
		myTurnPhase=TurnPhase.NOTMYTURN;
	}

	public int getLevel()
	{
		return level;
	}
	public int levelUp(int amount)
	{
		if(amount>0)
		{
			levelUpsCounter+=amount;
			fireEvent(GameEvent.EventType.LEVELUP, this);
		}
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
	public MunchkinGroup getCardsInPlay()
	{
		return cardsInPlay;
	}
	public TurnPhase getMyTurnPhase()
	{
		return myTurnPhase;
	}
	public MunchkinGroup getCarriedCards()
	{
		return carriedCards;
	}
	public MunchkinHand getHand()
	{
		return hand;
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

	public void drawTreasure(int amount)
	{
		objCard temp;
		for(int i=0;i<amount;i++)if((temp=environment.drawTreasure())!=null)hand.addCard(temp);
	}
	public void drawDoor(int amount)
	{
		for(int i=0;i<amount;i++)hand.addCard(environment.getDoorDeck().removeLastCard());
	}
	public void playCard(int cardNr, objEntity target)
	{
		objCard temp =hand.removeCard(cardNr);
		switch (temp.getSecondaryType())
		{
		case ARMOR:
			armorCounter=equipItem(temp,armorCounter);
			break;
		case BOOTS:
			footgearCounter=equipItem(temp,footgearCounter);
			break;
		case HAT:
			headgearCounter=equipItem(temp,headgearCounter);
			break;
		case ITEMENCHANCER:
			environment.getEffectHandler().handleEffect(objCard.SecondaryType.ITEMENCHANCER, temp.getEffect(0), target);
			environment.getEffectHandler().handleEffect(objCard.SecondaryType.ITEMENCHANCER, temp.getEffect(1), target);
			break;
		case MONSTER:
			if(environment.getCurrentFight()!=null)
			{

				if((environment.getCurrentFight().isThere(objCard.Tag.UNDEAD)&&temp.getTag()==objCard.Tag.UNDEAD)||(environment.getCurrentFight().isThere(objCard.Tag.SHARK)&&temp.getTag()==objCard.Tag.SHARK)||temp.getTag()!=objCard.Tag.UNDEAD&&environment.getCurrentFight().isThere(12)||temp.getEffect(0)==17)
					{
					environment.getCurrentFight().addMonster(new objMonster(temp));
					}
				else throw new IllegalArgumentException();
			}
			else throw new IllegalStateException();
			break;
		case DISASTER:
		case OTHER:
			environment.addCardToStack(temp, target);
			break;
		case OTHERITEM:
			carriedCards.addCard(temp);
			break;
		case ONEHANDWEAPON:
			freeHandCounter=equipItem(temp,freeHandCounter);
			break;
		case TWOHANDWEAPON:
			freeHandCounter=equipItem(temp,freeHandCounter,2);
			break;
		case CLASS:
			if(classCounter>0)
			{
				classCounter--;
			}
			else
			{
				if(((objCard)target).getSecondaryType()==objCard.SecondaryType.CLASS)
				{
					int index=cardsInPlay.getCardIndex(temp);
					if(index!=-1)discardCardFromPlay(index);
					else throw new IllegalArgumentException();
				}
				else throw new IllegalArgumentException();
			}
			cardsInPlay.addCard(temp);
			if(environment.getEffectHandler().getTargetClass(temp.getSecondaryType(), temp.getEffect(0))==objPlayer.class)environment.getEffectHandler().handleEffect(temp.getSecondaryType(), temp.getEffect(0),this);
			if(environment.getEffectHandler().getTargetClass(temp.getSecondaryType(), temp.getEffect(1))==objPlayer.class)environment.getEffectHandler().handleEffect(temp.getSecondaryType(), temp.getEffect(1),this);
			break;
		case SEAL:
			break;

		}
	}
	public int equipItem(objCard temp, int counter)
	{
		if(temp.getTag()!=objCard.Tag.BIG&&!isThereBigItem())
		{
			if(counter>0)
			{
				cardsInPlay.addCard(temp);
				counter--;
				environment.getEffectHandler().handleEffect(temp.getSecondaryType(), temp.getEffect(0), temp);
				environment.getEffectHandler().handleEffect(temp.getSecondaryType(), temp.getEffect(1), temp);
			}
			else carriedCards.addCard(temp);
			fireEvent(GameEvent.EventType.INVENTORYCHANGED, this);
			return counter;
		}
		throw new IllegalStateException();
	}
	public int equipItem(objCard temp, int counter, int amount)
	{
		if(temp.getTag()!=objCard.Tag.BIG&&!isThereBigItem())
		{
			if(counter>0)
			{
				cardsInPlay.addCard(temp);
				counter=-amount;
				environment.getEffectHandler().handleEffect(temp.getSecondaryType(), temp.getEffect(0), temp);
				environment.getEffectHandler().handleEffect(temp.getSecondaryType(), temp.getEffect(1), temp);
			}
			else carriedCards.addCard(temp);
			fireEvent(GameEvent.EventType.INVENTORYCHANGED, this);
			return counter;
		}
		throw new IllegalStateException();
	}
	public void discardCardfromHand(int index)
	{
		objCard temp=hand.removeCard(index);
		fireEvent(GameEvent.EventType.DSICARD, temp);
		environment.discardCard(temp);
	}
	public void discardCardFromPlay(int index)
	{
		objCard temp=cardsInPlay.removeCard(index);
		fireEvent(GameEvent.EventType.DSICARD, temp);
		environment.discardCard(temp);
	}
	public void discardCarriedCard(int index)
	{
		objCard temp=carriedCards.removeCard(index);
		fireEvent(GameEvent.EventType.DSICARD, temp);
		environment.discardCard(temp);
	}
	public boolean isThereBigItem()
	{
		Vector<objCard> help=carriedCards.findCards(null, objCard.Tag.BIG);
		help.addAll(cardsInPlay.findCards(null, objCard.Tag.BIG));
		return (help.size()>0);
	}
	public Vector<Integer> findArmor()
	{
		return cardsInPlay.findCardsIndex(null, objCard.SecondaryType.ARMOR);
	}
	public Vector<Integer> findHat()
	{
		return cardsInPlay.findCardsIndex(null, objCard.SecondaryType.HAT);
	}
	public Vector<Integer> findBoots()
	{
		return cardsInPlay.findCardsIndex(null, objCard.SecondaryType.BOOTS);
	}
	public Vector<Integer> findWeapon()
	{
		Vector<Integer> temp=cardsInPlay.findCardsIndex(null, objCard.SecondaryType.ONEHANDWEAPON);
		temp=cardsInPlay.findCardsIndex(null, objCard.SecondaryType.TWOHANDWEAPON);
		return temp;
	}
	public Vector<Integer> findClass()
	{
		return cardsInPlay.findCardsIndex(null, objCard.SecondaryType.CLASS);
	}
	public void moveFromPlayToCarried(Vector<Integer> cardIndexes)
	{
		for(int i=0; i<cardIndexes.size();i++)carriedCards.addCard(cardsInPlay.removeCard(cardIndexes.elementAt(i)));
	}
	public void moveFromPlayToCarried(int cardIndex)
	{
		carriedCards.addCard(cardsInPlay.removeCard(cardIndex));
	}
	public void moveFromCarriedToPlay(int cardIndex)
	{
		objCard temp=carriedCards.removeCard(cardIndex);
		switch (temp.getSecondaryType())
		{
		case ARMOR:
			armorCounter=equipItem(temp,armorCounter);
			break;
		case BOOTS:
			footgearCounter=equipItem(temp,footgearCounter);
			break;
		case HAT:
			headgearCounter=equipItem(temp,headgearCounter);
			break;
		case ONEHANDWEAPON:
			freeHandCounter=equipItem(temp,freeHandCounter);
			break;
		case TWOHANDWEAPON:
			freeHandCounter=equipItem(temp,freeHandCounter,2);
			break;
		default:
			break;
		}
		fireEvent(GameEvent.EventType.INVENTORYCHANGED, this);
	}
	public void sellTreasureFromCarried(int index)
	{
		objCard temp =carriedCards.removeCard(index);
		money+=temp.getValue();
		if(money>1000)
		{
			levelUp(1);
			money-=1000;
		}
	}
	public void sellTreasureFromPlayed(int index)
	{
		objCard temp =carriedCards.removeCard(index);
		money+=temp.getValue();
		if(money>1000)
		{
			levelUp(1);
			money-=1000;
		}
	}
	public Vector<Integer> findItemsInPlay()
	{
		Vector<Integer> temp= getCardsInPlay().findCardsIndex(null, objCard.SecondaryType.ARMOR);
		temp.addAll(getCardsInPlay().findCardsIndex(null, objCard.SecondaryType.BOOTS));
		temp.addAll(getCardsInPlay().findCardsIndex(null, objCard.SecondaryType.HAT));
		temp.addAll(getCardsInPlay().findCardsIndex(null, objCard.SecondaryType.ONEHANDWEAPON));
		temp.addAll(getCardsInPlay().findCardsIndex(null, objCard.SecondaryType.TWOHANDWEAPON));
		temp.addAll(getCardsInPlay().findCardsIndex(null, objCard.SecondaryType.OTHERITEM));
		return temp;
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
			charity();
		}
		else throw new IllegalStateException();
	}
	public void charity()
	{
		if(myTurnPhase==TurnPhase.FIGHT||myTurnPhase==TurnPhase.LOOT||myTurnPhase==TurnPhase.KICKDOOR)
		{
			myTurnPhase=TurnPhase.CHARITY;
		}
	}
	public void endTour()
	{
		if(myTurnPhase==TurnPhase.CHARITY&&hand.size()<=5)
		{
			myTurnPhase=TurnPhase.NOTMYTURN;
			if(levelUpsCounter>=3)environment.closeSeal();
			levelUpsCounter=0;
			money=0;
			fireEvent(GameEvent.EventType.TOUREND, this);
		}
		else throw new IllegalStateException();
	}
	public void endImmediately()
	{
		myTurnPhase=TurnPhase.NOTMYTURN;
		if(levelUpsCounter>=3)environment.closeSeal();
		levelUpsCounter=0;
		money=0;
		fireEvent(GameEvent.EventType.TOUREND, this);
	}

	public void die()
	{
		for(int i=0;i <hand.size();i++)discardCardfromHand(i);
		for(int i=0;i <carriedCards.size();i++)if(carriedCards.getCard(i).getEffect(0)!=4||cardsInPlay.getCard(i).getSecondaryType()!=objCard.SecondaryType.OTHER)discardCarriedCard(i);
		for(int i=0;i <cardsInPlay.size();i++)discardCardFromPlay(i);
		environment.getEffectHandler().addContinuousEffect(30, this);
		endImmediately();
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

	public int getMoney() {
		return money;
	}


public int getPlayerId()
{
	return PlayerId;
}





}
