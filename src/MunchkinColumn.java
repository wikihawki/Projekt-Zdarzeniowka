//Athor: ^-^ Veerle ^-^
//Class for storing a column of solitare cards, extends the basic stack and adds features only for solitare columns
import java.awt.*;

public class MunchkinColumn extends MunchkinStack
{
	
	private final Color clrSlotHolder = new Color(149, 146, 140);
	
	private Image imgCardBack = null;
	private objInstruction paintInstruction = new objInstruction(0,0);
	
	public MunchkinColumn (int startX, int startY)
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
			colHeight = getHeight() + 30;
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
					
		for (int card = 0; card < size(); card++) //Checks each card for which one the mouse was clicked/pressed
		{
						
			currentCard = (objCard)getCard(card);
			
			if (card == (size() - 1))
			{
				
				if (y >= gap && y <= (gap + 96))
				{
					return card;
				}
				
			}
			else if (currentCard.isFaceDown())
			{
				
				if (y >= gap && y <= (gap + gapFaceDown))
				{
					return card;					
				}
				else
				{
					gap += gapFaceDown;
				}
				
			}
			else
			{
				
				if (y >= gap && y <= (gap + gapFaceUp))
				{
					return card;
					
				}
				else
				{
					gap += gapFaceUp;
				}
				
			}
			
		}
		
		return -1;			
		
	}
	
	protected boolean canFlipCard (int mouseOverCard)
	{
		
		if (mouseOverCard == -1) //Should never get this, but just in case
		{
			return false;
		}
		else if (mouseOverCard == (size() - 1)) //Check if it is the last card
		{
			
			objCard card = (objCard)getCard(mouseOverCard); //Gets a copy of the card
			
			if (card.isFaceDown()) //Checks if it is currently face down
			{
				
				card.setFaceDown(false); //Face up
				paintInstruction.setRect(startX, getHeight(mouseOverCard), 71, 96);
				return true;
				
			}
			
		}
		
		return false;
		
	}
	
	protected objInstruction getPaintInstruction ()
	{
		return paintInstruction;
	}
	
	public objInstruction getDragStartInstruction (int dragCard)
	{
		return new objInstruction(startX, getHeight(dragCard - 1), 71, getStackHeight(dragCard));
	}
	
	protected boolean canBeDragged (int startCard)
	{
		
		if (startCard != -1)
		{
			
			objCard firstCard = (objCard)getCard(startCard);
			
			if (firstCard.isFaceDown())
			{
				return false;
			}			
			
			for (int card = (startCard + 1); card < size(); card++) //Checks any cards ahead of it
			{
				
				if (!ruleCanBeDragged((objCard)getCard(card - 1), (objCard)getCard(card)))
				{
					return false;
				}
				
			}
			
			return true;
			
		}
		
		return false;
		
	}
	
	protected boolean ruleCanBeDragged (objCard previousCard, objCard currentCard)
	{
		return true;
	}
	
	protected boolean canBeDropped (objCard dragCard)
	{
		
		if (ruleCanBeDropped(dragCard, (objCard)getLastCard()))
		{
			return true;			
		}
		
		return false;
		
	}
	
	protected boolean ruleCanBeDropped (objCard dragCard, objCard columnCard)
	{
		return true;
	}
	
	protected void drawExtra (Graphics grpOffScreen)
	{
		
		if (size() == 0) //If the column is empty
		{
			
			grpOffScreen.setColor(clrSlotHolder); 
			grpOffScreen.fillRect(startX, startY, 71, 96); //Makes a slot holder
			grpOffScreen.setColor(new Color(0,0,0)); //black
			grpOffScreen.drawRect(startX, startY, 71, 96); //Gives the holder a border
			
		}
		
	}
	
	protected Image getFaceDownImage ()
	{
		return imgCardBack;
	}		
	
}