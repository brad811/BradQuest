package gui;

import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class GuiMainMenu extends GuiScreen
{
	public static Texture mainMenuTexture;
	
	public GuiMainMenu(Gui gui)
	{
		this.gui = gui;
		
		// add elements
		elements.add(new GuiElementButton(this, 0, -75, 30, 150, 20, "Single Player"));
		elements.add(new GuiElementButton(this, 1, -75, -10, 150, 20, "Multiplayer"));
		elements.add(new GuiElementButton(this, 2, -75, -60, 150, 20, "Server Mode"));
		
		elements.add(new GuiElementButton(this, 2, -150, -60, 50, 20, "one"));
		elements.add(new GuiElementButton(this, 2, 125, -60, 50, 20, "one"));
	}
	
	public static void init()
	{
		try
		{
			if(mainMenuTexture == null)
				mainMenuTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("/main_menu.png"));
			
			glBindTexture(GL_TEXTURE_2D, mainMenuTexture.getTextureID());
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		} catch (IOException e)
		{
			System.out.println("Unable to load main menu texture!");
		}
	}
	
	public void handleClick(int id)
	{
		switch(id) {
			case 0: singlePlayerModeClicked(); break;
			case 1: multiplayerModeClicked(); break;
			case 2: serverModeClicked(); break;
		}
	}
	
	public void singlePlayerModeClicked()
	{
		gui.gameApplet.game.console = true;
		gui.gameApplet.game.startServerMode();
		gui.gameApplet.startMultiplayerMode();
	}
	
	public void multiplayerModeClicked()
	{
		System.out.println("Starting multiplayer!");
	}
	
	public void serverModeClicked()
	{
		System.out.println("Starting server mode!");
	}
}
