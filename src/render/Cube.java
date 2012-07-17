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
			// back quad (bottom)
			 half,  half,  half,  0.0f,  0.0f,  1.0f,  right, down,
	        -half,  half,  half,  0.0f,  0.0f,  1.0f,  left, down,
	        -half, -half,  half,  0.0f,  0.0f,  1.0f,  left, up,
	         half, -half,  half,  0.0f,  0.0f,  1.0f,  right, up,
	         
	         // front quad (top)
	         half,  half, -half,  0.0f,  0.0f, -1.0f,  right, down,
	        -half,  half, -half,  0.0f,  0.0f, -1.0f,  left, down,
	        -half, -half, -half,  0.0f,  0.0f, -1.0f,  left, up,
	         half, -half, -half,  0.0f,  0.0f, -1.0f,  right, up,
	         
	         // left quad 
	        -half,  half,  half, -1.0f,  0.0f,  0.0f,  right, down,
	        -half,  half, -half, -1.0f,  0.0f,  0.0f,  left, down,
	        -half, -half, -half, -1.0f,  0.0f,  0.0f,  left, up,
	        -half, -half,  half, -1.0f,  0.0f,  0.0f,  right, up,
	        
	        // right quad
	         half,  half,  half,  1.0f,  0.0f,  0.0f,  right, down,
	         half, -half,  half,  1.0f,  0.0f,  0.0f,  left, down,
	         half, -half, -half,  1.0f,  0.0f,  0.0f,  left, up,
	         half,  half, -half,  1.0f,  0.0f,  0.0f,  right, up,
	         
	         // top quad (back)
	         half,  half,  half,  0.0f,  1.0f,  0.0f,  right, down,
	        -half,  half,  half,  0.0f,  1.0f,  0.0f,  left, down,
	        -half,  half, -half,  0.0f,  1.0f,  0.0f,  left, up,
	         half,  half, -half,  0.0f,  1.0f,  0.0f,  right, up,
	         
	         // bottom quad (front)
	        half, -half,  half,  0.0f, -1.0f,  0.0f,  right, down,
	       -half, -half,  half,  0.0f, -1.0f,  0.0f,  left, down,
	       -half, -half, -half,  0.0f, -1.0f,  0.0f,  left, up,
	        half, -half, -half,  0.0f, -1.0f,  0.0f,  right, up
	    };
		
		this.vertex_data_array = vertex_data_array;
	}
}
