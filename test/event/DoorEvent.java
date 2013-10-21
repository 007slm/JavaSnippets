package event;

import java.util.EventObject;

public class DoorEvent extends EventObject{

	public DoorEvent(Door source) {
		super(source);
	}
	
}
