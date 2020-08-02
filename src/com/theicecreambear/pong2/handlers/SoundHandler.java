package com.theicecreambear.pong2.handlers;

import java.awt.event.KeyEvent;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * Made For My Personal Sound Board
 * @author Joseph Terribile
 *
 */
public class SoundHandler {
	
	public boolean running = true;

	public static final int INTRO = 0;

	
	public static SoundHandler instance;
	
	private static String dirPrefix;
	
	private static AudioInputStream introIn;
	
	private static Clip introClip;
	
	public SoundHandler() {
		this("C:/Sounds/");
	}
	
	public SoundHandler(String dirPrefixIn) {
		dirPrefix = dirPrefixIn;
		instance = this;
	}
	
	public void playSound(int id) {
		try {
			switch (id) {
				case 0: {
					introIn = AudioSystem.getAudioInputStream(new File(dirPrefix + "intro.wav"));
					introClip = AudioSystem.getClip();
					introClip.open(introIn);
					introClip.start();
				}
			}
		} catch (Exception e) {
			System.err.println("An Error occoured while playing the sound.");
			e.printStackTrace();
		}
	}
	
	public void stopSound(int id) {
		switch (id) {
			case 1: {
				
			}
			
		}
	}
	
	public static SoundHandler getNewSoundHandler(String dirPrefix) {
		if (dirPrefix != null) {
			new SoundHandler(dirPrefix);
		} else {
			new SoundHandler();
		}
		return instance;
	}
	
	public void loopHandler() {
		while (running) {
			if (InputHandler.handler.isKeyDown(KeyEvent.VK_W)) {
				this.playSound(INTRO);
			}
		}
	}
}