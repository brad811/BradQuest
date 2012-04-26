package main;

import org.lwjgl.opengl.GL11;

public class Renderer
{
	public static int[] tiles = new int[128];
	public static int
		tree,
		player;
	
	static float tileDivider = 16.001f;
	
	public static void buildTile(int id, int tileX, int tileY)
	{
		tiles[id] = GL11.glGenLists(1); // Generate 2 Different Lists
		GL11.glNewList(tiles[id],GL11.GL_COMPILE); // Start With The Box List
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, GameApplet.getTilesTexture().getTextureID());
		
		GL11.glColor3f(1.0f,1.0f,1.0f);
		
		drawTexturedPlane(tileX, tileY, 0.5f, 0.5f, 0.0f);
		
		GL11.glEndList();
	}
	
	public static void buildTree()
	{
		tree = GL11.glGenLists(1); // Generate 2 Different Lists
		GL11.glNewList(tree,GL11.GL_COMPILE); // Start With The Box List
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, GameApplet.getTilesTexture().getTextureID());
		
		// Trunk
		int tileX = 4, tileY = 0;
		GL11.glTranslatef(0.0f, 0.0f, 0.5f);
		drawTexturedBox(tileX, tileY, 1.0f, 0.5f, 0.5f, 1.0f);
		
		// Leaves
		tileX = 6;
		tileY = 0;
		GL11.glTranslatef(0.0f, -1.0f, 0.0f);
		drawTexturedBox(tileX, tileY, 0.8f, 1.0f, 1.0f, 1.0f);
		
		GL11.glEndList();
	}
	
	public static void buildPlayer()
	{
		player = GL11.glGenLists(1); // Generate 2 Different Lists
		GL11.glNewList(player,GL11.GL_COMPILE); // Start With The Box List
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		
		GL11.glBegin(GL11.GL_QUADS);
		
		GL11.glColor3f(1.0f,0.8f,0.6f); // peach?
		GL11.glTranslatef(0.0f, 0.0f, 0.5f);
		Renderer.drawBox(0.5f, 1.0f, 1.0f, 1.0f);
		
		GL11.glEnd();
		
		GL11.glEndList();
	}
	
	public static void drawTexturedPlane(int tileX, int tileY, float x, float y, float z)
	{
		float 	up = (tileY + 1.0f) / tileDivider,
				down = (tileY) / tileDivider,
				left = (tileX) / tileDivider,
				right = (tileX + 1.0f) / tileDivider;
		
		GL11.glBegin(GL11.GL_QUADS);
		
		GL11.glTexCoord2f(right,down);
		GL11.glVertex3f(-x,y,z);
		
		GL11.glTexCoord2f(right,up);
		GL11.glVertex3f(-x,-y,z);
		
		GL11.glTexCoord2f(left,up);
		GL11.glVertex3f(x,-y,z);
		
		GL11.glTexCoord2f(left,down);
		GL11.glVertex3f(x,y,z);
		
		GL11.glEnd();
	}
	
	public static void drawTexturedBox(int tileX, int tileY, float scale, float width, float height, float depth)
	{
		float w = width * scale / 2;
		float h = height * scale / 2;
		float d = depth * scale / 2;
		
		GL11.glBegin(GL11.GL_QUADS);
		
		// Top
		GL11.glRotatef(90, 1.0f, 0.0f, 0.0f);
		drawTexturedPlane(tileX, tileY, w, h, d);
		
		// Bottom
		drawTexturedPlane(tileX, tileY, w, h, -d);
		
		// Left
		GL11.glRotatef(90, -1.0f, 0.0f, 0.0f);
		GL11.glRotatef(90, 0.0f, -1.0f, 0.0f);
		drawTexturedPlane(tileX, tileY, h, d, w);
		
		// Right
		//GL11.glRotatef(180, 0.0f, 0.0f, 1.0f);
		GL11.glTranslatef(0.0f, 0.0f, -w*2);
		drawTexturedPlane(tileX, tileY, h, d, w);
		
		// Front
		GL11.glRotatef(90, 0.0f, 1.0f, 0.0f);
		GL11.glTranslatef(-w*2, 0.0f, -h*2);
		drawTexturedPlane(tileX, tileY, w, d, h);
		
		// Back
		GL11.glTranslatef(0.0f, 0.0f, h*2);
		drawTexturedPlane(tileX, tileY, w, d, h);
		
		GL11.glEnd();
	}
	
	public static void drawBox(float scale, float width, float height, float depth)
	{
		float w = width * scale / 2;
		float h = height * scale / 2;
		float d = depth * scale / 2;
		
		GL11.glBegin(GL11.GL_QUADS);
		
		// Top
		GL11.glVertex3f(-w,d,h);
		GL11.glVertex3f(w,d,h);
		GL11.glVertex3f(w,-d,h);
		GL11.glVertex3f(-w,-d,h);
		
		// Bottom
		GL11.glVertex3f(-w,d,-h);
		GL11.glVertex3f(w,d,-h);
		GL11.glVertex3f(w,-d,-h);
		GL11.glVertex3f(-w,-d,-h);
		
		// Left
		GL11.glVertex3f(-w,d,h);
		GL11.glVertex3f(-w,d,-h);
		GL11.glVertex3f(-w,-d,-h);
		GL11.glVertex3f(-w,-d,h);
		
		// Right
		GL11.glVertex3f(w,d,h);
		GL11.glVertex3f(w,d,-h);
		GL11.glVertex3f(w,-d,-h);
		GL11.glVertex3f(w,-d,h);
		
		// Front
		GL11.glVertex3f(-w,-d,h);
		GL11.glVertex3f(-w,-d,-h);
		GL11.glVertex3f(w,-d,-h);
		GL11.glVertex3f(w,-d,h);
		
		// Back
		GL11.glVertex3f(-w,d,h);
		GL11.glVertex3f(-w,d,-h);
		GL11.glVertex3f(w,d,-h);
		GL11.glVertex3f(w,d,h);
		
		GL11.glEnd();
	}
}
