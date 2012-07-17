package gui;

import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;
import java.util.HashMap;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class FontRenderer
{
	static float charSize = 1.0f / 16.0f;
	
	public static Texture fontTexture;
	
	static HashMap<Character, Float> letterWidth = new HashMap<Character,Float>();
	
	public static void init()
	{
		try
		{
			if(fontTexture == null)
				fontTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("font2.png"));
			
			glBindTexture(GL_TEXTURE_2D, fontTexture.getTextureID());
			
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
			
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_BORDER_COLOR, 0.0f);
		} catch (IOException e)
		{
			System.out.println("Unable to load font texture!");
		}
		
		letterWidth.put(' ',0.75f);
		letterWidth.put('!',0.25f);
		letterWidth.put('"',0.50f);
		letterWidth.put('#',0.75f);
		letterWidth.put('$',0.75f);
		letterWidth.put('%',0.75f);
		letterWidth.put('&',0.75f);
		letterWidth.put('\'',0.75f);
		letterWidth.put('(',0.50f);
		letterWidth.put(')',0.50f);
		letterWidth.put('*',0.50f);
		letterWidth.put('+',0.75f);
		letterWidth.put(',',0.50f);
		letterWidth.put('-',0.50f);
		letterWidth.put('.',0.25f);
		letterWidth.put('/',0.50f);
		letterWidth.put('0',0.75f);
		letterWidth.put('1',0.75f);
		letterWidth.put('2',0.75f);
		letterWidth.put('3',0.75f);
		letterWidth.put('4',0.75f);
		letterWidth.put('5',0.75f);
		letterWidth.put('6',0.75f);
		letterWidth.put('7',0.75f);
		letterWidth.put('8',0.75f);
		letterWidth.put('9',0.75f);
		letterWidth.put(':',0.50f);
		letterWidth.put(';',0.50f);
		letterWidth.put('<',0.50f);
		letterWidth.put('=',0.75f);
		letterWidth.put('>',0.50f);
		letterWidth.put('?',0.75f);
		letterWidth.put('@',0.75f);
		letterWidth.put('A',0.75f); letterWidth.put('a',0.75f);
		letterWidth.put('B',0.75f); letterWidth.put('b',0.75f);
		letterWidth.put('C',0.75f); letterWidth.put('c',0.75f);
		letterWidth.put('D',0.75f); letterWidth.put('d',0.75f);
		letterWidth.put('E',0.75f); letterWidth.put('e',0.75f);
		letterWidth.put('F',0.75f); letterWidth.put('f',0.50f);
		letterWidth.put('G',0.75f); letterWidth.put('g',0.75f);
		letterWidth.put('H',0.75f); letterWidth.put('h',0.75f);
		letterWidth.put('I',0.75f); letterWidth.put('i',0.25f);
		letterWidth.put('J',0.75f); letterWidth.put('j',0.75f);
		letterWidth.put('K',0.75f); letterWidth.put('k',0.75f);
		letterWidth.put('L',0.75f); letterWidth.put('l',0.25f);
		letterWidth.put('M',0.75f); letterWidth.put('m',0.75f);
		letterWidth.put('N',0.75f); letterWidth.put('n',0.75f);
		letterWidth.put('O',0.75f); letterWidth.put('o',0.75f);
		letterWidth.put('P',0.75f); letterWidth.put('p',0.75f);
		letterWidth.put('Q',0.75f); letterWidth.put('q',0.75f);
		letterWidth.put('R',0.75f); letterWidth.put('r',0.50f);
		letterWidth.put('S',0.75f); letterWidth.put('s',0.75f);
		letterWidth.put('T',0.75f); letterWidth.put('t',0.50f);
		letterWidth.put('U',0.75f); letterWidth.put('u',0.75f);
		letterWidth.put('V',0.75f); letterWidth.put('v',0.75f);
		letterWidth.put('W',0.75f); letterWidth.put('w',0.75f);
		letterWidth.put('X',0.75f); letterWidth.put('x',0.75f);
		letterWidth.put('Y',0.75f); letterWidth.put('y',0.75f);
		letterWidth.put('Z',0.75f); letterWidth.put('z',0.75f);
		
		letterWidth.put('[',0.50f);
		letterWidth.put('\\',0.50f);
		letterWidth.put(']',0.50f);
		letterWidth.put('^',0.50f);
		letterWidth.put('_',0.75f);
		
		letterWidth.put('{',0.50f);
		letterWidth.put('|',0.25f);
		letterWidth.put('}',0.50f);
		
		
	}
	
	static float size = 8.0f;
	
	public static float stringWidth(String string)
	{
		float totalWidth = 0.0f;
		for(int i=0; i<string.length(); i++)
		{
			totalWidth += letterWidth.get(string.charAt(i)) * size;
		}
		
		return totalWidth;
	}
	
	public static void renderCentered(String string, float x, float y, float z)
	{
		// a little bit too far to the right, using temporary correction
		x -= stringWidth(string) / 2 + 3.0f;
		
		render(string, x, y, z);
	}
	
	public static void render(String string, float x, float y, float z)
	{
		y -= size / 2;
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		glBindTexture(GL_TEXTURE_2D, fontTexture.getTextureID());
		
		glBegin(GL_QUADS);
		
		for(int i=0; i<string.length(); i++)
		{
			float width = letterWidth.get(string.charAt(i)) * size;
			x += width / 2.0f;
			renderChar(string.charAt(i), x, y, z);
			x += width / 2.0f;
		}
		
		glEnd();
	}
	
	private static void renderChar(char c, float x, float y, float z)
	{
		int charCode = (int)c;
		
		float charX = (float)(charCode % 16) * charSize;
		float charY = (float)(charCode / 16) * charSize;
		
		glTexCoord2f(charX + charSize,	charY);				glVertex3f(x+size, y+size, 	z);
		glTexCoord2f(charX,				charY);				glVertex3f(x, 	y+size,	z);
		glTexCoord2f(charX,				charY + charSize);	glVertex3f(x, 	y, 		z);
		glTexCoord2f(charX + charSize,	charY + charSize);	glVertex3f(x+size, y, 		z);
	}
}
