import java.util.Vector;

public class objFight
{
	private Vector<objMonster> monsters;
	private objPlayer mainPlayer;
	private objPlayer helperPlayer;
	private int playersStrength;
	private int sidesBonuses;
	public objFight(objMonster monster, objPlayer player)
	{
		monsters=new Vector<objMonster>();
		monsters.add(monster);
		setHelperPlayer(null);
		setMainPlayer(player);
		
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
	public int getplayersStrength()
	{
		return playersStrength;
	}
	public void updatePlayersStrength()
	{
		playersStrength=mainPlayer.getLevel();
		Vector<objCard> temp= mainPlayer.getCardsInPlay().findCards(null, objCard.Type.ARMOR);
		temp.addAll(mainPlayer.getCardsInPlay().findCards(null, objCard.Type.BOOTS));
		temp.addAll(mainPlayer.getCardsInPlay().findCards(null, objCard.Type.HEADGEAR));
		temp.addAll(mainPlayer.getCardsInPlay().findCards(null, objCard.Type.WEAPON));
		temp.addAll(mainPlayer.getCardsInPlay().findCards(null, objCard.Type.OTHERITEM));
		if(helperPlayer!=null)
		{
			playersStrength+=helperPlayer.getLevel();
			temp.addAll(helperPlayer.getCardsInPlay().findCards(null, objCard.Type.ARMOR));
			temp.addAll(helperPlayer.getCardsInPlay().findCards(null, objCard.Type.BOOTS));
			temp.addAll(helperPlayer.getCardsInPlay().findCards(null, objCard.Type.HEADGEAR));
			temp.addAll(helperPlayer.getCardsInPlay().findCards(null, objCard.Type.WEAPON));
			temp.addAll(helperPlayer.getCardsInPlay().findCards(null, objCard.Type.OTHERITEM));
		}
		for(int i=0;i<temp.size();i++)playersStrength+=((objTreasureCard)temp.elementAt(i)).getBonus();
	}
	public objPlayer getMainPlayer() {
		return mainPlayer;
	}
	public void setMainPlayer(objPlayer mainPlayer) {
		this.mainPlayer = mainPlayer;
	}
	public objPlayer getHelperPlayer() {
		return helperPlayer;
	}
	public void setHelperPlayer(objPlayer helperPlayer) 
	{
		if(helperPlayer!=mainPlayer)this.helperPlayer = helperPlayer;
	}
	public Vector<objMonster> getMonsters() {
		return monsters;
	}
	
	
}
