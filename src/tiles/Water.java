package tiles;

import main.Map;

public class Water extends Tile
{
	public static int _tileX = 2;
	public static int _tileY = 0;
	public static String _name = "water";
	public static int _type = Tile.TYPE_LIQUID;
	public static int _id = Tile.TILE_WATER;
	
	public Water(Map map, int x, int y)
	{
		super(map, x,y);
		tileX = _tileX;
		tileY = _tileY;
		name = _name;
		type = _type;
		id = _id;
	}
}
