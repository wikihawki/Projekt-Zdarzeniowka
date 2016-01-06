import java.awt.*;
import java.awt.event.*;


public class MunchkinWindow extends MunchkinPaintAndLayout implements MouseListener, MouseMotionListener
{
	
	private final int refreshRate = 5;
	private int refreshCounter = 0;
	private boolean updateDrag = false;
	private MunchkinGUI GUI;
	objCardWindow singletonFrame ;
	public MunchkinWindow(MunchkinGUI gui)
	{
		this.GUI=gui;
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		singletonFrame  =  objCardWindow.getInstance(this,0);
	      
	       singletonFrame.setVisible(false);
		
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
			
			if (logikaMunchkin.getHand(0).isMouseCard(x, y,0)!=0) //Check if the menu or menu items were clicked
			{
			 singletonFrame.setCardIndex(logikaMunchkin.getPlayer(0).getHand().getCard(logikaMunchkin.getHand(0).isMouseCard(x, y,0)-1).getIdNr());	
			 singletonFrame.drawChanges(singletonFrame,logikaMunchkin.getPlayer(0).getHand().getCard(logikaMunchkin.getHand(0).isMouseCard(x, y,0)-1).getIdNr());
			 singletonFrame.repaint();
		     singletonFrame.setVisible(true);
	
		     
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
