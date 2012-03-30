package Scheduling;

import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

import Midi.MyMidiResources;

/*
 * Represents an event in which the voice is sending a midi msg
 * to start playing or stop playing. This is scheduled and run by the 
 * scheduler.
 */
public class VoiceEvent extends Event {
	public enum Msg {
		START, STOP
	};

	public Voice voice;
	public int pitch;
	public int dur;
	public Msg msg;

	/*
	 * Pitch and Duration needed to prevent MIDI pitches from never getting a
	 * stop message and duration to wait until the current loop is finished to
	 * adjust to a new duration.
	 */
	public VoiceEvent(Voice v, int p, int d, Msg m, double when) {
		super(when);
		voice = v;
		msg = m;
		pitch = p;
		dur = d;
	}

	/*
	 * Plays a MIDI message to system default receiver. Then it will schedule
	 * either a corresponding note end or next loop start if the ON button is
	 * toggled.
	 */
	@Override
	public void run() {
		Receiver recv = MyMidiResources.getReceiver();
		ShortMessage midiMsg;

		if (msg == Msg.START) {
			midiMsg = voice.msgOn();

			// To prevent match cases of START times subtract 1
			double stopTime = timeStamp + voice.length * (dur / 8.0) * 1000 - 1;
			Scheduler.schedule(new VoiceEvent(voice, pitch, dur, Msg.STOP,
					stopTime));

		} else {
			midiMsg = voice.msgOff(pitch);

			if (!voice.killed) {
				// need to add back 1 to return to multiple of the length
				double nextTime = timeStamp + voice.length * (1 - dur / 8.0)
						* 1000 + 1;
				Scheduler.schedule(new VoiceEvent(voice, voice.pitch,
						voice.dur, Msg.START, nextTime));
			}
		}

		recv.send(midiMsg, -1);

	}
}
