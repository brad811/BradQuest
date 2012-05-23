package render;

public class Shape
{
	public float[] vertex_data_array;
	public int offsetX = 0, offsetY = 0;
	float up, down, left, right;
	
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
		
		initVBO(); // populates vertex_data_array
	}
	
	public void initVBO()
	{
	}
	
	public float[] getVertexArray()
	{
		return vertex_data_array;
	}
}
