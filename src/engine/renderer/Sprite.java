package engine.renderer;

import java.io.IOException;
import java.util.HashMap;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Sprite {
	/////////////////////////////
	//LIST CONTROL
	static HashMap<String, Sprite> listSprites; 
	static public void createList() { listSprites = new HashMap<String, Sprite>(); };

	static public Sprite findSprite(String spriteName) {
		Sprite tempReturn = listSprites.get(spriteName);
		if (tempReturn == null) {
			return GraphicsControl.whiteSprite;
		} else {
			return tempReturn;
		}
	}
	
	static public Texture getTexture(String spriteName) {
		return findSprite(spriteName).texture;
	}
	
	/////////////////////////////
	//VARIABLE DECLARATIONS
	public int frameWidth, frameHeight;
	public Texture texture;
	public String spriteLocation;

	/////////////////////////////
	//METHOD DEFINITIONS	
	public Sprite(String fileName) {
		spriteLocation = fileName;
		if (!listSprites.containsKey(fileName)) {
			try {
				texture = TextureLoader.getTexture(
						"PNG",
						ResourceLoader.getResourceAsStream("res/images/"
								+ fileName + ".png"));
				frameWidth = texture.getImageWidth();
				frameHeight = texture.getImageHeight();

				listSprites.put(fileName, this);
			} catch (IOException e) {				
				e.printStackTrace();
				texture = GraphicsControl.whiteTex;
				frameWidth = 32;
				frameHeight = 32;
			}
			
			System.out.println("LOADED "+fileName);
		} else {
			Sprite tempSprite = listSprites.get(fileName);
			this.frameHeight = tempSprite.frameHeight;
			this.frameWidth = tempSprite.frameWidth;
			this.texture = tempSprite.texture;
		}
		
		GraphicsControl.pixelStretchTexture(this.texture);		
	}
	
	public Sprite(String fileName, int frameWidthX, int frameHeightX) {
		this(fileName);
		
		spriteLocation = fileName;
		frameWidth = frameWidthX;
		frameHeight = frameHeightX;
	}
	
	public void Draw(float xx, float yy, float zz) {
		GraphicsControl.ApplySprite(this, xx, yy, zz);;
	}
}