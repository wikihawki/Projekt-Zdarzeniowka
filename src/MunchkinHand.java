
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
		if ((x >= 235-72/2+(i*72+i*10) && x <= (235 +72/2)+(i*72+i*10)) && (y >= 700-100 && y <= 700)) 
		{
			return i+1;
		}
}	

	return 0;

		
	}
		
	protected int cardMouseIsOver (int y)
	{
		
		int gap = 700;
		objCard currentCard = null;
					
	return 0;
	}
	

	protected Image getFaceDownImage ()
	{
		return imgCardBack;
	}		
	
	public int getPlayer()
	{return Player;}
}