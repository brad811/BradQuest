package items;

import static org.lwjgl.opengl.GL11.*;

import main.Game;
import main.Map;
import main.MultiplayerMode;

// An ItemEntity is an instance of an item that is currently
// not in an inventory, such as an item on the ground
public class ItemEntity
{
	public int spriteX;
	public int spriteY;
	public String name;
	public int type;
	
	public Map map;
	public float x;
	public float y;
	
	public int entityId = 1;
	
	public Item item;
	
	float size = 1.0f;
	float half = size/2;
	
	int pickupDelay = Game.TPS*2; // half a second worth of ticks
	
	public ItemEntity(Map map, float x, float y, Item item, int entityId)
	{
		this.map = map;
		
		this.item = item;
		
		this.x = x;
		this.y = y;
		
		this.entityId = entityId;
	}
	
	public ItemEntity(Map map, float x, float y, Item item)
	{
		this.map = map;
		this.x = x;
		this.y = y;
		
		this.item = item;
		
		entityId = Game.entityIdCounter++;
	}
	
	public void tick()
	{
		if(pickupDelay > 0)
		{
			pickupDelay--;
		}
		
		// do some bounce animation if there's any left to be done
		
		// should the pickup animation be here? will there be one?
		
		// Client.out.println("item|"+name+"|"+entityId+"|"+x+"|"+y);
	}
	
	public Item pickup()
	{
		// check if already being picked up
		
		if(pickupDelay > 0)
			return null;
		
		map.removeItemEntity(entityId);
		
		// Client.out.println("item remove|"+entityId);
		
		return item;
	}
	
	public void render(float playerX, float playerY)
	{
		glLoadIdentity(); // Reset The View
		
		glTranslatef(
				x - playerX,
				y - playerY,
				0.0f
			); // Move down into position
		
		glRotatef(-45.0f, 1.0f, 0.0f, 0.0f); // Tilt 45 degrees back
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glBindTexture(GL_TEXTURE_2D, MultiplayerMode.itemsTexture.getTextureID());
		
		float blah = 16.0f;
		
		glBegin(GL_QUADS);
		glTexCoord2f(0,0);				glVertex3f(-half,0,-half);
		glTexCoord2f(1f/blah,0);		glVertex3f(half,0,-half);
		glTexCoord2f(1f/blah,1f/blah);	glVertex3f(half,0,half);
		glTexCoord2f(0,1f/blah);		glVertex3f(-half,0,half);
		glEnd();
		
		//Cylinder log = new Cylinder();
		//log.draw(size/4,size/4,size,8,8);
	}
}
