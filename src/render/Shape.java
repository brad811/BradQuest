package render;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import main.GameApplet;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

public class Shape
{
	float[] vertex_data_array;
	int offsetX = 0, offsetY = 0;
	float up, down, left, right;
	
	public void init(int offX, int offY)
	{
		offsetX = offX;
		offsetY = offY;
		
		up = (offsetY + 1.0f) / 16.0f;
		down = (offsetY) / 16.0f;
		left = (offsetX) / 16.0f;
		right = (offsetX + 1.0f) / 16.0f;
		
		// create our vertex buffer objects
		IntBuffer buffer = BufferUtils.createIntBuffer(1);
		GL15.glGenBuffers(buffer);
		
		int vertex_buffer_id = buffer.get(0);
		
		initVBO();
		
		FloatBuffer vertex_buffer_data = BufferUtils.createFloatBuffer(vertex_data_array.length);
		vertex_buffer_data.put(vertex_data_array);
		vertex_buffer_data.rewind();
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertex_buffer_id);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertex_buffer_data, GL15.GL_STATIC_DRAW);
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}
	
	public void initVBO()
	{
		
	}
	
	public void render()
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, GameApplet.getTilesTexture().getTextureID());
		
		// render the cube
		GL11.glVertexPointer(3, GL11.GL_FLOAT, 48, 0);
		GL11.glNormalPointer(GL11.GL_FLOAT, 48, 12);
		GL11.glColorPointer(4, GL11.GL_FLOAT, 48, 24);
		GL11.glTexCoordPointer(2, GL11.GL_FLOAT, 48, 40);
		GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);

		GL11.glDrawArrays(GL11.GL_QUADS, 0, vertex_data_array.length / 12);
	}
}
