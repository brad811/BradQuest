package main;

import java.util.Random;

public class Game
{
	public static final int build = 10;
	public static final int port = 2222;
	
	//public static final int tileSize = 32;
	public static final int FPS = 120;
	public static final int TPS = 60;
	public static final int mapSize = 500;
	
	public static final int NO_MODE = 0;
	public static final int CONSOLE_MODE = 1;
	public static final int SERVER_MODE = 2;
	public static final int SINGLE_PLAYER_MODE = 3;
	public static final int MULTIPLAYER_MODE = 4;
	
	public static int entityId = 1;
	
	public static int mode;
	
	ServerMode serverMode;
	public boolean console = false;
	
	public Map serverMap;
	
	public boolean loaded = false;
	
	public static Random rand;
	public static long seed = System.currentTimeMillis() * System.nanoTime();
	
	public Game()
	{
		rand = new Random(seed);
	}
	
	public void startConsoleMode()
	{
		console = true;
		initServerMode();
		startServerMode();
	}
	
	public void initServerMode()
	{
		serverMode = new ServerMode(this);
		serverMode.init();
	}
	
	public void startServerMode()
	{
		serverMode.start();
	}
	
	public void quit()
	{
		serverMap = null;
		loaded = false;
		
		// clean up server mode
		if (mode == SINGLE_PLAYER_MODE || mode == SERVER_MODE || mode == CONSOLE_MODE)
			serverMode.quit();
	}
}
