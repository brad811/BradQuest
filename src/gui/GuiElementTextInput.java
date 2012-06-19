package gui;

import static org.lwjgl.opengl.GL11.*;
import main.GameApplet;


public class GuiElementTextInput extends GuiElement
{
	String value;
	
	public GuiElementTextInput(GuiScreen screen, int id, float x, float y, float w, float h, String value)
	{
		this.screen = screen;
		this.id = id;
		
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		
		this.value = value;
	}
	
	public void handleClick()
	{
		if(!isClicked())
			return;
		
		screen.handleClick(id);
	}
	
	static float myZ = 105.0f;
	
	public void render()
	{
		glLoadIdentity();
		
		glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		//glBindTexture(GL_TEXTURE_2D, 0);
		glBindTexture(GL_TEXTURE_2D, GameApplet.getTilesTexture().getTextureID());
		
		glBegin(GL_QUADS);
		
		glTexCoord2f(6.0f/16.0f, 0.0f/16.0f);	glVertex3f(x + w,	y + h,	1.0f);
		glTexCoord2f(5.0f/16.0f, 1.0f/16.0f);	glVertex3f(x + 0,	y + h,	1.0f);
		glTexCoord2f(5.0f/16.0f, 1.0f/16.0f);	glVertex3f(x + 0,	y + 0,	1.0f);
		glTexCoord2f(6.0f/16.0f, 0.0f/16.0f);	glVertex3f(x + w,	y + 0,	1.0f);
	    
		glEnd();
		
		FontRenderer.render(
				value,
				x/12,
				y/8 + 0.7f,
				myZ
			);
	}
}
