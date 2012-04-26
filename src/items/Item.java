package items;

import java.awt.Graphics;

public class Item
{
	public int spriteX;
	public int spriteY;
	public String name;
	public int type;
	
	public Item()
	{
	}
	
	public void render(Graphics g, int screenX, int screenY)
	{
		/*
		g.drawImage(GameApplet.getItemsImage(),
				screenX, screenY,
				screenX+Game.tileSize*2, screenY+Game.tileSize*2,
				spriteX*Game.tileSize, spriteY*Game.tileSize,
				spriteX*Game.tileSize + Game.tileSize, spriteY*Game.tileSize + Game.tileSize,
			null);
		*/
	}
	
	public static Item getItemByName(String name)
	{
		if(name.equals((new Log()).name))
			return new Log();
		
		return null;
	}
}
