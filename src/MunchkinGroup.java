
import java.awt.*;
import java.util.Vector;

public class MunchkinGroup
{
	
	private Vector<objCard> stack = new Vector<objCard>();
	
	private int width = 0, previousStackSize = 0;

	
	public void MunchkinGroup ()
	{
	}
	
	protected int getWidth ()
	{
		
		if (size() == 0)
		{
			return 0;
		}
		else
		{
			return width;
		}
		
	}
	
	protected void addCard (objCard card)
	{
		

		stack.addElement(card);
		
	}
	
	protected objCard removeCard (int index)
	{
		
		objCard card = stack.remove(index);
	
		
		return card;
		
	}
	protected objCard removeLastCard()
	{
		return stack.elementAt(stack.size()-1);
	}
	
	protected void addStack (Vector<objCard> vecStack)
	{
		
		int addStackwidth = vecStack.size();
		objCard card = null;
		
		for (int counter = 0; counter < addStackwidth; counter++)
		{
			
			card = vecStack.remove(0);
			addCard(card);
			
		}
		
	}
	
	protected objCard getCard (int index)
	{
		return stack.elementAt(index);
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
		return stack.lastElement();
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
	
	
	protected Image getFaceDownImage ()
	{
		return null;
	}
	
}