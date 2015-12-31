import java.awt.*;
import java.util.Vector;
public class MunchkinPaintAndLayout extends Canvas
{
	
	protected final int gapFaceDown = 10, gapFaceUp = 20, gapColumn = 20;

	private final Color clrBackground = new Color(231, 218, 167);
	
	protected objMenuSystem menuSystem;
	protected MunchkinColumn[] cardColumn = new MunchkinColumn[10];
	protected MunchkinDeck cardDeck = new MunchkinDeck();
	protected int cardBackImageUsing = 0;
	protected Image[] imgCardBack;
	protected objInstruction currentInstruction, dragPaintInstruction; //Previous instruction is used for dragging
	public MunchkinPaintAndLayout ()
	{
				
		setupMenuSystem();
		setupColumnSystem();
			
	}
	
	private void setupMenuSystem ()
	{
		
		String[] strMenuItemText = {"Nowa Gra", "Zapisz Grê", "Wczytaj Grê"};
		boolean[] boolMenuItemSep = {true, false, false};
		
		menuSystem = new objMenuSystem(strMenuItemText, boolMenuItemSep);
		
	}
	
	private void setupColumnSystem ()
	{
		
		for (int column = 0; column < 10; column++)
		{
			cardColumn[column] = new MunchkinColumn((gapColumn + ((71 + gapColumn) * column)), 50);
		}
		
	}
	
	protected void performMenuAction ()
	{
		int menuAction = menuSystem.getMenuAction();
		repaint();
	}
	
	public void newGame ()
	{
		
		//currentInstruction.reset();
		
	}
	
	private void setCardBackImage (int index)
	{
		
		cardBackImageUsing = index;
		
		for (int column = 0; column < 10; column++)
		{
			cardColumn[column].setCardBackImage(imgCardBack[cardBackImageUsing]);
		}
		
		cardDeck.setCardBackImage(imgCardBack[cardBackImageUsing]);
		
	}
	
	
	
	public void setupCards (Image[][] imgCards, Image[] imgCardBack) //Sets up the images from the applet
	{
	
		
	}
		
	
	
	public void update (Graphics g)
	{
		paint(g);
	}
	private void clip (objInstruction clipInstruction, Graphics grpOffScreen, Graphics g)
	{
		
		int startX = clipInstruction.getStartX();
		int startY = clipInstruction.getStartY();
		int width = clipInstruction.getWidth();
		int height = clipInstruction.getHeight();

		grpOffScreen.clipRect(startX, startY, width, height);
		g.clipRect(startX, startY, width, height);
		
		grpOffScreen.setColor(clrBackground);
		grpOffScreen.clearRect(startX, startY, width, height);
		
	}		
	
	public void paint (Graphics g)
	{
			
		Image imgOffScreen = createImage(getSize().width, getSize().height);
		Graphics grpOffScreen = imgOffScreen.getGraphics();
		if (menuSystem.isMenuVisible())
		{
			clip(menuSystem.getPaintInstruction(), grpOffScreen, g);
		}
		
		grpOffScreen.setClip(0, 0, getSize().width, getSize().height);
		menuSystem.drawMenu(grpOffScreen);
		g.drawImage(imgOffScreen, 0, 0, this);
	
		
	}
	
	protected void drawCard (Graphics grpOffScreen, Image imgCard, int startX, int startY) //Called by solitareColumn() to paint each card
	{
		grpOffScreen.drawImage(imgCard, startX, startY, this);
		grpOffScreen.setColor(new Color(149,146,140)); //Grey
		grpOffScreen.drawRect(startX, startY, 71, 96); //Draw a border around the card
		
	}

}
