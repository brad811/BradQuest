package render;

import main.GameApplet;

import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

public class Renderer
{
	public static int[] tiles = new int[128];
	public static int
		tree,
		player;
	
	static float tileDivider = 16.001f;
	
	public static void buildTile(int id, int tileX, int tileY)
	{
		tiles[id] = glGenLists(1); // Generate 2 Different Lists
		glNewList(tiles[id],GL11.GL_COMPILE); // Start With The Box List
		
		glBindTexture(GL11.GL_TEXTURE_2D, GameApplet.getTilesTexture().getTextureID());
		
		glColor3f(1.0f,1.0f,1.0f);
		
		drawTexturedPlane(tileX, tileY, 0.5f, 0.5f, 0.0f);
		
		glEndList();
	}
	
	public static void buildTree()
	{
		tree = glGenLists(1); // Generate 2 Different Lists
		glNewList(tree,GL11.GL_COMPILE); // Start With The Box List
		
		glBindTexture(GL11.GL_TEXTURE_2D, GameApplet.getTilesTexture().getTextureID());
		
		// Trunk
		int tileX = 4, tileY = 0;
		glTranslatef(0.0f, 0.0f, 0.5f);
		drawTexturedBox(tileX, tileY, 1.0f, 0.5f, 0.5f, 1.0f);
		
		// Leaves
		tileX = 6;
		tileY = 0;
		glTranslatef(0.0f, 1.0f, 0.0f);
		//glRotatef(180, 0.0f, 0.0f, 1.0f);
		drawTexturedBox(tileX, tileY, 0.8f, 1.0f, 1.0f, 1.0f);
		
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
		Renderer.drawBox(0.5f, 1.0f, 1.0f, 1.0f);
		
		glEnd();
		
		glEndList();
	}
	
	public static void drawTexturedPlane(int tileX, int tileY, float x, float y, float z)
	{
		//glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
		//glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);
		
		float 	up = (tileY + 1.0f) / tileDivider,
				down = (tileY) / tileDivider,
				left = (tileX) / tileDivider,
				right = (tileX + 1.0f) / tileDivider;
		
		glBegin(GL11.GL_QUADS);
		
		glTexCoord2f(right,down);
		glVertex3f(-x,y,z);
		
		glTexCoord2f(right,up);
		glVertex3f(-x,-y,z);
		
		glTexCoord2f(left,up);
		glVertex3f(x,-y,z);
		
		glTexCoord2f(left,down);
		glVertex3f(x,y,z);
		
		glEnd();
	}
	
	public static void drawTexturedBox(int tileX, int tileY, float scale, float width, float height, float depth)
	{
		float w = width * scale / 2;
		float h = height * scale / 2;
		float d = depth * scale / 2;
		
		glBegin(GL11.GL_QUADS);
		
		// Top
		glRotatef(90, 1.0f, 0.0f, 0.0f);
		drawTexturedPlane(tileX, tileY, w, h, d);
		
		// Bottom
		drawTexturedPlane(tileX, tileY, w, h, -d);
		
		// Left
		glRotatef(90, -1.0f, 0.0f, 0.0f);
		glRotatef(90, 0.0f, -1.0f, 0.0f);
		drawTexturedPlane(tileX, tileY, h, d, w);
		
		// Right
		glRotatef(180, 0.0f, 0.0f, 1.0f);
		glTranslatef(0.0f, 0.0f, -w*2);
		drawTexturedPlane(tileX, tileY, h, d, w);
		
		// Front
		glRotatef(90, 0.0f, 1.0f, 0.0f);
		glTranslatef(-w*2, 0.0f, -h*2);
		drawTexturedPlane(tileX, tileY, w, d, h);
		
		// Back
		glTranslatef(0.0f, 0.0f, h*2);
		drawTexturedPlane(tileX, tileY, w, d, h);
		
		glEnd();
	}
	
	public static void drawBox(float scale, float width, float height, float depth)
	{
		float w = width * scale / 2;
		float h = height * scale / 2;
		float d = depth * scale / 2;
		
		glBegin(GL11.GL_QUADS);
		
		// Top
		glVertex3f(-w,d,h);
		glVertex3f(w,d,h);
		glVertex3f(w,-d,h);
		glVertex3f(-w,-d,h);
		
		// Bottom
		glVertex3f(-w,d,-h);
		glVertex3f(w,d,-h);
		glVertex3f(w,-d,-h);
		glVertex3f(-w,-d,-h);
		
		// Left
		glVertex3f(-w,d,h);
		glVertex3f(-w,d,-h);
		glVertex3f(-w,-d,-h);
		glVertex3f(-w,-d,h);
		
		// Right
		glVertex3f(w,d,h);
		glVertex3f(w,d,-h);
		glVertex3f(w,-d,-h);
		glVertex3f(w,-d,h);
		
		// Front
		glVertex3f(-w,-d,h);
		glVertex3f(-w,-d,-h);
		glVertex3f(w,-d,-h);
		glVertex3f(w,-d,h);
		
		// Back
		glVertex3f(-w,d,h);
		glVertex3f(-w,d,-h);
		glVertex3f(w,d,-h);
		glVertex3f(w,d,h);
		
		glEnd();
	}
}
