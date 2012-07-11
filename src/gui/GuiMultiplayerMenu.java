package gui;

import main.MultiplayerMode;

public class GuiMultiplayerMenu extends GuiScreen
{
	GuiElementTextInput serverText;
	
	public GuiMultiplayerMenu(Gui gui)
	{
		this.gui = gui;
		
		// add elements
		elements.add(
				new GuiElementButton(
						this, 0,
						-75, -60,
						150, 20,
						"Join Server"
					)
			);
		
		elements.add(
				new GuiElementText(
						this, 1,
						-75, 5,
						1, 1, 1, 1,
						"Server",
						false
					)
			);
		
		serverText = new GuiElementTextInput(
				this, 1,
				-75, -20,
				150, 20,
				"bradsproject.com", false, true
			);
		
		elements.add(
				serverText
			);
	}
	
	public void handleClick(int id)
	{
		switch(id) {
			case 0: joinServerClicked(); break;
		}
	}
	
	public void joinServerClicked()
	{
		MultiplayerMode.server = serverText.value;
		gui.gameApplet.multiplayerMode.keepStarting(false);
	}
}
