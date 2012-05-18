package main;

import java.util.HashMap;

import org.lwjgl.input.Keyboard;

public class Input
{
	public Input()
	{
		keys.put("up",false);
		keys.put("left",false);
		keys.put("down",false);
		keys.put("right",false);
		
		keys.put("z",false);
		keys.put("x",false);
		
		keys.put("shift",false);
	}
	
	HashMap<String,Boolean> keys = new HashMap<String,Boolean>();
	
	public void toggle(String key, boolean pressed)
	{
		keys.put(key, pressed);
	}
	
	public void getKeys()
	{
		try {
			if(Keyboard.isKeyDown(Keyboard.KEY_UP)) { toggle("up",true); }
			else { toggle("up",false); }
			
			if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) { toggle("left",true); }
			else { toggle("left",false); }
			
			if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)) { toggle("down",true); }
			else { toggle("down",false); }
			
			if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) { toggle("right",true); }
			else { toggle("right",false); }
			
			if(Keyboard.isKeyDown(Keyboard.KEY_Z)) { toggle("z",true); }
			else { toggle("z",false); }
			
			if(Keyboard.isKeyDown(Keyboard.KEY_X)) { toggle("x",true); }
			else { toggle("x",false); }
			
			if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) { toggle("shift",true); }
			else { toggle("shift",false); }
		} catch (IllegalStateException e)
		{
			// Keyboard not ready yet
		}
	}
}
