//Author ^-^ Veerle ^-^
//Extends a solitare stack and is used for dragging a stack of cards
public class dragStack extends solitareStack
{
	
	private int dragStartCard = 0, orgColumn = 0;
	private int mouseCurrentX = 0, mouseCurrentY = 0;
	private objInstruction previousPaintInstruction = new objInstruction(0,0);
	
	public void dragStack ()
	{
	}
	
	public int getDragStartCard ()
	{
		return dragStartCard;
	}
	
	public void setDragStartCard (int dragStartCard)
	{
		this.dragStartCard = dragStartCard;
	}
	
	public int getOrgColumn ()
	{
		return orgColumn;
	}
	
	public void setOrgColumn (int orgColumn)
	{
		this.orgColumn = orgColumn;
	}
	
	public void setMouseCurrentPosition (int mouseCurrentX, int mouseCurrentY)
	{
		
		this.mouseCurrentX = mouseCurrentX;
		this.mouseCurrentY = mouseCurrentY;
		
		this.startX = (mouseCurrentX - 30);
		this.startY = (mouseCurrentY - 25);
		
	}
	
	public void setStartInstruction (objInstruction previousPaintInstruction)
	{
		this.previousPaintInstruction = previousPaintInstruction;
	}
	
	public objInstruction getPaintInstruction ()
	{
		int stackHeight = getStackHeight(0);
		
		int previousStartX = previousPaintInstruction.getStartX();
		int previousStartY = previousPaintInstruction.getStartY();
		
		previousPaintInstruction.setRect(startX, startY, 71, stackHeight);
		
		int clipStartX = 0;
		int clipStartY = 0;
		int clipWidth = 0;
		int clipHeight = 0;
		
		if (previousStartX >= startX)
		{
			
			clipStartX = startX;
			clipWidth = clipStartX + (previousStartX - clipStartX) + 71;
			
		}
		else
		{
			
			clipStartX = previousStartX;
			clipWidth = clipStartX + (startX - previousStartX) + 71;
			
		}
		
		if (previousStartY >= startY)
		{
			
			clipStartY = startY;
			clipHeight = clipStartY + (previousStartY - clipStartY) + stackHeight;
			
		}
		else
		{
			
			clipStartY = previousStartY;
			clipHeight = clipStartY + (startY - previousStartY) + stackHeight;
			
		}
		
		return new objInstruction(clipStartX, clipStartY, clipWidth, clipHeight);
		
	}
	
}