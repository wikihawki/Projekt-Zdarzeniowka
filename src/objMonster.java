
public class objMonster extends objEntity
{
	private String name;
	private int bonus;
	private int levelReward;
	private int treasures;
	private int escapeBonus;
	private objCard myCard;
	private int badStuff;
	private int level;
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
		level=myCard.getLevel();
	}
	public String getName() {
		return name;
	}
	public int getStrength() {
		return bonus+level;
	}
	public int getBonus()
	{
		return bonus;
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

		if(amount>-getStrength()) return (bonus+=amount);
		return getStrength();
	}
	public void setBonus(int bonus)
	{
		this.bonus=bonus;
	}
	public void setLevel(int level)
	{
		this.level=level;
	}
	public int increaseTreasures(int amount)
	{
		if(amount>-treasures)
		return (treasures+=amount);
		else
			return treasures;
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
