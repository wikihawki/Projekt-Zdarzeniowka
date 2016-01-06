import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.Vector;
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

		//rysowanie dla pozycji gracza numer 1
		int tempX = logikaMunchkin.getPlayerHandPositionX(1);
		int tempY = logikaMunchkin.getPlayerHandPositionY(1);
		Image tempImg = logikaMunchkin.getHand(0).getLastCard().getImg();
		for(int i=0 ; i<logikaMunchkin.getPlayer(0).getHand().size();i++)
		{
			if(i!=0)
		drawCard(grpOffScreen,tempImg,tempX+(10*i+i*imgWidth),tempY-imgHeight/2,1);
		}

		//rysowanie dla pozycji gracza numer 2 po lewej stronie
		 tempX = logikaMunchkin.getPlayerHandPositionX(2);
		 tempY = logikaMunchkin.getPlayerHandPositionY(2);
	     tempImg = logikaMunchkin.getHand(0).getCard(3).getImg();
       for(int i=0 ; i<logikaMunchkin.getPlayer(1).getHand().size();i++)
		{
		drawCard(grpOffScreen,tempImg,tempX+imgHeight/2,tempY+(i*10+i*imgWidth),2);
		}

		//rysowanie dla pozycji gracza numer 3 u góry
		 tempX = logikaMunchkin.getPlayerHandPositionX(3);
		 tempY = logikaMunchkin.getPlayerHandPositionY(3);
	     tempImg = logikaMunchkin.getHand(0).getLastCard().getImg();
      for(int i=0 ; i<logikaMunchkin.getPlayer(2).getHand().size();i++)
		{
		drawCard(grpOffScreen,tempImg,tempX+(10*i+i*imgWidth),tempY+imgHeight/2,3);
		}

		//rysowanie dla pozycji gracza numer 2 po lewej stronie
		 tempX = logikaMunchkin.getPlayerHandPositionX(4);
		 tempY = logikaMunchkin.getPlayerHandPositionY(4);
	     tempImg = logikaMunchkin.getHand(0).getLastCard().getImg();
     for(int i=0 ; i<logikaMunchkin.getPlayer(3).getHand().size();i++)
		{
		drawCard(grpOffScreen,tempImg,tempX-imgHeight/2,tempY+(i*10+i*imgWidth),4);
		}
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

		

	}
	public void DrawCharacterImage(Graphics grpOffScreen)
	{
		int tempX = logikaMunchkin.getPlayerHandPositionX(1);
		int tempY = logikaMunchkin.getPlayerHandPositionY(1);
		Image tempImg = logikaMunchkin.getCharacterImage();

		drawCard(grpOffScreen,tempImg,tempX,tempY-100-imgHeight,1);




		//rysowanie dla pozycji gracza numer 2 po lewej stronie
		 tempX = logikaMunchkin.getPlayerHandPositionX(2);
		 tempY = logikaMunchkin.getPlayerHandPositionY(2);
	     tempImg = logikaMunchkin.getCharacterImage();

		drawCard(grpOffScreen,tempImg,tempX+100+imgHeight,tempY,2);


		//rysowanie dla pozycji gracza numer 3 u góry
		 tempX = logikaMunchkin.getPlayerHandPositionX(3);
		 tempY = logikaMunchkin.getPlayerHandPositionY(3);
	     tempImg = logikaMunchkin.getCharacterImage();

		drawCard(grpOffScreen,tempImg,tempX+400,tempY+200,3);


		//rysowanie dla pozycji gracza numer 2 po lewej stronie
		 tempX = logikaMunchkin.getPlayerHandPositionX(4);
		 tempY = logikaMunchkin.getPlayerHandPositionY(4);
	     tempImg = logikaMunchkin.getCharacterImage();

		drawCard(grpOffScreen,tempImg,tempX-200,tempY+400,4);

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

	public objGameLogic getLogic()
	{
		return logikaMunchkin;
	}

}
