import java.util.EventObject;

public class GameEvent extends EventObject
{
	private static final long serialVersionUID = 1L;
	public enum EventType{SEALOPEN, FIGHTCHANGED, FIGHTOVER, SEALCLOSED, LEVELUP,SEVENTHSEAL,DSICARD,RUNAWAY,INVENTORYCHANGED, TOUREND}
	private EventType eventType;
	private objEntity target;
	public GameEvent(Object source, EventType eventType, objEntity target)
	{
		super(source);
		this.eventType=eventType;
		this.target=target;
	}
	public EventType getEventType() {
		return eventType;
	}
	public objEntity getTarget()
	{
		return target;
	}

}
