import java.util.Random;
import java.util.Vector;


public class objFight
{
	private Vector<objMonster> monsters;
	private objPlayer mainPlayer;
	private objPlayer helperPlayer;
	private int playersStrength;
	private int sidesBonuses;
	private int treasuresForHelper;
	private int escapeBonus;
	objGameLogic parent;
	public objFight(objMonster monster, objPlayer player, objGameLogic parent)
	{
		monsters=new Vector<objMonster>();
		monsters.add(monster);
		setHelperPlayer(null);
		setMainPlayer(player);
		treasuresForHelper=0;
		this.parent=parent;
	}
	public void resolveBattle()
	{
		if(getMonstersStrength() < getPlayersStrength())
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

			for(int i=0; i<monsters.size();i++)if(!tryToRunAway(i, mainPlayer))parent.getEffectHandler().handleEffect(monsters.elementAt(i).getMyCard().getSecondaryType(),monsters.elementAt(i).getMyCard().getEffect(1), mainPlayer);
			if(helperPlayer!=null)for(int i=0; i<monsters.size();i++)if(!tryToRunAway(i, helperPlayer))parent.getEffectHandler().handleEffect(monsters.elementAt(i).getMyCard().getSecondaryType(),monsters.elementAt(i).getMyCard().getEffect(1), helperPlayer);
		}
	}
	private boolean tryToRunAway(int monsterNr, objPlayer player)
	{
		Random gen= new Random();
		int result=gen.nextInt(6)+1;
		objMonster temp=monsters.elementAt(monsterNr);
		result+=temp.getEscapeBonus();
		Vector<objCard> cards= player.getCardsInPlay().findCards(null, objCard.SecondaryType.ARMOR);
		cards.addAll(player.getCardsInPlay().findCards(null, objCard.SecondaryType.BOOTS));
		cards.addAll(player.getCardsInPlay().findCards(null, objCard.SecondaryType.ONEHANDWEAPON));
		cards.addAll(player.getCardsInPlay().findCards(null, objCard.SecondaryType.OTHERITEM));
		for(int i=0;i<cards.size();i++)
		{
			if(cards.elementAt(i).getEffect(1)==10)result++;
			if(cards.elementAt(i).getEffect(1)==11)result--;
		}
		return (result>=5);
	}
	public void addhelper(objPlayer helper, int treasuresReward) throws IllegalStateException
	{
		if(helperPlayer==null)
		{
			setHelperPlayer(helper);
			treasuresForHelper=treasuresReward;
		}
		else throw new IllegalStateException("pomagac mo¿e tylko jedna osoba");
	}
	public void addBonus(int bonus)
	{
		sidesBonuses+=bonus;
	}
	public int getSidesBonuses()
	{
		return sidesBonuses;
	}
	public int getMonstersStrength()
	{
		int monsters=0;
		for(int i=0; i<this.monsters.size();i++)monsters+=this.monsters.elementAt(i).getStrength();
		return monsters;
	}
	public int getPlayersStrength()
	{
		return playersStrength;
	}
	public void updatePlayersStrength()
	{
		playersStrength=mainPlayer.getLevel();
		Vector<objCard> temp= mainPlayer.getCardsInPlay().findCards(null, objCard.SecondaryType.ARMOR);
		temp.addAll(mainPlayer.getCardsInPlay().findCards(null, objCard.SecondaryType.BOOTS));
		temp.addAll(mainPlayer.getCardsInPlay().findCards(null, objCard.SecondaryType.HAT));
		temp.addAll(mainPlayer.getCardsInPlay().findCards(null, objCard.SecondaryType.ONEHANDWEAPON));
		temp.addAll(helperPlayer.getCardsInPlay().findCards(null, objCard.SecondaryType.TWOHANDWEAPON));
		temp.addAll(mainPlayer.getCardsInPlay().findCards(null, objCard.SecondaryType.OTHERITEM));
		if(helperPlayer!=null)
		{
			playersStrength+=helperPlayer.getLevel();
			temp.addAll(helperPlayer.getCardsInPlay().findCards(null, objCard.SecondaryType.ARMOR));
			temp.addAll(helperPlayer.getCardsInPlay().findCards(null, objCard.SecondaryType.BOOTS));
			temp.addAll(helperPlayer.getCardsInPlay().findCards(null, objCard.SecondaryType.HAT));
			temp.addAll(helperPlayer.getCardsInPlay().findCards(null, objCard.SecondaryType.ONEHANDWEAPON));
			temp.addAll(helperPlayer.getCardsInPlay().findCards(null, objCard.SecondaryType.TWOHANDWEAPON));
			temp.addAll(helperPlayer.getCardsInPlay().findCards(null, objCard.SecondaryType.OTHERITEM));
		}
		for(int i=0;i<temp.size();i++)playersStrength+=(temp.elementAt(i)).getBonus();
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
		if(helperPlayer!=mainPlayer)this.helperPlayer = helperPlayer;
	}
	public Vector<objMonster> getMonsters()
	{
		return monsters;
	}
	public void addMonster(objMonster e)
	{
		monsters.add(e);
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

}
