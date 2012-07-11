package gui;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.input.Keyboard;

import main.Game;
import main.MultiplayerMode;

public class GuiElementTextInput extends GuiElement
{
	String value;
	boolean password = false, focus = true, caret = false;
	int caretCount;
	
	public GuiElementTextInput(GuiScreen screen, int id, float x, float y, float w, float h, String value, boolean p, boolean f)
	{
		this.screen = screen;
		this.id = id;
		
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		
		this.value = value;
		
		this.password = p;
		this.focus = f;
	}
	
	public void handleClick()
	{
		if(!isClicked())
		{
			focus = false;
			return;
		}
		
		screen.handleClick(id);
		
		focus = true;
	}
	
	public void handleKeyboard()
	{
		if(focus
				&& Keyboard.getEventKeyState()
				&& (int)Keyboard.getEventCharacter() >= 32
				&& (int)Keyboard.getEventCharacter() <= 127
				&& Keyboard.getEventCharacter() != '\u0000'
			)
		{
			value += Keyboard.getEventCharacter();
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_BACK) && value.length() > 0)
		{
			value = value.substring(0,value.length()-1);
		}
	}
	
	public void render()
	{
		glLoadIdentity();
		
		glColor4f(1.0f, 1.0f, 1.0f, 0.8f);
		//glBindTexture(GL_TEXTURE_2D, 0);
		glBindTexture(GL_TEXTURE_2D, MultiplayerMode.tilesTexture.getTextureID());
		
		glBegin(GL_QUADS);
		
		glTexCoord2f(6.0f/16.0f, 0.0f/16.0f);	glVertex3f(x + w,	y + h,	0.0f);
		glTexCoord2f(5.0f/16.0f, 1.0f/16.0f);	glVertex3f(x + 0,	y + h,	0.0f);
		glTexCoord2f(5.0f/16.0f, 1.0f/16.0f);	glVertex3f(x + 0,	y + 0,	0.0f);
		glTexCoord2f(6.0f/16.0f, 0.0f/16.0f);	glVertex3f(x + w,	y + 0,	0.0f);
		
		glColor4f(0.0f, 0.0f, 0.0f, 0.8f);
		glBindTexture(GL_TEXTURE_2D, 0);
		
		glTexCoord2f(6.0f/16.0f, 0.0f/16.0f);	glVertex3f(x + w - 2,	y + h - 2,	0.0f);
		glTexCoord2f(5.0f/16.0f, 1.0f/16.0f);	glVertex3f(x + 2,		y + h - 2,	0.0f);
		glTexCoord2f(5.0f/16.0f, 1.0f/16.0f);	glVertex3f(x + 2,		y + 2,		0.0f);
		glTexCoord2f(6.0f/16.0f, 0.0f/16.0f);	glVertex3f(x + w - 2,	y + 2,		0.0f);
		
		glEnd();
		
		glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		
		String myValue = "";
		
		if(password)
			for(int i=0; i<value.length(); i++)
				myValue += "*";
		else
			myValue = value;
		
		if(focus)
		{
			if(++caretCount > Game.FPS / 2)
			{
				caret = !caret;
				caretCount = 0;
			}
			
			if(caret)
			{
				FontRenderer.render("|", x + FontRenderer.stringWidth(myValue), y + h/2, 0.0f);
			}
		}
		
		FontRenderer.render(
				myValue,
				x,
				y + h/2,
				0.0f
			);
	}
}
