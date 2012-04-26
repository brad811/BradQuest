package main;

import items.Item;

public class InventoryItem
{
	public Item item;
	public int quantity;
	public int row;
	public int column;
	
	public InventoryItem(Item i, int q, int r, int c)
	{
		item = i;
		quantity = q;
		row = r;
		column = c;
	}
}
