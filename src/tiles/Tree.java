package tiles;

import static org.lwjgl.opengl.GL11.*;

import items.ItemEntity;
import items.LogEntity;
import main.Map;

public class Tree extends SolidTile
{
	public static int _tileX = 4;
	public static int _tileY = 0;
	public static String _name = "tree";
	public static int _type = Tile.TYPE_SOLID;
	public static int _id = Tile.TILE_TREE;
	
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
	}
	
	// Eventually this should go away, and just add a model to be rendered in the constructor, in SolidTile
	public void render(float screenX, float screenY)
	{
		bottom.render(screenX, screenY);
		
		glLoadIdentity(); // Reset The View
		glTranslatef(screenX, screenY, 0.0f); // Move down into position
		
		// Trunk
		Tile.models.get(Tile.TILE_TREE).get(0).render(0.5f, 0.5f, 1.0f);
		
		// Leaves
		glTranslatef(0.0f, 0.0f, 0.75f);
		Tile.models.get(Tile.TILE_TREE).get(1).render(1.5f, 1.5f, 0.5f);
	}
}
