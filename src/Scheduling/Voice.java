package Scheduling;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

/*
 * Represents a voice that will generate MIDI messages with specific
 * properties for the GUI interface.
 */
public class Voice {

	public Receiver recv;

	public enum Msg {
		ON, OFF
	};

	public boolean killed;
	public int dur;
	public int chan;
	public int pitch;
	public int vel;
	public double length;

	public Voice() {
		dur = 6;
		chan = 6;
		pitch = 69;
		vel = 100;
		length = 1;
		killed = true;

	}

	public ShortMessage msgOn() {

		try {
			ShortMessage msgOn = new ShortMessage();
			msgOn.setMessage(ShortMessage.NOTE_ON, chan, pitch, vel);
			return msgOn;
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ShortMessage msgOff(int p) {
		try {

			ShortMessage msgOff = new ShortMessage();
			msgOff.setMessage(ShortMessage.NOTE_OFF, chan, p, 0);
			return msgOff;
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		}
		return null;
	}

}
