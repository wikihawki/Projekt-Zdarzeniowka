import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class objCharacterWindow extends JFrame implements ActionListener{
private MunchkinWindow MainWindow;
private static objCharacterWindow myInstance;
private  JPanel pnlUpper = new JPanel(new GridLayout(1, 3));
private  JPanel pnlMiddle = new JPanel(new GridLayout(1, 3));
private  JPanel pnlBottom = new JPanel(new GridLayout(1, 3));
private ArrayList<JLabel> PaneleEkwipunku=new ArrayList<JLabel>();
public objCharacterWindow(MunchkinWindow Window,int index)
{
	this.getContentPane().setBackground( new Color(231, 218, 167) );
	this.MainWindow=Window;
	this.setLayout(new GridLayout(3,3));
    this.setSize(450, 750);
    this.setResizable(false);
    this.setTitle("Singleton Frame. Timestamp:" +
     System.currentTimeMillis());
    this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    for(int i=0;i<9;i++)
    {
    	JLabel tmp = new JLabel();
    	PaneleEkwipunku.add(tmp);
    	setLabelImages(i);
    	add(PaneleEkwipunku.get(i));
    }
    
    
    
}

public void setLabelImages(int i)
{
	BufferedImage img;
    img  =scaleImage(150, 250,"src/images/Ekwipunek/ekw(" +i+ ").jpg");
    ImageIcon icon=new ImageIcon((Image) img);
    
switch(i)
{
case 1:
	
	break;
case 2:
	PaneleEkwipunku.get(i).setIcon(icon);
	break;
case 3:
	PaneleEkwipunku.get(i).setIcon(icon);
	break;
case 4:
	PaneleEkwipunku.get(i).setIcon(icon);
	break;
case 5:
	PaneleEkwipunku.get(i).setIcon(icon);
	break;
case 6:
	break;
case 7:
	break;
case 8:
	PaneleEkwipunku.get(i).setIcon(icon);
	break;
case 9:
	break;
default:
	break;
	

}
}

public static objCharacterWindow getInstance(MunchkinWindow Window,int index) {
    if (myInstance == null)
        myInstance = new objCharacterWindow(Window,index);
        
    return myInstance;
}    
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
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
}
