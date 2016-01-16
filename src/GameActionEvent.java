import java.util.EventObject;

public class GameActionEvent extends EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String type;
	private objCard card;
	public GameActionEvent(Object source, String type, objCard card)
	{
		super(source);
		this.type=type;
		this.card=card;
	}
	public String getType() {
		return type;
	}
	public objCard getCard() {
		return card;
	}
	

}
