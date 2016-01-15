import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Vector;


public class objFight implements GameEventListener
{
	private List<GameEventListener> listeners = new ArrayList<GameEventListener>();
	private Vector<objMonster> monsters;
	private objPlayer mainPlayer;
	private objPlayer helperPlayer;
	private int playersStrength;
	private int monstersBonus;
	private int playersBonus;
	private int treasuresForHelper;
	private int escapeBonus;
	private int mainPlayerEscape, helperEscape;
	private int levelBonus;
	objGameLogic parent;
	public objFight(objMonster monster, objPlayer player, objGameLogic parent)
	{
		monsters=new Vector<objMonster>();
		monsters.add(monster);
		setHelperPlayer(null);
		setMainPlayer(player);
		treasuresForHelper=0;
		this.parent=parent;
		mainPlayer.addListener(this);
	}
	public void resolveBattle()
	{
		if(getMonstersStrength() < getPlayersStrength()&&getMonstersStrength()>=0)
		{
			int levels=0,treasures=0;
			for(int i=0; i<this.monsters.size();i++)
			{
				levels+=this.monsters.elementAt(i).getLevelReward();
				treasures+=this.monsters.elementAt(i).getTreasures();
			}

			mainPlayer.levelUp(levels);
			if(helperPlayer!=null)
			{
				if(treasures>treasuresForHelper)
				{
					helperPlayer.drawTreasure(treasuresForHelper);
					treasures-=treasuresForHelper;
				}
				else
				{
					helperPlayer.drawTreasure(treasures);
					treasures=0;
				}
			}
			mainPlayer.drawTreasure(treasures);
		}
		else
		{
			for(int i=0; i<monsters.size();i++)if(!tryToRunAway(i, true))parent.getEffectHandler().handleEffect(monsters.elementAt(i).getMyCard().getSecondaryType(),monsters.elementAt(i).getBadStuff(), mainPlayer);
			if(helperPlayer!=null)for(int i=0; i<monsters.size();i++)if(!tryToRunAway(i, false))parent.getEffectHandler().handleEffect(monsters.elementAt(i).getMyCard().getSecondaryType(),monsters.elementAt(i).getBadStuff(), helperPlayer);
		}
		this.fireEvent(GameEvent.EventType.FIGHTOVER, null);
	}
	private boolean tryToRunAway(int monsterNr, boolean which)
	{
		if(which)fireEvent(GameEvent.EventType.RUNAWAY,mainPlayer);
		else fireEvent(GameEvent.EventType.RUNAWAY, helperPlayer);
		Random gen= new Random();
		int result=gen.nextInt(6)+1;
		objMonster temp=monsters.elementAt(monsterNr);
		result+=temp.getEscapeBonus();
		if(which)result+=mainPlayerEscape;
		else result+=helperEscape;
		return (result>=5);
	}
	public void addHelper(objPlayer helper, int treasuresReward) throws IllegalStateException
	{
		if(helperPlayer==null)
		{
			this.fireEvent(GameEvent.EventType.FIGHTCHANGED, null);
			setHelperPlayer(helper);
			treasuresForHelper=treasuresReward;
		}
		else throw new IllegalStateException("pomagac mo¿e tylko jedna osoba");
	}
	public void addBonus(int bonus)
	{
		fireEvent(GameEvent.EventType.FIGHTCHANGED,null);
		if(bonus>0)playersBonus+=bonus;
		else monstersBonus-=bonus;
	}
	public void playerChanged()
	{
		fireEvent(GameEvent.EventType.FIGHTCHANGED, null);

	}
	public int getMonstersBonus()
	{
		return monstersBonus;
	}
	public int getPlayerBonus()
	{
		return playersBonus;
	}
	public int getMonstersStrength() //-1 automatyczne zwyciestwo potworow
	{
		int monsters=monstersBonus;
		for(int i=0; i<this.monsters.size();i++)
		{
			int temp=this.monsters.elementAt(i).getStrength();
			if(temp==-1)return -1;
			monsters+=temp;
		}
		return monsters;
	}
	public int getPlayersStrength()
	{
		return playersStrength;
	}
	public void updatePlayers()
	{
		playersStrength=mainPlayer.getLevel();
		playersStrength+=playersBonus;
		Vector<objCard> temp= mainPlayer.getCardsInPlay().findCards(null, objCard.SecondaryType.ARMOR);
		temp.addAll(mainPlayer.getCardsInPlay().findCards(null, objCard.SecondaryType.BOOTS));
		temp.addAll(mainPlayer.getCardsInPlay().findCards(null, objCard.SecondaryType.HAT));
		temp.addAll(mainPlayer.getCardsInPlay().findCards(null, objCard.SecondaryType.ONEHANDWEAPON));
		temp.addAll(mainPlayer.getCardsInPlay().findCards(null, objCard.SecondaryType.TWOHANDWEAPON));
		temp.addAll(mainPlayer.getCardsInPlay().findCards(null, objCard.SecondaryType.OTHERITEM));
		temp.addAll(mainPlayer.getCardsInPlay().findCards(null, objCard.SecondaryType.ITEMENCHANTER));
		for(int i=0;i<temp.size();i++)
		{
			if(temp.elementAt(i).getEffect(1)==10)mainPlayerEscape++;
			if(temp.elementAt(i).getEffect(1)==11)mainPlayerEscape--;
		}
		if(helperPlayer!=null)
		{
			playersStrength+=helperPlayer.getLevel();
			Vector<objCard> temp2=helperPlayer.getCardsInPlay().findCards(null, objCard.SecondaryType.ARMOR);
			temp2.addAll(helperPlayer.getCardsInPlay().findCards(null, objCard.SecondaryType.BOOTS));
			temp2.addAll(helperPlayer.getCardsInPlay().findCards(null, objCard.SecondaryType.HAT));
			temp2.addAll(helperPlayer.getCardsInPlay().findCards(null, objCard.SecondaryType.ONEHANDWEAPON));
			temp2.addAll(helperPlayer.getCardsInPlay().findCards(null, objCard.SecondaryType.TWOHANDWEAPON));
			temp2.addAll(helperPlayer.getCardsInPlay().findCards(null, objCard.SecondaryType.OTHERITEM));
			temp2.addAll(helperPlayer.getCardsInPlay().findCards(null, objCard.SecondaryType.ITEMENCHANTER));
			for(int i=0;i<temp.size();i++)
			{
				if(temp2.elementAt(i).getEffect(1)==10)helperEscape++;
				if(temp2.elementAt(i).getEffect(1)==11)helperEscape--;
			}
		}
		for(int i=0;i<temp.size();i++)playersStrength+=(temp.elementAt(i)).getBonus();
		fireEvent(GameEvent.EventType.FIGHTCHANGED, null);
	}
	public objPlayer getMainPlayer()
	{
		return mainPlayer;
	}
	public void setMainPlayer(objPlayer mainPlayer)
	{
		this.mainPlayer = mainPlayer;
	}
	public objPlayer getHelperPlayer()
	{
		return helperPlayer;
	}
	private void setHelperPlayer(objPlayer helperPlayer)
	{
		if(helperPlayer!=mainPlayer)
			{
				this.helperPlayer = helperPlayer;
				helperPlayer.addListener(this);
			}
	}
	public Vector<objMonster> getMonsters()
	{
		return monsters;
	}
	public void addMonster(objMonster e)
	{
		monsters.add(e);
	}
	public boolean removeMonster(objMonster monster)
	{
		fireEvent(GameEvent.EventType.FIGHTCHANGED, monster);
		return monsters.remove(monster);
	}
	public int getTreasuresForHelper()
	{
		return treasuresForHelper;
	}
	public int getEscapeBonus()
	{
		return escapeBonus;
	}
	public void setEscapeBonus(int escapeBonus)
	{
		this.escapeBonus = escapeBonus;
	}
	public boolean isThere(objCard.Tag tag)
	{
		boolean temp=false;
		for(int i=0; i<monsters.size();i++)if(monsters.elementAt(i).getMyCard().getTag()==tag)temp=true;
		return temp;

	}
	public boolean isThere(int effect)
	{
		boolean temp=false;
		for(int i=0; i<monsters.size();i++)if(monsters.elementAt(i).getMyCard().getEffect(0)==effect)temp=true;
		return temp;

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
	public int getMainPlayerEscape() {
		return mainPlayerEscape;
	}
	public int getHelperEscape() {
		return helperEscape;
	}
	public void setMainPlayerEscape(int mainPlayerEscape) {
		this.mainPlayerEscape = mainPlayerEscape;
	}
	public void setHelperEscape(int helperEscape) {
		this.helperEscape = helperEscape;
	}
	public int getTreasures()
	{
		int monsters=0;
		for(int i=0; i<this.monsters.size();i++)
		{
			int temp=this.monsters.elementAt(i).getTreasures();
			if(temp==-1)return -1;
			monsters+=temp;
		}
		return monsters;
	}
	public int getLevels()
	{
		int monsters=levelBonus;
		for(int i=0; i<this.monsters.size();i++)
		{
			int temp=this.monsters.elementAt(i).getLevelReward();
			if(temp==-1)return -1;
			monsters+=temp;
		}
		return monsters;
	}
	public void addLevelBonus(int amount)
	{
		levelBonus+=amount;
	}
	@Override
	public void gameEventOccurred(GameEvent evt) {
		if(evt.getEventType()==GameEvent.EventType.INVENTORYCHANGED)playerChanged();

	}
}
