package gui;

import main.GameApplet;

public class GuiLogin extends GuiScreen
{
	GuiElementTextInput username;
	
	public GuiLogin(Gui gui)
	{
		this.gui = gui;
		
		// add elements
		elements.add(
				new GuiElementButton(
						this, 0,
						-75, -60,
						150, 20,
						"Login"
					)
			);
		
		elements.add(
				new GuiElementText(
						this, 1,
						-75, 45,
						1, 1, 1, 1,
						"Username",
						false
					)
			);
		
		username = new GuiElementTextInput(
				this, 1,
				-75, 20,
				150, 20,
				"", false, true
			);
		
		elements.add(
				username
			);
		
		elements.add(
				new GuiElementText(
						this, 1,
						-75, 5,
						1, 1, 1, 1,
						"Password",
						false
					)
			);
		
		elements.add(
				new GuiElementTextInput(
						this, 1,
						-75, -20,
						150, 20,
						"", true, false
					)
			);
	}
	
	public void handleClick(int id)
	{
		switch(id) {
			case 0: loginClicked(); break;
		}
	}
	
	public void loginClicked()
	{
		// check that login credentials are legit
		// save the user somewhere? should it be its own class?
		GameApplet.username = username.value;
		
		gui.setScreen(Gui.GUI_MAIN_MENU);
	}
}
