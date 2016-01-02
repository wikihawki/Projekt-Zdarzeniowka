//Author: ^-^ Veerle ^-^
//Object representing a playing card
import java.awt.*;

public class objCard
{
	public enum Type{MONSTER, DISASTER, OTHER, OTHERITEM, BOOTS, ARMOR, HEADGEAR, WEAPON, SEAL}
	private String name="";
	private String discription="";
	private Type type;
	
	
	private boolean isFaceDown = true;	
	private Image imgCard = null;
	
	public objCard (Type type, Image imgCard, String name, String discription)
	{
		

		this.type=type;
		this.name=name;
		this.imgCard = imgCard;
		this.discription=discription;
		
	}
	
	public boolean isFaceDown () 
	{
		return isFaceDown;
	}
	
	public void setFaceDown (boolean isFaceDown)
	{
		this.isFaceDown = isFaceDown;
	}
	
	public Image getImg ()
	{
		return imgCard;
	}

	public String getName() {
		return name;
	}

	public Type getType() {
		return type;
	}

	public String getDiscription() {
		return discription;
	}
	


	
}