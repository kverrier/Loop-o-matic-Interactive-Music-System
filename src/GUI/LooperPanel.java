package GUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Midi.MyMidiResources;

// make 4 XLPanels in a grid, and add midi program controls
public class LooperPanel extends JPanel implements ChangeListener {

	private static final long serialVersionUID = 1L;
	protected JSpinner[] programs; // array of program selectors
	static final int NCHANS = 16;

	public LooperPanel() {
		super(new GridBagLayout());
		programs = new JSpinner[NCHANS];
		// create MIDI Program controls
		JPanel programsPanel = new JPanel(new GridBagLayout());
		for (int chan = 0; chan < NCHANS; chan++) {
			GridBagConstraints c = new GridBagConstraints();
			c.gridx = 0;
			c.gridy = chan;
			programsPanel.add(new JTextArea(Integer.toString(chan + 1)), c);
			JSpinner prog;
			SpinnerNumberModel model = new SpinnerNumberModel(1, 1, 128, 1);
			prog = new JSpinner(model);
			prog.setSize(40, 15);
			prog.addChangeListener(this);
			c.gridx = 1;
			programsPanel.add(prog, c);
			programs[chan] = prog;
		}

		GridBagConstraints c = new GridBagConstraints();
		c.gridheight = 2;
		add(programsPanel, c);
		c.gridheight = 1;

		for (int col = 1; col <= 2; col++) {
			for (int row = 0; row <= 1; row++) {
				c.gridx = col;
				c.gridy = row;
				add(new XYPanel(), c);
			}
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// find the spinner that generated this event
		// linear search is ugly but simple and fast since n = 16
		for (int i = 0; i < NCHANS; i++) {
			if (programs[i] == e.getSource()) {
				System.out.println("channel " + (i + 1) + " program "
						+ programs[i].getValue());
				try {
					// send PC message through MIDI
					Receiver recv = MyMidiResources.getReceiver();
					ShortMessage msgPC = new ShortMessage();
					msgPC.setMessage(ShortMessage.PROGRAM_CHANGE, i,
							(Integer) programs[i].getValue());
					recv.send(msgPC, -1);
				} catch (InvalidMidiDataException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}
