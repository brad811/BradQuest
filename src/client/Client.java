package client;

import items.Item;
import items.Log;
import items.LogEntity;

import java.awt.TextArea;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import main.Game;
import main.MultiplayerMode;
import main.Player;

public class Client implements Runnable
{
	static TextArea text = null;
	public static PrintWriter out = null;
	BufferedReader in = null;
	public Socket socket;
	MultiplayerMode multiplayerMode;
	Player player;
	boolean connected = false;
	public static boolean loaded = false;
	boolean quit = false;
	
	public Client(MultiplayerMode multiplayerMode)
	{
		this.multiplayerMode = multiplayerMode;
		this.player = multiplayerMode.player;
	}
	
	public void start()
	{
		Thread t = new Thread(this);
		t.start();
	}
	
	public boolean connect()
	{
		socket = null;
		try
		{
			socket = new Socket();
			socket.setTcpNoDelay(true);
			socket.connect(new InetSocketAddress(MultiplayerMode.server, Game.port), 3000);
			out = new PrintWriter(socket.getOutputStream(), true);
			
			try
			{
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			} catch (IOException e)
			{
				System.out.println("Connection error!");
				return false;
			}
			
			// Handshake
			// player | name | x | y | direction
			out.println("name|"+player.name);
			String[] position = in.readLine().split("\\|");
			if(position[1].equals(player.name))
			{
				player.setX( Integer.parseInt(position[2]) );
				player.setY( Integer.parseInt(position[3]) );
				player.direction = Integer.parseInt(position[4]);
			}
			else
			{
				System.out.println("Bad handshake!");
				return false;
			}
			
			connected = true;
			return true;
		} catch (UnknownHostException e)
		{
			System.out.println("Dont know about that host");
			return false;
		} catch (ConnectException e)
		{
			System.out.println("Could not connect to server.");
			return false;
		} catch (IOException e)
		{
			System.out.println("Error:" + e);
			return false;
		}
	}
	
	@Override
	public void run()
	{
		while (!quit)
		{System.out.println("Client - run");
			String serverMessage = "";
			
			try
			{
				serverMessage = in.readLine();
			} catch (SocketException e)
			{
				System.out.println("Connection to server lost.");
				return;
			} catch (IOException e)
			{
				System.out.println("Error: " + e);
				return;
			}
			
			if (!serverMessage.equals(""))
			{
				String message[] = serverMessage.split("\\|");
				
				//System.out.println("Type: " + message[0]);
				
				// [map][row][column]
				if (message[0].equals("map"))
				{
					parseMapData(message);
				}
				else if(message[0].equals("map done"))
				{
					loaded = true;
				}
				else if(message[0].equals("player"))
				{
					parsePlayerData(message);
				}
				else if(message[0].equals("tile"))
				{
					parseTileData(message);
				}
				else if(message[0].equals("item"))
				{
					parseItemData(message);
				}
				else if(message[0].equals("item remove"))
				{
					parseItemRemoveData(message);
				}
				else if(message[0].equals("item get"))
				{
					parseItemGetData(message);
				}
				else if(message[0].equals("inventory"))
				{
					parseInventoryData(message);
				}
			}
		}
	}
	
	public void parsePlayerData(String playerData[])
	{
		// player|name|x|y|direction
		
		if(playerData[1].equals(player.name))
			return;
		
		boolean found = false;
		for(Player p : multiplayerMode.clientMap.players)
		{
			if(p.name.equals(playerData[1]))
			{
				p.setX( Integer.parseInt(playerData[2]) );
				p.setY( Integer.parseInt(playerData[3]) );
				p.direction = Integer.parseInt(playerData[4]);
				found = true;
				break;
			}
		}
		
		if(!found)
		{
			System.out.println("Adding player: " + playerData[1]);
			Player p = new Player(null,multiplayerMode.clientMap,playerData[1]);
			multiplayerMode.clientMap.players.add(p);
		}
	}
	
	public void parseMapData(String mapData[])
	{
		int i = Integer.parseInt(mapData[1]), j = Integer.parseInt(mapData[2]);
		String tiles[] = mapData[3].split(",");
		
		for (; j < tiles.length; j++)
		{
			multiplayerMode.clientMap.setTile(i, j, Integer.parseInt(tiles[j]));
		}
		
		if(!loaded)
		{
			multiplayerMode.clientMap.percentLoaded = (Double.parseDouble(mapData[1])+1)/Game.mapSize;
		}
	}
	
	public void parseInventoryData(String inventoryData[])
	{
		try {
			String items[] = inventoryData[1].split(";");
			
			for(int i=0; i<items.length; i++)
			{
				String info[] = items[i].split(",");
				String itemName = info[0];
				int itemQuantity = Integer.parseInt(info[1]);
				
				player.addItem(Item.getItemByName(itemName), itemQuantity);
			}
		} catch(ArrayIndexOutOfBoundsException e) {
			return;
		}
	}
	
	public void parseTileData(String tileData[])
	{
		multiplayerMode.clientMap.setTile(
				Integer.parseInt(tileData[1]), // x
				Integer.parseInt(tileData[2]), // y
				Integer.parseInt(tileData[3])  // type
			);
	}
	
	public void parseItemData(String itemData[])
	{
		String name = itemData[1];
		int entityId = Integer.parseInt(itemData[2]);
		int x = Integer.parseInt(itemData[3]);
		int y = Integer.parseInt(itemData[4]);
		
		if(multiplayerMode.clientMap.hasItemEntity(entityId))
			return;
		
		if(name.equals("log"))
		{
			//System.out.println("Adding item to map! ("+entityId+")(client)");
			multiplayerMode.clientMap.addItemEntity(new LogEntity(multiplayerMode.clientMap,x,y,entityId));
		}
	}
	
	public void parseItemRemoveData(String itemRemoveData[])
	{
		System.out.println("Item remove data!");
		int entityId = Integer.parseInt(itemRemoveData[1]);
		
		if(multiplayerMode.clientMap.hasItemEntity(entityId))
		{
			System.out.println("Removing!");
			multiplayerMode.clientMap.removeItemEntity(entityId);
		}
	}
	
	public void parseItemGetData(String itemGetData[])
	{
		String name = itemGetData[1];
		int entityId = Integer.parseInt(itemGetData[2]);
		
		if(name.equals(player.name) && multiplayerMode.clientMap.hasItemEntity(entityId))
		{
			//String itemName = itemGetData[2];
			player.addItem(new Log());
		}
		multiplayerMode.clientMap.removeItemEntity(entityId);
	}
	
	public void tick()
	{
		out.println("player|"+player.name+"|"+player.getX()+"|"+player.getY()+"|"+player.direction);
	}
	
	public void quit()
	{
		quit = true;
		
		connected = false;
		loaded = false;
		
		out.close();
		try { in.close(); } catch (IOException e) {	}
		try { socket.close(); } catch (IOException e) {	}
	}
}
