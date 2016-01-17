import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
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
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class MunchkinMenu extends Canvas implements MouseListener, MouseMotionListener
{

    private Image logo;
	private static final long serialVersionUID = 1L;
	protected int imgHeight=0;
	protected int imgWidth=0;
    private Image ButtonImage ;
    private Image PressedButtonImage ;
    protected boolean buttonPressed=false;
	private objCreateAppletImage createImage = new objCreateAppletImage();
	public MunchkinMenu(MunchkinGUI gui)
	{
		  if (this.getMouseListeners().length<1){this.addMouseListener(this);}
		   if (this.getMouseMotionListeners().length<1){this.addMouseMotionListener(this);}
		this.logo=createImage.getImage(this, "images/Munchkin_Banner.png",500000);
		this.ButtonImage= createImage.getImage(this, "images/Buttons/buttonLong_beige.png", 1000).getScaledInstance(150, 49, Image.SCALE_DEFAULT);
		this.PressedButtonImage= createImage.getImage(this, "images/Buttons/buttonLong_beige_pressed.png", 1000).getScaledInstance(150, 49, Image.SCALE_DEFAULT);
	}
	public void paint (Graphics g)
	{

		Image imgOffScreen = createImage(getSize().width, getSize().height);
		Graphics grpOffScreen = imgOffScreen.getGraphics();
		//logikaMunchkin.getCurrentPlayer().setCurrentPhase();
		paintLogo(grpOffScreen);
		DrawButtons(grpOffScreen);
		grpOffScreen.setClip(0, 0, getSize().width, getSize().height);
		
		g.drawImage(imgOffScreen, 0, 0, this);


	}
	public void paintLogo(Graphics grpOffScreen)
	{
	
		drawCard(grpOffScreen,logo,0,0,1);
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
    public boolean isAboveButton(int x , int y)
    {
   	 if ((x >= 220&& x <= 370 )&& (y >= 350 && y <= 400)) //Check if mouse is in this column's card area
			{
				return true;

	        }
   	 return false;
    }
    protected void DrawButtons(Graphics grpOffScreen)
    {
    	
     Image tempImg;
	if(!buttonPressed)
      {
    	 
    	 tempImg = ButtonImage;	
    
    		drawCard(grpOffScreen,tempImg,220,350,1);
    		grpOffScreen.setFont(new Font("Garamond", Font.PLAIN, 24));
    	     
    		grpOffScreen.setColor(Color.black);
     	   
    		grpOffScreen.drawString("Nowa Gra", 240,380);
    		
    		
    	

    		
        }else{
    		
    		tempImg = PressedButtonImage;	
    	    
    		drawCard(grpOffScreen,tempImg,220,350,1);
    		grpOffScreen.setFont(new Font("Garamond", Font.PLAIN, 24));
    	     
    		grpOffScreen.setColor(Color.black);
     	   
    		grpOffScreen.drawString("Nowa Gra", 240,380);
        }
    	
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
	public void mousePressed(MouseEvent arg0) 
	{
		
		if (arg0.getButton() == MouseEvent.BUTTON1)
		{
			int x = arg0.getX();
			int y = arg0.getY();
			System.out.println("x "+x+" y "+y);				
											
			if (isAboveButton(x, y)) //Check if the menu or menu items were clicked
			{
												
			 buttonPressed=true;
			 repaint();
								
			}else
			{
				buttonPressed=false;
				repaint();
		    }
												
		}			
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		if (arg0.getButton() == MouseEvent.BUTTON1)
		{
			int x = arg0.getX();
			int y = arg0.getY();
			System.out.println("x "+x+" y "+y);				
											
			if (isAboveButton(x, y)) //Check if the menu or menu items were clicked
			{
												
			 buttonPressed=false;
			 repaint();
			 ComplexDialogPanel panelNowegoGracza = new ComplexDialogPanel();
			 ArrayList<String> listaGraczy= panelNowegoGracza.createAndShowGui();		
			 System.out.println(listaGraczy);
			}else
			{
			
				repaint();
		    }
												
		}			
	}
	public ArrayList<String> zwrocListeGraczy()
	{
	}
	}
}
