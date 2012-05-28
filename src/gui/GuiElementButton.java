package gui;

import static org.lwjgl.opengl.GL11.*;

public class GuiElementButton extends GuiElement
{
	int x, y, w, h;
	
	public GuiElementButton(int x, int y, int w, int h)
	{
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public void render()
	{
		//System.out.println("Rendering GUI button. ("+x+","+y+","+w+","+h+")");
		
		glBegin(GL_POINTS); //starts drawing of points
		
		glColor4f(0,0,1,1);
		
		glVertex3f(x, y, 0.0f);
		glVertex3f(w, h, 0.0f);
	    
		glEnd();//end drawing of points
	}
}
