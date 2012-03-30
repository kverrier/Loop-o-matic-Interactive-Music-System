package GUI;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Scheduling.Voices;

/*
 * Creates GUI that can have interactive looping MIDI notes with 
 * adjustable parameters.
 * 
 * Design Decisions:
 * 
 * Each XY panel has 2 Voice objects with adjustable qualities
 * 
 * Event scheduler takes VoiceEvents that when run properly send MIDI
 * msgs to receiver and schedule future STOPs and STARTs appropriately
 * 
 * On/Off button could always be synced to a global clock but instead 
 * decided to allow for phase shifts done through human interaction.
 * 
 * The scheduler continuously polls on an incrementing virtual clock
 * to check if any events are pending. This clock is subject to overflow
 * but this will occur after an order of hundreds of hours running.
 * 
 * 
 */
public class Main {
	private static JPanel panel;

	private static void createGUI() {
		JFrame frame = new JFrame("Loop-o-Matic");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new LooperPanel();
		panel.setOpaque(true);
		frame.setContentPane(panel);
		frame.pack();
		frame.setVisible(true);

	}

	public static void main(String[] args) throws InterruptedException {
		System.out.println("starting main");
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createGUI();
			}
		});

		try {
			Voices.play();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
