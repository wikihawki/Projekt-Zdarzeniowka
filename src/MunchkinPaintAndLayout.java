import java.awt.*;
import java.util.Vector;
public class MunchkinPaintAndLayout extends Canvas
{
	
	protected final int gapFaceDown = 10, gapFaceUp = 20, gapColumn = 20;

	private final Color clrBackground = new Color(231, 218, 167);
	
	protected objMenuSystem menuSystem;
	protected Image imgCardBack;
	protected objInstruction currentInstruction, dragPaintInstruction; 
	public MunchkinPaintAndLayout ()
	{
				
		setupMenuSystem();
		
			
	}
	
	private void setupMenuSystem ()
	{
		
		String[] strMenuItemText = {"Nowa Gra", "Zapisz Grê", "Wczytaj Grê"};
		boolean[] boolMenuItemSep = {true, false, false};
		
		menuSystem = new objMenuSystem(strMenuItemText, boolMenuItemSep);
		
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
	
	public void setCardBackImage (Image index)
	{
		
		imgCardBack = index;
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
		drawCard(grpOffScreen,imgCardBack,100,100);
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
