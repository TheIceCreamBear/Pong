package com.theicecreambear.pong2.network;

public class GameMessage {
	public Type messageType;
	
	public GameMessage(Type type) {
		
	}

	public enum Type {
		LOCAL_PALYER,
		NETWEORED_PALYER,
		GAME_OBJECT,
		BALL;
	}
}