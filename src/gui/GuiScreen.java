package gui;

import java.util.ArrayList;
import java.util.Iterator;

public class GuiScreen
{
	public ArrayList<GuiElement> elements = new ArrayList<GuiElement>();
	
	public void render()
	{
		Iterator<GuiElement> it = elements.iterator();
		while(it.hasNext())
		{
			it.next().render();
		}
	}
}
