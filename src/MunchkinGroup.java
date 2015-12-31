import java.util.Vector;

public class MunchkinGroup
{
	private Vector<objCard> group=new Vector<objCard>();
	
	protected int startX=0,startY=0;
	
	protected boolean faceUp=false;
	
	
	public MunchkinGroup()
	{
		
	}
	protected void addCard(objCard card)
	{
		group.addElement(card);
	}
	protected objCard removeCard(int index)
	{
		return group.remove(index);
	}
	protected void addStack(Vector<objCard> vec)
	{
		for(int i=0;i<vec.size();i++) addCard(vec.remove(0));
	}
	protected objCard getCard(int index)
	{
		return group.elementAt(index);
	}
	protected int size()
	{
		return group.size();
	}
	
}
