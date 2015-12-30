import java.awt.*;
import java.awt.event.*;


public class MunchkinWindow extends MunchkinPaintAndLayout implements MouseListener, MouseMotionListener
{
	
	private final int refreshRate = 5;
	private int refreshCounter = 0;
	private boolean updateDrag = false;
	
	public MunchkinWindow()
	{
		
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
	}	
	
	
	
	
	
	
	
	
	@Override
	public void mouseDragged(MouseEvent arg0) {
		int x = arg0.getX();
		int y = arg0.getY();
		
	}
	@Override
	public void mouseMoved(MouseEvent arg0) {
		if (menuSystem.checkMouseMove(arg0.getX(),arg0.getY()))
		{
			repaint();
		}
		
		
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {

		if (arg0.getButton() == MouseEvent.BUTTON1)
		{

			int x = arg0.getX();
			int y = arg0.getY();
			
			if (menuSystem.checkMenuClicked(x,y)) //Check if the menu or menu items were clicked
			{
				performMenuAction(); //if so, perform the action of the menu item clicked
			}
			

			
		}
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
