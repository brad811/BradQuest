package main;

import items.Item;
import items.ItemEntity;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import tiles.*;

public class Map
{
	public Tile[][] tiles;
	public double percentLoaded = 0.0;
	public double percentGenerated = 0.0;
	
	public int timeOfDay = 12000;
	
	public ConcurrentHashMap<Integer,ItemEntity> itemEntities = new ConcurrentHashMap<Integer,ItemEntity>();
	public HashSet<Player> players = new HashSet<Player>();
	
	public Map()
	{
		tiles = new Tile[Game.mapSize][Game.mapSize];
	}
	
	public void generate()
	{
		tiles = new Tile[Game.mapSize][Game.mapSize];
		PerlinNoise p = new PerlinNoise();
		int[][] values = p.GenerateGradientMap();
		
		int count = 0;
		for(int i=0; i<Game.mapSize; i++)
		{
			for(int j=0; j<Game.mapSize; j++)
			{
				int r = values[i][j];
				if(r < 35) { tiles[i][j] = new Tree(this,i,j,new Grass(this,i,j)); }
				else if(r < 50) { tiles[i][j] = new Grass(this,i,j); }
				else if(r < 51) { tiles[i][j] = new Dirt(this,i,j); }
				else if(r < 75) { tiles[i][j] = new Water(this,i,j); }
				else if(r < 80) { tiles[i][j] = new Ash(this,i,j); }
				else { tiles[i][j] = new Lava(this,i,j); }
				
				count++;
				percentGenerated = ((double)count/(double)(Game.mapSize * Game.mapSize))*100;
			}
		}
		
		percentGenerated = 100.0;
	}
	
	public boolean isOnMap(int x, int y)
	{
		// Can't I just check by multiplying by tile size?
		try
		{
			@SuppressWarnings("unused")
			Tile tile = tiles[x / Game.tileSize][y / Game.tileSize];
		} catch (ArrayIndexOutOfBoundsException e)
		{
			return false;
		}
		return true;
	}
	
	public void setTile(int row, int column, int type)
	{
		tiles[row][column] = Tile.create(this, type, row, column);
	}
	
	public void serverTick()
	{
		Iterator<Entry<Integer, ItemEntity>> it = itemEntities.entrySet().iterator();
		
		while(it.hasNext())
		{
			Entry<Integer, ItemEntity> pairs = it.next();
			ItemEntity entity = pairs.getValue();
			entity.tick();
		}
	}
	
	public void addItemEntity(ItemEntity entity)
	{
		itemEntities.put(entity.entityId, entity);
	}
	
	public void serverAddItemEntity(ItemEntity entity)
	{
		System.out.println("Adding entity before ("+entity.entityId+"): " + itemEntities.size());
		
		itemEntities.put(entity.entityId, entity);
		
		Iterator<Entry<Integer, ItemEntity>> it = itemEntities.entrySet().iterator();
		
		while(it.hasNext())
		{
			Entry<Integer, ItemEntity> pairs = it.next();
			ItemEntity entitys = pairs.getValue();
			System.out.println("Before EntityId: " + entitys.entityId);
		}
	}
	
	public void removeItemEntity(int entityId)
	{
		itemEntities.remove(entityId);
	}
	
	public void serverRemoveItemEntity(int entityId)
	{
		itemEntities.remove(entityId);
	}
	
	public boolean hasItemEntity(int entityId)
	{
		if(itemEntities.containsKey(entityId))
			return true;
		
		return false;
	}
	
	public void renderItemEntities(int playerX, int playerY)
	{
		Iterator<Entry<Integer, ItemEntity>> it = itemEntities.entrySet().iterator();
		
		while(it.hasNext())
		{
			Entry<Integer, ItemEntity> pairs = it.next();
			ItemEntity entity = pairs.getValue();
			
			entity.render(playerX, playerY);
		}
	}
	
	public ItemEntity pickUpItemEntities(Player player)
	{
		Iterator<Entry<Integer, ItemEntity>> it = itemEntities.entrySet().iterator();
		
		while(it.hasNext())
		{
			Entry<Integer, ItemEntity> pairs = it.next();
			ItemEntity entity = pairs.getValue();
			
			double distance = Math.sqrt(Math.pow(entity.x - player.getX(), 2) + Math.pow(entity.y - player.getY(), 2));
			
			if (distance <= 20)
			{
				Item item = entity.pickup();
				player.playerInventory.addItem(item);
				return entity;
			}
		}
		
		return null;
	}
	
	public void render(int screenX, int screenY, int playerX, int playerY)
	{
		int width = 32, height = 30;
		for (int i = (playerX / Game.tileSize) - width/2;
				i <= (playerX / Game.tileSize) + width/2;
				i++
			)
		{
			for (int j = (playerY / Game.tileSize) - height/2;
					j <= (playerY / Game.tileSize) + height/2;
					j++
				)
			{
				try
				{
					tiles[i][j].render(
							i - ((float)(playerX % Game.tileSize) / (float)Game.tileSize),
							j - ((float)(playerY % Game.tileSize) / (float)Game.tileSize)
						);
				} catch (ArrayIndexOutOfBoundsException e)
				{
					//System.out.println("AIOOBE!");
				} catch (NullPointerException e)
				{
					//System.out.println("NPE!");
				}
			}
		}
	}
}
