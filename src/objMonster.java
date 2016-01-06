
public class objMonster extends objEntity
{
	private String name;
	private int strength;
	private int levelReward;
	private int treasures;
	private int escapeBonus;
	private objCard myCard;
	public objMonster(objCard card)
	{
		myCard=card;
		setName(card.getName());
		this.strength=card.getLevel();
		levelReward=card.getReward();
		this.treasures=card.getTreasures();
	}
	public String getName() {
		return name;
	}
	public int getStrength() {
		return strength;
	}
	public int getLevelReward() {
		return levelReward;
	}
	public int getTreasures() {
		return treasures;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int increaseStrength(int amount)
	{
		return (strength+=amount);
	}
	public int increaseTreasures(int amount)
	{
		return (treasures+=amount);
	}
	public objCard getMyCard() {
		return myCard;
	}
	public int getEscapeBonus() {
		return escapeBonus;
	}
	public void setEscapeBonus(int escapeBonus) {
		this.escapeBonus = escapeBonus;
	}

}
