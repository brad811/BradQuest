package render;

import java.nio.FloatBuffer;

import java.util.ArrayList;

import main.GameApplet;

import static org.lwjgl.opengl.GL11.*;

public class Renderer
{
	public static Plane buildTile(int id, int tileX, int tileY)
	{
		glBindTexture(GL_TEXTURE_2D, GameApplet.getTilesTexture().getTextureID());
		
		Plane plane = new Plane();
		plane.init(tileX, tileY);
		
		return plane;
	}
	
	public static ArrayList<Shape> buildTree()
	{
		ArrayList<Shape> tree = new ArrayList<Shape>();
		glBindTexture(GL_TEXTURE_2D, GameApplet.getTilesTexture().getTextureID());
		
		// Trunk
		int tileX = 4, tileY = 0;
		Cube trunk = new Cube();
		trunk.init(tileX, tileY);
		tree.add(trunk);
		
		// Leaves
		tileX = 6;
		tileY = 0;
		Cube leaves = new Cube();
		leaves.init(tileX, tileY);
		tree.add(leaves);
		
		return tree;
	}
	
	public static ArrayList<Shape> buildPlayer()
	{
		ArrayList<Shape> tree = new ArrayList<Shape>();
		glBindTexture(GL_TEXTURE_2D, GameApplet.getTilesTexture().getTextureID());
		
		// Head
		int tileX = 3, tileY = 0;
		Cube head = new Cube();
		head.init(tileX, tileY);
		tree.add(head);
		
		// Torso
		tileX = 1;
		tileY = 0;
		Cube torso = new Cube();
		torso.init(tileX, tileY);
		tree.add(torso);
		
		return tree;
	}
}
