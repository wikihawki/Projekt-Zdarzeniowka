
import java.awt.*;

public class MunchkinHand extends MunchkinGroup
{
	
	private final Color clrSlotHolder = new Color(149, 146, 140);
	private Image imgCardBack = null;
	private objInstruction paintInstruction = new objInstruction(0,0);
	protected int handX = 0, handY = 0;
	private int Player;
	
	
	
	
	public MunchkinHand (int handX, int handY,int player)
	{
		
		this.handX = handX;
		this.handY = handY;
		this.Player = player;
	}
	
	protected int gethandX ()
	{
		return handX;
	}
	protected int gethandY ()
	{return handY;}
	protected void setCardBackImage (Image imgCardBack)
	{
		this.imgCardBack = imgCardBack;
	}
	
	protected int isMouseCard(int x, int y)
	{
		int i;
		//int cardHeight = handY + 96;
	//	int cardWidth = handX + 72;
		//System.out.println("x "+x+" y "+y+" karta handx"+handX+" handY "+handY);
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