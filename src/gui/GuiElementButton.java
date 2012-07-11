package gui;

import static org.lwjgl.opengl.GL11.*;

import main.MultiplayerMode;

public class GuiElementButton extends GuiElement
{
	String text;
	
	public GuiElementButton(GuiScreen screen, int id, float x, float y, float w, float h, String text)
	{
		this.screen = screen;
		this.id = id;
		
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		
		this.text = text;
	}
	
	public void handleClick()
	{
		if(!isClicked())
			return;
		
		screen.handleClick(id);
	}
	
	public void render()
	{
		glLoadIdentity();
		
		glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		glBindTexture(GL_TEXTURE_2D, MultiplayerMode.tilesTexture.getTextureID());
		
		glBegin(GL_QUADS);
		
		glTexCoord2f(7.0f/16.0f, 0.0f/16.0f);	glVertex3f(x + w,	y + h,	0.0f);
		glTexCoord2f(6.0f/16.0f, 1.0f/16.0f);	glVertex3f(x + 0,	y + h,	0.0f);
		glTexCoord2f(6.0f/16.0f, 1.0f/16.0f);	glVertex3f(x + 0,	y + 0,	0.0f);
		glTexCoord2f(7.0f/16.0f, 0.0f/16.0f);	glVertex3f(x + w,	y + 0,	0.0f);
	    
		glEnd();
		
		FontRenderer.renderCentered(
				text,
				x + w/2,
				y + h/2,
				0.0f
			);
	}
}
