package Scheduling;
// Scheduler -- a general discrete event executive



import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.PriorityQueue;

public class Scheduler {
	private static PriorityQueue<Event> pending;
	public static double now;
	public enum State { RUNNING, CURRENT, FINISHED }
	private static PrintWriter log;
	
	// call initialize before each run
	public static void initialize() {
		pending = new PriorityQueue<Event>();
		now = 0;
	}
	
	// schedule an event to run in the future
	public static void schedule(Event event) {
		pending.add(event);
	}
	
	// run the next event in the queue
	public static void dispatch() {
		Event event = pending.poll();
		if (event != null) {
			event.run();
		}
	}
	
	// runs one event (if any) before end, 
	// returns true if event was run
	// returns false if no event was run due to
	//   the event time beyond end or no more events
	public static State step(double end) {
	    Event next = pending.peek();
	    if (next == null) return State.FINISHED;
	    now = next.timeStamp;
	    if (now > end) return State.CURRENT;
		dispatch();
		return State.RUNNING;
	}
	
	// runs until virtual time exceeds end
	// returns true if execution finishes (no more events)
	public static State execute(double end) {
	    State state = State.RUNNING;
            while (state == State.RUNNING) {
                state = step(end);
            }
            return state;
	}
	
	// open a log file
	public static void logto(String name) {
		try {
    		log = new PrintWriter(new FileWriter(name));
		}
		catch (Exception e) {
			System.err.println("Could not open" + name);
		}
	}
	
	// write a string to the log file after appending current time
	public static void log(String s) {
		log.println(Double.toString(now) + " " + s);
	}

	// close the log file
	public static void logClose() {
		log.close();
	}

}
