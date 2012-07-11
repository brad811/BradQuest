package gui;

public class GuiSinglePlayerMenu extends GuiScreen
{
	public GuiSinglePlayerMenu(Gui gui)
	{
		this.gui = gui;
		
		// add elements
		elements.add(
				new GuiElementButton(
						this, 0,
						-75, -60,
						150, 20,
						"Start Game"
					)
			);
		
		elements.add(
				new GuiElementText(
						this, 1,
						-75, 5,
						1, 1, 1, 1,
						"Name",
						false
					)
			);
		
		elements.add(
				new GuiElementTextInput(
						this, 1,
						-75, -20,
						150, 20,
						"Steve", false, true
					)
			);
	}
	
	public void handleClick(int id)
	{
		switch(id) {
			case 0: startGameClicked(); break;
		}
	}
	
	public void startGameClicked()
	{
		// start the server first!
		gui.gameApplet.multiplayerMode.startSinglePlayer();
	}
}
