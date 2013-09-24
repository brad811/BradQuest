package main;

import java.util.Random;

public class Game
{
	public static final int 
		build = 11,
		port = 2222,
		// tileSize = 32,
		FPS = 120,
		TPS = 60,
		mapSize = 500;
	
	public static final int
		NO_MODE = 0,
		CONSOLE_MODE = 1,
		SERVER_MODE = 2,
		SINGLE_PLAYER_MODE = 3,
		MULTIPLAYER_MODE = 4;
	
	public static int mode;
	
	ServerMode serverMode;
	public boolean console = false;
	public static int entityIdCounter = 1;
	
	public Map serverMap;
	public static Random rand;
	public static long seed = System.currentTimeMillis() * System.nanoTime();
	public boolean loaded = false;
	
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
