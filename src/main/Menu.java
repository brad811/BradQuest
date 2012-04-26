package main;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu
{
	GameApplet gameApplet;
	TextArea serverOut;
	
	TextField nameField;
	TextField serverField;
	
	MultiplayerMode multiplayerMode;
	
	public Menu(GameApplet g)
	{
		this.gameApplet = g;
	}
	
	public class SinglePlayerButton implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			
		}
	}
	
	public class MultiplayerButton implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			clear();
			gameApplet.game.startMultiplayerMode();
		}
	}
	
	public class ServerModeButton implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			clear();
			gameApplet.game.startServerMode();
		}
	}
	
	public class JoinServerButton implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			clear();
			MultiplayerMode.name = nameField.getText();
			MultiplayerMode.server = serverField.getText();
			
			multiplayerMode.keepStarting();
		}
	}
	
	public void mainMenu()
	{
		//clear();
		
		gameApplet.setLayout(new GridBagLayout());
		gameApplet.setPreferredSize(gameApplet.dim);
		GridBagConstraints c = new GridBagConstraints();
		
		Button singlePlayerButton = new Button("Single Player");
		singlePlayerButton.addActionListener(new SinglePlayerButton());
		singlePlayerButton.setPreferredSize(new Dimension(400,32));
		singlePlayerButton.setEnabled(false);
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(10,0,0,0);
		gameApplet.add(singlePlayerButton, c);
		
		Button multiplayerButton = new Button("Multiplayer");
		multiplayerButton.addActionListener(new MultiplayerButton());
		multiplayerButton.setPreferredSize(new Dimension(400,32));
		c.gridx = 0;
		c.gridy++;
		c.insets = new Insets(10,0,0,0);
		gameApplet.add(multiplayerButton, c);
		
		Button serverModebutton = new Button("Server Mode");
		serverModebutton.addActionListener(new ServerModeButton());
		serverModebutton.setPreferredSize(new Dimension(400,32));
		c.gridx = 0;
		c.gridy++;
		c.insets = new Insets(40,0,0,0);
		gameApplet.add(serverModebutton, c);
		
		gameApplet.validate();
	}
	
	public void multiplayerScreen(MultiplayerMode multiplayerMode)
	{
		this.multiplayerMode = multiplayerMode;
		
		clear();
		
		gameApplet.setLayout(new GridBagLayout());
		gameApplet.setPreferredSize(gameApplet.dim);
		GridBagConstraints c = new GridBagConstraints();
		
		Label nameLabel = new Label("Name");
		nameLabel.setPreferredSize(new Dimension(400,12));
		nameLabel.setAlignment(Label.LEFT);
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(10,0,0,0);
		gameApplet.add(nameLabel, c);
		
		nameField = new TextField();
		nameField.setPreferredSize(new Dimension(400,32));
		c.gridx = 0;
		c.gridy++;
		c.insets = new Insets(10,0,0,0);
		gameApplet.add(nameField, c);
		
		Label serverLabel = new Label("Server");
		serverLabel.setPreferredSize(new Dimension(400,12));
		serverLabel.setAlignment(Label.LEFT);
		c.gridx = 0;
		c.gridy++;
		c.insets = new Insets(30,0,0,0);
		gameApplet.add(serverLabel, c);
		
		serverField = new TextField();
		serverField.setPreferredSize(new Dimension(400,32));
		c.gridx = 0;
		c.gridy++;
		c.insets = new Insets(10,0,0,0);
		gameApplet.add(serverField, c);
		
		Button joinServerButton = new Button("Join Server");
		joinServerButton.addActionListener(new JoinServerButton());
		joinServerButton.setPreferredSize(new Dimension(400,32));
		c.gridx = 0;
		c.gridy++;
		c.insets = new Insets(40,0,0,0);
		gameApplet.add(joinServerButton, c);
		
		gameApplet.validate();
	}
	
	public void serverScreen()
	{
		clear();
		
		gameApplet.setLayout(new FlowLayout());
		
		serverOut = new TextArea();
		serverOut.setBackground(Color.BLACK);
		serverOut.setForeground(Color.WHITE);
		serverOut.setPreferredSize(gameApplet.dim);
		serverOut.setEditable(false);
		
		gameApplet.add(serverOut);
		gameApplet.validate();
	}
	
	public void serverPrint(String msg)
	{
		if(!gameApplet.game.console)
			serverOut.append(msg + "\n");
		
		System.out.println(msg);
	}
	
	public void clear()
	{
		gameApplet.removeAll();
		gameApplet.validate();
	}
}
