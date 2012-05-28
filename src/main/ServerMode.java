package main;

import java.io.IOException;

import server.Server;

public class ServerMode extends Mode implements Runnable
{
	Server server;
	
	public ServerMode(GameApplet g)
	{
		super(g.game);
		gameApplet = g;
		game = g.game;
		mode = Game.SERVER_MODE;
		Game.mode = mode;
	}
	
	public ServerMode(Game g)
	{
		super(g);
		game = g;
		mode = Game.SERVER_MODE;
		Game.mode = mode;
	}
	
	public void tick()
	{
		// do all the stuff that the entities should do
		game.serverMap.serverTick();
	}
	
	public void start()
	{
		lastTime = System.currentTimeMillis();
		curTime = System.currentTimeMillis();
		
		game.serverMap = new Map();
		
		if(!game.console)
			gameApplet.menu.serverScreen();
		
		new Thread(this).start();
	}
	
	public void run()
	{
		long lastTick = 0L;
		
		GeneratorThread gen = new GeneratorThread();
		gen.setMap(game.serverMap);
		gen.start();
		
		int percent = 0, lastPercent = 0;
		while(percent < 100 && !quit)
		{System.out.println("ServerMode - run1");
			percent = (int)game.serverMap.percentGenerated;
			if(percent >= lastPercent)
			{
				say("Generating map... " +  Integer.toString(percent) + "%");
				repaint();
				lastPercent += 10;
			}
		}
		
		say("Map generated.");
		repaint();
		game.loaded = true;
		
		server = new Server();
		
		try
		{
			if(game.console)
				server.startServer(game);
			else
				server.startServer(gameApplet);
		} catch (IOException e)
		{
			say("Unable to start server! IOException!");
		}
		
		while(!quit)
		{System.out.println("ServerMode - run2");
			Long startTime = System.currentTimeMillis();
			
			if(startTime - lastTick >= 1000/Game.TPS)
			{
				tick();
				lastTick = startTime;
			}
			
			try
			{
				Thread.sleep(1);
			} catch (InterruptedException e)
			{
				// Oops
			}
			
			repaint();
		}
	}
	
	public void repaint()
	{
		if(!game.console)
			gameApplet.repaint();
	}
	
	public void say(String msg)
	{
		if(!game.console)
			gameApplet.menu.serverPrint(msg);
		else
			System.out.println(msg);
	}
	
	public void quit()
	{
		say("Quitting...");
		quit = true;
		server.quit();
	}
}
