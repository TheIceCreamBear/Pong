package com.theicecreambear.pong2.player;

import com.theicecreambear.pong2.gameobject.GameObject;

public abstract class Player extends GameObject {
	public abstract boolean isBallColiding(int ballY);
}