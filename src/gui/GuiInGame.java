package gui;

import main.Game;

public class GuiInGame extends GuiScreen
{
	GuiElementText nameText;
	
	public GuiInGame(Gui gui)
	{
		this.gui = gui;
		
		nameText = new GuiElementText(
				this, 0, 
				0, 10,
				1, 1, 1, 0.5f,
				"Nobody", true
			);
		
		// add elements
		elements.add(
				nameText
			);
		
		elements.add(
				new GuiElementText(
						this, 1, 
						-210, 110,
						1, 1, 1, 1,
						"Seed " + Game.seed, false
					)
			); 
	}
	
	public void setName()
	{
		nameText.text = gui.gameApplet.multiplayerMode.player.name;
	}
}
