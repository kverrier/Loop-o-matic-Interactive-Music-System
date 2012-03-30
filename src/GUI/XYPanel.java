package GUI;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import Scheduling.Voice;


public class XYPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	public CtrlPanel xCtrls;
	public CtrlPanel yCtrls;
	public Component joystick;
	
	public XYPanel() {
		Voice xVoice = new Voice();
		Voice yVoice = new Voice();
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		yCtrls = new CtrlPanel(xVoice, false);
		c.gridx = 0;
		c.gridy = 1;
		add(yCtrls, c);
		joystick = new XYMouse(xVoice, yVoice);
		c.gridx = 1;
		c.gridy = 0;
		c.gridheight = 2;
		c.gridwidth = 2;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.fill = GridBagConstraints.BOTH;
		add(joystick, c);
		c.gridx = 1;
		c.gridy = 2;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.NONE;
		xCtrls = new CtrlPanel(yVoice, true);
		add(xCtrls, c);
	}
}
