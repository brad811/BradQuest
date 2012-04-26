package main;

import java.awt.Graphics;
import java.awt.Image;

public class Strike
{
	public static boolean striking = false;
	static int time = Game.TPS/8;
	static int coolDown = time;
	static int warmUp = time;
	public static boolean ready = true;
	
	public static void strike()
	{
		if(striking == false && warmUp == time)
		{
			ready = false;
			coolDown = 0;
			warmUp = 0;
		}
	}
	
	public static void tick()
	{
		if(coolDown < time)
		{
			coolDown++;
		}
		else if(warmUp < time)
		{
			warmUp++;
			return;
		}
		else if(striking == true)
		{
			striking = false;
			return;
		}
		else if(striking == false)
		{
			ready = true;
			return;
		}
	}
	
	public static void render(Graphics g, Image img, int screenX, int screenY, int direction)
	{
		int imageOffset = 0;
		int offsetX = 0, offsetY = 0;
		
		if(direction == Player.RIGHT)
		{
			offsetX = Game.tileSize;
			offsetY = 0;
			imageOffset = 12;
		}
		else if(direction == Player.DOWN)
		{
			offsetX = 0;
			offsetY = Game.tileSize;
			imageOffset = 13;
		}
		else if(direction == Player.LEFT)
		{
			offsetX = -Game.tileSize;
			offsetY = 0;
			imageOffset = 14;
		}
		else if(direction == Player.UP)
		{
			offsetX = 0;
			offsetY = -Game.tileSize;
			imageOffset = 15;
		}
		
		g.drawImage(img,
				screenX/2 - Game.tileSize/2 + offsetX, screenY/2 - Game.tileSize/2 + 5 + offsetY,
				screenX/2+Game.tileSize - Game.tileSize/2 + offsetX, screenY/2+Game.tileSize - Game.tileSize/2 + 5 + offsetY,
				imageOffset*Game.tileSize, 0*Game.tileSize,
				imageOffset*Game.tileSize + Game.tileSize, 0*Game.tileSize + Game.tileSize,
			null);
	}
}
