package com.theicecreambear.pong2.player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.ImageObserver;

import com.theicecreambear.pong2.screen.Screen;

public class NetworkedPlayer extends Player {
	private Color color;

	private int x = 0;
	private int y = 37;

	private int length = 60;
	private int width = 20;

	public NetworkedPlayer(Color color) {
		this(color, 1);
	}

	public NetworkedPlayer(Color color, int id) {
		this.color = color;
		this.x = id == 1 ? 0 : Screen.width - 21;
	}

	@Override
	public void update(double deltaTime) {
		// TODO
		
		if (canMoveUp()) {
			this.y -= 3;
		}

		if (this.canMoveDown()) {
			this.y += 3;
		}

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

	public boolean isBallColiding(int ballY) {
		if (ballY >= this.y && ballY <= this.y + 60) {
			return true;
		}
		return false;
	}
}