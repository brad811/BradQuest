package tiles;

import render.Renderer;

import items.ItemEntity;
import items.LogEntity;
import main.Game;
import main.Map;

public class Tree extends SolidTile
{
	public static int _tileX = 4;
	public static int _tileY = 0;
	public static String _name = "tree";
	public static int _type = Tile.TYPE_SOLID;
	public static int _id = Tile.TILE_TREE;
	
	public float rx, ry;
	
	public Tree(Map map, int mapX, int mapY, Tile bottom)
	{
		super(map, mapX, mapY, bottom);
		tileX = _tileX;
		tileY = _tileY;
		name = _name;
		id = _id;
		
		health = 4;
		
		destroyedBy.add(null);
		
		ItemEntity drop[] = {new LogEntity(map,x,y)};
		drops.put(50,drop);
		
		double jitter = 0.0;
		rx = (float)(jitter - Game.rand.nextDouble() * jitter);
		ry = (float)(jitter - Game.rand.nextDouble() * jitter);
	}
	
	// Eventually this should go away, and just add a model to be rendered in the constructor, in SolidTile
	// ^^^ Is this still true? ^^^
	public void render()
	{
		bottom.render();
		
		Renderer.addTile(
				Tile.models.get(Tile.TILE_TREE).get(0).vertex_data_array, 
				x + rx, y + ry, 0.0f, 
				0.5f, 0.5f, 1.0f
			);
		
		Renderer.addTile(
				Tile.models.get(Tile.TILE_TREE).get(1).vertex_data_array, 
				x + rx, y + ry, 0.9f, 
				0.8f, 0.8f, 0.8f
			);
	}
}
