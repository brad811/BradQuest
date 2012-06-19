package gui;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import main.GameApplet;

public class Gui
{
	public static final int GUI_MAIN_MENU = 0;
	public static final int GUI_SINGLE_PLAYER_MENU = 1;
	public static final int GUI_MULTIPLAYER_MENU = 2;
	public static final int GUI_LOADING_MAP = 3;
	public static final int GUI_IN_GAME = 4;
	
	public int currentScreen = GUI_MAIN_MENU;
	public GuiScreen currentScreenInstance;
	
	GuiMainMenu guiMainMenu;
	GuiSinglePlayerMenu guiSinglePlayerMenu;
	public GuiLoadingMap guiLoadingMap;
	GuiInGame guiInGame;
	
	GameApplet gameApplet;
	
	public Gui(GameApplet applet)
	{
		gameApplet = applet;
		
		guiMainMenu = new GuiMainMenu(this);
		guiSinglePlayerMenu = new GuiSinglePlayerMenu(this);
		guiLoadingMap = new GuiLoadingMap(this);
		guiInGame = new GuiInGame(this);
		
		currentScreenInstance = guiMainMenu;
	}
	
	public static void init()
	{
		GuiMainMenu.init();
	}
	
	public void setScreen(int screen)
	{
		switch(screen) {
			case GUI_MAIN_MENU: currentScreenInstance = guiMainMenu; break;
			case GUI_SINGLE_PLAYER_MENU: currentScreenInstance = guiSinglePlayerMenu; break;
			//case GUI_MULTIPLAYER_MENU: currentScreenInstance = guiMainMenu; break;
			case GUI_LOADING_MAP: currentScreenInstance = guiLoadingMap; break;
			case GUI_IN_GAME: currentScreenInstance = guiInGame; break;
		}
	}
	
	public void render()
	{
		glPushMatrix();
		
		glMatrixMode(GL_PROJECTION); // Select The Projection Matrix
		glLoadIdentity(); // Reset The Projection Matrix
		gluPerspective(90.0f, ((float) GameApplet.dim.width / (float) GameApplet.dim.height), 0.1f, 200.0f); // Calculate The Aspect Ratio Of The Window
		gluLookAt(
				// Position
				0.0f, 0.0f, 120.0f,
				
				// Look at
				0.0f, 0.0f, 0.0f,	// Center
				
				0.0f, 1.0f, 0.0f	// Up
			);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		
		glDisable(GL_LIGHTING);
		
		currentScreenInstance.render();
		
		glPopMatrix();
	}
	
	static boolean leftClickDown = false;
	public void handleInput()
	{
		if(leftClickDown)
		{
			if(!Mouse.isButtonDown(0))
				leftClickDown = false;
			
			return;
		}
		
		while(Mouse.next() && !leftClickDown)
		{
			if(Mouse.isButtonDown(0))
			{
				leftClickDown = true;
				currentScreenInstance.handleMouse();
			}
		}
		
		while(Keyboard.next())
		{
			currentScreenInstance.handleKeyboard();
		}
		
		
	}
}
