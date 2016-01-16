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
	private static objCardWindow myInstance;
    private MunchkinWindow window;
    public static objCard Card;
    private int CardPlace;
    public JLabel label;
    private String Source="Hand";
    public  JPanel pnlButtons = new JPanel(new GridLayout(8, 1));
    public  JPanel pnlImage = new JPanel();
    public JButton odrzuc = new JButton("Odrzu�");
    public JButton skill1 = new JButton("U�yj karty");
    public JButton zaloz = new JButton("Dodaj na st�");
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

        odrzuc.setBounds(200, 200, 100, 30);
   	    if (odrzuc.getActionListeners().length<1){odrzuc.addActionListener(this);}
   	    //odrzuc.setIcon(icon);
        pnlButtons.add(odrzuc);



        skill1.setBounds(200, 200, 100, 30);
   	    if (skill1.getActionListeners().length<1){skill1.addActionListener(this);}
   	   // skill1.setIcon(icon);
        pnlButtons.add(skill1);




        zaloz.setBounds(200, 200, 100, 30);
   	    if (zaloz.getActionListeners().length<1){zaloz.addActionListener(this);}
        pnlButtons.add(zaloz);
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
			    	window.getLogic().getPlayer(window.getFocusedPlayer()-1).discardCardfromHand(CardPlace-1);
			  }else
			  {
				  window.getLogic().getPlayer(window.getFocusedPlayer()-1).discardCardFromPlay(window.getLogic().getPlayer(window.getFocusedPlayer()-1).getCardsInPlay().getCardIndex(Card));
			  }
			        window.repaint();
			       this.setVisible(false);
		        }else
		  if (src == skill1)
			    {

			  Object[] listops= {"List 1","List 2"};

			  String ch=(String) JOptionPane.showInputDialog(rootPane, "Select","Select List Box", JOptionPane.PLAIN_MESSAGE,null, listops,"list1");
			  System.out.println(ch);
			       this.setVisible(false);
		        }else
		  if (src == zaloz)
			    {
			    	window.getLogic().getPlayer(window.getFocusedPlayer()-1).playCard(Card, null);
			        window.repaint();
			       this.setVisible(false);
		        }

	}
@SuppressWarnings("deprecation")
   private void menageButtons(objCard karta)
   {
	
	if(karta.getType()==objCard.Type.TREASURE)
	{
		if(window.getLogic().getCurrentPlayer().getMyTurnPhase()!=objPlayer.TurnPhase.FIGHT&&Source=="Hand")
		{
		odrzuc.setVisible(true);
		odrzuc.setEnabled(true);
		zaloz.setVisible(true);
		zaloz.setEnabled(true);
		skill1.setVisible(false);
		skill1.setEnabled(false);
		}
		else if(window.getLogic().getCurrentPlayer().getMyTurnPhase()!=objPlayer.TurnPhase.FIGHT&&Source=="Equipment")
		{
			odrzuc.setVisible(true);
			odrzuc.setEnabled(true);
			zaloz.setVisible(false);
			zaloz.setEnabled(false);
			skill1.setVisible(true);
			skill1.setEnabled(true);
		}else if(Source=="Hand")
		{
			odrzuc.setVisible(true);
			odrzuc.setEnabled(true);
			zaloz.setVisible(true);
			zaloz.setEnabled(true);
			skill1.setVisible(false);
			skill1.setEnabled(false);
		}
		else if(Source=="Equipment")
		{
			odrzuc.setVisible(true);
			odrzuc.setEnabled(true);
			zaloz.setVisible(false);
			zaloz.setEnabled(false);
			skill1.setVisible(true);
			skill1.setEnabled(true);
		}
	}else
	{
		odrzuc.setVisible(true);
		odrzuc.setEnabled(true);
		zaloz.setVisible(false);
		zaloz.setEnabled(false);
		skill1.setVisible(true);
		skill1.setEnabled(true);
	}
	
}
   public void setSignalSource(String source)
   {
	   this.Source=source;
   }



}