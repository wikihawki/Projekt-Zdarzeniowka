import java.awt.Image;


public class objTreasureCard extends objCard
{
	private int bonus;
	private int value;
	private int restrictions;
	public objTreasureCard(Type type, Image imgCard, String name, String discription,int bonus, int value, int restriction, int effect) {
		super(type, imgCard, name, discription, effect);
		this.bonus=bonus;
		this.value=value;
		this.restrictions=restriction;
	}
	public int getBonus() {
		return bonus;
	}
	public int getValue() {
		return value;
	}
	public int getRestrictions() {
		return restrictions;
	}



}
