package render;

public class Cube extends Shape
{
	public Cube()
	{
		
	}
	
	public Cube(float scale)
	{
		super(scale);
	}
	
	public void initVBO()
	{
		float[] vertex_data_array = {
			//  x      y      z     nx     ny     nz   tx    ty
			// back quad
			 half,  half,  half,  0.0f,  0.0f,  1.0f,  right, up,
	        -half,  half,  half,  0.0f,  0.0f,  1.0f,  left, up,
	        -half, -half,  half,  0.0f,  0.0f,  1.0f,  left, down,
	         half, -half,  half,  0.0f,  0.0f,  1.0f,  right, down,
	
	         // front quad
	         half,  half, -half,  0.0f,  0.0f, -1.0f,  right, up,
	        -half,  half, -half,  0.0f,  0.0f, -1.0f,  left, up,
	        -half, -half, -half,  0.0f,  0.0f, -1.0f,  left, down,
	         half, -half, -half,  0.0f,  0.0f, -1.0f,  right, down,
	         
	         // left quad
	        -half,  half, -half, -1.0f,  0.0f,  0.0f,  right, up,
	        -half,  half,  half, -1.0f,  0.0f,  0.0f,  left, up,
	        -half, -half,  half, -1.0f,  0.0f,  0.0f,  left, down,
	        -half, -half, -half, -1.0f,  0.0f,  0.0f,  right, down,
			
	        // right quad
	         half,  half, -half,  1.0f,  0.0f,  0.0f,  right, up,
	         half,  half,  half,  1.0f,  0.0f,  0.0f,  left, up,
	         half, -half,  half,  1.0f,  0.0f,  0.0f,  left, down,
	         half, -half, -half,  1.0f,  0.0f,  0.0f,  right, down,
	
	         // top quad
	        -half,  half, -half,  0.0f,  1.0f,  0.0f,  right, up,
	        -half,  half,  half,  0.0f,  1.0f,  0.0f,  left, up,
	         half,  half,  half,  0.0f,  1.0f,  0.0f,  left, down,
	         half,  half, -half,  0.0f,  1.0f,  0.0f,  right, down,
	
	         // bottom quad
	        -half, -half, -half,  0.0f, -1.0f,  0.0f,  right, up,
	        -half, -half,  half,  0.0f, -1.0f,  0.0f,  left, up,
	         half, -half,  half,  0.0f, -1.0f,  0.0f,  left, down,
	         half, -half, -half,  0.0f, -1.0f,  0.0f,  right, down
	    };
		
		this.vertex_data_array = vertex_data_array;
	}
}
