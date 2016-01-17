import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class objCardWindow extends JFrame implements ActionListener{
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private objPlayer  Gracz;
	private static objCardWindow myInstance;
    private MunchkinWindow window;
    public static objCard Card;
    private int CardPlace;
    public JLabel label;
    private String Source="Hand";
    public  JPanel pnlButtons = new JPanel(new GridLayout(8, 1));
    public  JPanel pnlImage = new JPanel();
    public  JPanel pnlSealImage = new JPanel();
    public JButton odrzuc = new JButton("Odrzuæ");
    public JButton skill1 = new JButton("U¿yj karty");
    public JButton zaloz = new JButton("Dodaj na stó³");
    public JButton zagrajPotwora = new JButton("Zagraj potwora");
    public JButton ZdejmijItem = new JButton("Zdejmij Item");
    public JButton ZalozItem = new JButton("Za³ó¿ Item");
    // the constructor
    private objCardWindow(MunchkinWindow Window,int index)  {


    	this.window=Window;
        this.setSize(300, 400);
        this.setResizable(false);
        this.setTitle("Singleton Frame. Timestamp:" +
         System.currentTimeMillis());
        pnlButtons.setBounds(200, 200, 100, 400);
        pnlImage.setBounds(0, 0, 200, 400);


        BufferedImage img;
        img  =scaleImage(205, 285,"src/images/Buttons/buttonLong_beige.png");
        ImageIcon icon=new ImageIcon((Image) img);

        odrzuc.setBounds(200, 200, 50, 30);
   	    if (odrzuc.getActionListeners().length<1){odrzuc.addActionListener(this);}
   	    //odrzuc.setIcon(icon);
        pnlButtons.add(odrzuc);



        skill1.setBounds(200, 200, 50, 30);
   	    if (skill1.getActionListeners().length<1){skill1.addActionListener(this);}
   	   // skill1.setIcon(icon);
        pnlButtons.add(skill1);




        zaloz.setBounds(200, 200,50, 30);
   	    if (zaloz.getActionListeners().length<1){zaloz.addActionListener(this);}
        pnlButtons.add(zaloz);
       // zaloz.setIcon(icon);
        add(pnlButtons);
        
        zagrajPotwora.setBounds(200, 200, 50, 30);
   	    if (zagrajPotwora.getActionListeners().length<1){zagrajPotwora.addActionListener(this);}
        pnlButtons.add(zagrajPotwora);
       // zaloz.setIcon(icon);
        add(pnlButtons);
        
        
        ZdejmijItem.setBounds(200, 200, 50, 30);
   	    if ( ZdejmijItem.getActionListeners().length<1){ ZdejmijItem.addActionListener(this);}
        pnlButtons.add( ZdejmijItem);
       // zaloz.setIcon(icon);
        add(pnlButtons);
        
        
        ZalozItem.setBounds(200, 200, 50, 30);
   	    if ( ZalozItem.getActionListeners().length<1){ ZalozItem.addActionListener(this);}
        pnlButtons.add( ZalozItem);
       // zaloz.setIcon(icon);
        add(pnlButtons);







        label = new JLabel();
        add(label, BorderLayout.WEST);

        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        //drawChanges(this);
    }
    public static objCardWindow getInstance(MunchkinWindow Window,int index) {
        if (myInstance == null)
            myInstance = new objCardWindow(Window,index);

        return myInstance;
    }
    public void setCardIndex(objCard karta)
    {
    	this.Card=karta;
    }
    private void initComponents(JFrame frame, objCard karta) {
    	Card=karta;
    	 menageButtons(karta);
         setSize(400, 400);
         this.getContentPane().setBackground( new Color(231, 218, 167) );
         setTitle("Karta");
         setLocationRelativeTo(null);
         BufferedImage img;
         pnlButtons.setBackground( new Color(231, 218, 167) );
       //  setVisible(true);

         if(karta.getType()==objCard.Type.DOOR)
     	{
        	 img  =scaleImage(205, 285,"src/images/karta (" +karta.getIdNr()+ ").jpg");
     	}else{
     		 img  =scaleImage(205, 285,"src/images/karta (" +(karta.getIdNr())+ ").jpg");
     	}
         if(karta.getType()==objCard.Type.SEAL)
      	{
         	 img  =scaleImage(300, 300,"src/images/karta (" +karta.getIdNr()+ ").jpg");
      	}
         ImageIcon icon=new ImageIcon((Image) img);
         label.setIcon(icon);

    }
    public BufferedImage scaleImage(int WIDTH, int HEIGHT, String filename) {
        BufferedImage bi = null;
        ImageIcon ii ;
        ii=null;
        try {
          ii = new ImageIcon(ImageIO.read(new File(filename)));//path to image
            bi = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = (Graphics2D) bi.createGraphics();
            g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
            g2d.drawImage(ii.getImage(), 0, 0, WIDTH, HEIGHT, null);
        } catch (Exception e) {
        	System.out.println("blad");
            e.printStackTrace();
            return null;
        }
        return bi;
    }
    public void drawChanges(objCardWindow window,objCard karta)
    {

    	initComponents(this,karta);


    }
    public void setCardPklaceOnHand(int place)

    {
    	this.CardPlace=place;
    }
    @Override
	public void actionPerformed(ActionEvent e) {
		//System.out.println("Count of listeners: " + ((JButton) e.getSource()).getActionListeners().length);

	      Object src =e.getSource();

		  if (src == odrzuc)
			    {
			//  .out.println(CardIdex-1+" odrzucono");
			  
			  if(Source=="Hand")
			  {
				  if(Card.getType()==objCard.Type.DOOR)
				  {
			    	window.getLogic().getPlayer(window.getFocusedPlayer()-1).discardCardfromHand(CardPlace-1);
				  }else
				  {	window.getLogic().getPlayer(window.getFocusedPlayer()-1).sellTreasureFromHand(CardPlace-1);}
			  }else
			  {

				if(Card.getType()==objCard.Type.DOOR)
			     {
			      window.getLogic().getPlayer(window.getFocusedPlayer()-1).discardCardFromPlay(window.getLogic().getPlayer(window.getFocusedPlayer()-1).getCardsInPlay().getCardIndex(Card));
				 }else{	
					  if(window.getLogic().getPlayer(window.getFocusedPlayer()-1).getCardsInPlay().getCardIndex(Card)>=0)
					  {
						  window.getLogic().getPlayer(window.getFocusedPlayer()-1).sellTreasureFromPlayed(window.getLogic().getPlayer(window.getFocusedPlayer()-1).getCardsInPlay().getCardIndex(Card));
					  }
					  else
					  {
						  window.getLogic().getPlayer(window.getFocusedPlayer()-1).sellTreasureFromCarried(window.getLogic().getPlayer(window.getFocusedPlayer()-1).getCarriedCards().getCardIndex(Card)); 
					  }
					  
					         

			     }
			  }
			        window.repaint();
			       this.setVisible(false);
		        }else
		  if (src == skill1)
			    {
 
			  Vector<objEntity> listops=window.getLogic().getEffectHandler().getValidTargets(Card);
              if(Source =="Hand")
              {
                if(listops!=null)
               {
            	  
            	    if(!listops.contains(null)&&!listops.isEmpty())
            	   {
            		  objEntity tmp1 =(objEntity) JOptionPane.showInputDialog(rootPane, "Select","Select List Box", JOptionPane.PLAIN_MESSAGE,null, listops.toArray(),"list1");
            		  System.out.println(tmp1);
            		  if(tmp1!=null)
            		  window.getLogic().getPlayer((window.getFocusedPlayer()-1)).playCard(Card,tmp1 );
            	    }
            	         else JOptionPane.showMessageDialog(null, "Brak celu");
                    }
                    else window.getLogic().getPlayer((window.getFocusedPlayer()-1)).playCard(Card,null);
			        this.setVisible(false);
               }else
               {
            	   if(listops!=null)
                   {
                	  
                	    if(!listops.contains(null)&&!listops.isEmpty())
                	   {
                		  objEntity tmp1 =(objEntity) JOptionPane.showInputDialog(rootPane, "Select","Select List Box", JOptionPane.PLAIN_MESSAGE,null, listops.toArray(),"list1");
                		  System.out.println(tmp1);
                		  if(tmp1!=null)
                		  window.getLogic().getPlayer((window.getFocusedPlayer()-1)).useCardFromBackpack(Card,tmp1 );
                	    }
                	         else JOptionPane.showMessageDialog(null, "Brak celu");
                        }
                        else window.getLogic().getPlayer((window.getFocusedPlayer()-1)).useCardFromBackpack(Card,null);
    			        this.setVisible(false);
               }
		      }else
		      if (src == zaloz)
			    {

	              if(Card.getSecondaryType()!=objCard.SecondaryType.OTHER)
	              {
			    	  Vector<objEntity> listops=window.getLogic().getEffectHandler().getValidTargets(Card);
	                if(listops!=null)
	               {
	            	  
	            	    if(!listops.contains(null)&&!listops.isEmpty())
	            	   {
	            		  objEntity tmp1 =(objEntity) JOptionPane.showInputDialog(rootPane, "Select","Select List Box", JOptionPane.PLAIN_MESSAGE,null, listops.toArray(),"list1");
	            		  System.out.println(tmp1);
	            		  if(tmp1!=null)
	            		  window.getLogic().getPlayer((window.getFocusedPlayer()-1)).playCard(Card,tmp1 );
	            	    }
	            	         else JOptionPane.showMessageDialog(null, "Brak celu");
	                    }
	                    else window.getLogic().getPlayer((window.getFocusedPlayer()-1)).playCard(Card,null);
				        this.setVisible(false);
	               }
	              else
	              {
				    	window.getLogic().getPlayer(window.getFocusedPlayer()-1).playCard(Card, null);
				        window.repaint();
				       this.setVisible(false);
	              }
			       
			       
		        }

		  
		  if (src == ZalozItem)
		    {
			  window.getLogic().getPlayer(window.getFocusedPlayer()-1).moveFromCarriedToPlay(window.getLogic().getPlayer(window.getFocusedPlayer()-1).getCarriedCards().getCardIndex(Card));
			  window.repaint();
		       this.setVisible(false);
		    }
		    
		  
		  
		  if (src == ZdejmijItem)
		    {
			  window.getLogic().getPlayer(window.getFocusedPlayer()-1).moveFromPlayToCarried(window.getLogic().getPlayer(window.getFocusedPlayer()-1).getCardsInPlay().getCardIndex(Card));
			  window.repaint();
		       this.setVisible(false);
		    }
		  
		  
		  if (src == zagrajPotwora)
		    {
			    if(window.getLogic().getCurrentPlayer().getMyTurnPhase()==objPlayer.TurnPhase.KICKDOOR)
			    {
			    window.getLogic().getPlayer(window.getFocusedPlayer()-1).lookForTrouble(Card);
			    window.getLogic().getPlayer(window.getFocusedPlayer()-1).getHand().removeCard(window.getLogic().getPlayer(window.getFocusedPlayer()-1).getHand().getCardIndex(Card));
			    window.repaint();
		        this.setVisible(false);
			    }else
			    {
			    	   JOptionPane.showMessageDialog(null, "To nie jest odpowiednia faza na zagrywanie potwora","error", JOptionPane.ERROR_MESSAGE);
			    }
		    }
		  
		  
		  
	}
@SuppressWarnings("deprecation")
   private void menageButtons(objCard karta)
   {
	RemoveButtons();
	System.out.println(window.getLogic().getCurrentPlayer().getPlayerId()+" Current Player "+(window.getFocusedPlayer()-1)+" focused player");
	if(window.getLogic().getCurrentPlayer().getPlayerId()==Gracz.getPlayerId())
	{
	if(karta.getType()==objCard.Type.TREASURE)
	{
		if(window.getLogic().getCurrentPlayer().getMyTurnPhase()!=objPlayer.TurnPhase.FIGHT&&Source=="Hand")
		{
		
			pnlButtons.add(odrzuc);
			pnlButtons.add(zaloz);
		}
		else if(window.getLogic().getCurrentPlayer().getMyTurnPhase()!=objPlayer.TurnPhase.FIGHT&&Source=="Equipment")
		{
			
			pnlButtons.add(odrzuc);
			if(Card.getSecondaryType()!=objCard.SecondaryType.OTHER)
			{
				if(window.getLogic().getPlayer(window.getFocusedPlayer()-1).getCarriedCards().getCardIndex(Card)>=0)
				{
					pnlButtons.add(ZalozItem);
				}else
				{
					pnlButtons.add(ZdejmijItem);
				}
				
			}else
			{
				pnlButtons.add(skill1);
			}
		}else if(Source=="Hand")
		{
			pnlButtons.add(odrzuc);
		}
		else if(Source=="Equipment")
		{
			pnlButtons.add(odrzuc);
			if(Card.getSecondaryType()!=objCard.SecondaryType.OTHER)
			{
				pnlButtons.add(zaloz);
			}else
			{
				pnlButtons.add(skill1);
			}
		}
	}else if(karta.getType()==objCard.Type.SEAL)
	{
	
	}else 
	{
		pnlButtons.add(odrzuc);
		if(Card.getSecondaryType()!=objCard.SecondaryType.MONSTER)
		{
		pnlButtons.add(skill1);	
		}else
		{
			pnlButtons.add(zagrajPotwora);	
		}

	}
	
	}
}
   public void setSignalSource(String source)
   {
	   this.Source=source;
   }
 
   private void RemoveButtons()
   {
	

		pnlButtons.remove(odrzuc);
		pnlButtons.remove(skill1);
		pnlButtons.remove(zaloz);
		pnlButtons.remove(zagrajPotwora);
		pnlButtons.remove(ZdejmijItem);
		pnlButtons.remove(ZalozItem);
		//pnlButtons = new JPanel(new GridLayout(8, 1));
   }
   
  public void setPlayer(objPlayer player)
  {
	  this.Gracz=player;
  }

}