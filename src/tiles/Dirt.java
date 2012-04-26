package tiles;

import main.Map;

public class Dirt extends Tile
{
	public static int _tileX = 1;
	public static int _tileY = 0;
	public static String _name = "dirt";
	public static int _type = Tile.TYPE_LAND;
	public static int _id = Tile.TILE_DIRT;
	
	public Dirt(Map map, int x, int y)
	{
		super(map, x,y);
		tileX = _tileX;
		tileY = _tileY;
		name = _name;
		type = _type;
		id = _id;
	}
}
