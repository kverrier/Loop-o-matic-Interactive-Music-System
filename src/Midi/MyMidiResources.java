package Midi;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;

public class MyMidiResources {

	public static Receiver getReceiver() {
		try {
			return MidiSystem.getReceiver();
		} catch (MidiUnavailableException e) {
			e.printStackTrace();
		}
		return null;
	}
}
