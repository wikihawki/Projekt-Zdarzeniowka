import java.awt.*;
import java.util.Vector;
public class MunchkinPaintAndLayout extends Canvas
{
	
	protected final int gapFaceDown = 10, gapFaceUp = 20, gapColumn = 20;

	private final Color clrBackground = new Color(75, 141, 221);
	
	protected objMenuSystem menuSystem;

	
	public MunchkinPaintAndLayout ()
	{
				
		setupMenuSystem();
		setupColumnSystem();
			
	}
	
	private void setupMenuSystem ()
	{
		
		String[] strMenuItemText = {"Deal", "Easy difficulty", "Medium difficulty", "Hard difficulty", "Card image Sonic the Hedgehog",
	"Card image Belgium flag", "Card image Dutch mouse", "Card image Finding Nemo", "Card image UK flag", "Card image Vanessa Mae"};
		boolean[] boolMenuItemSep = {true, false, false, true, false, false, false, false, false, false};
		
		menuSystem = new objMenuSystem(strMenuItemText, boolMenuItemSep);
		
	}
	
	private void setupColumnSystem ()
	{
		
	
		
	}
	
	protected void performMenuAction ()
	{
		
		
	}
	
	public void newGame ()
	{
		
		
	}
	
	private void setCardBackImage (int index)
	{
		
		
	}
	
	public void setupCards (Image[][] imgCards, Image[] imgCardBack) //Sets up the images from the applet
	{
	
		
	}
		
	private void layoutCards ()
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
