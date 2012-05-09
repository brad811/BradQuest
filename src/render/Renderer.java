package render;

import main.GameApplet;

import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

public class Renderer
{
	public static int
		tree,
		player;
	
	public static Plane buildTile(int id, int tileX, int tileY)
	{
		glBindTexture(GL11.GL_TEXTURE_2D, GameApplet.getTilesTexture().getTextureID());
		
		Plane plane = new Plane();
		plane.init(tileX, tileY);
		
		return plane;
	}
	
	public static void buildTree()
	{
		tree = glGenLists(1); // Generate 2 Different Lists
		glNewList(tree,GL11.GL_COMPILE); // Start With The Box List
		
		glBindTexture(GL11.GL_TEXTURE_2D, GameApplet.getTilesTexture().getTextureID());
		
		// Trunk
		int tileX = 4, tileY = 0;
		glTranslatef(0.0f, 0.0f, 0.5f);
		//drawTexturedBox(tileX, tileY, 1.0f, 0.5f, 0.5f, 1.0f);
		
		// Leaves
		tileX = 6;
		tileY = 0;
		glTranslatef(0.0f, 1.0f, 0.0f);
		//glRotatef(180, 0.0f, 0.0f, 1.0f);
		//drawTexturedBox(tileX, tileY, 0.8f, 1.0f, 1.0f, 1.0f);
		
		glEndList();
	}
	
	public static void buildPlayer()
	{
		player = glGenLists(1); // Generate 2 Different Lists
		glNewList(player,GL11.GL_COMPILE); // Start With The Box List
		
		glBindTexture(GL11.GL_TEXTURE_2D, 0);
		
		glBegin(GL11.GL_QUADS);
		
		glColor3f(1.0f,0.8f,0.6f); // peach?
		glTranslatef(0.0f, 0.0f, 0.5f);
		//Renderer.drawBox(0.5f, 1.0f, 1.0f, 1.0f);
		
		glEnd();
		
		glEndList();
	}
}
