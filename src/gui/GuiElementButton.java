package gui;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.input.Mouse;

import main.GameApplet;

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
		
		glTexCoord2f(7.0f/16.0f, 0.0f/16.0f);	glVertex3f(x + w,	y + h,	1.0f);
		glTexCoord2f(6.0f/16.0f, 1.0f/16.0f);	glVertex3f(x + 0,	y + h,	1.0f);
		glTexCoord2f(6.0f/16.0f, 1.0f/16.0f);	glVertex3f(x + 0,	y + 0,	1.0f);
		glTexCoord2f(7.0f/16.0f, 0.0f/16.0f);	glVertex3f(x + w,	y + 0,	1.0f);
	    
		glEnd();
		
		FontRenderer.render(
				text,
				x/7.95f + w/7.95f/2,
				y/7.95f + 0.7f,
				myZ
			);
	}
}
