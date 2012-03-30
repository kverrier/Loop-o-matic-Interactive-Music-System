Loop-o-Matic Interactive Music System
=====================================
Overview
--------
The Loop-o-matic is an interactive musical “instrument” inspired by a design by David Wessel ([Video Reference](http://www.youtube.com/watch?v=q_mtCZqN0Ms)). The instrument generates MIDI notes that repeat, with interactive control over therepetition rate, pitch, velocity, duration, channel, and program. Unlike most drum machines, this program allows different drums or notes to play at different rates, creating interesting musical patterns and textures.

Running the Program
-------------------
To run the Loop-o-matic, download and open the [jar file](https://github.com/downloads/kverrier/Loop-o-matic-Interactive-Music-System/loop-o-matic.jar). After running the jar file, a window will appear resembeling this:

![start-up](http://i.imgur.com/RHC14.png)

There are 8 voices, where each voice plays a note over and over again. The parameters of the note are given by the interface. Each large square represents two voices. Once voice follows the controls below the box and the horizontal position of the small circle in the box, and the other voice follows the controls to the left of the box and the vertical position of the small circle. These controls specify the duration (1-8), channel (1-16), pitch (0-127), and velocity (1-127) of the note, and there is an on/off switch that enables or mutes the voice. In addition, the “tempo” or inter-note onset (IOI) time of the voice is controlled by the position of the small drag-able circle in the box. The relative position of the circle specifies the repetition rate of the note, from 0.5Hz to 5Hz (30 to 300BPM).

There is a separate control for MIDI Program, shown on the left of the image.


Details
-------
More precisely, a note is generated when the on/off switch is turned on. The note takes the current pitch, velocity, and channel from the associated controls. The duration (from 1 to 8) determines the proportion of the IOI taken by the note. For example, if the repetition rate is 2Hz (120BPM), the IOI is 0.5s. If the duration is 5, then the note-off message will be sent 5/8 of a period = 5/8 * 0.5s = 5/16 = 0.3125s after the note-on message. After one period (the IOI), a new note-on will be sent using the control values in effect at that time. Notes are generated until the on/off switch is set to off. The tempo scale is logarithmic. If the linear position of the small circle, expressed as a fraction of full-scale, is x, then the tempo is 30 * 10^x (beats per minute), where 0 ≤ x ≤ 1. 

Implementation
--------------
This is implemented with a single scheduler using a heap-based priority queue and in this with precise timing for all 8 voices.  The scheduler polls for work every 2 milliseconds within the same thread handling the graphical interface.
