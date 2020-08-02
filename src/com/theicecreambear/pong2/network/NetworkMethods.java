package com.theicecreambear.pong2.network;

import javax.swing.JOptionPane;

public class NetworkMethods {
	public static void registerUser(String userName, String password) throws ExistingAccountException {
		if (userName.equals("TheIceCreamBear")) {
			JOptionPane.showMessageDialog(null, "User \"" + userName + "\" already exists, Sorry.");
			throw new ExistingAccountException("User \"" + userName + "\" already exists.");
		}
	}
}