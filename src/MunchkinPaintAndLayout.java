import java.awt.*;
import java.awt.geom.AffineTransform;

import java.awt.image.BufferedImage;
import java.io.File;


import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
public class MunchkinPaintAndLayout extends Canvas
{

	
	private static final long serialVersionUID = -1202675550011086749L;
	protected objGameLogic logikaMunchkin;
	protected final int gapFaceDown = 10, gapFaceUp = 20, gapColumn = 20;
	private final Color clrBackground = new Color(231, 218, 167);
	protected objMenuSystem menuSystem;
	protected Image imgCardBack;
	protected int imgHeight=100;
	protected int imgWidth=72;
	protected boolean buttonPressed=false;
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
System.out.println("wow");
		//currentInstruction.reset();

	}
	public void setCardBackImage (Image index)
	{

		imgCardBack = index.getScaledInstance(71, 96, Image.SCALE_DEFAULT);
	}
	

	public void DrawHand(Graphics grpOffScreen)
	{
		Image tempImg =null;
		 int[] tmp=logikaMunchkin.getNextPlayerId(logikaMunchkin.getCurrentPlayer().getPlayerId());
		//rysowanie dla pozycji gracza numer 1
	
	
		for(int i=0 ; i<logikaMunchkin.getCurrentPlayer().getHand().size();i++)
		{
		if(logikaMunchkin.getCurrentPlayer().getHand().size()!=0)
		{
		tempImg =logikaMunchkin.getCardImage(logikaMunchkin.getCurrentPlayer().getHand().getCard(i),i+1);	
		}
		drawCard(grpOffScreen,tempImg,235+(10*i+i*imgWidth),700-imgHeight/2,1);
		}

		//rysowanie dla pozycji gracza numer 2 po lewej stronie
       for(int i=0 ; i<logikaMunchkin.getPlayer(tmp[0]).getHand().size();i++)
		{
    	if(logikaMunchkin.getPlayer(tmp[0]).getHand().size()!=0)
    	tempImg = logikaMunchkin.getPlayer(tmp[0]).getHand().getCard(i).getImg();	
		drawCard(grpOffScreen,tempImg,imgHeight/2,70+(i*10+i*imgWidth),2);
		}

		//rysowanie dla pozycji gracza numer 3 u góry
         for(int i=0 ; i<logikaMunchkin.getPlayer(tmp[1]).getHand().size();i++)
		 {
         if(logikaMunchkin.getPlayer(tmp[1]).getHand().size()!=0)
         tempImg = logikaMunchkin.getPlayer(tmp[1]).getHand().getCard(i).getImg();	
		  drawCard(grpOffScreen,tempImg,235+(10*i+i*imgWidth),imgHeight/2,3);
		 }

		//rysowanie dla pozycji gracza numer 4 po prawej stronie
     for(int i=0 ; i<logikaMunchkin.getPlayer(tmp[2]).getHand().size();i++)
		{
    	 if(logikaMunchkin.getPlayer(tmp[2]).getHand().size()!=0)
          tempImg = logikaMunchkin.getPlayer(tmp[2]).getHand().getCard(i).getImg();	
		drawCard(grpOffScreen,tempImg,910,70+(i*10+i*imgWidth),4);
		}
    

	}
	public void DrawCharacterImage(Graphics grpOffScreen)
	{
		Image tempImg = logikaMunchkin.getCharacterImage();
       // System.out.println("player 1"+" X "+(tempX)+" Y "+(tempY-222));
		drawCard(grpOffScreen,tempImg,250,500,1);




		//rysowanie dla pozycji gracza numer 2 po lewej stronie
	
	   //  System.out.println("player 2"+" X "+(tempX+200)+" Y "+(tempY+100));
		drawCard(grpOffScreen,tempImg,200,160,2);


		//rysowanie dla pozycji gracza numer 3 u góry
		
	   //  System.out.println("player 3"+" X "+(tempX+500)+" Y "+(tempY+200));
		drawCard(grpOffScreen,tempImg,710,200,3);


		//rysowanie dla pozycji gracza numer 4 po prawej stronie
		
	    // System.out.println("player 4"+" X "+(tempX-150)+" Y "+(tempY+500));
		drawCard(grpOffScreen,tempImg,760,500,4);

	}
    public void DrawPlayerNames(Graphics g)
    {
    	 int fontSize = 20;

    	    g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
    	     
    	    g.setColor(Color.black);
    	    g.drawString("Player "+logikaMunchkin.getCurrentPlayer().getName(), 350, 520);
    	    g.drawString("LVL", 350, 580);
    	    int[] tmp=logikaMunchkin.getNextPlayerId(logikaMunchkin.getCurrentPlayer().getPlayerId());
    	    
    	    g.drawString("Player "+logikaMunchkin.getPlayer(tmp[0]).getName(), 105, 290);
    	    g.drawString("LVL", 105, 350);
    	    
    	    g.drawString("Player "+logikaMunchkin.getPlayer(tmp[1]).getName(), 530, 155);
    	    g.drawString("LVL", 530, 215);
    	    
    	    g.drawString("Player "+logikaMunchkin.getPlayer(tmp[2]).getName(), 760, 325);
    	    g.drawString("LVL", 760, 385);
    	    
    }
    public void DrawDoorStack(Graphics g)
    {
    	Image tempImg =null;
		tempImg =logikaMunchkin.getDoorImage();	

		drawCard(g,tempImg,250,340,1);
    	
    }
    public void DrawTreasureStack(Graphics g)
    {
    	Image tempImg =null;
		tempImg =logikaMunchkin.getTreasureImage();	

		drawCard(g,tempImg,400,340,1);
    	
    }
    public void DrawSealStack(Graphics g)
    {
    	Image tempImg =null;
		tempImg =logikaMunchkin.getSealImage();	

		drawCard(g,tempImg,550,340,1);
    	
    }
    public void DrawPlayerLVL(Graphics g)
    {
    	 int[] tmp=logikaMunchkin.getNextPlayerId(logikaMunchkin.getCurrentPlayer().getPlayerId());
    	BufferedImage img;
        img  =scaleImage(50, 50,"src/images/LVL/"+logikaMunchkin.getCurrentPlayer().getLevel()+".jpg");
        drawCard(g,img,425,595,1);
        
    
        img  =scaleImage(50, 50,"src/images/LVL/"+logikaMunchkin.getPlayer(tmp[0]).getLevel()+".jpg");
        drawCard(g,img,180,370,1);
        
        img  =scaleImage(50, 50,"src/images/LVL/"+logikaMunchkin.getPlayer(tmp[1]).getLevel()+".jpg");
        drawCard(g,img,605,235,1);
        
        img  =scaleImage(50, 50,"src/images/LVL/"+logikaMunchkin.getPlayer(tmp[2]).getLevel()+".jpg");
        drawCard(g,img,835,405,1);
    }
    protected void DrawEndOfTurnButton(Graphics grpOffScreen)
    {
    	Image tempImg=null;
     if(!buttonPressed)
      {
    		 tempImg = logikaMunchkin.getButtonImage();	
    
    		drawCard(grpOffScreen,tempImg,470,540,1);
    		grpOffScreen.setFont(new Font("TimesRoman", Font.PLAIN, 20));
    	     
    		grpOffScreen.setColor(Color.black);
     	   
    		grpOffScreen.drawString("Koniec Tury", 450, 520);
        }else{
    		
    		tempImg = logikaMunchkin.getPressedButtonImage();	
    	    
    		drawCard(grpOffScreen,tempImg,470,540,1);
    		grpOffScreen.setFont(new Font("TimesRoman", Font.PLAIN, 20));
    	     
    		grpOffScreen.setColor(Color.black);
     	   
    		grpOffScreen.drawString("Koniec Tury", 450, 520);
        }
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
		DrawEndOfTurnButton(grpOffScreen);
		DrawDoorStack(grpOffScreen);
		DrawTreasureStack(grpOffScreen);
		DrawSealStack(grpOffScreen);
		grpOffScreen.setClip(0, 0, getSize().width, getSize().height);
		menuSystem.drawMenu(grpOffScreen);
		g.drawImage(imgOffScreen, 0, 0, this);


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
