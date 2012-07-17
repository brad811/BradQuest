package main;

import items.Item;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;


public class PlayerInventory extends Inventory
{
	Image img;
	int imgWidth = 442, imgHeight = 64;
	public Item equippedItem = null;
	
	public PlayerInventory()
	{
		super();
		columns = 8;
		rows = 1;
	}
	
	public void render(Graphics g, int screenX, int screenY)
	{
		try
		{
			img = ImageIO.read(Game.class.getResourceAsStream("inventory.png"));
		} catch (IOException e)
		{
			System.out.println("Inventory image not found! (1)");
		} catch (IllegalArgumentException e)
		{
			System.out.println("Inventory image not found! (2)");
		}
		
		g.drawImage(img,
				screenX/2 - imgWidth/2, screenY - imgHeight,
				screenX/2 + imgWidth/2, screenY,
				0, 0,
				imgWidth, imgHeight,
			null);
		
		InventoryItem item;
		for(int i=0; i<items.size(); i++)
		{
			item = items.get(i);
			//item.item.render(g, screenX/2 - imgWidth/2 + i*2, screenY - imgHeight/2 - 1.0f);
			g.setColor(Color.WHITE);
			g.drawString("" + item.quantity, screenX/2 - imgWidth/2 + 8, screenY - imgHeight + 18);
		}
	}
}
