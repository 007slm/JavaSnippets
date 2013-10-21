package event;

import java.util.ArrayList;
import java.util.EventListener;

public  class  DoorManager {
	private ArrayList<EventListener> listeners =new ArrayList<EventListener>();
	
	private void add(DoorEventListener listener){
		listeners.add(listener);
	}
	
	
	
	public void openDoor(Door door) {
		door.setState("open");
	}
	
	public void closeDoor(Door door) {
		door.setState("close");
	}
}
