package main;

import gui.Gui;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.nio.FloatBuffer;
import java.util.Date;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.Texture;

import render.Renderer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

import client.Client;

public class GameApplet extends Applet
{
	private static final long serialVersionUID = 1L;
	
	public Menu menu;
	
	public static Dimension dim = new Dimension(854, 480);
	
	public Game game;
	public Gui gui;
	public MultiplayerMode multiplayerMode;
	
	long lastFrameTime; // used to calculate delta
	public boolean closeRequested = false;
	
	Canvas display_parent;
	Thread gameThread;
	boolean running = false;
	
	public void init()
	{say("init");
		setSize(dim);
		
		game = new Game();
		gui = new Gui();
		
		MultiplayerMode.gameApplet = this;
		ServerMode.gameApplet = this;
		
		gui.currentScreen = Gui.GUI_MAIN_MENU;
	}
	
	public void start()
	{say("start");
		this.run();
	}
	
	public void run()
	{say("run");
		gameThread = new Thread() {
			public void run()
			{
				setLayout(new BorderLayout());
				try {
					display_parent = new Canvas() {
						private static final long serialVersionUID = 1L;
						
						public final void addNotify() {
							super.addNotify();
						}
						public final void removeNotify() {
							try
							{
								finalize();
							} catch (Throwable e)
							{
								e.printStackTrace();
							}
							super.removeNotify();
						}
					};
					display_parent.setSize(dim.width,dim.height);
					add(display_parent);
					display_parent.setFocusable(true);
					display_parent.requestFocus();
					display_parent.setIgnoreRepaint(true);
					setVisible(true);
				} catch (Exception e) {
					System.err.println(e);
					throw new RuntimeException("Unable to create display");
				}
				
				running = true;
				try {
					Display.setParent(display_parent);
					Display.create();
					initGL();
				} catch (LWJGLException e) {
					e.printStackTrace();
					return;
				}
				
				gameLoop();
				
				remove(display_parent);
				Display.destroy();
				
				say("gameThread - done");
			}
		};
		
		gameThread.start();
	}
	
	private void initGL()
	{say("initGL");
		try
		{
			Display.setDisplayMode(new DisplayMode(dim.width, dim.height));
		} catch (LWJGLException e)
		{
			e.printStackTrace();
		}
		
		int width = Display.getDisplayMode().getWidth();
		int height = Display.getDisplayMode().getHeight();
		
		glViewport(0, 0, width, height); // Reset The Current Viewport
		
		glMatrixMode(GL_MODELVIEW); // Select The Modelview Matrix
		glLoadIdentity(); // Reset The Modelview Matrix
		
		glEnable(GL_TEXTURE_2D);
		glShadeModel(GL_SMOOTH); // Enables Smooth Shading
		glClearColor(0.4f, 0.5f, 1.0f, 0.0f); // Black Background
		glClearDepth(1.0f); // Depth Buffer Setup
		glEnable(GL_DEPTH_TEST); // Enables Depth Testing
		glDepthFunc(GL_LEQUAL); // The Type Of Depth Test To Do
		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST); // Really Nice Perspective Calculations
		
		glEnable(GL_CULL_FACE);
		
		Renderer.init();
		
		float lightLevel = 1.0f;
		FloatBuffer lightAmbient = BufferUtils.createFloatBuffer(4).put(new float[] { lightLevel, lightLevel, lightLevel, 1.0f });
		FloatBuffer lightDiffuse = BufferUtils.createFloatBuffer(4).put(new float[] { 1.0f, 1.0f, 1.0f, 1.0f });
		FloatBuffer lightPosition = BufferUtils.createFloatBuffer(4).put(new float[] { -3.0f, -3.0f, 5.0f, 0.0f });
		
		lightAmbient.flip();
		lightDiffuse.flip();
		lightPosition.flip();
		
		glLight(GL_LIGHT1, GL_AMBIENT, lightAmbient); // Setup The Ambient Light
		glLight(GL_LIGHT1, GL_DIFFUSE, lightDiffuse); // Setup The Diffuse Light
		glLight(GL_LIGHT1, GL_POSITION, lightPosition); // Position The Light
		glEnable(GL_LIGHT1); // Enable Light One
		
		glEnable(GL_LIGHTING);
	}
	
	public void gameLoop()
	{say("gameLoop");
		int framerate_count = 0;
		long framerate_timestamp = new Date().getTime();
		
		Date d;
		long this_framerate_timestamp;
		
		while(running)
		{System.out.println("GameApplet - gameLoop");
			framerate_count++;

			d = new Date();
			this_framerate_timestamp = d.getTime();
			
			if ((this_framerate_timestamp - framerate_timestamp) >= 1000)
			{
				System.err.println("Frame Rate: " + framerate_count);

				framerate_count = 0;
				framerate_timestamp = this_framerate_timestamp;
			}
			
			render();
			Display.sync(Game.FPS);
			Display.update();
		}
		
		System.out.println("GameApplet - gameLoop done");
	}
	
	public void startMultiplayerMode()
	{say("startMultiplayerMode");
		multiplayerMode = new MultiplayerMode(game);
		MultiplayerMode.init();
		multiplayerMode.start();
		
		multiplayerMode.player.init();
	}
	
	public void render()
	{
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // Clear The Screen And The Depth Buffer
		
		gui.render();
		
		if(Client.loaded)
		{
			updateCamera();
			multiplayerMode.render(dim.width, dim.height);
		}
	}
	
	public void updateCamera()
	{
		glMatrixMode(GL_PROJECTION); // Select The Projection Matrix
		glLoadIdentity(); // Reset The Projection Matrix
		gluPerspective(45.0f, ((float) dim.width / (float) dim.height), 0.1f, 100.0f); // Calculate The Aspect Ratio Of The Window
		
		float cameraHeight = 17.0f;
		float cameraTilt = -10.0f;
		
		gluLookAt(
				// Be over the player
				((float)multiplayerMode.player.getX() / (float)Game.tileSize) - 0.5f, 
				((float)multiplayerMode.player.getY() / (float)Game.tileSize) + cameraTilt,
				cameraHeight,	// Eyes
				
				// Look at the player
				((float)multiplayerMode.player.getX() / (float)Game.tileSize) - 0.5f, 
				((float)multiplayerMode.player.getY() / (float)Game.tileSize) + 0.0f, 
				0.0f,	// Center
				
				0.0f, 0.0f, 1.0f	// Up
			);
	}
	
	public static Texture getItemsTexture()
	{
		return MultiplayerMode.itemsTexture;
	}
	
	public static Texture getTilesTexture()
	{
		return MultiplayerMode.tilesTexture;
	}
	
	public void finalize()
	{say("finalize");
		destroy();
	}
	
	public void destroy()
	{say("destroy");
		game.quit();
		
		if(multiplayerMode != null)
			multiplayerMode.quit();
		
		running = false;
		try {
			gameThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		super.destroy();
	}
	
	public void say(String msg)
	{
		System.out.println(msg);
	}
}
