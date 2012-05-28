package gui;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluLookAt;
import static org.lwjgl.util.glu.GLU.gluPerspective;
import main.GameApplet;

public class Gui
{
	public static final int GUI_MAIN_MENU = 0;
	public static final int GUI_SINGLE_PLAYER_MENU = 1;
	public static final int GUI_MULTIPLAYER_MENU = 2;
	public static final int GUI_LOADING_MAP = 3;
	public static final int GUI_IN_GAME = 4;
	
	public int currentScreen = GUI_MAIN_MENU;
	
	GuiMainMenu guiMainMenu;
	
	public Gui()
	{
		guiMainMenu = new GuiMainMenu();
	}
	
	public void render()
	{
		glMatrixMode(GL_PROJECTION); // Select The Projection Matrix
		glLoadIdentity(); // Reset The Projection Matrix
		gluPerspective(45.0f, ((float) GameApplet.dim.width / (float) GameApplet.dim.height), 0.1f, 100.0f); // Calculate The Aspect Ratio Of The Window
		
		gluLookAt(
				// Be over the player
				0, 0, 10,
				
				// Look at the player
				0, 0, 0,	// Center
				
				0.0f, 0.0f, 1.0f	// Up
			);
		
		switch(currentScreen) {
			case GUI_MAIN_MENU: guiMainMenu.render(); break;
		}
	}
}
