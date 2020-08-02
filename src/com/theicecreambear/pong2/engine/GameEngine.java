package com.theicecreambear.pong2.engine;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.theicecreambear.pong2.gameobject.Ball;
import com.theicecreambear.pong2.gameobject.GameObject;
import com.theicecreambear.pong2.interfaces.Drawable;
import com.theicecreambear.pong2.interfaces.Updateable;
import com.theicecreambear.pong2.player.AIPlayer;
import com.theicecreambear.pong2.player.LocalPlayer;
import com.theicecreambear.pong2.player.NetworkedPlayer;
import com.theicecreambear.pong2.player.Player;
import com.theicecreambear.pong2.refrence.Refrence;
import com.theicecreambear.pong2.screen.Screen;
import com.theicecreambear.pong2.starter.Starter;

/**
 * 
 * @author Joseph Terribile - Current Maintainer
 * @author David Santamaria - Original Author
 *
 */
public class GameEngine {
	// Starter stuff
	public boolean notStarted = true;
	public int id;
	// end starter
	
	public static boolean running = true;
	public static Random rand;
	public static GameEngine instance;
	public static String stats = "";
	public int ticks;
	public JFrame frame;
	public Graphics g;
	public Graphics g2;
	public BufferedImage i;
	public Player p1;
	public Player p2;
	public Ball ball;
	
	private JTextArea consoleReadout;
	private JTextField consoleIn;
	private boolean consoleShowing;
	
	/* The three types of Game Objects */
	// 8/29/16 TODO possibly make these maps, idk
	// TODO private or public?
	static ArrayList<GameObject> updateableAndDrawable = new ArrayList<GameObject>();
	static ArrayList<Updateable> updateable = new ArrayList<Updateable>();
	static ArrayList<Drawable> drawable = new ArrayList<Drawable>();
	
