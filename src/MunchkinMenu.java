import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class MunchkinMenu extends Canvas implements MouseListener, MouseMotionListener
{


	private static final long serialVersionUID = 1L;
	protected int imgHeight=0;
	protected int imgWidth=0;
	private objCreateAppletImage createImage = new objCreateAppletImage();
	public MunchkinMenu(MunchkinGUI gui)
	{
		
	}
	public void paint (Graphics g)
	{

		Image imgOffScreen = createImage(getSize().width, getSize().height);
		Graphics grpOffScreen = imgOffScreen.getGraphics();
		//logikaMunchkin.getCurrentPlayer().setCurrentPhase();
		paintLogo(grpOffScreen);
		grpOffScreen.setClip(0, 0, getSize().width, getSize().height);
		
		g.drawImage(imgOffScreen, 0, 0, this);


	}
	public void paintLogo(Graphics grpOffScreen)
	{
		 Image img = createImage.getImage(this, "images/Munchkin_Banner.png", 500000);

		drawCard(grpOffScreen,img,0,0,1);
	}
    protected void drawCard (Graphics grpOffScreen, Image imgCard, int startX, int startY,int Player) //Called by solitareColumn() to paint each card
		{
			/*
			grpOffScreen.drawImage(imgCard, startX, startY, this);
			grpOffScreen.setColor(new Color(149,146,140)); //Grey
			grpOffScreen.drawRect(startX, startY, imgWidth, imgHeight); //Draw a border around the card
		*/

			Graphics2D g2d = (Graphics2D) grpOffScreen;


		       AffineTransform at = new AffineTransform();

		       // 4. translate it to the center of the component
		       at.translate( startX,  startY);

		       // 3. do the actual rotation
		       at.rotate((Player-1)*(Math.PI/2));


		       // 1. translate the object so that you rotate it around the
		       //    center (easier :))
		       at.translate(-imgWidth/2, -imgHeight/2);

		       // draw the image

		       g2d.drawImage( imgCard, at, this);

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
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
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
