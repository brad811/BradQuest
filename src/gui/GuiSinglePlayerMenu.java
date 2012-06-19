package gui;

public class GuiSinglePlayerMenu extends GuiScreen
{
	public GuiSinglePlayerMenu(Gui gui)
	{
		this.gui = gui;
		
		// add elements
		elements.add(new GuiElementButton(this, 0, -75, -60, 150, 20, "Start Game"));
		elements.add(new GuiElementTextInput(this, 1, -75, -20, 150, 20, "testing"));
	}
	
	public void handleClick(int id)
	{
		switch(id) {
			case 0: startGameClicked(); break;
		}
	}
	
	public void startGameClicked()
	{
		gui.gameApplet.multiplayerMode.keepStarting();
	}
}
