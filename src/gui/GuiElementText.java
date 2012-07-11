package gui;

import static org.lwjgl.opengl.GL11.*;

public class GuiElementText extends GuiElement
{
	float r, g, b, a;
	String text;
	boolean center;
	
	public GuiElementText(GuiScreen screen, int id, 
			float x, float y,
			float r, float g, float b, float a,
			String text, boolean center
		)
	{
		this.screen = screen;
		this.id = id;
		
		this.x = x;
		this.y = y;
		
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
		
		this.text = text;
		this.center = center;
	}
	
	public void render()
	{
		glLoadIdentity();
		
		glColor4f(r, g, b, a);
		glBindTexture(GL_TEXTURE_2D, 0);
		
		if(center)
			FontRenderer.renderCentered(
					text,
					x,
					y,
					0.0f
				);
		else
			FontRenderer.render(
					text,
					x,
					y,
					0.0f
				);
	}
}
