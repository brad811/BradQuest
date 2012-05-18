package tiles;

import items.Item;
import items.ItemEntity;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Map.Entry;

import client.Client;

import main.Game;
import main.Map;

public class SolidTile extends Tile
{
	public HashSet<Item> destroyedBy;
	public HashMap<Integer,ItemEntity[]> drops;
	public Tile bottom;
	public int health;
	
	public SolidTile(Map map, int x, int y, Tile bottom)
	{
		super(map, x, y);
		type = Tile.TYPE_SOLID;
		this.bottom = bottom;
		
		destroyedBy = new HashSet<Item>();
		drops = new HashMap<Integer,ItemEntity[]>();
	}
	
	public void render(Graphics g, int screenX, int screenY)
	{
		bottom.render(screenX, screenY);
		super.render(screenX, screenY);
	}
	
	public void strike(Item item)
	{
		if(destroyedBy.contains(item))
			health--;
		
		if(health == 0)
		{
			destroy();
		}
		
		// Later add damage tile message to server
	}
	
	public void clientDestroy()
	{
		Client.out.println("destroy tile|"+x+"|"+y);
	}
	
	public void serverDestroy()
	{
		map.tiles[x][y] = bottom;
		Random rand = new Random(System.currentTimeMillis() + System.nanoTime());
		int value = rand.nextInt(100);
		
		try {
			Iterator<Entry<Integer,ItemEntity[]>> it = drops.entrySet().iterator();
			Entry<Integer,ItemEntity[]> cur;
			while(it.hasNext())
			{
				cur = it.next();
				if(value < cur.getKey())
				{
					// drop the stuff in the entry
					ItemEntity[] items = cur.getValue();
					for(int i=0; i<items.length; i++)
					{
						items[i].entityId = Game.entityId++;
						map.addItemEntity(items[i]);
					}
					
					break;
				}
				//it.remove();
			}
		} catch(NullPointerException e)
		{
			// this tile drops nothing
		}
		
		id = bottom.id;
	}
	
	public void destroy()
	{
		if(Game.mode == Game.CLIENT_MODE)
			clientDestroy();
		else if(Game.mode == Game.SERVER_MODE)
			serverDestroy();
	}
}
