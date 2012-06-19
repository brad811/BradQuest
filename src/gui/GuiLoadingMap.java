package gui;

public class GuiLoadingMap extends GuiScreen
{
	public float loadedPercent;
	GuiElementBox loadingBar;
	
	public GuiLoadingMap(Gui gui)
	{
		this.gui = gui;
		
		int border = 4;
		int x = -100, y = 0, w = 200, h = 10;
		
		loadingBar = new GuiElementBox(this, 1, x, y, w, h, 0,0,0.8f,1);
		
		elements.add(new GuiElementBox(this, 0, x-border, y-border, w+border*2, h+border*2, 0.4f,0.4f,0.4f,1));
		elements.add(loadingBar);
	}
	
	public void setPercent(float percent)
	{
		loadingBar.w = percent * 200;
	}
}
