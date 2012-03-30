package GUI;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import Scheduling.Voice;

public class XYMouse extends JPanel implements MouseMotionListener {

	private static final long serialVersionUID = 1L;
	public int x;
	public int y;

	// Added voice references for mouse events to affect
	private Voice xVoice;
	private Voice yVoice;

	public XYMouse(Voice v1, Voice v2) {
		xVoice = v1;
		yVoice = v2;

		x = 100;
		y = 100;
		addMouseMotionListener(this);
	}

	public void mouseMoved(MouseEvent e) {
		return;
	}

	public void mouseDragged(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		repaint();

		// change period of voice
		xVoice.length = getXDur();
		yVoice.length = getYDur();

	}

	public void paint(Graphics g) {
		super.paintComponent(g);
		g.fillOval(x - 5, y - 5, 11, 11);
	}

	public float getXDur() {
		// duration at left is 2.0s, at right is 0.2s
		int w = getWidth();
		float f = (float) x / (float) w;
		return (float) (2.0 / Math.pow(10.0, f));
	}

	public float getYDur() {
		// duration at left is 2.0s, at right is 0.2s
		int h = getHeight();
		float f = (float) y / (float) h;
		return (float) (2.0 / Math.pow(10.0, f));
	}

}
