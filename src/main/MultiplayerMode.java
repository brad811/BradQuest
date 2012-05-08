package main;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

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
	
	boolean connected = false;
	boolean failed = false;
	
	public MultiplayerMode(Game g)
	{
		super(g);
		game = g;
		mode = Game.CLIENT_MODE;
		Game.mode = mode;
	}
	
	public void start()
	{
		// Move this to a new menu screen with text input for name, and server address input
		gameApplet.menu.multiplayerScreen(this);
	}
	
	public void keepStarting()
	{
		game.map = new Map();
		
		input = new Input();
		
		player = new Player(input, game.map, name);
		
		System.out.println("Trying to connect...");
		client = new Client(game);
		
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
		gameApplet.run();
	}
	
	public static void init()
	{
		try
		{
			if(tilesTexture == null)
				tilesTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("/tiles.png"));
			
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, tilesTexture.getTextureID());
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		} catch (IOException e)
		{
			System.out.println("Unable to load tiles texture!");
		}
		
		try
		{
			if(itemsTexture == null)
				itemsTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("/items.png"));
			
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, itemsTexture.getTextureID());
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		} catch (IOException e)
		{
			System.out.println("Unable to load items texture!");
		}
		
		Tile.init();
	}
	
	public void run()
	{
		long lastTick = 0L;
		
		while ((!Client.loaded || game.map.percentLoaded < 1.0) && !quit)
		{
			repaint();
		}
		
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
			repaint();
		}
	}
	
	public void tick()
	{
		// do all the stuff that the entities should do
		
		// map will now tick on the server
		
		getPlayer().tick();
		client.tick();
	}
	
	public Player getPlayer()
	{
		return game.getPlayer();
	}
	
	public void repaint()
	{
		gameApplet.repaint();
	}
	
	public void paint(Graphics bufferGraphics, int width, int height)
	{
		if (!connected)
		{
			// do something here
			return;
		}
		
		bufferGraphics.clearRect(0, 0, width, height);
		
		int w = 400, h = 20;
		
		bufferGraphics.setColor(Color.black);
		bufferGraphics.drawString("Downloading map from server...", width / 2 - w / 2, height / 2 - h);
		bufferGraphics.fillRect(width / 2 - w / 2, height / 2 - h / 2, w, h);
		
		bufferGraphics.setColor(Color.white);
		bufferGraphics.fillRect(width / 2 - w / 2 + 2, height / 2 - h / 2 + 2, w - 4, h - 4);
		
		bufferGraphics.setColor(new Color(80, 100, 255));
		
		try
		{
			bufferGraphics.fillRect(width / 2 - w / 2 + 2, height / 2 - h / 2 + 2,
					(int) ((double) (w - 4) * (double) (game.map.percentLoaded)), h - 4);
		} catch (NullPointerException e)
		{
			// Either buffer graphics or map not ready, most likely
		}
	}
	
	public void render(int width, int height)
	{
		game.map.render(width, height, getPlayer().getX(), getPlayer().getY());
		
		game.map.renderItemEntities(game.getPlayer().getX(), game.getPlayer().getY());
		
		try
		{
			Player p;
			Iterator<Player> i = game.map.players.iterator();
			while (i.hasNext() && !quit)
			{
				p = (Player) i.next();
				p.renderOther(getPlayer().getX(), getPlayer().getY());
			}
		} catch (ConcurrentModificationException e)
		{
			
		}
		
		getPlayer().render();
		
		lastTime = curTime;
		curTime = System.currentTimeMillis();
		//Long fps = 1000 / (curTime - lastTime);
		
		/*
		bufferGraphics.setColor(Color.white);
		bufferGraphics.fillRect(8, 8, 134, 42);
		
		bufferGraphics.setFont(new Font("", Font.PLAIN, 12));
		bufferGraphics.setColor(Color.black);
		bufferGraphics.drawString("FPS: " + Long.toString(fps), 10, 20);
		
		bufferGraphics.drawString("Pos: ("
				+ ((double) ((int) (((double) getPlayer().x / (double) Game.tileSize) * 10)) / 10) + ","
				+ ((double) ((int) (((double) getPlayer().y / (double) Game.tileSize) * 10)) / 10) + ")", 10, 32);
		*/
		
		// bufferGraphics.drawString("Seed: " + map.seed, 10, 46);
		
		//getPlayer().playerInventory.render(width, height);
	}
	
	public void quit()
	{
		quit = true;
		connected = false;
		
		if(client != null)
			client.quit();
	}
}
