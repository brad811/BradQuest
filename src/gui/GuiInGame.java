package gui;

public class GuiInGame extends GuiScreen
{
	public GuiInGame(Gui gui)
	{
		this.gui = gui;
		
		// add elements
		elements.add(new GuiElementText(this, 0, 0, 0, 1,1,1,0.5f, "Brad", true));
	}
}
