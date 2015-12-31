
import java.awt.*;

public class MunchkinHand extends MunchkinGroup
{
	
	private final Color clrSlotHolder = new Color(149, 146, 140);
	private Image imgCardBack = null;
	private objInstruction paintInstruction = new objInstruction(0,0);
	
	
	
	
	
	
	public MunchkinHand (int startX, int startY)
	{
		
		this.startX = startX;
		this.startY = startY;
		
	}
	
	protected int getStartX ()
	{
		return startX;
	}
	
	protected void setCardBackImage (Image imgCardBack)
	{
		this.imgCardBack = imgCardBack;
	}
	
	protected boolean isMouseOverColumn (int x, int y)
	{
		
		int colHeight = startY + 96;
		
		if (size() != 0)
		{
			colHeight = getWidth() + 30;
		}
		
		if ((x >= startX && x <= (startX + 71)) && (y >= startY && y <= colHeight)) //Check if mouse is in this column's card area
		{
			return true;
		}
		
		return false;
		
	}
		
	protected int cardMouseIsOver (int y)
	{
		
		int gap = startY;
		objCard currentCard = null;
					
	return 0;
	}
	
	protected objInstruction getPaintInstruction ()
	{
		return paintInstruction;
	}

	protected Image getFaceDownImage ()
	{
		return imgCardBack;
	}		
	
}