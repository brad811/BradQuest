package render;

public class Plane extends Shape
{
	public Plane()
	{
		
	}
	
	public Plane(float scale)
	{
		super(scale);
	}
	
	public void initVBO()
	{
		float[] vertex_data_array = {
			//  x      y      z     nx     ny     nz   tx    ty
			// back quad
			 half,  half,  half,  0.0f,  0.0f,  1.0f,  right, down,
	        -half,  half,  half,  0.0f,  0.0f,  1.0f,  left, down,
	        -half, -half,  half,  0.0f,  0.0f,  1.0f,  left, up,
	         half, -half,  half,  0.0f,  0.0f,  1.0f,  right, up
	    };
		
		this.vertex_data_array = vertex_data_array;
	}
}
