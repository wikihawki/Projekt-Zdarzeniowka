
public class objMonster extends objEntity
{
	private String name;
	private int bonus;
	private int levelReward;
	private int treasures;
	private int escapeBonus;
	private objCard myCard;
	private int badStuff;
	private boolean effectTookPlace;
	public objMonster(objCard card)
	{
		myCard=card;
		setName(card.getName());
		this.bonus=0;
		levelReward=card.getReward();
		this.treasures=card.getTreasures();
		badStuff=card.getEffect(1);
		effectTookPlace=false;
	}
	public String getName() {
		return name;
	}
	public int getStrength() {
		if(bonus==-1)return -1;
		return bonus+myCard.getLevel();
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
		return (bonus+=amount);
	}
	public void setBonus(int bonus)
	{
		this.bonus=bonus;
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
	public int getBadStuff() {
		return badStuff;
	}
	public void setBadStuff(int badStuff) {
		this.badStuff = badStuff;
	}
	public boolean didEffectTookPlace() {
		return effectTookPlace;
	}
	public void setEffectTookPlace(boolean effectTookPlace) {
		this.effectTookPlace = effectTookPlace;
	}

}
