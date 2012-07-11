package gui;

import main.GameApplet;

import org.lwjgl.input.Mouse;

public class GuiElement
{
	GuiScreen screen;
	int id;
	
	float x, y, w, h;
	
	public void render()
	{
		
	}
	
	public boolean isClicked()
	{
		int mx = Mouse.getEventX();
		int my = GameApplet.dim.height - Mouse.getEventY();
		
		
		int leftEdge = GameApplet.dim.width/2 + (int)x*2;
		int rightEdge = GameApplet.dim.width/2 + (int)x*2 + (int)w*2;
		
		int topEdge = GameApplet.dim.height / 2 - (int)y*2 - (int)h*2;
		int bottomEdge = GameApplet.dim.height / 2 - (int)y*2;
		
		if(mx > leftEdge && mx < rightEdge && my > topEdge && my < bottomEdge)
		{
			return true;
		}
		return false;
	}
	
	public void handleClick()
	{
	}

	public void handleKeyboard()
	{
	}
}
