package com.theicecreambear.pong2.player;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;

import com.theicecreambear.pong2.engine.GameEngine;
import com.theicecreambear.pong2.handlers.InputHandler;
import com.theicecreambear.pong2.screen.Screen;

public class LocalPlayer extends Player {

	private InputHandler handler;
	private Color color;

	private int id = 0;
	private int x = 0;
	private int y = 37;
	
	private int length = 60;
	private int width = 20;
	
	private int upKeyNum;
	private int downKeyNum;

	public LocalPlayer(Component c) {
		this(c, Color.white);
	}
	
	public LocalPlayer(Component c, Color color) {
		this(c, color, 1);
	}
	
	public LocalPlayer(Component c, Color color, int id) {
		this.color = color;
		this.handler = new InputHandler(c);
		this.x = id == 1 ? 0 : Screen.width - 21;
		this.id = id;
		this.assignMoveKeys(id);
	}
	
	/**
	 * FOR LOCAL 1V1 ONLY
	 */
	public LocalPlayer(LocalPlayer lp) {
		this.color = lp.color;
		this.handler = lp.handler;
		if (lp.id == 1) {
			this.id = 2;
			this.assignMoveKeys(id);
			this.x = Screen.width - 21;
			return;
		}
		
		if (lp.id == 2) {
			this.id = 1;
			this.assignMoveKeys(id);
			this.x = 0;
			return;
		}
	}


	@Override
	public void update(double deltaTime) {
//		this.localTicks++;
		
		
		if (handler.isKeyDown(KeyEvent.VK_ESCAPE) && GameEngine.instance.isConsoleShowing()) {
			GameEngine.instance.hideConsole();
		}
		
		if (handler.isKeyDown(KeyEvent.VK_ESCAPE) && !GameEngine.instance.isConsoleShowing()) {
			System.exit(-1);
		}
		
		if (handler.isKeyDown(KeyEvent.VK_DIVIDE)) {
//			GameEngine.instance.showConsole();
			// TODO Console showing stuffs
		}
		
		if (GameEngine.instance.isConsoleShowing()) {
			return;
		}
		
		
		if (handler.isKeyDown(this.upKeyNum) && !isOtherMoveKeyDown(KeyEvent.VK_W)) {
			if (canMoveUp()) {
				this.y -= 3;
			}
		}

		if (handler.isKeyDown(this.downKeyNum) && !isOtherMoveKeyDown(KeyEvent.VK_S)) {
			if (this.canMoveDown()) {
				this.y += 3;
			}
		}
		
	}

	@Override
	public void draw(Graphics g, ImageObserver observer) {
		g.setColor(color);
		g.fillRect(x, y, width, length);
	}

	private boolean isOtherMoveKeyDown(int currentKey) {
		if (currentKey == KeyEvent.VK_UP || currentKey == KeyEvent.VK_W) {
			if (handler.isKeyDown(KeyEvent.VK_DOWN) || handler.isKeyDown(KeyEvent.VK_S)) {
				return true;
			}

		}
		
		if (currentKey == KeyEvent.VK_DOWN || currentKey == KeyEvent.VK_S) {
			if (handler.isKeyDown(KeyEvent.VK_UP) || handler.isKeyDown(KeyEvent.VK_W)) {
				return true;
			}

		}

		return false;
	}
	
	private boolean canMoveUp() {
		if (this.y <= 37) {
			return false;
		}
		return true;
	}
	
	private boolean canMoveDown() {
		if (this.y + this.length >= Screen.height - 13) {
			return false;
		}
		return true;
	}
	
	public boolean isBallColiding(int ballY) {
		if (ballY >= this.y && ballY <= this.y + 60) {
			return true;
		}
		return false;
	}
	
	private void assignMoveKeys(int id) {
		if (id == 1) {
			this.upKeyNum = KeyEvent.VK_W;
			this.downKeyNum = KeyEvent.VK_S;
		} else if (id == 2) {
			this.upKeyNum = KeyEvent.VK_UP;
			this.downKeyNum = KeyEvent.VK_DOWN;
		}
	}
}