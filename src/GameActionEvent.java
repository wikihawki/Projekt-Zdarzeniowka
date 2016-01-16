import java.util.EventObject;

public class GameActionEvent extends EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String type;
	public GameActionEvent(Object source)
	{
		super(source);
		
	}

}
