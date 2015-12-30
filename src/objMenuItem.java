import java.awt.*;

public class objMenuItem
{
	
	private int startX = 0, startY = 0;
	private String strText = "";
	private boolean itemMouseOver = false, itemMouseOverPrevious = false, lineSep = false;
	
	private final Color clrText = new Color(0, 0, 0); //Black
	private final Color clrNormalBackground = new Color(26, 193, 66); //Light green
	private final Color clrHighlightBackground = new Color(9, 100, 30); //Dark green
	private final Font fntText = new Font("Serif", Font.PLAIN, 14);
	private final int width = 210, height = 30;
	
	public objMenuItem (int startX, int startY, String strText, boolean lineSep)
	{
		
		this.startX = startX;
		this.startY = startY;
		this.strText = strText;
		this.lineSep = lineSep;
		
	}
	
	public int getStartY ()
	{
		return startY;
	}
	
	public int getHeight ()
	{
		return height;
	}
	
	public void removeMouseOver ()
	{
		
		itemMouseOver = false;
		itemMouseOverPrevious = false;
		
	}
	
	public boolean isMouseOver (int mouseX, int mouseY)
	{
		
		itemMouseOverPrevious = itemMouseOver;
		
		if ((mouseX > startX && mouseX < (startX + width)) && (mouseY > startY && mouseY < (startY + height)))
		{
			itemMouseOver = true;			
		}
		else
		{
			itemMouseOver = false; 
		}
		
		return itemMouseOver;
		
	}
	
	public void drawMenuItem (Graphics grpOffScreen)
	{
		
		if (itemMouseOver)
		{
			grpOffScreen.setColor(clrHighlightBackground);
		}
		else
		{
			grpOffScreen.setColor(clrNormalBackground);
		}
		
		grpOffScreen.fillRect(startX, startY, width, height);
		
		grpOffScreen.setColor(clrText);
		grpOffScreen.drawString(strText, (startX + 5), (startY + 17));
		
		if (lineSep)
		{
			grpOffScreen.drawLine(startX, ((startY + height) - 1), ((startX + width) - 1), ((startY + height) - 1));
		}
		
	}
	
}