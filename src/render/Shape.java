package render;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

public class Shape
{
	float[] vertex_data_array;
	public int offsetX = 0, offsetY = 0;
	float up, down, left, right;
	
	int vertex_buffer_id;
	FloatBuffer vertex_buffer_data;
	
	float size = 1.01f;
	float half = size/2;
	
	float scale = 1.0f;
	
	public Shape()
	{
		
	}
	
	public Shape(float scale)
	{
		this.scale = scale;
	}
	
	public void init(int offX, int offY)
	{
		offsetX = offX;
		offsetY = offY;
		
		up = (offsetY + 1.0f) / 16.2f;
		down = (offsetY) / 16.0f;
		left = (offsetX) / 16.0f;
		right = (offsetX + 1.0f) / 16.0f;
		
		// create our vertex buffer objects
		IntBuffer buffer = BufferUtils.createIntBuffer(1);
		glGenBuffers(buffer);
		
		vertex_buffer_id = buffer.get(0);
		
		initVBO(); // populates vertex_buffer_data
		
		vertex_buffer_data = BufferUtils.createFloatBuffer(vertex_data_array.length);
		vertex_buffer_data.put(vertex_data_array);
		vertex_buffer_data.rewind();
		
		glEnable(GL_TEXTURE_2D);
		glBindBuffer(GL_ARRAY_BUFFER, vertex_buffer_id);
		glBufferData(GL_ARRAY_BUFFER, vertex_buffer_data, GL_STATIC_DRAW);
		
		glEnableClientState(GL_VERTEX_ARRAY);
		glEnableClientState(GL_NORMAL_ARRAY);
		glEnableClientState(GL_TEXTURE_COORD_ARRAY);
	}
	
	public void initVBO()
	{
	}
	
	public void render(float scaleX, float scaleY, float scaleZ)
	{
		glScalef(scaleX, scaleY, scaleZ);
		render();
	}
	
	public float[] getVertexArray()
	{
		return vertex_data_array;
	}
	
	public void render()
	{
		glPushMatrix();
		
		if(this.scale != 1.0f)
			glScalef(scale, scale, scale);
		
		glBindBuffer(GL_ARRAY_BUFFER, vertex_buffer_id);
		
		glVertexPointer(3, GL_FLOAT, 32, 0);
		glNormalPointer(GL_FLOAT, 32, 12);
		glTexCoordPointer(2, GL_FLOAT, 32, 24);
		
		glDrawArrays(GL_QUADS, 0, vertex_data_array.length / 8);
		
		glPopMatrix();
	}
}
