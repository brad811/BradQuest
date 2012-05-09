package render;

public class Plane extends Shape
{
	public void initVBO()
	{
		float[] vertex_data_array = {
			//  x      y      z     nx     ny     nz      r      g      b      a    tx    ty
			// back quad
			 1.0f,  1.0f,  1.0f,  0.0f,  0.0f,  1.0f,  1.0f,  1.0f,  1.0f,  1.0f,  right, up,
	        -1.0f,  1.0f,  1.0f,  0.0f,  0.0f,  1.0f,  1.0f,  1.0f,  1.0f,  1.0f,  left, up,
	        -1.0f, -1.0f,  1.0f,  0.0f,  0.0f,  1.0f,  1.0f,  1.0f,  1.0f,  1.0f,  left, down,
	         1.0f, -1.0f,  1.0f,  0.0f,  0.0f,  1.0f,  1.0f,  1.0f,  1.0f,  1.0f,  right, down,
	    };
		
		this.vertex_data_array = vertex_data_array;
	}
}
