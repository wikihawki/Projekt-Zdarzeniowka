//Author: ^-^ Veerle ^-^
//Used to set what area of the screen to paint to
public class objInstruction
{

	private int startX = 0, startY = 0, width = 0, height = 0;
	private int canvasWidth = 0, canvasHeight = 0;
	
	public objInstruction (int canvasWidth, int canvasHeight) //Creates an instruction that will paint the whole screen
	{
		
		this.canvasWidth = canvasWidth;
		this.canvasHeight = canvasHeight;
		
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
	
}