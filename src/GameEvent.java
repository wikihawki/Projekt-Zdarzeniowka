import java.util.EventObject;

public class GameEvent extends EventObject
{
	private static final long serialVersionUID = 1L;
	public enum EventType{SEALOPEN, FIGHTCHANGED, FIGHTOVER, SEALCLOSED, LEVELUP,SEVENTHSEAL}
	private EventType eventType;
	public GameEvent(Object source, EventType eventType)
	{
		super(source);
		this.eventType=eventType;
	}
	public EventType getEventType() {
		return eventType;
	}

}
