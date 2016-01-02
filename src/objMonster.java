
public class objMonster
{
	private String name;
	private int strength;
	private int levelReward;
	private int treasures;
	private int badStuff;
	
	public objMonster(String name, int level, int levelR, int treasures, int badStuff)
	{
		setName(name);
		this.strength=level;
		levelReward=levelR;
		this.treasures=treasures;
		this.badStuff=badStuff;
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
	public int getBadStuff() {
		return badStuff;
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
}
