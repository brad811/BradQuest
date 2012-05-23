package main;

public class Game
{
	public static final int build = 9;
	public static final int port = 2222;
	
	public static final int tileSize = 32;
	public static final int FPS = 100060;
	public static final int TPS = 60;
	public static final int mapSize = 1000;
	
	public static final int NO_MODE = 0;
	public static final int CLIENT_MODE = 1;
	public static final int SERVER_MODE = 2;
	
	public static int entityId = 1;
	
	public static int mode;
	
	MultiplayerMode multiplayerMode;
	ServerMode serverMode;
	public boolean console = false;
	
	public Map clientMap;
	public Map serverMap;
	
	public boolean loaded = false;
	
	public void startMultiplayerMode()
	{
		multiplayerMode = new MultiplayerMode(this);
		multiplayerMode.start();
	}
	
	public void startServerMode()
	{
		serverMode = new ServerMode(this);
		serverMode.start();
	}
	
	public void startConsoleMode()
	{
		console = true;
		serverMode = new ServerMode(this);
		serverMode.start();
	}
	
	public Player getPlayer()
	{
		return multiplayerMode.player;
	}
	
	public void quit()
	{
		clientMap = null;
		serverMap = null;
		loaded = false;
		
		// clean up server mode and client mode
		if (mode == CLIENT_MODE)
			multiplayerMode.quit();
		else if (mode == SERVER_MODE)
			serverMode.quit();
	}
}
