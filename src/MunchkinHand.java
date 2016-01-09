
import java.awt.*;

public class MunchkinHand extends MunchkinGroup
{
	
	private final Color clrSlotHolder = new Color(149, 146, 140);
	private Image imgCardBack = null;

	private int Player;
	
	
	
	
	public MunchkinHand (int player)
	{
		

		this.Player = player;
	}
	

	protected void setCardBackImage (Image imgCardBack)
	{
		this.imgCardBack = imgCardBack;
	}
	
	protected int isMouseCard(int x, int y,int Player)
	{
		int i;
		
       for(i =0;i<size();i++)
        {
		if ((x >= handX-72/2+(i*72+i*10) && x <= (handX +72/2)+(i*72+i*10)) && (y >= handY-100 && y <= handY)) //Check if mouse is in this column's card area
		{
			return i+1;
		}
}	

	return 0;

		
	}
		
	protected int cardMouseIsOver (int y)
	{
		
		int gap = handY;
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
	
	public int getPlayer()
	{return Player;}
}