	/**
	 * @deprecated - This method may get to be removed in the final export of the game so that 
	 * {@link com.theicecreambear.pong2.Main#main(String[]) Main.main()} will be invoked when executing 
	 * the exported .jar file.
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		if (Refrence.DEBUG_MODE) {			
			System.out.println(Runtime.getRuntime().maxMemory());
			System.err.println("x: " + Screen.width + "y: " + Screen.height);
		}
//		new Starter();
		new GameEngine();
	}
	
	public static void startGameEngine() {
		new GameEngine();
	}
	
	public GameEngine() {
		rand = new Random();
		initialize();
		
		if (Refrence.STARTER) {
			// keeps the engine stopped until the player makes a decision.
			while (notStarted) {
				if (Refrence.DEBUG_MODE) {				
					System.err.println("WAITING");
				}
			}
		} else {
			this.id = Refrence.LOCAL_1V1_AI;
		}
		
		initializePlayers(id);
		run();
	}

	public void initialize() {
		
		// TODO Server only sends clients the positions of the things and their color and the score.
		
		frame = new JFrame("Pong Remastered");
		frame.setBounds(0, 0, Screen.width, Screen.height);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		consoleReadout = new JTextArea(); 
		consoleReadout.setEditable(false);
		consoleReadout.setBounds(0, 0, 600, 400);
		consoleReadout.setBorder(null);
		consoleReadout.setRows(10);
		
		consoleIn = new JTextField();
		consoleIn.setBounds(0, 401, 600, 30);
		consoleIn.setBorder(null);
		consoleIn.setEditable(true);
		
		
		// TODO
		ball = new Ball();
		updateableAndDrawable.add(ball);
		
		
		i = new BufferedImage(Screen.width, Screen.height, BufferedImage.TYPE_INT_RGB);
		g2 = i.createGraphics();
		g = frame.getGraphics();
		
		System.gc();

		if (Refrence.STARTER) {
			try {
				Starter s = new Starter();
				s.updateFrame();
			} catch (IOException e) {
				System.err.println("There was an error in initalizing the starter.");
				e.printStackTrace();
			}
		}
		
		instance = this;
	}

	public void initializePlayers(int id) {
		switch (id) {
			case Refrence.LOCAL_1V1: 
				p1 = new LocalPlayer(frame, new Color(130, 130, 130));
				p2 = new LocalPlayer((LocalPlayer)p1);
				updateableAndDrawable.add(p1);
				updateableAndDrawable.add(p2);
				break;
			case Refrence.LOCAL_1V1_AI: 
				p1 = new LocalPlayer(frame, new Color(130, 130, 130));
				p2 = new AIPlayer(new Color(130, 130, 130), 2);
				updateableAndDrawable.add(p1);
				updateableAndDrawable.add(p2);
				break;
			case Refrence.NETWORKED_1V1: 
				p1 = new LocalPlayer(frame, new Color(130, 130, 130));
				p2 = new NetworkedPlayer(new Color(130, 130, 130), 2);
				updateableAndDrawable.add(p1);
				updateableAndDrawable.add(p2);
				break;
//			default: System.exit(-1);
		}
	}
	
	public void update(double deltaTime) {
		for(GameObject gameObject: updateableAndDrawable) {
			gameObject.update(deltaTime); // TODO SORT THIS
		}
		
		for(Updateable upject : updateable) {
			upject.update(deltaTime); // TODO SEPERATE THREAD?
		}
	}
	
	public void updateNetwork(double deltaTime) {
		
	}

	public void render(Graphics g, ImageObserver observer) {
		g2.setColor(Color.BLACK);
		g2.fillRect(0, 0, Screen.width, Screen.height);
		
		g2.setFont(new Font("Arial", 1, 75));
		g2.setColor(Color.WHITE);
		g2.drawString(String.valueOf(ball.leftScore), Screen.width / 2 - 135, 100);
		g2.setColor(Color.WHITE);
		g2.drawString(String.valueOf(ball.rightScore), Screen.width / 2 + 100, 100);
		
		// Field Drawing
		g2.setColor(Color.WHITE);
		g2.fillRect(Screen.width / 2 - 5, 0, 10, Screen.height);
		
		if (Refrence.DEBUG_MODE) {
			g2.setColor(Color.blue);
			g2.fillRect(Screen.width - 21, 0, 1, Screen.height);

			g2.fillRect(20, 0, 1, Screen.height);
		}
		
		g2.setColor(Color.WHITE);
		g2.drawOval(Screen.width / 2 - 250, Screen.height / 2 - 250, 500, 500);

		g2.setColor(Color.WHITE);
		g2.fillRect(0, 27, Screen.width, 10);
		g2.setColor(Color.WHITE);
		g2.fillRect(0, Screen.height - 13, Screen.width, 10);
		// End Field Drawing
		
		for(GameObject gameObject : updateableAndDrawable) {
			gameObject.draw(g2, observer);
		}
		
		for(Drawable staject : drawable) {
			staject.draw(g, observer);
		}
		
		if (Refrence.DEBUG_MODE) {
			g2.setColor(Color.GREEN);
			g2.setFont(new Font("Arial", 1, 20));
			g2.drawString(stats, 25, 60);			
		}
		
		g.drawImage(i, 0, 0, frame);
	}

	public void run() {
		
		
		long time = System.nanoTime();
		final double tick = 60.0;
		double ms = 1000000000 / tick;
		double deltaTime = 0;
		ticks = 0;
		int fps = 0;
		long timer = System.currentTimeMillis();
		long frameLimit = 80;
		long currentTime;
		int seconds = 0;
		int minutes = 0;
		int hours = 0;

		while (running) {
			
			currentTime = System.nanoTime();
			deltaTime += (currentTime - time) / ms;
			time = currentTime;

			if (deltaTime >= 1) {
				ticks++;
				update(deltaTime);					
				deltaTime--;
			}
			
			render(g, frame);
			fps++;
			
			while(deltaTime < frameLimit) {
				currentTime = System.nanoTime();
				deltaTime += (currentTime - time) / ms;
				time = currentTime;
			}
			
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				seconds++;
				if(seconds > 60) {
					seconds %= 60;
					minutes++;
					
					if(minutes > 60) {
						minutes %= 60;
						hours++;
					}
				}
				
				// GT stands for GameTime. P.C stands for Player coordinates
				stats = "Ticks: " + ticks + " FPS: " + fps + " GT: " + ((hours < 10) ? "0" + hours : hours) + ":" + ((minutes < 10) ? "0" + minutes : minutes) + ":" + ((seconds < 10) ? "0" + seconds : seconds);
				if (Refrence.DEBUG_MODE) {					
					System.out.println(stats);
				}
				ticks = 0;
				fps = 0;
				if (Refrence.DEBUG_MODE) {					
					System.out.println(Runtime.getRuntime().freeMemory());
				}
				System.gc();
				if (Refrence.DEBUG_MODE) {					
					System.out.println(Runtime.getRuntime().freeMemory());
				}
			}
		}
	}

	public boolean isConsoleShowing() {
		return consoleShowing;
	}

	public void showConsole() {
		consoleShowing = true;
		frame.add(consoleReadout);
		frame.add(consoleIn);
		frame.update(g);
		// TODO Possibly stop pause engine if console is showing?
		
	}
	
	public void hideConsole() {
		consoleShowing = false;
		frame.remove(consoleReadout);
		frame.remove(consoleIn);
		frame.update(g);
	}
	
	public enum EngineType {
		CLIENT,
		SERVER,
		LOCAL_GAME;
	}
	
}