package main;

import items.Item;

import java.awt.Graphics;
import java.util.ArrayList;

public class Inventory
{
	int rows = 1;
	int columns = 1;
	public ArrayList<InventoryItem> items;
	
	public Inventory()
	{
		items = new ArrayList<InventoryItem>();
	}
	
	public void addItem(Item item)
	{
		addItem(item, 1);
	}
	
	public void addItem(Item item, int quantity)
	{
		if(item == null)
			return;
		
		for(int i=0; i<items.size(); i++)
		{
			if(items.get(i).item.name.equals(item.name))
			{
				items.get(i).quantity += quantity;
				return;
			}
		}
		
		items.add(new InventoryItem(item,quantity,1,1));
	}
	
	public Item getItem(int row, int column)
	{
		InventoryItem item;
		for(int i=0; i<columns; i++)
		{
			item = items.get(i);
			if(item.row == row && item.column == column)
			{
				return item.item;
			}
		}
		return null;
	}
	
	public void render(Graphics g)
	{
		
	}
}
