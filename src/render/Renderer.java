package render;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;

import main.GameApplet;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

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
		ArrayList<Shape> player = new ArrayList<Shape>();
		glBindTexture(GL_TEXTURE_2D, GameApplet.getTilesTexture().getTextureID());
		
		// Head
		int tileX = 3, tileY = 0;
		Cube head = new Cube();
		head.init(tileX, tileY);
		player.add(head);
		
		// Torso
		tileX = 1;
		tileY = 0;
		Cube torso = new Cube();
		torso.init(tileX, tileY);
		player.add(torso);
		
		return player;
	}
	
	
	public static ArrayList<float[]> renderList = new ArrayList<float[]>();
	static float[] vertex_data_array;
	static FloatBuffer vertex_buffer_data;
	static int vertex_buffer_id;
	
	public static void init()
	{
		IntBuffer buffer = BufferUtils.createIntBuffer(1);
		glGenBuffers(buffer);
		
		vertex_buffer_id = buffer.get(0);
		
		
		glEnable(GL_TEXTURE_2D);
		
		glEnableClientState(GL_VERTEX_ARRAY);
		glEnableClientState(GL_NORMAL_ARRAY);
		glEnableClientState(GL_TEXTURE_COORD_ARRAY);
	}
	
	public static void addTile(float[] v, float x, float y, float z, float sx, float sy, float sz)
	{
		float[] array = v.clone();
		
		for(int i=0; i<array.length; i+=6)
		{
			array[i] *= sx;
			array[i+1] *= sy;
			array[i+2] *= sz;
			
			array[i] += x;
			array[++i] += y;
			array[++i] += z;
		}
		
		renderList.add(array);
	}
	
	public static void renderMap()
	{
		if(renderList.size() == 0)
			return;
		
		int arraySize = 0;
		for(int i=0; i<renderList.size(); i++)
		{
			arraySize += renderList.get(i).length;
		}
		
		vertex_data_array = new float[arraySize];
		
		int count = 0;
		for(int i=0; i<renderList.size(); i++)
		{
			for(int j=0; j<renderList.get(i).length; j++)
			{
				vertex_data_array[count++] = renderList.get(i)[j];
			}
		}
		
		vertex_buffer_data = BufferUtils.createFloatBuffer(vertex_data_array.length);
		vertex_buffer_data.put(vertex_data_array);
		vertex_buffer_data.rewind();
		
		glBindBuffer(GL_ARRAY_BUFFER, vertex_buffer_id);
		glBufferData(GL_ARRAY_BUFFER, vertex_buffer_data, GL_STATIC_DRAW);
		
		glVertexPointer(3, GL_FLOAT, 32, 0);
		glNormalPointer(GL_FLOAT, 32, 12);
		glTexCoordPointer(2, GL_FLOAT, 32, 24);
		
		glDrawArrays(GL_QUADS, 0, vertex_data_array.length / 8);
		
		renderList.clear();
	}
}
