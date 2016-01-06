import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
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
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class objCardWindow extends JFrame implements ActionListener{
    private static objCardWindow myInstance;
    private JPanel panel ;
    int i =1;
    public  ImageIcon ii ;
    private MunchkinWindow window;
    public static int CardIdex;
    public JLabel label;
    public BufferedImage img;
    public  JPanel pnlButtons = new JPanel();
    public JButton przycisk = new JButton("Odrzuæ");
    // the constructor
        private objCardWindow(MunchkinWindow Window,int index)  {
        	
        this.CardIdex=5;
    	this.window=Window;
        this.setSize(300, 400);
        this.setResizable(false);
        this.setTitle("Singleton Frame. Timestamp:" +
         System.currentTimeMillis());
        
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
    	
    	
    	 przycisk.setBounds(200, 200, 100, 30);
    	 if (przycisk.getActionListeners().length<1){przycisk.addActionListener(this);}
    	 
         // JPanel bounds
         pnlButtons.setBounds(200, 200, 100, 400);

         // Adding to JFrame
         pnlButtons.add(przycisk);
      
         add(pnlButtons);
         // JFrame properties
         setSize(400, 400);
         this.getContentPane().setBackground( new Color(231, 218, 167) );
         setTitle("Air Traffic Control");
         setLocationRelativeTo(null);
      
         setVisible(true);
   
         
         img  =scaleImage(205, 285,"src/images/kd (" +index+ ").jpg");
         ImageIcon icon=new ImageIcon((Image) img);
        label = new JLabel(); 
        label.setIcon(icon);
        add(label, BorderLayout.WEST);
       
    }
  
   public BufferedImage scaleImage(int WIDTH, int HEIGHT, String filename) {
        BufferedImage bi = null;
        ii=null;
        try {
          ii = new ImageIcon(ImageIO.read(new File(filename)));//path to image
            System.out.println(ii);
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
    	 img =scaleImage(205, 285,"src/images/kd (" +index+ ").jpg");
    	initComponents(this,index);
     
        
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Count of listeners: " + ((JButton) e.getSource()).getActionListeners().length);

	      Object src =e.getSource();
	     
		  if (src == przycisk) 
			    {
			  System.out.println(CardIdex-1+" odrzucono");
			    	window.getLogic().getPlayer(0).discardCardfromHand(CardIdex-1);
			        window.repaint();
			       this.setVisible(false);
		        }
		
	}
}