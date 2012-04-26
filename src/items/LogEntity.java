package items;

import main.Map;

public class LogEntity extends ItemEntity
{
	// Client
	public LogEntity(Map map, int x, int y, int entityId)
	{
		super(map, x, y, new Log(), entityId);
		spriteX = 0;
		spriteY = 0;
		name = "log";
	}
	
	// Server
	public LogEntity(Map map, int x, int y)
	{
		super(map, x, y, new Log());
		name = "log";
	}
}
