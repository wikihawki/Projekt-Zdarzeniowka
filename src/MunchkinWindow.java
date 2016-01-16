import java.awt.*;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.sun.glass.ui.Window;


public class MunchkinWindow extends MunchkinPaintAndLayout implements MouseListener, MouseMotionListener
{

	private final int refreshRate = 5;
	private int refreshCounter = 0;
	private boolean updateDrag = false;
	private MunchkinGUI GUI;
	private objCardWindow CardsingletonFrame ;
	private objCharacterWindow CharactersingletonFrame ;
	private objFightFrame FightsingletonFrame ;
	
public void useCardWindow(objCard karta )
{
	//CardsingletonFrame .setCardPklaceOnHand(id);
	CardsingletonFrame .setSignalSource("Equipment");
	CardsingletonFrame .drawChanges(CardsingletonFrame ,karta);
// singletonFrame.repaint();
 CardsingletonFrame.setVisible(true);
}	
	
	
	public MunchkinWindow(MunchkinGUI gui)
	{
		this.GUI=gui;
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.CardsingletonFrame   =  objCardWindow.getInstance(this,0);
		this.CardsingletonFrame .setVisible(false);
		this.CharactersingletonFrame   =  objCharacterWindow.getInstance(this,0);
		this.CharactersingletonFrame .setVisible(false);

		FightsingletonFrame=objFightFrame.getInstance(this, 0);
		this.FightsingletonFrame  .setVisible(false);


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
			System.out.println("X "+x+" Y "+y);
	if(logikaMunchkin.getCurrentPlayer().getMyTurnPhase()!=objPlayer.TurnPhase.FIGHT)
	{
			if (menuSystem.checkMenuClicked(x,y)) //Check if the menu or menu items were clicked
			{
				performMenuAction(); //if so, perform the action of the menu item clicked
			}else

			if (logikaMunchkin.getHand(0).isMouseCard(x, y,0)!=0) 
			{
				int[] tmp=logikaMunchkin.getNextPlayerId(logikaMunchkin.getCurrentPlayer().getPlayerId());
				CardsingletonFrame .setCardPklaceOnHand(logikaMunchkin.getHand(tmp[3]).isMouseCard(x, y,0));
				CardsingletonFrame .setSignalSource("Hand");
				CardsingletonFrame .drawChanges(CardsingletonFrame ,logikaMunchkin.getPlayer(tmp[3]).getHand().getCard(logikaMunchkin.getHand(tmp[3]).isMouseCard(x, y,0)-1));
			// singletonFrame.repaint();
		     CardsingletonFrame.setVisible(true);


			}else
				if(logikaMunchkin.isMouseOnCharacter(x, y)>=0)
				{

					CharactersingletonFrame.setPlyer(logikaMunchkin.isMouseOnCharacter(x, y)+1);
					CharactersingletonFrame.repaint();
					CharactersingletonFrame.setVisible(true);
				}else
					if(logikaMunchkin.isAboveStack(x, y)!=0)
					{
                    switch(logikaMunchkin.isAboveStack(x, y))
                    {
                    case 1:
                    	if(logikaMunchkin.getCurrentPlayer().getMyTurnPhase()==objPlayer.TurnPhase.ITEMSARRANGE)
                    	{
                    	logikaMunchkin.getCurrentPlayer().kickOpenDoor();
                    	}else	
                    	if(logikaMunchkin.getCurrentPlayer().getMyTurnPhase()==objPlayer.TurnPhase.KICKDOOR)
                    	{
                    	logikaMunchkin.getCurrentPlayer().lootRoom();
                    	}
                    	break;
                    case 2:
                    	break;
                    case 3:
                    	if(logikaMunchkin.getOpenedSeals().size()!=0)
                    	{
        				CardsingletonFrame .setSignalSource("Seal");
        				CardsingletonFrame .drawChanges(CardsingletonFrame ,logikaMunchkin.getOpenedSeals().getLastCard());
        			// singletonFrame.repaint();
        		        CardsingletonFrame.setVisible(true);
                    	}
                    	break;
             
                    }
                 
					}


		 }else///////////////////////////// Tutaj bêd¹ efekty dla walki
		 {
			 if (logikaMunchkin.getPlayer(focusPlayer-1).getHand().isMouseCard(x, y,0)!=0) 
				{
					CardsingletonFrame .setCardPklaceOnHand(logikaMunchkin.getPlayer(focusPlayer-1).getHand().isMouseCard(x, y,0));
					CardsingletonFrame .drawChanges(CardsingletonFrame ,logikaMunchkin.getPlayer(focusPlayer-1).getHand().getCard(logikaMunchkin.getPlayer(focusPlayer-1).getHand().isMouseCard(x, y,0)-1));
				// singletonFrame.repaint();
			     CardsingletonFrame.setVisible(true);


				}else
					if(logikaMunchkin.isMouseOnCharacter(x, y)>=0)
					{

						CharactersingletonFrame.setPlyer(getFocusedPlayer());
						CharactersingletonFrame.repaint();
						CharactersingletonFrame.setVisible(true);
					}
		 }
	  }
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {

	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mousePressed(MouseEvent arg0)
	{
		if (arg0.getButton() == MouseEvent.BUTTON1)
		{

			int x = arg0.getX();
			int y = arg0.getY();
			System.out.println(buttonPressed);
		if(logikaMunchkin.getCurrentPlayer().getMyTurnPhase()!=objPlayer.TurnPhase.FIGHT)
		{
			if (logikaMunchkin.isAboveButton(x, y)) //Check if the menu or menu items were clicked
			{
				 System.out.println("yup");
				 buttonPressed=true;
				 repaint();

			}else
			{System.out.println("yup");
				buttonPressed=false;
				repaint();
		    }
		}else ////////////////////////////////////// zdarzenia walki
		{
			if (logikaMunchkin.isAboveFocusedPlayerButton(x, y)!=0) //Check if the menu or menu items were clicked
			{
				setFocusedPlayer(logikaMunchkin.isAboveFocusedPlayerButton(x, y));

		    }
				  
				
				
				 repaint();
		}
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent arg0)
{
		int x = arg0.getX();
		int y = arg0.getY();
	if (arg0.getButton() == MouseEvent.BUTTON1)
	{


		if(logikaMunchkin.getCurrentPlayer().getMyTurnPhase()!=objPlayer.TurnPhase.FIGHT)
		{
			if (logikaMunchkin.isAboveButton(x, y)) //Check if the menu or menu items were clicked
			{try {
				  logikaMunchkin.getCurrentPlayer().endTurn();
		      } catch (IllegalStateException e) {
		    	

	            }
			logikaMunchkin.getCurrentPlayer().endTurn();
			int[] tmp=logikaMunchkin.getNextPlayerId(logikaMunchkin.getCurrentPlayer().getPlayerId());
			if(focusPlayer!=4)
			{
			focusPlayer++;
			}else
			{
				focusPlayer=1;
			}
			 buttonPressed=false;
			 repaint();
		      }
				  
				
		
			}else
			{
		    }
		}else
		{
			if (logikaMunchkin.isAboveFocusedPlayerButton(x, y)!=0) 
			{
				System.out.println("PP");
				focusPlayer=logikaMunchkin.isAboveFocusedPlayerButton(x, y);
		    }
				  
				
				
				 repaint();
		}
	
	}

}

