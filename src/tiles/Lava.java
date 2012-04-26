package tiles;

import main.Map;

public class Lava extends Tile
{
	public static int _tileX = 3;
	public static int _tileY = 0;
	public static String _name = "lava";
	public static int _type = Tile.TYPE_LIQUID;
	public static int _id = Tile.TILE_LAVA;
	
	public Lava(Map map, int x, int y)
	{
		super(map, x,y);
		tileX = _tileX;
		tileY = _tileY;
		name = _name;
		type = _type;
		id = _id;
	}
}
