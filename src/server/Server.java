package server;

import items.ItemEntity;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import tiles.SolidTile;

import main.Game;
import main.GameApplet;
import main.Player;
import main.PlayerInventory;

public class Server
{
	// keep inventory
	// don't spawn on lava, trees, etc
	// try adding an enemy!
	List<ServerThread> clients = new ArrayList<ServerThread>();
	ConcurrentHashMap<String,Player> players = new ConcurrentHashMap<String,Player>();
	List<String> onlinePlayers = Collections.synchronizedList(new ArrayList<String>());
	
	ServerSocket socket;
	Game game;
	GameApplet gameApplet;
	boolean quit = false;
	
	public void connectServer() throws IOException
	{
		say("Opening socket...");
		socket = new ServerSocket(Game.port, 5);
		say("Server running. (Build "+Game.build+")");
	}
	
	public void startServer(GameApplet g) throws IOException
	{
		gameApplet = g;
		startServer(g.game);
	}
	
	public void startServer(Game g) throws IOException
	{
		game = g;
		
		connectServer();
		
		Socket incoming;
		
		while (!quit)
		{
			incoming = socket.accept();
			ServerThread connection = new ServerThread(this, incoming);
			Thread t = new Thread(connection);
			
			t.start();
		}
	}
	
	public void say(String msg)
	{
		if(!game.console)
			gameApplet.menu.serverPrint(msg);
		else
			System.out.println(msg);
	}
	
	public void addPlayer(String name)
	{
		if(!onlinePlayers.contains(name))
		{
			if(!players.containsKey(name))
			{
				players.put(name, new Player(game.map, Game.mapSize/2, Game.mapSize/2));
				players.get(name).name = name;
				players.get(name).setX( (Game.mapSize*Game.tileSize)/2 );
				players.get(name).setY( (Game.mapSize*Game.tileSize)/2 );
				players.get(name).direction = Player.DOWN;
			}
			onlinePlayers.add(name);
		}
	}
	
	public void handle(String msg)
	{
		String message[] = msg.split("\\|");
		
		if(message[0].equals("player"))
		{
			String name = message[1];
			int x = Integer.parseInt(message[2]);
			int y = Integer.parseInt(message[3]);
			int direction = Integer.parseInt(message[4]);
			
			addPlayer(name);
			
			Player player = players.get(name);
			player.setX(x);
			player.setY(y);
			player.direction = direction;
			
			ItemEntity item = game.map.pickUpItemEntities(player);
			
			try {
				if(!item.equals(null))
				{
					game.map.serverRemoveItemEntity(item.entityId);
					players.get(name).addItem(item.item);
					broadcast("item get|"+name+"|"+item.entityId+"|"+item.name);
				}
			} catch(NullPointerException e)
			{
				
			}
				
			broadcast(msg);
		}
		else if(message[0].equals("tile"))
		{
			int x = Integer.parseInt(message[1]);
			int y = Integer.parseInt(message[2]);
			int id = Integer.parseInt(message[3]);
			game.map.setTile(x, y, id);
			
			broadcast(msg);
		}
		else if(message[0].equals("destroy tile"))
		{
			int x = Integer.parseInt(message[1]);
			int y = Integer.parseInt(message[2]);
			
			SolidTile tile = (SolidTile) game.map.tiles[x][y];
			tile.destroy();
			
			broadcast("tile|"+tile.x+"|"+tile.y+"|"+tile.id);
			
			Iterator<Entry<Integer, ItemEntity>> it = game.map.itemEntities.entrySet().iterator();
			
			while(it.hasNext() && !quit)
			{
				Entry<Integer, ItemEntity> pairs = it.next();
				ItemEntity entity = pairs.getValue();
				
				broadcast("item|"+entity.name+"|"+entity.entityId+"|"+entity.x+"|"+entity.y);
			}
		}
	}
	
	public void broadcast(String msg)
	{
		for (int i = 0; i < clients.size(); i++)
		{
			send(clients.get(i), msg);
		}
	}
	
	public void send(ServerThread out, String msg)
	{
		out.say(msg);
	}
	
	public void connectClient(ServerThread thread)
	{
		// Add the new client to the message distribution list
		clients.add(thread);
		
		// Handshake
		String name;
		try
		{
			name = thread.in.readLine().split("\\|")[1];
			
			addPlayer(name);
			Player player = players.get(name);
			send(thread, "player|"+player.name+"|"+player.getX()+"|"+player.getY()+"|"+player.direction+"");
			
			say("Client connected: "+name+" (" + clients.size() + " clients connected)");
			
			sendMap(thread);
			sendInventory(thread, player);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendMap(ServerThread out)
	{
		String msg = "";
		
		for(int i=0; i<Game.mapSize; i++)
		{
			msg = "map|"+i+"|0|";
			for(int j=0; j<Game.mapSize; j++)
			{
				if(j != 0)
					msg += ",";
				
				msg += game.map.tiles[i][j].id;
			}
			send(out, msg);
		}
		
		Iterator<Entry<Integer, ItemEntity>> it = game.map.itemEntities.entrySet().iterator();
		
		while(it.hasNext() && !quit)
		{
			Entry<Integer, ItemEntity> pairs = it.next();
			ItemEntity entity = pairs.getValue();
			
			broadcast("item|"+entity.name+"|"+entity.entityId+"|"+entity.x+"|"+entity.y);
		}
		
		send(out,"map done");
	}
	
	public void sendInventory(ServerThread out, Player player)
	{
		String msg = "";
		PlayerInventory inventory = player.playerInventory;
		
		msg = "inventory|";
		
		for(int i=0; i<inventory.items.size(); i++)
		{
			if(i > 0)
				msg += ";";
			
			msg += inventory.items.get(i).item.name + "," + inventory.items.get(i).quantity;
		}
		
		send(out, msg);
	}
	
	public void disconnectClient(ServerThread out)
	{
		// Remove the client from the message distribution list
		clients.remove(out);
		say("Client disconnected. Clients connected: " + clients.size());
	}
	
	public void quit()
	{
		quit = true;
		for (int i = 0; i < clients.size(); i++)
		{
			clients.get(i).quit();
		}
	}
}
