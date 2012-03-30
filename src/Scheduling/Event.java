package Scheduling;

// Event -- abstract superclass for schedulable event

public abstract class Event implements Runnable, Comparable<Event> {
    public double timeStamp;

    protected Event(double when) { timeStamp = when; }

    // schedule the event to run at when
    public void schedule(double when) {
    	timeStamp = when;
    	Scheduler.schedule(this);
    }
    
    // after(delay) returns the virtual time that is 
    // delay seconds into the future
    protected double after(double delay) { 
    	return Scheduler.now + delay; 
    }

    // compare event times, used by PriorityQueue<Event>
    public int compareTo(Event other) {
    	if (timeStamp < other.timeStamp) return -1;
    	else if (timeStamp == other.timeStamp) return 0;
    	else return 1;
    }
}
