//Author: ^-^ Veerle ^-^
//Handles the menu, seeing what menu items were clicked, drawing the menu and items etc
import java.awt.*;

public class objMenuSystem
{
	
	private final Font fntHeader = new Font("Serif", Font.PLAIN, 16);	
	private final Color clrMenuHeader = new Color(13, 173, 58);
	private final Color clrMenuHeaderBorder = new Color(6, 96, 31);
	
	private final int menuHeight = 26, menuLength = 50;
	private objMenuItem[] menuItems;
	private boolean isMenuVisible = false, hideMenu = false;
	private objInstruction menuPaintInstruction = new objInstruction(0,0,0,0);
	private int menuAction = -1, mouseItemOverPrevious = -1;
	
	public objMenuSystem (String[] strMenuItemText, boolean[] boolMenuItemSep)
	{
		
		menuItems = new objMenuItem[strMenuItemText.length];		
		menuItems[0] = new objMenuItem(0, menuHeight, strMenuItemText[0], boolMenuItemSep[0]);		
		int height = menuItems[0].getHeight();
		
		for (int item = 1; item < strMenuItemText.length; item++)
		{
			menuItems[item] = new objMenuItem(0, (menuHeight + (item * height)), strMenuItemText[item], boolMenuItemSep[item]);
		}
		
	}
	
	public boolean isMenuVisible ()
	{

		if (hideMenu)
		{
			
			hideMenu = false;
			return true;
			
		}
		
		return isMenuVisible;
	}
	
	private int mouseOverMenuItem (int x, int y, boolean canBreakEarly)
	{
		
		int menuItemIndex = -1;
		
		for (int item = 0; item < menuItems.length; item++)
		{
			
			if (menuItems[item].isMouseOver(x,y))
			{
				
				menuItemIndex = item;
				
				if (canBreakEarly)
				{
					break;
				}
				
			}
			
		}
		
		return menuItemIndex;
		
	}
	
	private void invertMenuVisible () //Method for toggling the menu on/off
	{
		
		if (isMenuVisible) //If currently visible
		{
			
			hideMenu = true; //Turn on so that the paint method will paint over the last drawing of the menu
			isMenuVisible = false; //Turns menu off
			
		}
		else //If not currently visible
		{
			isMenuVisible = true; //Turn menu on
		}	
		
	}	
	
	public boolean checkMenuClicked (int x, int y) //Checks if the menu area was clicked
	{
		
		menuAction = -1;
		
		if ((x > 0 && x < 50) && (y > 0 && y < 25)) //Menu button clicked
		{
			invertMenuVisible(); //Toggles the menu on/off
		}
		else if (isMenuVisible) //If menu is visible
		{
			
			menuAction = mouseOverMenuItem(x, y, true); //Checks each menu item for if the mouse clicked it, and returns the index number of the menu item clicked
						
			if (menuAction == -1) //If no menu items were clicked
			{				
				invertMenuVisible(); //Turns off the menu (also tells the paint method to hide the menu)		
			}
			else
			{
				
				menuItems[menuAction].removeMouseOver();
				isMenuVisible = false; //Turns off the menu (since an action has been performed, the whole screen will repaint)
				
			}
			
		}
		else //If the menu was not visible and the menu header wasn't clicked
		{
			return false;
		}
		
		menuPaintInstruction.setRect(0, 0, 210, 330); //Sets the paint instruction for the menu area
		
		return true;
		
	}
	
	public int getMenuAction ()
	{
		return menuAction;
	}
	
	public objInstruction getPaintInstruction ()
	{
		return menuPaintInstruction;
	}
	
	public boolean checkMouseMove (int x, int y)
	{
		
		boolean requireRepaint = false;
		
		if (isMenuVisible)
		{
			
			int mouseOverCurrent = mouseOverMenuItem(x, y, false);
			
			if (mouseItemOverPrevious != mouseOverCurrent) //if a different menu item is selected
			{
				
				menuPaintInstruction.setRect(0, menuItems[0].getStartY(), 210, (menuItems[0].getHeight() * menuItems.length));					
				mouseItemOverPrevious = mouseOverCurrent; //stops wasteful repaints when mouse is over same item
				requireRepaint = true;			
				
			}
			
		}
		
		return requireRepaint;
		
	}
	
	public void drawMenu (Graphics grpOffScreen)
	{
		
		drawMenuHeader(grpOffScreen);
		
		if (isMenuVisible)
		{
			drawMenuItems(grpOffScreen);
		}
		
	}
	
	private void drawMenuItems (Graphics grpOffScreen)
	{
		
		for (int item = 0; item < menuItems.length; item++)
		{
			menuItems[item].drawMenuItem(grpOffScreen);
		}
		
	}
	
	private void drawMenuHeader (Graphics grpOffScreen)
	{

		grpOffScreen.setColor(clrMenuHeader); 
		grpOffScreen.fillRect(0, 0, menuLength, (menuHeight - 1)); //Menu header
		grpOffScreen.setColor(new Color(0,0,0));
		grpOffScreen.setFont(fntHeader);
		grpOffScreen.drawString("Menu", 7, 17); //Menu title
		grpOffScreen.setColor(clrMenuHeaderBorder);
		grpOffScreen.drawRect(0, 0, menuLength, (menuHeight - 1)); //Border around menu header
		
	}	
	
}