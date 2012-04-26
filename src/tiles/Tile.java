package tiles;

import items.Item;

import org.lwjgl.opengl.GL11;

import client.Client;

import main.Map;
import main.Renderer;

public class Tile
{
	public int tileX;
	public int tileY;
	public String name;
	public int type;
	public int id;
	
	public final Map map;
	public final int x;
	public final int y;
	
	public final static int TYPE_LAND = 1;
	public final static int TYPE_LIQUID = 2;
	public final static int TYPE_SOLID = 3;
	
	public final static int TILE_GRASS = 1;
	public final static int TILE_DIRT = 2;
	public final static int TILE_WATER = 3;
	public final static int TILE_LAVA = 4;
	public final static int TILE_TREE = 5;
	public final static int TILE_ASH = 6;
	
	public Tile(Map map, int x, int y)
	{
		this.map = map;
		this.x = x;
		this.y = y;
	}
	
	public static void init()
	{
		Renderer.buildTile(TILE_ASH, Ash._tileX, Ash._tileY);
		Renderer.buildTile(TILE_DIRT, Dirt._tileX, Dirt._tileY);
		Renderer.buildTile(TILE_GRASS, Grass._tileX, Grass._tileY);
		Renderer.buildTile(TILE_LAVA, Lava._tileX, Lava._tileY);
		Renderer.buildTile(TILE_TREE, Tree._tileX, Tree._tileY);
		Renderer.buildTile(TILE_WATER, Water._tileX, Water._tileY);
		
		Renderer.buildTree();
	}
	
	public void strike(Item item)
	{
	}
	
	public static Tile create(Map map, int type, int row, int column)
	{
		Tile tile;
		switch (type) {
			case Tile.TILE_ASH:
				tile = new Ash(map, row, column);
				break;
			case Tile.TILE_DIRT:
				tile = new Dirt(map, row, column);
				break;
			case Tile.TILE_GRASS:
				tile = new Grass(map, row, column);
				break;
			case Tile.TILE_LAVA:
				tile = new Lava(map, row, column);
				break;
			case Tile.TILE_TREE:
				tile = new Tree(map, row, column, new Grass(map, row, column));
				break;
			case Tile.TILE_WATER:
				tile = new Water(map, row, column);
				break;
			default:
				tile = new Ash(map, row, column);
		}
		
		return tile;
	}
	
	public void render(float screenX, float screenY)
	{
		GL11.glLoadIdentity(); // Reset The View
		GL11.glTranslatef(screenX, screenY, -1.0f); // Move down into position
		
		GL11.glCallList(Renderer.tiles[id]);
	}
	
	public void tick()
	{
		Client.out.println("tile|"+x+"|"+y+"|"+id);
	}
}
