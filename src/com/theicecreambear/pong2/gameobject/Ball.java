package com.theicecreambear.pong2.gameobject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.ImageObserver;

import com.theicecreambear.pong2.engine.GameEngine;
import com.theicecreambear.pong2.refrence.Refrence;
import com.theicecreambear.pong2.screen.Screen;

public class Ball extends GameObject {
	
	public int leftScore = 0;
	public int rightScore = 0;

	private final int startX = 600 - 15;
	private final int startY = 400 - 15;
	
	/** The x cord represents the top left of the rectangle that the ball fills	 */
	private int x = startX;

	/** The y cord represents the top left of the rectangle that the ball fills	 */
	private int y = startY;
		
	private double xScale = 1;
	private double yScale = 1;
	private int tickDelay = 0;
	
	private boolean up = true;
	private boolean left = true;
	
	private final int ballWidth = 30;
	private final int ballHeight = 30;

	@Override
	public void draw(Graphics g, ImageObserver observer) {
		g.setColor(new Color(30, 0, 195));
		g.fillOval(x, y, ballWidth, ballHeight);
	}

	@Override
	public void update(double deltaTime) {
		if (this.tickDelay > 0) {
			this.tickDelay--;
			return;
		}
		
		if (up) {
			this.y -= 3 * this.yScale;
		} else {
			this.y += 3 * this.yScale;
		}
		
		
		if (left) {
			this.x -= 3 * this.xScale;
		} else {
			this.x += 3 * this.xScale;
		}
		
		if (Refrence.DEBUG_MODE) {			
			System.err.println("Ball: x " + x + " y " + y + " left " + left + " up " + up);
		}
		
		checkColision();
		checkScored();
	}
	
	public void respawn() {
		this.x = startX;
		this.y = startY;
		this.up = true;
		this.left = true;
		this.xScale = 1;
		this.yScale = 1;
		this.tickDelay = 60;
	}
	
	private void checkColision() {
		
		// TODO non linear ball movement
		
		if (this.x < 20 && GameEngine.instance.p1.isBallColiding(y)) {
			this.x = 20;
			this.left = false;
			
//			if (this.y) TODO PLAYERS ARE CURRENTLY 60 PIXELS, MAYBE DIVIDE THIS COLISION INTO 6 PARTS of 10 PIXLES OR 4 PARTS OF 15 PIXELS
		}
		
		if (this.x + ballWidth > Screen.width - 21 && GameEngine.instance.p2.isBallColiding(y)) {
			// TODO add player check
			this.x = Screen.width - 21 - ballWidth;
			this.left = true;
		}
		
		if (this.y < 36) {
			this.y = 36;
			this.up = false;
		}
		
		if (this.y + ballHeight > Screen.height - 13) {
			this.y = Screen.height - 13 - ballHeight;
			this.up = true;
		}
	}
	
	private void checkScored() {
		if (x + 30 <= 1) {
			this.rightScore++;
			this.respawn();
		}
		
		if (x >= Screen.width) {
			this.leftScore++;
			this.respawn();
		}
	}
	
	public int getY() {
		return this.y;
	}
}