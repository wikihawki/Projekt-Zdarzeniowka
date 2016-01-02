import java.awt.Image;


public class objDoorCard extends objCard
{
	
	private int level=0;
	private int reward=0;
	private int treasures=0;
	private int effect=0;
	private int secondaryEffect=0;
	public objDoorCard(Type type, Image imgCard, String name, String discription, int level, int effect, int effect2,int reward, int treasures)
	{
		super(type, imgCard, name, discription);
		this.level=level;
		this.effect=effect;
		this.secondaryEffect=effect2;
		this.reward=reward;
		this.treasures=treasures;
	}
	public int getLevel() {
		return level;
	}
	public int getEffect() {
		return effect;
	}
	public int getSecondaryEffect() {
		return secondaryEffect;
	}
	public int getReward() {
		return reward;
	}
	public int getTreasures() {
		return treasures;
	}
	
	
}
