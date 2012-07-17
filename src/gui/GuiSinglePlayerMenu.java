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
						-75, -40,
						150, 20,
						"Start Game"
					)
			);
		
		elements.add(
				new GuiElementText(
						this, 1,
						-75, 25,
						1, 1, 1, 1,
						"World name",
						false
					)
			);
		
		elements.add(
				new GuiElementTextInput(
						this, 2,
						-75, 10,
						150, 20,
						"Not added yet!", false, true
					)
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
			case 0: startGameClicked(); break;
			case 3: backClicked(); break;
		}
	}
	
	private void startGameClicked()
	{
		// start the server first!
		gui.gameApplet.multiplayerMode.startSinglePlayer();
	}
	
	private void backClicked()
	{
		reset();
		gui.setScreen(Gui.GUI_MAIN_MENU);
	}
	
	private void reset()
	{
		//serverText.value = serverTextDefault;
	}
}
