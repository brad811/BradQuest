package main;

public class Mode
{
	Game game;
	public static GameApplet gameApplet;
	public int mode = Game.NO_MODE;
	Long lastTime, curTime;
	boolean quit = false;
	
	public Mode(Game g)
	{
		game = g;
		lastTime = System.currentTimeMillis();
		curTime = System.currentTimeMillis();
	}
}
