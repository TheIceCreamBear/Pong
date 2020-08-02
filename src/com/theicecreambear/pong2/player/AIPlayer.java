package com.theicecreambear.pong2.player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.ImageObserver;

import com.theicecreambear.pong2.engine.GameEngine;
import com.theicecreambear.pong2.screen.Screen;

public class AIPlayer extends Player {
	private Color color;
	private String side;

	private int movingTicks = 0;
	private int tickDelay = 0;
	
	private int x = Screen.width - 21;
	private int y = 37;
	private int lastY = 37;
	
	private int length = 60;
	private int width = 20;
	
	// TODO difficulty stuff

	public AIPlayer() {
		this(Color.white);
	}
	
	public AIPlayer(Color color) {
		this(color, 2);
	}
	
	public AIPlayer(Color color, int id) {
		this.color = color;
		
		if (id == 2) {
			this.side = "right";
			this.x = Screen.width - 21;
			return;
		}
		
		if (id == 1) {
			this.side = "left";
			this.x = 0;
			return;
		}
	}


	@Override
	public void update(double deltaTime) {
		if (this.tickDelay > 0) {
			this.tickDelay--;
			return;
		}
		
		this.movingTicks++;
		
		if (this.movingTicks >= 90) {
			this.tickDelay = 20;
			this.movingTicks = 0;
			return;
		}
		if (this.y + 15 >= GameEngine.instance.ball.getY()) {
			if (this.canMoveUp()) {
				this.y -= 2;
			}
		}
		
		if (this.y + 15 < GameEngine.instance.ball.getY()) {
			if (this.canMoveDown()) {
				this.y += 2;
			}
		}
		
		if (!this.didMovement()) {
			this.movingTicks = 0;
		}
		this.lastY = y;
	}

	@Override
	public void draw(Graphics g, ImageObserver observer) {
		g.setColor(color);
		g.fillRect(x, y, width, length);
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
	
	private boolean didMovement() {
		if (this.y == this.lastY) {
			return true;
		}
		return false;
	}
	
	public boolean isBallColiding(int ballY) {
		if (ballY >= this.y && ballY <= this.y + 60) {
			return true;
		}
		return false;
	}
	
	public String getSideOField() {
		return this.side;
	}
}