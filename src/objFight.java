import java.util.Vector;

public class objFight
{
	private Vector<objMonster> monsters;
	private objPlayer mainPlayer;
	private objPlayer helperPlayer;
	private int playersStrength;
	private int sidesBonuses;
	private int escapeDiff;
	private int treasuresForHelper;
	public objFight(objMonster monster, objPlayer player)
	{
		monsters=new Vector<objMonster>();
		monsters.add(monster);
		setHelperPlayer(null);
		setMainPlayer(player);
		treasuresForHelper=0;
	}
	public void resolveBattle()
	{
		if(getMonstersStrength() < getPlayersStrength())
		{
			int levels=0;
			for(int i=0; i<this.monsters.size();i++)levels+=this.monsters.elementAt(i).getLevelReward();
			mainPlayer.levelUp(levels);

		}
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
	private void setHelperPlayer(objPlayer helperPlayer)
	{
		if(helperPlayer!=mainPlayer)this.helperPlayer = helperPlayer;
	}
	public Vector<objMonster> getMonsters() {
		return monsters;
	}
	public int getEscapeDiff() {
		return escapeDiff;
	}
	public void addEscapeDiff(int amount) {
		this.escapeDiff += amount;
	}
	public int getTreasuresForHelper() {
		return treasuresForHelper;
	}


}
