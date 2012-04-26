package main;

public class GeneratorThread implements Runnable
{
	Map map;
	
	public void start()
	{
		Thread th = new Thread(this);
		th.start();
	}
	
	public void setMap(Map m)
	{
		map = m;
	}
	
	@Override
	public void run()
	{
		map.generate();
	}

}