package main;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.opengl.Texture;

import client.Client;

public class GameApplet extends Applet
{
	private static final long serialVersionUID = 1L;
	
	public Menu menu;
	
	Dimension dim = new Dimension(640, 480);
	
	Graphics bufferGraphics;
	Image offscreen;
	
	public Game game;
	
	long lastFrameTime; // used to calculate delta
	public boolean closeRequested = false;
	
	Canvas display_parent;
	Thread gameThread;
	boolean running = false;
	
	public void init()
	{say("init");
		setSize(dim);
		
		game = new Game();
		MultiplayerMode.gameApplet = this;
		ServerMode.gameApplet = this;
		menu = new Menu(this);
		menu.mainMenu();
	}
	
	public void start()
	{
		
	}
	
	public void startLWJGL()
	{say("startLWJGL");
		gameThread = new Thread() {
			public void run()
			{
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
			}
		};
		gameThread.start();
	}
	
	private void stopLWJGL()
	{say("stopLWJGL");
		running = false;
		try {
			gameThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void gameLoop()
	{say("gameLoop");
		
		MultiplayerMode.init();
		game.getPlayer().init();
		
		while(running)
		{
			render();
			Display.sync(Game.FPS);
			Display.update();
		}
		
		Display.destroy();
	}
	
	public void say(String msg)
	{
		System.out.println(msg);
	}
	
	public void run()
	{say("run");
		setLayout(new BorderLayout());
		try {
			display_parent = new Canvas() {
				private static final long serialVersionUID = 1L;
				
				public final void addNotify() {
					super.addNotify();
				}
				public final void removeNotify() {
					stopLWJGL();
					super.removeNotify();
				}
			};
			display_parent.setSize(dim.width,dim.height);
			add(display_parent);
			display_parent.setFocusable(true);
			display_parent.requestFocus();
			//display_parent.setIgnoreRepaint(true);
			setVisible(true);
		} catch (Exception e) {
			System.err.println(e);
			throw new RuntimeException("Unable to create display");
		}
		
		startLWJGL();
	}
	
	private void initGL()
	{say("initGL");
		/* OpenGL */
		
		try
		{
			Display.setDisplayMode(new DisplayMode(dim.width, dim.height));
		} catch (LWJGLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int width = Display.getDisplayMode().getWidth();
		int height = Display.getDisplayMode().getHeight();
		
		GL11.glViewport(0, 0, width, height); // Reset The Current Viewport
		GL11.glMatrixMode(GL11.GL_PROJECTION); // Select The Projection Matrix
		GL11.glLoadIdentity(); // Reset The Projection Matrix
		
		GL11.glMatrixMode(GL11.GL_MODELVIEW); // Select The Modelview Matrix
		GL11.glLoadIdentity(); // Reset The Modelview Matrix
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glShadeModel(GL11.GL_SMOOTH); // Enables Smooth Shading
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // Black Background
		GL11.glClearDepth(1.0f); // Depth Buffer Setup
		GL11.glEnable(GL11.GL_DEPTH_TEST); // Enables Depth Testing
		GL11.glDepthFunc(GL11.GL_LEQUAL); // The Type Of Depth Test To Do
		GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST); // Really Nice Perspective Calculations
		
		
		
		float lightLevel = 0.8f;
		FloatBuffer lightAmbient = BufferUtils.createFloatBuffer(4).put(new float[] { lightLevel, lightLevel, lightLevel, 1.0f });
		FloatBuffer lightDiffuse = BufferUtils.createFloatBuffer(4).put(new float[] { 1.0f, 1.0f, 1.0f, 1.0f });
		FloatBuffer lightPosition = BufferUtils.createFloatBuffer(4).put(new float[] { 0.0f, 0.0f, 2.0f, 1.0f });
		
		lightAmbient.flip();
		lightDiffuse.flip();
		lightPosition.flip();
		
	    GL11.glLight(GL11.GL_LIGHT1, GL11.GL_AMBIENT, lightAmbient); // Setup The Ambient Light
	    GL11.glLight(GL11.GL_LIGHT1, GL11.GL_DIFFUSE, lightDiffuse); // Setup The Diffuse Light
	    GL11.glLight(GL11.GL_LIGHT1, GL11.GL_POSITION, lightPosition); // Position The Light
	    GL11.glEnable(GL11.GL_LIGHT1); // Enable Light One
	    
	    GL11.glEnable(GL11.GL_COLOR_MATERIAL);
	    GL11.glColorMaterial(GL11.GL_FRONT_AND_BACK, GL11.GL_AMBIENT_AND_DIFFUSE);
	    GL11.glEnable(GL11.GL_LIGHTING);
	}
	
	public static Texture getItemsTexture()
	{
		return MultiplayerMode.itemsTexture;
	}
	
	public static Texture getTilesTexture()
	{
		return MultiplayerMode.tilesTexture;
	}
	
	public void render()
	{
		updateCamera();
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); // Clear The Screen And The Depth Buffer
		
		if(!Client.loaded)
		{
			display_parent.setIgnoreRepaint(false);
			//game.multiplayerMode.paint(bufferGraphics, dim.width, dim.height);
		}
		else
		{
			display_parent.setIgnoreRepaint(true);
			game.multiplayerMode.render(dim.width, dim.height);
		}
	}
	
	public void updateCamera()
	{
		GL11.glMatrixMode(GL11.GL_PROJECTION); // Select The Projection Matrix
		GL11.glLoadIdentity(); // Reset The Projection Matrix
		GLU.gluPerspective(45.0f, ((float) 640 / (float) 480), 0.1f, 100.0f); // Calculate The Aspect Ratio Of The Window
		
		float cameraHeight = 17.0f;
		float cameraTilt = -10.0f;
		
		GLU.gluLookAt(
				// Be over the player
				(game.getPlayer().getX() / Game.tileSize) - 0.5f, (game.getPlayer().getY() / Game.tileSize) + cameraTilt, cameraHeight,	// Eyes
				
				// Look at the player
				(game.getPlayer().getX() / Game.tileSize) - 0.5f, (game.getPlayer().getY() / Game.tileSize) + 0.0f, 0.0f,	// Center
				
				0.0f, 0.0f, 1.0f	// Up
			);
		
		GL11.glMatrixMode(GL11.GL_MODELVIEW); // Select The Modelview Matrix
		GL11.glLoadIdentity(); // Reset The Modelview Matrix
	}
	
	public void destroy()
	{
		//Display.destroy();
		game.quit();
	}
	
	public void finalize()
	{
		destroy();
	}
}
