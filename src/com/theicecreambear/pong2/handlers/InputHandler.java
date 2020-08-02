package com.theicecreambear.pong2.handlers;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class InputHandler {

	/**
	 * @author David Santamaria
	 * @version 0.2.1
	 */
	public static InputHandler handler;
	boolean[] keys = new boolean[256];
	boolean[] mouse = new boolean[4];
	MouseEvent[] mEvents = new MouseEvent[4];

	public InputHandler(Component c) {
		c.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
			}

			public void keyPressed(KeyEvent e) {
				keys[e.getKeyCode()] = true;
			}

			public void keyReleased(KeyEvent e) {
				keys[e.getKeyCode()] = false;
			}
		});
		c.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {

			}

			public void mousePressed(MouseEvent e) {
				mouse[e.getButton()] = true;
				mEvents[e.getButton()] = e;
			}

			public void mouseReleased(MouseEvent e) {
				mouse[e.getButton()] = false;
				mEvents[e.getButton()] = e;
			}

			public void mouseEntered(MouseEvent e) {

			}

			public void mouseExited(MouseEvent e) {

			}
		});
		handler = this;
	}

	public synchronized boolean isKeyDown(int keyCode) {
		if (keyCode > 0 && keyCode < keys.length) {
			return keys[keyCode];
		}

		return false;
	}

	public boolean isMouseDown(int button) {
		if (button > 0 && button <= 3) {
			return mouse[button];
		}
		return false;
	}

	public MouseEvent getEvent(int event) {
		if (event > 0 && event <= 3) {
			return mEvents[event];
		}

		return null;
	}
	
	public synchronized boolean isMoveKeyDown() {
		// right, left, up, down
		if (this.isKeyDown(KeyEvent.VK_RIGHT) || this.isKeyDown(KeyEvent.VK_D)) {
			return true;
		} else if (this.isKeyDown(KeyEvent.VK_LEFT) || this.isKeyDown(KeyEvent.VK_A)) {
			return true;
		} else if (this.isKeyDown(KeyEvent.VK_UP) || this.isKeyDown(KeyEvent.VK_W)) {
			return true;
		} else if (this.isKeyDown(KeyEvent.VK_DOWN) || this.isKeyDown(KeyEvent.VK_S)) {
			return true;
		}
		return false;
	}
}
