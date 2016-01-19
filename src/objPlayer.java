import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;


public class objPlayer extends objEntity
{
	public enum TurnPhase{NOTMYTURN, ITEMSARRANGE, KICKDOOR, LOOT, CHARITY, FIGHT}
	private int PlayerId;
	private TurnPhase myTurnPhase;
	private List<GameEventListener> listeners = new ArrayList<GameEventListener>();
	private List<GameActionEventListener> listenersA = new ArrayList<GameActionEventListener>();
	private String name;
	private boolean sex;
	private int level;
	private MunchkinHand hand;
	private MunchkinGroup cardsInPlay;
	private MunchkinGroup carriedCards;
	private MunchkinGroup effects;
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
		setHeadgearCounter(1);
		this.name=name;
		level=1;
		cardsInPlay=new MunchkinGroup();
		carriedCards=new MunchkinGroup();
		effects=new MunchkinGroup();
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
		else return level;
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
	public MunchkinGroup getEffects() {
		return effects;
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
		playCard(hand.getCard(cardNr),target);
	}
	public void playCard(objCard temp, objEntity target)
	{

		switch (temp.getSecondaryType())
		{
		case ARMOR:
			equipItem(temp,armorCounter,temp.getSecondaryType());
			break;
		case BOOTS:
			equipItem(temp,footgearCounter,temp.getSecondaryType());
			break;
		case HAT:
			equipItem(temp,headgearCounter,temp.getSecondaryType());
			break;
		case ONEHANDWEAPON:
			equipWeapon(temp);
			break;
		case ITEMENCHANTER:
			if(target.getClass()==objCard.class)if(((objCard)target).getType()==objCard.Type.TREASURE&&((objCard)target).getSecondaryType()!=objCard.SecondaryType.OTHER)
			{
				cardsInPlay.addCard(temp);
				environment.getEffectHandler().handleEffect(objCard.SecondaryType.ITEMENCHANTER, temp.getEffect(0), new objPlayedCard(temp, target, this));
				environment.getEffectHandler().handleEffect(objCard.SecondaryType.ITEMENCHANTER, temp.getEffect(1), new objPlayedCard(temp, target, this));
				playCard((objCard)target,null);

			}
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
			environment.addCardToStack(temp, target,this);
			fireEvent(GameEvent.EventType.DISASTER, temp);
			environment.resolveStackTopCard();
			break;
		case OTHER:
			if(temp.getType()==objCard.Type.TREASURE)carriedCards.addCard(temp);
			else
			{
				environment.addCardToStack(temp, target,this);
				environment.resolveStackTopCard();
			}
			break;
		case OTHERITEM:
			cardsInPlay.addCard(temp);
			environment.getEffectHandler().handleEffect(objCard.SecondaryType.OTHERITEM, temp.getEffect(0), temp);
			break;
		case TWOHANDWEAPON:
			equipWeapon(temp);
			break;
		case CLASS:
			if(classCounter<findClass().size())
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
		fireEvent(GameEvent.EventType.CARDPLAYED, temp);
		hand.removeCard(hand.getCardIndex(temp));
	}
	public void useCardFromBackpack(objCard temp, objEntity target)
	{
		carriedCards.removeCard(carriedCards.getCardIndex(temp));
		environment.getEffectHandler().handleEffect(temp.getSecondaryType(), temp.getEffect(0), target);
		environment.getEffectHandler().handleEffect(temp.getSecondaryType(), temp.getEffect(1), target);
		environment.discardCard(temp);
		fireEvent(GameEvent.EventType.CARDPLAYED, temp);
	}

	private void equipItem(objCard temp,int counter, objCard.SecondaryType type)
	{
		if(temp.getTag()!=objCard.Tag.BIG||!isThereBigItem())
		{
			if(counter>findItem(type).size()-cardsInPlay.findCardsIndex(8, type).size()||temp.getEffect(0)==8)
			{
				cardsInPlay.addCard(temp);
				environment.getEffectHandler().handleEffect(temp.getSecondaryType(), temp.getEffect(0), temp);
				environment.getEffectHandler().handleEffect(temp.getSecondaryType(), temp.getEffect(1), temp);
			}
			else carriedCards.addCard(temp);
		}else
		{
			JOptionPane.showMessageDialog(null, "nie mo¿esz zagrac tej karty bo masz ju¿ du¿y item");
			hand.addCard(temp);
		}
	}
	private void equipWeapon(objCard temp)
	{
		if(temp.getTag()!=objCard.Tag.BIG||!isThereBigItem())
		{
			if(temp.getSecondaryType()==objCard.SecondaryType.ONEHANDWEAPON)
			{
				if(freeHandCounter-1>=findWeapon().size()+cardsInPlay.findCardsIndex(null, objCard.SecondaryType.TWOHANDWEAPON).size()-cardsInPlay.findCardsIndex(8, objCard.SecondaryType.ONEHANDWEAPON).size()-2*cardsInPlay.findCardsIndex(8, objCard.SecondaryType.TWOHANDWEAPON).size()||temp.getEffect(0)==8)
				{
					cardsInPlay.addCard(temp);
					environment.getEffectHandler().handleEffect(temp.getSecondaryType(), temp.getEffect(0), temp);
					environment.getEffectHandler().handleEffect(temp.getSecondaryType(), temp.getEffect(1), temp);
				}
				else carriedCards.addCard(temp);
			}
			else
			{
				if(freeHandCounter-2>=findWeapon().size()+cardsInPlay.findCardsIndex(8, objCard.SecondaryType.TWOHANDWEAPON).size()-cardsInPlay.findCardsIndex(8, objCard.SecondaryType.ONEHANDWEAPON).size()-2*cardsInPlay.findCardsIndex(8, objCard.SecondaryType.TWOHANDWEAPON).size()||temp.getEffect(0)==8)
				{
					cardsInPlay.addCard(temp);
					environment.getEffectHandler().handleEffect(temp.getSecondaryType(), temp.getEffect(0), temp);
					environment.getEffectHandler().handleEffect(temp.getSecondaryType(), temp.getEffect(1), temp);
				}
				else carriedCards.addCard(temp);
			}
		}else
		{
			JOptionPane.showMessageDialog(null, "nie mo¿esz zagrac tej karty bo masz ju¿ du¿y item");
			hand.addCard(temp);
		}
	}
	public void discardCardfromHand(int index)
	{
		objCard temp=hand.getCard(index);
		fireEvent(GameEvent.EventType.DSICARD, temp);
		temp=hand.removeCard(index);
		environment.discardCard(temp);
	}
	public void discardCardFromPlay(int index)
	{
		objCard temp=cardsInPlay.getCard(index);
		fireEvent(GameEvent.EventType.DSICARD, temp);
		temp=cardsInPlay.removeCard(index);
		environment.discardCard(temp);
	}
	public void discardCarriedCard(int index)
	{
		objCard temp=carriedCards.getCard(index);
		fireEvent(GameEvent.EventType.DSICARD, temp);
		temp=carriedCards.removeCard(index);
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
		temp.addAll(cardsInPlay.findCardsIndex(null, objCard.SecondaryType.TWOHANDWEAPON));
		return temp;
	}
	public Vector<Integer> findClass()
	{
		return cardsInPlay.findCardsIndex(null, objCard.SecondaryType.CLASS);
	}
	public Vector<Integer> findOtherItem()
	{
		return cardsInPlay.findCardsIndex(null, objCard.SecondaryType.OTHERITEM);
	}
	public Vector<Integer> findItem(objCard.SecondaryType type)
	{
		return cardsInPlay.findCardsIndex(null, type);
	}
	public void moveFromPlayToCarried(Vector<Integer> cardIndexes)
	{
		objCard temp;
		for(int i=0; i<cardIndexes.size();i++)
		{
			temp=cardsInPlay.removeCard(cardIndexes.elementAt(i));
			carriedCards.addCard(temp);
			fireEvent(GameEvent.EventType.INVENTORYCHANGED, temp);
		}
	}
	public void moveFromPlayToCarried(int cardIndex)
	{
		objCard temp=cardsInPlay.removeCard(cardIndex);
		carriedCards.addCard(temp);
		fireEvent(GameEvent.EventType.INVENTORYCHANGED, temp);
	}
	public void moveFromCarriedToPlay(int cardIndex)
	{
		objCard temp=carriedCards.removeCard(cardIndex);
		switch (temp.getSecondaryType())
		{
		case ARMOR:
			equipItem(temp,armorCounter,temp.getSecondaryType());
			break;
		case BOOTS:
			equipItem(temp,footgearCounter,temp.getSecondaryType());
			break;
		case HAT:
			equipItem(temp,headgearCounter,temp.getSecondaryType());
			break;
		case ONEHANDWEAPON:
			equipWeapon(temp);
			break;
		case TWOHANDWEAPON:
			equipWeapon(temp);
			break;
		case OTHER:
			cardsInPlay.addCard(temp);
			break;
		default:
			break;
		}
		fireEvent(GameEvent.EventType.INVENTORYCHANGED, temp);
	}
	public void sellTreasureFromHand(int index)
	{
		objCard temp =hand.removeCard(index);
		money+=temp.getValue();
		if(money>=1000)
		{
			levelUp(1);
			money-=1000;
		}
		fireEvent(GameEvent.EventType.DSICARD, temp);
		environment.discardCard(temp);
	}
	public void sellTreasureFromCarried(int index)
	{
		objCard temp =carriedCards.removeCard(index);
		money+=temp.getValue();
		if(money>=1000)
		{
			levelUp(1);
			money-=1000;
		}
		fireEvent(GameEvent.EventType.DSICARD, temp);
		environment.discardCard(temp);
	}
	public void sellTreasureFromPlayed(int index)
	{
		objCard temp =cardsInPlay.removeCard(index);
		money+=temp.getValue();
		if(money>=1000)
		{
			levelUp(1);
			money-=1000;
		}
		fireEvent(GameEvent.EventType.DSICARD, temp);
		environment.discardCard(temp);
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
		if(myTurnPhase==TurnPhase.NOTMYTURN)
		{
			myTurnPhase=TurnPhase.ITEMSARRANGE;
			fireEvent(GameEvent.EventType.TURNSTARTED, this);
		}
		else JOptionPane.showMessageDialog(null, "nie mo¿esz zacz¹æ tury jeœli jest twoja tura");
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
				myTurnPhase=TurnPhase.FIGHT;

				objMonster monst=new objMonster(temp);
				environment.setCurrentFight(new objFight(monst, this, environment));
				environment.getEffectHandler().handleEffect(temp.getSecondaryType(),temp.getEffect(0), monst);
				environment.getCurrentFight().addListener(environment.getEffectHandler());
				fireEvent(GameEvent.EventType.FIGHTSTARTED,this);
				fireEvent("Monster",temp);
				break;
			case DISASTER:
				fireEvent(GameEvent.EventType.DISASTER, temp);
				fireEvent("Disaster",temp);
				environment.resolveStackTopCard();
				break;
			case CLASS:

			case OTHER:
				fireEvent("Other",temp);
				hand.addCard(temp);
				break;
			default:
				throw new IllegalStateException();
			}
		}
		else JOptionPane.showMessageDialog(null, "z³a kolejnoœæ faz tury");
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
				environment.getCurrentFight().addListener(environment.getEffectHandler());
				fireEvent(GameEvent.EventType.FIGHTSTARTED,this);
			}
			else JOptionPane.showMessageDialog(null, "teraz mo¿esz zagrac tylko potwora");
		}
		else JOptionPane.showMessageDialog(null, "z³a kolejnoœæ faz tury");
	}
	public void lootRoom()
	{
		if(myTurnPhase==TurnPhase.KICKDOOR)
		{
			myTurnPhase=TurnPhase.LOOT;
			this.drawDoor(1);
			charity();
		}
		else JOptionPane.showMessageDialog(null, "z³a kolejnoœæ faz tury");
	}
	public void charity()
	{
		if(myTurnPhase==TurnPhase.FIGHT||myTurnPhase==TurnPhase.LOOT||myTurnPhase==TurnPhase.KICKDOOR)
		{
			myTurnPhase=TurnPhase.CHARITY;
		}
		else JOptionPane.showMessageDialog(null, "z³a kolejnoœæ faz tury");
	}
	public void endTurn()
	{
		if(myTurnPhase==TurnPhase.CHARITY&&hand.size()<=5)
		{
			myTurnPhase=TurnPhase.NOTMYTURN;
			if(levelUpsCounter>=3)environment.closeSeal();
			levelUpsCounter=0;
			money=0;
			fireEvent(GameEvent.EventType.TURNEND, this);
			environment.nextPlayer();
		}
		else JOptionPane.showMessageDialog(null, "Musisz miec na rece maksymalnie piec kart");
	}
	public void endImmediately()
	{
		myTurnPhase=TurnPhase.NOTMYTURN;
		if(levelUpsCounter>=3)environment.closeSeal();
		levelUpsCounter=0;
		money=0;
		fireEvent(GameEvent.EventType.TURNEND, this);
		environment.nextPlayer();
	}
    public void setCurrentPhase()
    {
    	this.myTurnPhase=objPlayer.TurnPhase.FIGHT;
    }
	public void die()
	{
		if(cardsInPlay.findCardsIndex("Kid", objCard.SecondaryType.CLASS).size()==0){
			while(hand.size()>0)discardCardfromHand(0);
			while(carriedCards.size()>0)if(carriedCards.getCard(0).getEffect(0)!=4||cardsInPlay.getCard(0).getSecondaryType()!=objCard.SecondaryType.OTHER&&cardsInPlay.getCard(0).getType()==objCard.Type.DOOR)discardCarriedCard(0);
			while(cardsInPlay.size()>0)discardCardFromPlay(0);
			environment.getEffectHandler().addContinuousEffect(42, this);
			if(this==environment.getCurrentPlayer())endImmediately();
		}
		else levelUp(-1);
	}
	public int getPower()
	{
		int power=level;
		Vector<objCard> temp= getCardsInPlay().findCards(null, objCard.SecondaryType.ARMOR);
		temp.addAll(getCardsInPlay().findCards(null, objCard.SecondaryType.BOOTS));
		temp.addAll(getCardsInPlay().findCards(null, objCard.SecondaryType.HAT));
		temp.addAll(getCardsInPlay().findCards(null, objCard.SecondaryType.ONEHANDWEAPON));
		temp.addAll(getCardsInPlay().findCards(null, objCard.SecondaryType.TWOHANDWEAPON));
		temp.addAll(getCardsInPlay().findCards(null, objCard.SecondaryType.OTHERITEM));
		temp.addAll(getCardsInPlay().findCards(null, objCard.SecondaryType.ITEMENCHANTER));
		for(int i=0;i<temp.size();i++)power+=(temp.elementAt(i)).getBonus();
		return power;
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
	    	i.next().gameEventOccurred(event);
	    }
	}

	public synchronized void addListener(GameActionEventListener listener)
	{
		listenersA.add(listener);
	}
	public synchronized void removeListener(GameActionEventListener listener)
	{
	    listenersA.remove(listener);
	}
	private synchronized void fireEvent(String type, objCard card)
	{
	    GameActionEvent event = new GameActionEvent(this, type, card);
	    Iterator<GameActionEventListener> i = listenersA.iterator();
	    while(i.hasNext())
	    {
	    	i.next().gameActionEventOccurred(event);
	    }
	}
	public int getMoney() {
		return money;
	}


public int getPlayerId()
{
	return PlayerId;
}

public int getHeadgearCounter() {
	return headgearCounter;
}

public void setHeadgearCounter(int headgearCounter) {
	this.headgearCounter = headgearCounter;
}






}
