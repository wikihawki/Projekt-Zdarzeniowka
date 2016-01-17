import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.sun.glass.ui.Window;


public class MunchkinWindow extends MunchkinPaintAndLayout implements MouseListener, MouseMotionListener,GameActionEventListener
{

	private final int refreshRate = 5;
	private int refreshCounter = 0;
	private boolean updateDrag = false;
	private MunchkinGUI GUI;
	private objCardWindow CardsingletonFrame ;
	private objCharacterWindow CharactersingletonFrame ;
	private objFightFrame FightsingletonFrame ;
	
public void useCardWindow(objCard karta,objPlayer player )
{
	//CardsingletonFrame .setCardPklaceOnHand(id);
	CardsingletonFrame.setPlayer(player);
	CardsingletonFrame .setSignalSource("Equipment");
	CardsingletonFrame .drawChanges(CardsingletonFrame ,karta);
// singletonFrame.repaint();
 CardsingletonFrame.setVisible(true);
}	
	
	
	public MunchkinWindow(MunchkinGUI gui)
	{
		this.GUI=gui;
		   if (this.getMouseListeners().length<1){this.addMouseListener(this);}
		   if (this.getMouseMotionListeners().length<1){this.addMouseMotionListener(this);}
		   for(int x =0;x<4;x++)
		   {
			   logikaMunchkin.getPlayer(x).addListener(this);
		   }
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

		if(logikaMunchkin.getCurrentPlayer().getMyTurnPhase()!=objPlayer.TurnPhase.FIGHT)
		{
			focusPlayer=logikaMunchkin.getCurrentPlayer().getPlayerId()+1;
		}
		
		
		
		if (arg0.getButton() == MouseEvent.BUTTON1)
		{

			int x = arg0.getX();
			int y = arg0.getY();
			System.out.println("X "+x+" Y "+y);
	if(logikaMunchkin.getCurrentPlayer().getMyTurnPhase()!=objPlayer.TurnPhase.FIGHT)
	{
			if (menuSystem.checkMenuClicked(x,y)) 
			{
				performMenuAction();
			}else

			if (logikaMunchkin.getCurrentPlayer().getHand().isMouseCard(x, y,0)!=0) 
			{
				int[] tmp=logikaMunchkin.getNextPlayerId(logikaMunchkin.getCurrentPlayer().getPlayerId());
				CardsingletonFrame.setPlayer(logikaMunchkin.getCurrentPlayer());
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
                        if(logikaMunchkin.isMouseOnCharacter(x, y)+1==1)
                        {
						CharactersingletonFrame.setPlyer(getFocusedPlayer());
						CharactersingletonFrame.repaint();
						CharactersingletonFrame.setVisible(true);
                        }
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
													
											
			if(logikaMunchkin.getCurrentPlayer().getMyTurnPhase()!=objPlayer.TurnPhase.FIGHT)
				{
							if (logikaMunchkin.isAboveButton(x, y)) //Check if the menu or menu items were clicked
							{
																
							 buttonPressed=true;
							 repaint();
												
							}else
							{
								buttonPressed=false;
								repaint();
						    }
			}else ////////////////////////////////////// zdarzenia walki
			{
					if (logikaMunchkin.isAboveFocusedPlayerButton(x, y)!=0) //Check if the menu or menu items were clicked
					{
						setFocusedPlayer(logikaMunchkin.isAboveFocusedPlayerButton(x, y));
		
				    }else 
				    	if (logikaMunchkin.isAboveRun(x, y)!=0) 
						{
		                 
						
							if(logikaMunchkin.isAboveRun(x, y)==5) 
							logikaMunchkin.getCurrentFight().resolveBattle();
							if(logikaMunchkin.isAboveRun(x, y)==6) 
							{
													if(logikaMunchkin.getCurrentFight().getHelperPlayer()==null)
													{
																		Object[] possibilities = {"1"," 2", "3","4","5"};
																		String s = (String)JOptionPane.showInputDialog(
																		                    null,
																		                    "Liczba wymaganych skarbów:\n"
																		                   ,
																		                    "Customized Dialog",
																		                    JOptionPane.PLAIN_MESSAGE,
																		                    null,
																		                    possibilities,
																                    "");
																if(s !=null)
																{
																
																	int k = Integer.parseInt(s);
																	logikaMunchkin.getCurrentFight().addHelper((logikaMunchkin.getPlayer(focusPlayer-1)), k);	
																}
													}
							}
						    }else
						    {
						    	repaint();
						    }
				  
				
				
				 repaint();
				}
			}
		}
	public void StartNewGame(ArrayList<String> listaGraczy)
	{
		logikaMunchkin.newGame(4, listaGraczy);
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
			{
			   try {
				   logikaMunchkin.getCurrentPlayer().endTurn();
		           } 
			   catch (IllegalStateException e) 
			       {
		    	   }
			   int[] tmp=logikaMunchkin.getNextPlayerId(logikaMunchkin.getCurrentPlayer().getPlayerId());
			   if(focusPlayer<4)
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
				int tmp=logikaMunchkin.isAboveFocusedPlayerButton(x, y);
				if(tmp <4)
				focusPlayer=tmp;
		    }else 
		    	if (logikaMunchkin.isAboveRun(x, y)!=0) 
				{
                 
				
					
					logikaMunchkin.getCurrentFight().resolveBattle();
					
					
			    }else
			    {
			    	repaint();
			    }
			    
		}
		
	
	}


	@Override
	public void gameActionEventOccurred(GameActionEvent evt) {
		if(evt.getType()=="Monster")
		{
			int numer = evt.getCard().getIdNr();
			ImageIcon icon =  new ImageIcon("src/images/karta ("+numer+").jpg");
			JOptionPane.showMessageDialog(
       			   null, 
       			   "",
       			   "Wylosowa³eœ potwora , musisz walczyæ!",
       			   JOptionPane.INFORMATION_MESSAGE,
       			  icon);
			repaint();
	
		}else
		if(evt.getType()=="Disaster")
		{
			int numer = evt.getCard().getIdNr();
			ImageIcon icon =  new ImageIcon("src/images/karta ("+numer+").jpg");
			JOptionPane.showMessageDialog(
       			   null, 
       			   "",
       			   "Wylosowa³eœ katastrofê ,jej efekty dzia³aj¹ natychmiastowo",
       			   JOptionPane.INFORMATION_MESSAGE,
       			  icon);
			repaint();
		}else
		{
			int numer = evt.getCard().getIdNr();
			ImageIcon icon =  new ImageIcon("src/images/karta ("+numer+").jpg");
			JOptionPane.showMessageDialog(
       			   null, 
       			   "",
       			   "DRZWI!",
       			   JOptionPane.INFORMATION_MESSAGE,
       			  icon);
			repaint();
		}

		
	}

}

