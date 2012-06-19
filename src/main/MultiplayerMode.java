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
	static String name = "nobody";
	public static String server = "localhost";
	public Player player;
	Input input;
	public static Texture tilesTexture;
	public static Texture itemsTexture;
	
	public Map clientMap;
	
	boolean connected = false;
	boolean failed = false;
	
	public MultiplayerMode(GameApplet g)
	{
		super(g.game);
		gameApplet = g;
		mode = Game.CLIENT_MODE;
		Game.mode = mode;
		System.out.println("multiplayerMode - created");
	}
	
	public void start()
	{
		// Move this to a new menu screen with text input for name, and server address input
		gameApplet.gui.setScreen(Gui.GUI_SINGLE_PLAYER_MENU);
	}
	
	// This needs to change!!!! gameApplet.run() will break things
	public void keepStarting()
	{
		clientMap = new Map();
		
		input = new Input();
		
		player = new Player(input, clientMap, name);
		player.init();
		
		System.out.println("Trying to connect...");
		client = new Client(this);
		
		if (!client.connect())
		{
			failed = true;
			System.out.println("Unable to connect!");
			return;
		}
		connected = true;
		System.out.println("Connected.");
		
		System.out.println("Starting client...");
		client.start();
		
		System.out.println("Starting multiplayer mode...");
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
			System.out.println("Unable to load tiles texture!");
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
			System.out.println("Unable to load items texture!");
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
		
		System.out.println("Entering main game loop...");
		while (!quit)
		{
			input.getKeys();
			Long startTime = System.currentTimeMillis();
			
			if (startTime - lastTick >= 1000 / Game.TPS)
			{
				tick();
				lastTick = startTime;
			} else
			{
				System.out.println(startTime + " - " + lastTick + " (" + (startTime - lastTick) + ") < " + 1000
						/ Game.TPS);
			}
			
			Long stopTime = System.currentTimeMillis();
			
			if (stopTime - startTime < 1000 / Game.TPS)
			{
				try
				{
					// Thread.sleep(16,666666);
					Thread.sleep(1000 / Game.TPS - (stopTime - startTime));
				} catch (InterruptedException e)
				{
					System.out.println("Thread sleep interrupted!");
				}
			}
		}
		System.out.println("multiplayerMode - quit");
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
		clientMap.render(width, height, player.getX(), player.getY());
		
		try
		{
			Player p;
			Iterator<Player> i = clientMap.players.iterator();
			while (i.hasNext() && !quit)
			{
				p = (Player) i.next();
				p.renderOther(player.getX(), player.getY());
			}
		} catch (ConcurrentModificationException e)
		{
			
		}
		
		player.render();
		
		// Move this to last when it's not immediate anymore
		clientMap.renderItemEntities(player.getX(), player.getY());
		
		// bufferGraphics.drawString("Seed: " + map.seed, 10, 46);
		
		//getPlayer().playerInventory.render(width, height);
	}
	
	public void quit()
	{
		quit = true;
		connected = false;
		
		if(client != null)
		{
			client.quit();
			System.out.println("client - quit");
		}
	}
}
