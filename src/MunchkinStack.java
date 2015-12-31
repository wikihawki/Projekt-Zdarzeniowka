import java.awt.*;
import java.util.Vector;

public class MunchkinStack
{
	
	private Vector stack = new Vector();
	protected final int gapFaceDown = 10, gapFaceUp = 20;
	private int height = 0, previousStackSize = 0;
	
	protected int startX = 0, startY = 0;
	
	public void solitareStack ()
	{
	}
	
	protected int getHeight ()
	{
		
		if (size() == 0)
		{
			return 0;
		}
		else
		{
			return height;
		}
		
	}
	
	private void amendStackHeight (objCard card)
	{
		
		int gap = 0;
		int size = size();
		
		if (card.isFaceDown())
		{
			gap = gapFaceDown;
		}
		else
		{
			gap = gapFaceUp;
		}
		
		if (size > previousStackSize) //If a card has been added
		{
			
			if (size == 1) //If only one card exists
			{
				height = 96;
			}
			else
			{
				height += gap;
			}
			
		}
		else //If a card has been removed
		{
			
			if (size == 0)
			{
				height = 0;
			}
			else if (size == 1)
			{
				height = 96;
			}
			else
			{
				height -= gap;
			}

		}
		
		previousStackSize = size;
		
	}
	
	protected void addCard (objCard card)
	{
		

		stack.addElement(card);
		amendStackHeight(card);
		
	}
	
	protected objCard removeCard (int index)
	{
		
		objCard card = (objCard)stack.remove(index);
		amendStackHeight(card);
		
		return card;
		
	}
	
	protected void addStack (Vector vecStack)
	{
		
		int addStackHeight = vecStack.size();
		objCard card = null;
		
		for (int counter = 0; counter < addStackHeight; counter++)
		{
			
			card = (objCard)vecStack.remove(0);
			addCard(card);
			
		}
		
	}
	
	protected objCard getCard (int index)
	{
		return (objCard)stack.elementAt(index);
	}
	
	public Vector getStack (int startIndex)
	{
		
		Vector vecStack = new Vector();
		
		for (int counter = startIndex; counter < size(); counter++)
		{
			vecStack.addElement(getCard(counter));
		}
		
		return stack;
		
	}
	
	protected objCard getLastCard ()
	{
		return (objCard)stack.lastElement();
	}
	
	protected Vector removeStack (int startIndex)
	{
		
		Vector vecStack = new Vector();
		
		while (startIndex < stack.size())
		{
			vecStack.addElement(removeCard(startIndex));
		}
		
		return vecStack;
		
	}
	
	protected void clear ()
	{
				
		stack.clear();
		previousStackSize = 0;
			
	}
	
	protected int size ()
	{
		return stack.size();
	}
	
	protected int getHeight (int cardIndex) //Finds the startY of a certain card
	{
		return cycleStackHeight(startY, 0, cardIndex, false);
	}
	
	protected int getStackHeight (int fromCard)
	{
		return cycleStackHeight(0, fromCard, size(), true);
	}
	
	private int cycleStackHeight (int gap, int startCard, int endCard, boolean fullLastCard)
	{
		
		objCard card = null;
		
		for (int counter = startCard; counter < endCard; counter++)
		{
			
			if (counter == (endCard - 1) && fullLastCard)
			{
				gap += 96;
			}
			else
			{
				
				card = (objCard)getCard(counter);
				
				if (card.isFaceDown())
				{
					gap += gapFaceDown;
				}
				else
				{
					gap += gapFaceUp;
				}
				
			}
			
		}
		
		return gap;
		
	}			
	
	protected void paintStack (Graphics grpOffScreen, MunchkinPaintAndLayout mainApp)
	{		

		drawExtra(grpOffScreen);
			
		objCard card = null;
		int gap = startY;
			
		for (int counter = 0; counter < size(); counter++)
		{
			
			card = (objCard)getCard(counter);
				
			if (card.isFaceDown())
			{
				//Can't paint images inside this class, so have to reference the mainApp and paint them there
				mainApp.drawCard(grpOffScreen, getFaceDownImage(), startX, gap);	
				gap += gapFaceDown;
				
			}
			else
			{
				
				mainApp.drawCard(grpOffScreen, card.getImg(), startX, gap);
				gap += gapFaceUp;
				
			}

		}
		
	}
	
	protected void drawExtra (Graphics grpOffScreen)
	{
	}
	
	protected Image getFaceDownImage ()
	{
		return null;
	}
	
}