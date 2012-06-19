package gui;

import java.util.ArrayList;
import java.util.Iterator;

import org.lwjgl.input.Mouse;

public class GuiScreen
{
	Gui gui;
	public ArrayList<GuiElement> elements = new ArrayList<GuiElement>();
	
	public void render()
	{
		Iterator<GuiElement> it = elements.iterator();
		while(it.hasNext())
		{
			it.next().render();
		}
	}
	
	public void handleMouse()
	{
		if(!Mouse.isButtonDown(0))
			return;
		
		Iterator<GuiElement> it = elements.iterator();
		while(it.hasNext())
		{
			it.next().handleClick();
		}
	}
	
	public void handleKeyboard()
	{
		
	}
	
	public void handleClick(int id)
	{
	}
}
