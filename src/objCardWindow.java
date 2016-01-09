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
    public static int CardIdex;
    public JLabel label;
    public  JPanel pnlButtons = new JPanel(new GridLayout(8, 1));
    public  JPanel pnlImage = new JPanel();
    public JButton odrzuc = new JButton("Odrzuæ");
    public JButton skill1 = new JButton("U¿yj karty");
    public JButton zaloz = new JButton("Dodaj na stó³");
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
    public void setCardIndex(int index)
    {
    	this.CardIdex=index;
    }
    private void initComponents(JFrame frame, int index) {
    	
         setSize(400, 400);
         this.getContentPane().setBackground( new Color(231, 218, 167) );
         setTitle("Karta");
         setLocationRelativeTo(null);
         BufferedImage img;
         pnlButtons.setBackground( new Color(231, 218, 167) );
       //  setVisible(true);
   
         
         img  =scaleImage(205, 285,"src/images/kd (" +index+ ").jpg");
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
    public int getCardIdex()
    {
     return CardIdex;
    }
    public void drawChanges(objCardWindow window,int index)
    {
    	
    	initComponents(this,index);
     
        
    }
	@Override
	public void actionPerformed(ActionEvent e) {
		//System.out.println("Count of listeners: " + ((JButton) e.getSource()).getActionListeners().length);

	      Object src =e.getSource();
	     
		  if (src == odrzuc) 
			    {
			//  .out.println(CardIdex-1+" odrzucono");
			    	window.getLogic().getPlayer(0).discardCardfromHand(CardIdex-1);
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
			    	window.getLogic().getPlayer(0).discardCardfromHand(CardIdex-1);
			        window.repaint();
			       this.setVisible(false);
		        }
		
	}
}