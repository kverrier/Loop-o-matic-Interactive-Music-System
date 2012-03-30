package GUI;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Scheduling.Voice;

public class ParamPanel extends JPanel implements ChangeListener {

	private static final long serialVersionUID = 1L;
	protected JSpinner spin;
	protected SpinnerNumberModel model;
	protected JTextArea label;
	public int val;

	// add voice reference to change params
	private Voice voice;

	@Override
	public void stateChanged(ChangeEvent e) {
		val = (Integer) model.getNumber();
		String param = label.getText();

		// change parameters
		if (param.equals("Vel")) {
			voice.vel = val;
		} else if (param.equals("Pitch")) {
			voice.pitch = val;
		} else if (param.equals("Chan")) {
			voice.chan = val - 1;
		} else {
			voice.dur = val;
		}
	}

	public ParamPanel(Voice v, int initial, int lowest, int highest, String text) {
		super(new GridLayout(2, 1));
		voice = v;

		label = new JTextArea(text);
		label.setSize(40, 20);
		add(label, "North");
		val = initial;
		model = new SpinnerNumberModel(val, lowest, highest, 1);
		spin = new JSpinner(model);
		spin.setSize(40, 15);
		spin.addChangeListener(this);
		add(spin, "South");
	}
}
