package gui;

import static org.lwjgl.opengl.GL11.*;

public class GuiElementBox extends GuiElement
{
	float r, g, b, a;
	
	public GuiElementBox(GuiScreen screen, int id, 
			float x, float y, float w, float h, 
			float r, float g, float b, float a
		)
	{
		this.screen = screen;
		this.id = id;
		
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	
	public void render()
	{
		glLoadIdentity();
		
		glColor4f(r, g, b, a);
		glBindTexture(GL_TEXTURE_2D, 0);
		
		glBegin(GL_QUADS);
		
		glTexCoord2f(7.0f/16.0f, 0.0f/16.0f);	glVertex3f(x + w,	y + h,	0.0f);
		glTexCoord2f(6.0f/16.0f, 1.0f/16.0f);	glVertex3f(x + 0,	y + h,	0.0f);
		glTexCoord2f(6.0f/16.0f, 1.0f/16.0f);	glVertex3f(x + 0,	y + 0,	0.0f);
		glTexCoord2f(7.0f/16.0f, 0.0f/16.0f);	glVertex3f(x + w,	y + 0,	0.0f);
	    
		glEnd();
	}
}
