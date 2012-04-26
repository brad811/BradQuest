package tiles;

import org.lwjgl.opengl.GL11;

import items.ItemEntity;
import items.LogEntity;
import main.Map;
import main.Renderer;

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
	
	public void render(float screenX, float screenY)
	{
		bottom.render(screenX, screenY);
		
		GL11.glLoadIdentity(); // Reset The View
		GL11.glTranslatef(screenX, screenY, -1.0f); // Move down into position
		
		GL11.glCallList(Renderer.tree);
	}
}
