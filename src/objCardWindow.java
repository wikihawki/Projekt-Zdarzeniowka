import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class objCardWindow extends JFrame implements ActionListener{
    private static objCardWindow myInstance;
    private JPanel panel ;
    private MunchkinWindow window;
    JPanel pnlButtons = new JPanel();
    // Buttons
    JButton przycisk = new JButton("Add Flight");
    // the constructor
    private objCardWindow(MunchkinWindow Window)  {
 
    	this.window=Window;
        this.setSize(300, 400);
         this.setResizable(false);
        this.setTitle("Singleton Frame. Timestamp:" +
            System.currentTimeMillis());
        
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        initComponents(this);
    }

    public static objCardWindow getInstance(MunchkinWindow Window) {
        if (myInstance == null)
            myInstance = new objCardWindow(Window);

        return myInstance;
    }
    
    private void initComponents(JFrame frame) {
    	
    	 przycisk.setBounds(200, 200, 100, 30);

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
   
    	
    	
    	
    	
    	
    	
    	
    	
        final BufferedImage img =scaleImage(205, 285,"");
        //create label with image as background
      

        JLabel label = new JLabel(new ImageIcon((Image) img));
     
     
        
        frame.getContentPane().add(label, BorderLayout.WEST);
       
    }
    
    public BufferedImage scaleImage(int WIDTH, int HEIGHT, String filename) {
        BufferedImage bi = null;
        try {
            ImageIcon ii = new ImageIcon("src/images/kd (1).jpg");//path to image
            bi = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = (Graphics2D) bi.createGraphics();
            g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
            g2d.drawImage(ii.getImage(), 0, 0, WIDTH, HEIGHT, null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return bi;
    }

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}




}