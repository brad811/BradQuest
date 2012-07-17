package main;

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
	
	public static void render(int direction)
	{
		/*
		int imageOffset = 0;
		int offsetX = 0, offsetY = 0;
		
		if(direction == Player.RIGHT)
		{
			offsetX = 1;
			offsetY = 0;
			imageOffset = 12;
		}
		else if(direction == Player.DOWN)
		{
			offsetX = 0;
			offsetY = 1;
			imageOffset = 13;
		}
		else if(direction == Player.LEFT)
		{
			offsetX = -1;
			offsetY = 0;
			imageOffset = 14;
		}
		else if(direction == Player.UP)
		{
			offsetX = 0;
			offsetY = -1;
			imageOffset = 15;
		}
		*/
	}
}
