import java.awt.*;
import java.awt.geom.AffineTransform;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
public class MunchkinPaintAndLayout extends Canvas
{

	
	private static final long serialVersionUID = -1202675550011086749L;
	protected objGameLogic logikaMunchkin;
	protected int focusPlayer=1;
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
		logikaMunchkin=new objGameLogic();//to coœ
		String[] strMenuItemText = {"Nowa Gra", "Zapisz Grê", "Wczytaj Grê"};
		boolean[] boolMenuItemSep = {true, false, false};

		menuSystem = new objMenuSystem(strMenuItemText, boolMenuItemSep);

	}
	protected void performMenuAction ()
	{
		int menuAction = menuSystem.getMenuAction();
		
		if(menuAction==0)
		{
			 ComplexDialogPanel panelNowegoGracza = new ComplexDialogPanel();
			 ArrayList<String> listaGraczy= panelNowegoGracza.createAndShowGui();		
			 logikaMunchkin.newGame(4,listaGraczy);
			
		}
		
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
	
	
	public void DrawTresure(Graphics grpOffScreen)
	{
		Image img  =logikaMunchkin.getChestImage();
        drawCard(grpOffScreen,img,420,55,1);
        int fontSize = 20;
		grpOffScreen.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
    	     
		grpOffScreen.setColor(Color.BLACK);
		  grpOffScreen.drawString("Number of treasures : ", 360, 160);
		  int tmp = logikaMunchkin.getCurrentFight().getMonsters().get(0).getTreasures();
		 
		  img  =scaleImage(50, 50,"src"
	    	   		+ "/images/LVL/"+tmp+".jpg");
	        drawCard(grpOffScreen,img,579,180,1);
	}
    public void DrawParty(Graphics grpOffScreen)
    {
    	Image tempImg =null;
    	int fontSize = 20;
		grpOffScreen.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
    	     
		grpOffScreen.setColor(Color.black);
    	logikaMunchkin.getCurrentFight().getMainPlayer();

	    tempImg = logikaMunchkin.getCharacterImage();
	    grpOffScreen.drawString(logikaMunchkin.getCurrentFight().getMainPlayer().getName(), 200,200);
       
		drawCard(grpOffScreen,tempImg,30,150,1);
	
	  if(logikaMunchkin.getCurrentFight().getHelperPlayer()!=null)
		{
	   drawCard(grpOffScreen,tempImg,30,350,1);
	   grpOffScreen.drawString("Player "+logikaMunchkin.getCurrentFight().getHelperPlayer().getName(), 200, 400);
		}
		
	   grpOffScreen.drawString("Party power "+logikaMunchkin.getCurrentFight().getPlayersStrength(), 250, 300);
	   int tmp = logikaMunchkin.getCurrentFight().getPlayersStrength();
	   //int tmp =5;
	  if(tmp>10)
	  {
	   Image img  =scaleImage(50, 50,"src"
	   		+ "/images/LVL/"+tmp/10+".jpg");
       drawCard(grpOffScreen,img,400,315,1);
	   img  =scaleImage(50, 50,"src"
		   		+ "/images/LVL/"+tmp%10+".jpg");
	       drawCard(grpOffScreen,img,432,315,1);
	}else if(tmp>0)
	   {
    	Image img  =scaleImage(50, 50,"src/images/lvl/"+tmp+".jpg");
        drawCard(grpOffScreen,img,400,315,1);
    }
    }
    public void DrawMonsters(Graphics grpOffScreen)
    {
    
    	Image img =null;
    	int fontSize = 20;
		grpOffScreen.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
    	     int tmp=0;
		grpOffScreen.setColor(Color.black);
    	logikaMunchkin.getCurrentFight().getMainPlayer();
      
		for(objMonster x : logikaMunchkin.getCurrentFight().getMonsters())
         {
		
	      img  =scaleImage(170, 190,"src/images/Karta ("+x.getMyCard().getIdNr()+").jpg");
           drawCard(grpOffScreen,img,752,95+tmp*160,1);
           tmp++;
         }
       
	   grpOffScreen.drawString("Monsters power "+logikaMunchkin.getCurrentFight().getMonstersStrength(), 540, 300);
	   /*
	 tmp = logikaMunchkin.getCurrentFight().getMonstersStrength();
	  //  tmp =5;
	   if(tmp>10)
	  {
	   img  =scaleImage(50, 50,"src"
	   		+ "/images/LVL/"+tmp+".jpg");
       drawCard(grpOffScreen,img,720,315,1);
	   img  =scaleImage(50, 50,"src"
		   		+ "/images/LVL/"+(tmp-10)+".jpg");
	       drawCard(grpOffScreen,img,752,315,1);
	   }else if(tmp>0)
	   {
		   img  =scaleImage(50, 50,"src"
			   		+ "/images/LVL/"+tmp+".jpg");
        }
        */
    }
    public void DrawBattlefield(Graphics grpOffScreen)
    {
    	Image tempImg =null;
    

	
		for(int i=0 ; i<logikaMunchkin.getPlayer(focusPlayer-1).getHand().size();i++)
		{
		if(logikaMunchkin.getCurrentPlayer().getHand().size()!=0)
		{
		tempImg =logikaMunchkin.getCardImage(logikaMunchkin.getPlayer(focusPlayer-1).getHand().getCard(i),i+1);	
		}
		drawCard(grpOffScreen,tempImg,235+(10*i+i*imgWidth),700-imgHeight/2,1);
		}
		
		    tempImg = logikaMunchkin.getCharacterImage();
	       
			drawCard(grpOffScreen,tempImg,250,500,1);
		
			int fontSize = 20;
			grpOffScreen.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
	    	     
			grpOffScreen.setColor(Color.black);
			grpOffScreen.drawString(logikaMunchkin.getPlayer(focusPlayer-1).getName(), 350, 520);
			grpOffScreen.drawString("LVL", 350, 580);
		
			DrawChoosePlayerButtons(grpOffScreen);
			BufferedImage img;
	        img  =scaleImage(50, 50,"src/images/LVL/"+logikaMunchkin.getPlayer(focusPlayer-1).getLevel()+".jpg");
	        drawCard(grpOffScreen,img,425,595,1);
	        DrawParty(grpOffScreen);
	        DrawMonsters( grpOffScreen);
	        DrawTresure(grpOffScreen);
	        DrawResolweFightButton(grpOffScreen);
    }
    protected void setFocusedPlayer(int PlayerIndex)
    {
    	this.focusPlayer=PlayerIndex;
    }
    public void DrawChoosePlayerButtons(Graphics grpOffScreen)
    {
    	Image tempImg=null;
        if(!buttonPressed)
         {
       		 tempImg = logikaMunchkin.getButtonImage();	

       		 for(int i=0;i<=1;i++)
       		 {

           		 for(int j=0;j<=1;j++)
           		 { 
       		      drawCard(grpOffScreen,tempImg,470 + j%2*150,540+ i%2*50,1);
           		 }
       		 }
       		grpOffScreen.setFont(new Font("TimesRoman", Font.PLAIN, 20));
       	     
       		grpOffScreen.setColor(Color.black);
        	   
       	int temp=0;	
       	 for(int i=0;i<=1;i++)
   		 {

       		 for(int j=0;j<=1;j++)
       		 { temp++;
   		      drawCard(grpOffScreen,tempImg,470 + j%2*150,540+ i%2*50,1);
   		   grpOffScreen.drawString("Player "+temp, 450+ j%2*150, 520+ i%2*50);
       		 }
   		 }
       		
           }else{
       		
       		tempImg = logikaMunchkin.getPressedButtonImage();	
       	    
       		drawCard(grpOffScreen,tempImg,470,540,1);
       		grpOffScreen.setFont(new Font("TimesRoman", Font.PLAIN, 20));
       	     
       		grpOffScreen.setColor(Color.black);
        	   
       		grpOffScreen.drawString("Koniec Tury", 450, 520);
           }
    }
    public void DrawResolweFightButton(Graphics grpOffScreen)
    {
    	Image tempImg=null;
    	 tempImg = logikaMunchkin.getButtonImage();	
    	   drawCard(grpOffScreen,tempImg,440 ,350,1);
    		grpOffScreen.setFont(new Font("TimesRoman", Font.PLAIN, 20));
      	     
       		grpOffScreen.setColor(Color.black);
       		if(logikaMunchkin.getCurrentFight().getPlayersStrength()>logikaMunchkin.getCurrentFight().getMonstersStrength())
       		{
       		grpOffScreen.drawString("Walcz", 450, 335);
            }else
            {
            	grpOffScreen.drawString("Uciekaj", 450, 335);
            }
       		
       		
       		
       		
	        if(logikaMunchkin.getCurrentFight().getHelperPlayer()==null)
	        {
			       	if(logikaMunchkin.getCurrentPlayer().getPlayerId()!=focusPlayer-1)	
			       	{
			     	   drawCard(grpOffScreen,tempImg,440 ,420,1);
			     		grpOffScreen.setFont(new Font("TimesRoman", Font.PLAIN, 20));
			       	     
			        		grpOffScreen.setColor(Color.black);
			    
			        		grpOffScreen.drawString("Do³¹cz do walki", 410, 400);
			            
			          
			          }
	        }
    }
    
    

	public void DrawHand(Graphics grpOffScreen)
	{
		Image tempImg =null;
	if(logikaMunchkin.getCurrentPlayer().getMyTurnPhase()!=objPlayer.TurnPhase.FIGHT)
		{	
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

	}
	public void DrawCharacterImage(Graphics grpOffScreen)
	{
		
		
		
if(logikaMunchkin.getCurrentPlayer().getMyTurnPhase()!=objPlayer.TurnPhase.FIGHT)
	{
		Image tempImg = logikaMunchkin.getCharacterImage();
    
		drawCard(grpOffScreen,tempImg,250,500,1);




		//rysowanie dla pozycji gracza numer 2 po lewej stronie
	

		drawCard(grpOffScreen,tempImg,200,160,2);


		//rysowanie dla pozycji gracza numer 3 u góry
		
	
		drawCard(grpOffScreen,tempImg,710,200,3);


		//rysowanie dla pozycji gracza numer 4 po prawej stronie
		

		drawCard(grpOffScreen,tempImg,760,500,4);
	  }
	}
    public void DrawPlayerNames(Graphics g)
    {
    	 int fontSize = 20;
     if(logikaMunchkin.getCurrentPlayer().getMyTurnPhase()!=objPlayer.TurnPhase.FIGHT)
    	{
    	    g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
    	     
    	    g.setColor(Color.black);
    	    g.drawString(logikaMunchkin.getCurrentPlayer().getName(), 350, 520);
    	    g.drawString("LVL", 350, 580);
    	    if(logikaMunchkin.getCurrentPlayer().getMyTurnPhase()!=objPlayer.TurnPhase.FIGHT)
    	    {
    	    int[] tmp=logikaMunchkin.getNextPlayerId(logikaMunchkin.getCurrentPlayer().getPlayerId());
    	    
    	    g.drawString(logikaMunchkin.getPlayer(tmp[0]).getName(), 105, 290);
    	    g.drawString("LVL", 105, 350);
    	    
    	    g.drawString(logikaMunchkin.getPlayer(tmp[1]).getName(), 530, 155);
    	    g.drawString("LVL", 530, 215);
    	    
    	    g.drawString(logikaMunchkin.getPlayer(tmp[2]).getName(), 760, 325);
    	    g.drawString("LVL", 760, 385);
    	    }
    	}
    }
    public void DrawDoorStack(Graphics g)
    {
    	if(logikaMunchkin.getCurrentPlayer().getMyTurnPhase()!=objPlayer.TurnPhase.FIGHT)
    	{
    	Image tempImg =null;
		tempImg =logikaMunchkin.getDoorImage();	

		drawCard(g,tempImg,250,340,1);
    	}
    }
    public void DrawTreasureStack(Graphics g)
    {
    	if(logikaMunchkin.getCurrentPlayer().getMyTurnPhase()!=objPlayer.TurnPhase.FIGHT)
    	{
    	Image tempImg =null;
		tempImg =logikaMunchkin.getTreasureImage();	

		drawCard(g,tempImg,400,340,1);
    	}
    }
    public void DrawSealStack(Graphics g)
    {
    	Image tempImg =null;

    	
    if(logikaMunchkin.getOpenedSeals()!=null&&logikaMunchkin.getOpenedSeals().size()!=0)
    {
   	tempImg =logikaMunchkin.getCardImage(logikaMunchkin.getOpenedSeals().getLastCard(),logikaMunchkin.getOpenedSeals().getLastCard().getIdNr());
       drawCard(g,tempImg,550,340,1);
    		
    }else
	{
    	
	tempImg =logikaMunchkin.getSealImage();	

	drawCard(g,tempImg,550,340,1);
	}
    }
    public void DrawPlayerLVL(Graphics g)
    {
    	if(logikaMunchkin.getCurrentPlayer().getMyTurnPhase()!=objPlayer.TurnPhase.FIGHT)
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
    }
    protected void DrawEndOfTurnButton(Graphics grpOffScreen)
    {
    	if(logikaMunchkin.getCurrentPlayer().getMyTurnPhase()!=objPlayer.TurnPhase.FIGHT)
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
    }
    protected void drawCard (Graphics grpOffScreen, Image imgCard, int startX, int startY,int Player) //Called by solitareColumn() to paint each card
	{
		Graphics2D g2d = (Graphics2D) grpOffScreen;


	       AffineTransform at = new AffineTransform();

	   
	       at.translate( startX,  startY);

	
	       at.rotate((Player-1)*(Math.PI/2));



	       at.translate(-imgWidth/2, -imgHeight/2);


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
		//logikaMunchkin.getCurrentPlayer().setCurrentPhase();
		if (menuSystem.isMenuVisible())
		{
			clip(menuSystem.getPaintInstruction(), grpOffScreen, g);
		}
		if(logikaMunchkin.getCurrentPlayer().getMyTurnPhase()!=objPlayer.TurnPhase.FIGHT)
		{
			DrawPlayerNames(grpOffScreen);
			DrawPlayerLVL(grpOffScreen);
	        DrawHand(grpOffScreen);
			DrawCharacterImage(grpOffScreen);
			DrawEndOfTurnButton(grpOffScreen);
			DrawDoorStack(grpOffScreen);
			DrawTreasureStack(grpOffScreen);
			DrawSealStack(grpOffScreen);
		}else
		{
			DrawBattlefield(grpOffScreen);
		}
		grpOffScreen.setClip(0, 0, getSize().width, getSize().height);
		menuSystem.drawMenu(grpOffScreen);
		g.drawImage(imgOffScreen, 0, 0, this);


	}
    public void StartNewGame(ArrayList<String> listaGraczy)
	{
		System.out.println(listaGraczy+" Starg");
		logikaMunchkin.newGame(4,listaGraczy);
		repaint();
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
   public int getFocusedPlayer()
   {
	   return focusPlayer;
   }
}
