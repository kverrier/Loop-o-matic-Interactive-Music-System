package Scheduling;

/*
 * Static Class that takes care of the looping of all the voices.
 * 
 * Polls every 2 milliseconds to the scheduler for pending events to be run.
 */
public class Voices {

	public static void play() throws InterruptedException {
		Scheduler.initialize();

		// Beware of overflow. Occurs a little bit before ~600 hours of play.
		// Avoid by buffered scheduler with overflow values that gets swapped in
		for (int i = 0; i < Integer.MAX_VALUE; i++) {
			// Virtual time does not progress if there are no pending events
			if (Scheduler.execute((double) i * 2) == Scheduler.State.FINISHED) {
				i--;
			}
			Thread.sleep(2);
		}
	}
}
