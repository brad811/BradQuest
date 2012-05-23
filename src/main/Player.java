package main;

import items.Item;

import java.io.IOException;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import render.Renderer;
import render.Shape;
import tiles.Tile;

public class Player
{
	public String name = "nobody";
	
	private int x, y, speed = 2;
	Texture playerTexture;
	Input input;
	Map map;
	int counter; // This helps animate the player
	
	float size = 1.0f;
	float half = size / 2;
	
	public static int UP = 1;
	public static int RIGHT = 2;
	public static int DOWN = 3;
	public static int LEFT = 4;
	public int direction = DOWN;
	boolean moving = false;
	boolean striking = false;
	boolean canStrike = true;
	
	public PlayerInventory playerInventory;
	
	ArrayList<Shape> model;
	
	public Player(Input input, Map map, String name)
	{
		// when map and other data are stored on the server, this needs to be there
		x = Game.mapSize / 2 * Game.tileSize;
		y = Game.mapSize / 2 * Game.tileSize;
		
		this.input = input;
		this.map = map;
		this.name = name;
		
		counter = 0;
		
		playerInventory = new PlayerInventory();
	}
	
	public void init()
	{
		try
		{
			if(playerTexture == null)
				playerTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("/player.png"));
		} catch (IOException e)
		{
			System.out.println("Unable to load player texture!");
		}
		
		model = Renderer.buildPlayer();
	}
	
	public Player(Map map, int x, int y)
	{
		this.map = map;
		
		counter = 0;
		
		playerInventory = new PlayerInventory();
	}
	
	public void clientTick()
	{
		int mySpeed = speed;
		
		if (input.keys.get("shift") || map.tiles[x / Game.tileSize][y / Game.tileSize].type == Tile.TYPE_LIQUID)
			mySpeed /= 2;
		
		int oldX = x, oldY = y;
		
		if (!(input.keys.get("left") && input.keys.get("right")))
		{
			if (input.keys.get("left"))
			{
				move(-mySpeed, 0);
				direction = LEFT;
			}
			if (input.keys.get("right"))
			{
				move(mySpeed, 0);
				direction = RIGHT;
			}
		}
		
		if (!(input.keys.get("up") && input.keys.get("down")))
		{
			if (input.keys.get("up"))
			{
				move(0, mySpeed);
				direction = UP;
			}
			if (input.keys.get("down"))
			{
				move(0, -mySpeed);
				direction = DOWN;
			}
		}
		
		if (input.keys.get("x") && canStrike && Strike.ready)
		{
			canStrike = false;
			strike();
		} else if (!input.keys.get("x"))
		{
			canStrike = true;
		}
		
		if (oldX != x || oldY != y)
			moving = true;
		else
			moving = false;
		
		counter = (counter + 1) % Game.FPS;
		
		if (striking)
		{
			Strike.strike();
			striking = false;
		}
		
		Strike.tick();
	}
	
	public void serverTick()
	{
		int oldX = x, oldY = y;
		
		if(oldX != x || oldY != y)
			moving = true;
		else
			moving = false;
		
		counter = (counter + 1) % Game.FPS;
	}
	
	public void tick()
	{
		if(Game.mode == Game.CLIENT_MODE)
			clientTick();
		else if(Game.mode == Game.SERVER_MODE)
			serverTick();
	}
	
	public void addItem(Item item)
	{
		addItem(item, 1);
	}
	
	public void addItem(Item item, int quantity)
	{
		playerInventory.addItem(item, quantity);
	}
	
	// this may be moved to the server as well
	public void strike()
	{
		striking = true;
		
		Tile tile = map.tiles[x / Game.tileSize][y / Game.tileSize];
		
		if (direction == UP)
			tile = map.tiles[x / Game.tileSize][y / Game.tileSize + 1];
		else if (direction == RIGHT)
			tile = map.tiles[x / Game.tileSize + 1][y / Game.tileSize];
		else if (direction == DOWN)
			tile = map.tiles[x / Game.tileSize][y / Game.tileSize - 1];
		else if (direction == LEFT)
			tile = map.tiles[x / Game.tileSize - 1][y / Game.tileSize];
		
		tile.strike(playerInventory.equippedItem);
	}
	
	public void move(int mx, int my)
	{
		if (map.isOnMap(x + Game.tileSize / 2 + mx, y + Game.tileSize / 2 + my)
				&& map.isOnMap(x - Game.tileSize - Game.tileSize / 2 + mx, y - Game.tileSize - Game.tileSize / 2 + my)
				&& map.tiles[(x + mx) / Game.tileSize][(y + my) / Game.tileSize].type != Tile.TYPE_SOLID)
		{
			setX(x + mx);
			setY(y + my);
		}
	}
	
	int prevOffset = 0;
	int strikeTimer = 0;
	
	public int getOffsetX()
	{
		int offsetX = 0;
		if (counter % 30 == 0)
		{
			prevOffset = (prevOffset + 1) % 2;
		}
		if (direction == RIGHT)
		{
			offsetX = 0;
		}
		if (direction == LEFT)
		{
			offsetX = 3;
		}
		if (direction == UP)
		{
			offsetX = 6;
		}
		if (direction == DOWN)
		{
			offsetX = 9;
		}
		if (moving)
			offsetX += prevOffset + 1;
		
		return offsetX;
	}
	
	public int getOffsetY()
	{
		int offsetY = 0;
		try
		{
			if (map.tiles[x / Game.tileSize][y / Game.tileSize].type == Tile.TYPE_LIQUID)
				offsetY = 1;
		} catch (NullPointerException e)
		{
			
		}
		
		return offsetY;
	}
	
	public void render()
	{
		//int offsetX = getOffsetX();
		//int offsetY = getOffsetY();
		
		//Strike.render(playerTexture, screenX, screenY, direction);
		
		//glLoadIdentity(); // Reset The View
		//glTranslatef(x/Game.tileSize - 0.5f, y/Game.tileSize - 0.5f, -size * 0.6f); // Move down into position
		
		// body
		//glScalef(0.5f, 0.6f, 0.5f);
		//glTranslatef(0.0f, 0.0f, 1.0f);
		//model.get(1).render();
		
		// head
		//glScalef(0.7f, 0.7f, 0.8f);
		//glTranslatef(0.0f, 0.0f, 1.0f);
		//model.get(0).render();
	}
	
	public void renderOther(int playerX, int playerY)
	{
		/*
		int offsetX = getOffsetX();
		int offsetY = getOffsetY();
		
		int fontSize = 18;
		int fontWidth = 11;
		
		g.setColor(new Color(0f,0f,0f,0.5f));
		g.fillRect(x - name.length()*fontWidth/2 + fontWidth, y - 40, name.length()*fontWidth + fontWidth - 2, 20);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Courier", Font.BOLD,  fontSize));
		g.drawString(name, x - name.length()*fontWidth/2 + fontWidth/2 + fontWidth, y - 25);
		*/
		
		System.out.println("Other: ("+playerX+","+playerY+")");
		
		glLoadIdentity(); // Reset The View
		glTranslatef(playerX/Game.tileSize - 0.5f, playerY/Game.tileSize - 0.5f, -size * 0.6f); // Move down into position
		
		//model.get(0).render();
		
		glTranslatef(0.0f, 0.0f, 1.0f);
		//model.get(1).render();
	}
	
	public int getX()
	{
		return x;
	}
	
	public void setX(int x)
	{
		this.x = x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public void setY(int y)
	{
		this.y = y;
	}
}
