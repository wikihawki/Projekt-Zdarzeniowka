import java.awt.List;
import java.util.ArrayList;

//Author: ^-^ Veerle ^-^
//Used to set what area of the screen to paint to
public class objInstruction
{

	private int startX = 0, startY = 0, width = 0, height = 0;
	private int canvasWidth = 0, canvasHeight = 0;
	private  ArrayList<Integer> polaGraczyX = new  ArrayList<Integer>();
	private  ArrayList<Integer> polaGraczyY = new  ArrayList<Integer>();
	
	public objInstruction (int canvasWidth, int canvasHeight) //Creates an instruction that will paint the whole screen
	{
		
		this.canvasWidth = canvasWidth;
		this.canvasHeight = canvasHeight;
		// Pozycja rêki gracza 1
		this.polaGraczyX.add(100);
		this.polaGraczyY.add(700);
		// Pozycja rêki gracza 2
		this.polaGraczyX.add(0);
		this.polaGraczyY.add(130);
		// Pozycja rêki gracza 3
		this.polaGraczyX.add(100);
		this.polaGraczyY.add(700);
		// Pozycja rêki gracza 4
		this.polaGraczyX.add(100);
		this.polaGraczyY.add(700);
	}
	
	public objInstruction (int startX, int startY, int width, int height) //Creates an instruction with a specific clip
	{
		
		this.startX = startX;
		this.startY = startY;
		this.width = width;
		this.height = height;
		
	}
	
	public void reset ()
	{
		
		startX = 0;
		startY = 0;
		width = canvasWidth;
		height = canvasHeight;

	}
	
	public int getStartX ()
	{
		return startX;
	}
	
	public int getStartY ()
	{
		return startY;
	}
	
	public int getWidth ()
	{
		return width;
	}
	
	public int getHeight ()
	{
		return height;
	}
	
	public void setRect (int startX, int startY, int width, int height)
	{
		
		this.startX = startX;
		this.startY = startY;
		this.width = width;
		this.height = height;
		
	}	
	
	public void setInstruction (objInstruction otherInstruction)
	{
		
		this.startX = otherInstruction.getStartX();
		this.startY = otherInstruction.getStartY();
		this.width = otherInstruction.getWidth();
		this.height = otherInstruction.getHeight();
		
	}		
	
	public int getPlayerHandPositionX(int Gracz)
	{
		return polaGraczyX.get(Gracz);
	}
	
	public int getPlayerHandPositionY(int Gracz)
	{
		return polaGraczyY.get(Gracz);
	}
	
}