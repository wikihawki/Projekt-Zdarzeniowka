//Author: ^-^ Veerle ^-^
//Extends a solite stack with code specific for a spider solitare deck
import java.awt.*;

public class MunchkinDeck extends MunchkinStack
{
	
	private final int endX = 850, startY = 550;
	private int deckCardsShowing = 0;
	private Image imgCardBack = null;
	
	public void spiderSolitareDeck ()
	{
	}
	
	public void update () //Updates the amount of card back images to show
	{
		
		if (size() == 0) //Prevents dividing zero
		{
			deckCardsShowing = 0;
		}
		else
		{
			deckCardsShowing = size() / 10;
		}
		
	}
	
	public void setCardBackImage (Image imgCardBack)
	{
		this.imgCardBack = imgCardBack;
	}
	
	public void paintDeck (Graphics grpOffScreen, MunchkinPaintAndLayout mainApp)
	{
		
		for (int card = 0; card < deckCardsShowing; card++)
		{
			mainApp.drawCard(grpOffScreen, imgCardBack, (endX - (20 * card)), startY);
		}
	
	}
	
	public boolean clickedDeck (int x, int y) //Checks if the top card in the deck was clicked
	{
		
		if (deckCardsShowing == 0)
		{
			return false;
		}
		
		int cardX = endX - (20 * (deckCardsShowing - 1));
		
		if (x >= cardX && x <= (cardX + 96) && y >= startY && y <= (startY + 71))
		{
			return true;
		}
		
		return false;
		
	}
	
	public boolean successfullDeckDeal (MunchkinColumn[] cardColumn) //Tests if it can deal a deck, and then does so
	{
		
		for (int column = 0; column < 10; column++) //Tests that no columns are empty
		{
			
			if (cardColumn[column].size() == 0)
			{				
				return false;
			}
			
		}
		
		objCard card = null;
		
		for (int column = 0; column < 10; column++) //Tests that all the last cards are face up
		{
			
			card = cardColumn[column].getLastCard();
			
			if (card.isFaceDown())
			{
				return false;
			}
			
		}
		
		for (int column = 0; column < 10; column++) //Deals one card per column
		{
			cardColumn[column].addCard(removeCard((size() - 1)));
		}
		
		deckCardsShowing--; //Decrease amount of full decks to represent
		
		return true; //Allows the main programme to repaint
		
	}
	
}