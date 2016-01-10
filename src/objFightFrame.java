

 import java.awt.*;
 import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

 public class objFightFrame extends Frame implements MouseListener{
    private MunchkinWindow MainWindow;
	private static objFightFrame myInstance;
	private static final long serialVersionUID = 1L;
	private final Color clrBackground = new Color(231, 218, 167);
	protected int imgHeight=100;
	protected int imgWidth=72;
   private objFightFrame(MunchkinWindow Window,int index) {
	  
     super("WALKA!");
     this.MainWindow=Window;
     setBackground(clrBackground);
    setSize(970,730);
    setResizable(false);
    setVisible(true);  
    
     addWindowListener(new WindowAdapter()
       {public void windowClosing(WindowEvent e)
        {setVisible(false);}
      }
     );
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
   public void DrawHand(Graphics grpOffScreen)
	{
	   
		Image tempImg =null;
		 int[] tmp=MainWindow.getLogic().getNextPlayerId(MainWindow.getLogic().getCurrentPlayer().getPlayerId());
		//rysowanie dla pozycji gracza numer 1
	
	
		for(int i=0 ; i<MainWindow.getLogic().getCurrentPlayer().getHand().size();i++)
		{
		if(MainWindow.getLogic().getCurrentPlayer().getHand().size()!=0)
		{
		tempImg =MainWindow.getLogic().getCardImage(MainWindow.getLogic().getCurrentPlayer().getHand().getCard(i),i+1);	
		}
		drawCard(grpOffScreen,tempImg,235+(10*i+i*imgWidth),700-imgHeight/2,1);
		}

	
   

	}
   public void paint(Graphics g) {
	  

    g.setColor(clrBackground );
   g.drawRect(50,50,200,200);

     DrawHand(g);
     Graphics2D g2d = (Graphics2D)g;
    g2d.setColor(Color.blue);
    g2d.drawRect(75,75,300,200);
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
   public static objFightFrame getInstance(MunchkinWindow Window,int index) {
	    if (myInstance == null)
	        myInstance = new objFightFrame(Window,index);
	        
	    return myInstance;
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

	
	
	
	
	

	
