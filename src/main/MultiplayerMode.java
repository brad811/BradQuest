package main;

import gui.Gui;

import java.io.IOException;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import static org.lwjgl.opengl.GL11.*;

import tiles.Tile;

import client.Client;

public class MultiplayerMode extends Mode implements Runnable
{
	Client client;
	public static String server = "localhost";
	public Player player;
	Input input;
	public static Texture tilesTexture;
	public static Texture itemsTexture;
	
	public Map clientMap;
	
	boolean runServer = false;
	
	boolean connected = false;
	boolean failed = false;
	
	public MultiplayerMode(GameApplet g)
	{
		super(g.game);
		gameApplet = g;
		mode = Game.CLIENT_MODE;
		Game.mode = mode;
		say("created");
	}
	
	public void start()
	{
		if(game.console)
			gameApplet.gui.setScreen(Gui.GUI_SINGLE_PLAYER_MENU);
		else
			gameApplet.gui.setScreen(Gui.GUI_MULTIPLAYER_MENU);
	}
	
	public void startSinglePlayer()
	{
		gameApplet.game.initServerMode();
		
		Thread startServerThread = new Thread() {
			public void run()
			{
				say("startSinglePlayer");
				gameApplet.game.startServerMode();
			}
		};
		
		startServerThread.start();
		
		gameApplet.gui.setScreen(Gui.GUI_LOADING_MAP);
		while ((gameApplet.game.serverMap.percentGenerated < 100.0) && !quit)
		{
			System.out.print(""); // for some reason putting this here makes the loading bar work...
			gameApplet.gui.guiLoadingMap.setPercent((float)gameApplet.game.serverMap.percentLoaded);
		}
		say("done - startStinglePlayer");
		keepStarting(true);
	}
	
	public void keepStarting(boolean runServer)
	{
		clientMap = new Map();
		
		input = new Input();
		
		player = new Player(input, clientMap, GameApplet.username);
		player.init();
		
		gameApplet.gui.guiInGame.setName();
		
		say("Trying to connect to '"+server+"'...");
		client = new Client(this);
		
		if (!client.connect())
		{
			failed = true;
			say("Unable to connect!");
			return;
		}
		connected = true;
		say("Connected.");
		
		say("Starting client...");
		client.start();
		
		Game.mode = mode; // starting the server sets the game to server mode, so here I'm changing it back
		
		say("Starting multiplayer mode...");
		new Thread(this).start();
	}
	
	public static void init()
	{
		try
		{
			if(tilesTexture == null)
				tilesTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("/tiles.png"));
			
			glBindTexture(GL_TEXTURE_2D, tilesTexture.getTextureID());
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		} catch (IOException e)
		{
			say("Unable to load tiles texture!");
		}
		
		try
		{
			if(itemsTexture == null)
				itemsTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("/items.png"));
			
			glBindTexture(GL_TEXTURE_2D, itemsTexture.getTextureID());
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		} catch (IOException e)
		{
			say("Unable to load items texture!");
		}
		
		Tile.init();
	}
	
	public void run()
	{
		long lastTick = 0L;
		
		gameApplet.gui.setScreen(Gui.GUI_LOADING_MAP);
		while ((!Client.loaded || clientMap.percentLoaded < 1.0) && !quit)
		{
			System.out.print(""); // for some reason putting this here makes the loading bar work...
			gameApplet.gui.guiLoadingMap.setPercent((float)clientMap.percentLoaded);
		}
		
		gameApplet.gui.setScreen(Gui.GUI_IN_GAME);
		
		Long startTime, stopTime;
		long tickInterval = 1000/Game.TPS;
		
		say("Entering main game loop...");
		while (!quit)
		{
			input.getKeys();
			startTime = System.currentTimeMillis();
			
			if (startTime - lastTick >= tickInterval)
			{
				tick();
				lastTick = startTime;
			} else
			{
				say(startTime + " - " + lastTick + " (" + (startTime - lastTick) + ") < " + tickInterval);
			}
			
			stopTime = System.currentTimeMillis();
			
			if (stopTime - startTime < tickInterval)
			{
				try
				{
					Thread.sleep(tickInterval - (stopTime - startTime));
				} catch (InterruptedException e)
				{
					say("Thread sleep interrupted!");
				}
			}
		}
		say("multiplayerMode - quit");
	}
	
	public void tick()
	{
		// do all the stuff that the entities should do
		
		// map will now tick on the server
		
		player.tick();
		client.tick();
	}
	
	public void render(int width, int height)
	{
		try
		{
			Player p;
			Iterator<Player> i = clientMap.players.iterator();
			while (i.hasNext() && !quit)
			{
				p = i.next();
				p.render();
			}
		} catch (ConcurrentModificationException e)
		{
			
		}
		
		player.render();
		
		clientMap.render(width, height, player.getX(), player.getY());
		
		// Move this to last when it's not immediate anymore - ???
		clientMap.renderItemEntities(player.getX(), player.getY());
		
		//getPlayer().playerInventory.render(width, height);
	}
	
	private static void say(String msg)
	{
		System.out.println("MultiplayerMode: " + msg);
	}
	
	public void quit()
	{
		quit = true;
		connected = false;
		
		if(client != null)
		{
			client.quit();
			say("client - quit");
		}
	}
}
