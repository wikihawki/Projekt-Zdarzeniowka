import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
public class MunchkinPaintAndLayout extends Canvas
{
	protected objGameLogic logikaMunchkin;
	protected final int gapFaceDown = 10, gapFaceUp = 20, gapColumn = 20;
	private final Color clrBackground = new Color(231, 218, 167);
	protected objMenuSystem menuSystem;
	protected Image imgCardBack;
	protected int imgHeight=100;
	protected int imgWidth=72;
	public MunchkinPaintAndLayout ()
	{
		setupMenuSystem();
	}
	private void setupMenuSystem ()
	{
		logikaMunchkin=new objGameLogic();
		String[] strMenuItemText = {"Nowa Gra", "Zapisz Grê", "Wczytaj Grê"};
		boolean[] boolMenuItemSep = {true, false, false};

		menuSystem = new objMenuSystem(strMenuItemText, boolMenuItemSep);

	}
	protected void performMenuAction ()
	{
		int menuAction = menuSystem.getMenuAction();
		repaint();
	}
	public void newGame ()
	{

		//currentInstruction.reset();

	}
	public void setCardBackImage (Image index)
	{

		imgCardBack = index.getScaledInstance(71, 96, Image.SCALE_DEFAULT);
	}
	public void DrawHand(Graphics grpOffScreen)
	{
		Image tempImg =null;

		//rysowanie dla pozycji gracza numer 1
		int tempX = logikaMunchkin.getPlayerHandPositionX(1);
		int tempY = logikaMunchkin.getPlayerHandPositionY(1);
		
	
		for(int i=0 ; i<logikaMunchkin.getPlayer(0).getHand().size();i++)
		{
		if(logikaMunchkin.getPlayer(0).getHand().size()!=0)
		{
		tempImg =logikaMunchkin.getCardImage(0, logikaMunchkin.getPlayer(0).getHand().getCard(i).getIdNr());	
		}
		drawCard(grpOffScreen,tempImg,tempX+(10*i+i*imgWidth),tempY-imgHeight/2,1);
		}

		//rysowanie dla pozycji gracza numer 2 po lewej stronie
		 tempX = logikaMunchkin.getPlayerHandPositionX(2);
		 tempY = logikaMunchkin.getPlayerHandPositionY(2);
		
       for(int i=0 ; i<logikaMunchkin.getPlayer(1).getHand().size();i++)
		{
    	if(logikaMunchkin.getPlayer(1).getHand().size()!=0)
    	tempImg = logikaMunchkin.getPlayer(1).getHand().getCard(i).getImg();	
		drawCard(grpOffScreen,tempImg,tempX+imgHeight/2,tempY+(i*10+i*imgWidth),2);
		}

		//rysowanie dla pozycji gracza numer 3 u góry
		 tempX = logikaMunchkin.getPlayerHandPositionX(3);
		 tempY = logikaMunchkin.getPlayerHandPositionY(3);
         for(int i=0 ; i<logikaMunchkin.getPlayer(2).getHand().size();i++)
		 {
         if(logikaMunchkin.getPlayer(2).getHand().size()!=0)
         tempImg = logikaMunchkin.getPlayer(2).getHand().getCard(i).getImg();	
		  drawCard(grpOffScreen,tempImg,tempX+(10*i+i*imgWidth),tempY+imgHeight/2,3);
		 }

		//rysowanie dla pozycji gracza numer 3 po prawej stronie
		 tempX = logikaMunchkin.getPlayerHandPositionX(4);
		 tempY = logikaMunchkin.getPlayerHandPositionY(4);
     for(int i=0 ; i<logikaMunchkin.getPlayer(3).getHand().size();i++)
		{
    	 if(logikaMunchkin.getPlayer(3).getHand().size()!=0)
          tempImg = logikaMunchkin.getPlayer(3).getHand().getCard(i).getImg();	
		drawCard(grpOffScreen,tempImg,tempX,tempY+(i*10+i*imgWidth),4);
		}
     /*
     //rysowanie decku karty drzwi
     tempX = logikaMunchkin.getPlayerHandPositionX(5);
	  tempY = logikaMunchkin.getPlayerHandPositionY(5);
	  tempImg = logikaMunchkin.getHand(0).getLastCard().getImg();
	  drawCard(grpOffScreen,tempImg,tempX,tempY,1);

     //rysowanie decku karty skarbu
     tempX = logikaMunchkin.getPlayerHandPositionX(6);
	  tempY = logikaMunchkin.getPlayerHandPositionY(6);
	  tempImg = logikaMunchkin.getHand(0).getLastCard().getImg();
	  drawCard(grpOffScreen,tempImg,tempX,tempY,1);

     //rysowanie decku karty foczek
     tempX = logikaMunchkin.getPlayerHandPositionX(7);
	  tempY = logikaMunchkin.getPlayerHandPositionY(7);
	  tempImg = logikaMunchkin.getHand(0).getLastCard().getImg();
	  drawCard(grpOffScreen,tempImg,tempX,tempY,1);

		*/

	}
	public void DrawCharacterImage(Graphics grpOffScreen)
	{
		int tempX = logikaMunchkin.getPlayerHandPositionX(1);
		int tempY = logikaMunchkin.getPlayerHandPositionY(1);
		Image tempImg = logikaMunchkin.getCharacterImage();
       // System.out.println("player 1"+" X "+(tempX)+" Y "+(tempY-222));
		drawCard(grpOffScreen,tempImg,tempX,tempY-100-imgHeight,1);




		//rysowanie dla pozycji gracza numer 2 po lewej stronie
		 tempX = logikaMunchkin.getPlayerHandPositionX(2);
		 tempY = logikaMunchkin.getPlayerHandPositionY(2);
	    
	   //  System.out.println("player 2"+" X "+(tempX+200)+" Y "+(tempY+100));
		drawCard(grpOffScreen,tempImg,tempX+100+imgHeight,tempY+100,2);


		//rysowanie dla pozycji gracza numer 3 u góry
		 tempX = logikaMunchkin.getPlayerHandPositionX(3);
		 tempY = logikaMunchkin.getPlayerHandPositionY(3);
	    
	   //  System.out.println("player 3"+" X "+(tempX+500)+" Y "+(tempY+200));
		drawCard(grpOffScreen,tempImg,tempX+500,tempY+200,3);


		//rysowanie dla pozycji gracza numer 4 po prawej stronie
		 tempX = logikaMunchkin.getPlayerHandPositionX(4);
		 tempY = logikaMunchkin.getPlayerHandPositionY(4);
	     
	    // System.out.println("player 4"+" X "+(tempX-150)+" Y "+(tempY+500));
		drawCard(grpOffScreen,tempImg,tempX-150,tempY+500,4);

	}
    public void DrawPlayerNames(Graphics g)
    {
    	 Dimension d = this.getPreferredSize();
    	    int fontSize = 20;

    	    g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
    	     
    	    g.setColor(Color.black);
    	   
    	    g.drawString("Player 1", 350, 520);
    	    g.drawString("LVL", 350, 580);
    	    
    	    g.drawString("Player 2", 105, 290);
    	    g.drawString("LVL", 105, 350);
    	    
    	    g.drawString("Player 3", 530, 155);
    	    g.drawString("LVL", 530, 215);
    	    
    	    g.drawString("Player 4", 760, 325);
    	    g.drawString("LVL", 760, 385);
    	    
    }
    public void DrawPlayerLVL(Graphics g)
    {
    	
    	BufferedImage img;
        img  =scaleImage(50, 50,"src/images/LVL/"+1+".jpg");
        drawCard(g,img,300,300,1);
    }
	public void update (Graphics g)
	{
		paint(g);
	}
	private void clip (objInstruction clipInstruction, Graphics grpOffScreen, Graphics g)
	{

		int startX = clipInstruction.getStartX();
		int startY = clipInstruction.getStartY();
		int width = clipInstruction.getWidth();
		int height = clipInstruction.getHeight();

		grpOffScreen.clipRect(startX, startY, width, height);
		g.clipRect(startX, startY, width, height);

		grpOffScreen.setColor(clrBackground);
		grpOffScreen.clearRect(startX, startY, width, height);

	}
    public void paint (Graphics g)
	{

		Image imgOffScreen = createImage(getSize().width, getSize().height);
		Graphics grpOffScreen = imgOffScreen.getGraphics();
		if (menuSystem.isMenuVisible())
		{
			clip(menuSystem.getPaintInstruction(), grpOffScreen, g);
		}
		DrawPlayerNames(grpOffScreen);
		DrawPlayerLVL(grpOffScreen);
        DrawHand(grpOffScreen);
		DrawCharacterImage(grpOffScreen);
		grpOffScreen.setClip(0, 0, getSize().width, getSize().height);
		menuSystem.drawMenu(grpOffScreen);
		g.drawImage(imgOffScreen, 0, 0, this);


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
	public objGameLogic getLogic()
	{
		return logikaMunchkin;
	}

}
