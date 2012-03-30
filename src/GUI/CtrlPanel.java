package GUI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JPanel;

import Scheduling.Scheduler;
import Scheduling.Voice;
import Scheduling.VoiceEvent;

public class CtrlPanel extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected JButton onOff;
	protected ParamPanel velPanel;
	protected ParamPanel durPanel;
	protected ParamPanel chanPanel;
	protected ParamPanel pitchPanel;

	// add voice reference to send to ParamPanel and toggle on/off
	private Voice voice;

	public boolean on;

	public CtrlPanel(Voice v, boolean horizontal) {
		super(new GridLayout(horizontal ? 1 : 5, horizontal ? 5 : 1));
		onOff = new JButton("Off");
		onOff.setSize(30, 20);
		onOff.setVerticalTextPosition(AbstractButton.CENTER);
		onOff.setHorizontalTextPosition(AbstractButton.CENTER);
		onOff.setActionCommand("on-off");
		onOff.addActionListener(this);
		on = false;

		voice = v;

		velPanel = new ParamPanel(voice, 100, 1, 127, "Vel");
		pitchPanel = new ParamPanel(voice, 69, 0, 127, "Pitch");
		chanPanel = new ParamPanel(voice, 1, 1, 16, "Chan");
		durPanel = new ParamPanel(voice, 6, 1, 8, "Dur");
		if (horizontal) {
			add(onOff);
			add(velPanel);
			add(pitchPanel);
			add(chanPanel);
			add(durPanel);
		} else {
			add(durPanel);
			add(chanPanel);
			add(pitchPanel);
			add(velPanel);
			add(onOff);
		}
	}

	public void actionPerformed(ActionEvent e) {
		if ("on-off".equals(e.getActionCommand())) {
			if (onOff.getText().equals("On")) {
				onOff.setText("Off");
				on = false;

				// Set that further STARTs will not be scheduled from run events
				voice.killed = true;

			} else {
				onOff.setText("On");
				on = true;

				// schedule a starting note and looping to begin
				voice.killed = false;
				Scheduler.schedule(new VoiceEvent(voice, voice.pitch,
						voice.dur, VoiceEvent.Msg.START, Scheduler.now));

			}
			System.out.println("on-off pressed");
		}
	}
}
