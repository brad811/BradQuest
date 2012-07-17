package gui;

import main.Game;

public class GuiInGame extends GuiScreen
{
	GuiElementText fps, position;
	
	public GuiInGame(Gui gui)
	{
		this.gui = gui;
		
		elements.add(
				new GuiElementText(
						this, 1, 
						-210, 110,
						1, 1, 1, 1,
						"Seed: " + Game.seed, false
					)
			);
		
		fps = new GuiElementText(
					this, 2,
					-210, 100,
					1, 1, 1, 1,
					"FPS: ?", false
				);
		
		elements.add(fps);
		
		position = new GuiElementText(
				this, 3,
				-210, 90,
				1, 1, 1, 1,
				"", false
			);
		
		elements.add(position);
	}
	
	public void setFps(int f)
	{
		fps.text = "FPS: " + f;
	}
	
	public void setPosition(String name, float x, float y)
	{
		position.text = name + ": (" + x + "," + y + ")";
	}
}
