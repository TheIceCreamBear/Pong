package com.theicecreambear.pong2.starter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.theicecreambear.pong2.engine.GameEngine;
import com.theicecreambear.pong2.network.ExistingAccountException;
import com.theicecreambear.pong2.network.NetworkMethods;
import com.theicecreambear.pong2.refrence.Refrence;

public class Starter {
	private JFrame frame;
		
	private JButton local1v1;
	private JButton localAi1v1;
//	private JButton online1v1;
	private JButton registerBtn;
	
	private JTextField userNameIn;
	private JTextField passwordIn;
	
	public Starter() throws IOException {
		
		frame = new JFrame("Pong Remastered");
		frame.setBounds(0, 0, 800, 800);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Icon icon = new ImageIcon(ImageIO.read(new File(Refrence.GUI_IMAGES + "startbutton.png")));
		local1v1 = new JButton(icon);
		local1v1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				GameEngine.instance.id = Refrence.LOCAL_1V1;
				GameEngine.instance.notStarted = false;
				// TODO
			}
		});
		local1v1.setToolTipText("Start and offline game with two human players.");
		local1v1.setBounds(400 - 45, 10, 90, 30);
		frame.add(local1v1);
		
		localAi1v1 = new JButton(icon);
		localAi1v1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				GameEngine.instance.id = Refrence.LOCAL_1V1_AI;
				GameEngine.instance.notStarted = false;
				// TODO
			}
		});
		localAi1v1.setToolTipText("Start an offline game with an ai player.");
		localAi1v1.setBounds(400 - 45, 40, 90, 30);
		frame.add(localAi1v1);
		
		frame.update(frame.getGraphics());

		JTextField tf = new JTextField("Username: ");
		tf.setBounds(10, 10, 90, 30);
		tf.setEditable(false);
		tf.setBorder(null);
		frame.add(tf);
		
		userNameIn = new JTextField(15);
		userNameIn.setBounds(100, 10, 120, 30);
		userNameIn.setToolTipText("The username of the player for server side login. Maximum 15 characters");
		frame.add(userNameIn);
		
		frame.update(frame.getGraphics());
		
		JTextField tf1 = new JTextField("Password: ");
		tf1.setBounds(10, 40, 90, 30);
		tf1.setEditable(false);
		tf1.setBorder(null);
		frame.add(tf1);

		passwordIn = new JTextField();
		passwordIn.setBounds(100, 40, 120, 30);
		passwordIn.setToolTipText("The password of the player for server side login.");
		frame.add(passwordIn);
		
		frame.update(frame.getGraphics());
		
		registerBtn = new JButton("Register");
		registerBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String userName = userNameIn.getText();
				String password = passwordIn.getText();
				try {
					NetworkMethods.registerUser(userName, password);
				} catch (ExistingAccountException e1) {
					e1.printStackTrace();
				}
			}
		});
		registerBtn.setToolTipText("Registers the given username and password as a new account. Will error if a similar username exists.");
		registerBtn.setBounds(10, 80, 90, 30);
		frame.add(registerBtn);
		
		
		// Tricks the buttons into working properly
		frame.add(new JLabel(""));
		
		frame.update(frame.getGraphics());
		
	}
	
	public void updateFrame() {
		frame.update(frame.getGraphics());
	}
}