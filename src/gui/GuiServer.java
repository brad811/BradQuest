package gui;

public class GuiServer extends GuiScreen
{
	static GuiElementText[] serverText;
	
	public GuiServer(Gui gui)
	{
		this.gui = gui;
		
		serverText = new GuiElementText[20];
		
		float height = 230, width = 417;
		elements.add(
				new GuiElementBox(
						this, serverText.length,
						-width/2.0f, -height/2.0f,
						width, height,
						0, 0, 0, 0.8f
					)
			);
		
		for(int i=0; i<serverText.length; i++)
		{
			serverText[i] = new GuiElementText(
					this, i, 
					-205, 105 - i*10,
					1, 1, 1, 1,
					"Server line " + i, false
				);
			
			elements.add(
					serverText[i]
				);
		}
	}
	
	public static void appendText(String s)
	{
		String[] lines = s.split("\n");
		
		for(int i=lines.length; i<serverText.length; i++)
		{
			serverText[i - lines.length].text = serverText[i].text;
		}
		
		for(int i=0; i<lines.length; i++)
		{
			serverText[serverText.length - lines.length + i].text = lines[i];
		}
	}
}
