import java.awt.Image;


public class objDoorCard extends objCard
{
	
	private int level=0;
	private int effect=0;
	private int secondaryEffect=0;
	public objDoorCard(Type type, Image imgCard, String name, String discription, int level, int effect, int effect2)
	{
		super(type, imgCard, name, discription);
		this.level=level;
		this.effect=effect;
		this.secondaryEffect=effect2;
		// TODO Auto-generated constructor stub
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
	
	
}
