//Author: ^-^ Veerle ^-^
//Object representing a playing card
import java.awt.*;

public class objCard
{
	
	private int colour = 0, rank = 0;
	private boolean isFaceDown = true;	
	private Image imgCard = null;
	
	public void objCard ()
	{
	}
	
	public objCard (int colour, int rank, Image imgCard)
	{
		
		this.colour = colour;
		this.rank = rank;
		this.imgCard = imgCard;
		
	}
	
	public int getColour ()
	{
		return colour;
	}
	
	public int getRank ()
	{
		return rank;
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
	
}