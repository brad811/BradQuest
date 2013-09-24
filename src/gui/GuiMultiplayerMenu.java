package gui;

import main.MultiplayerMode;

public class GuiMultiplayerMenu extends GuiScreen
{
	GuiElementTextInput serverText;
	String serverTextDefault = "localhost";
	
	public GuiMultiplayerMenu(Gui gui)
	{
		this.gui = gui;
		
		// add elements
		elements.add(
				new GuiElementButton(
						this, 0,
						-75, -40,
						150, 20,
						"Join Server"
					)
			);
		
		elements.add(
				new GuiElementText(
						this, 1,
						-75, 25,
						1, 1, 1, 1,
						"Server",
						false
					)
			);
		
		serverText = new GuiElementTextInput(
				this, 2,
				-75, 10,
				150, 20,
				serverTextDefault, false, true
			);
		
		elements.add(
				serverText
			);
		
		elements.add(
				new GuiElementButton(
						this, 3,
						-75, -70,
						150, 20,
						"Back"
					)
			);
	}
	
	public void handleClick(int id)
	{
		switch(id) {
			case 0: joinServerClicked(); break;
			case 3: backClicked(); break;
		}
	}
	
	private void joinServerClicked()
	{
		MultiplayerMode.server = serverText.value;
		gui.gameApplet.multiplayerMode.keepStarting(false);
	}
	
	private void backClicked()
	{
		reset();
		gui.setScreen(Gui.GUI_MAIN_MENU);
	}
	
	private void reset()
	{
		serverText.value = serverTextDefault;
	}
}
