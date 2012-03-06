package engine.renderer;

import java.awt.Canvas;
import java.io.IOException;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import engine.classes.GameObject;

import static org.lwjgl.opengl.GL11.*;

public class GraphicsControl {
	
	static int desktopWidth;
	static int desktopHeight;
	
	final static int screenWidth = 854;
	final static int screenHeight = 480;
	public final static float mouseMultiplier = (1f/640f)*screenWidth;
	public static Texture whiteTex;
	public static Sprite whiteSprite;
	
	static Canvas display_parent;
	
	static public int returnScrWidth() { return screenWidth; }
	static public int returnScrHeight() { return screenHeight; }
	
	public static void startLWJGL() {
		try {
			Display.setParent(display_parent);
			Display.create();
		} catch (LWJGLException e) {
				e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unused")
	private static void stopLWJGL() {
		Display.destroy();
	}
	
	static public void Init() {
		//Initialise OpenGL		
		//Set clear colour
		glClearColor(0.0f,52.0f/255,102.0f/255,1.0f);
		//glClearColor(0,0,0,1);

	    //Set projection
	    glMatrixMode( GL_PROJECTION );
	    glOrtho(0, screenWidth, screenHeight, 0, -1, 1);
		glShadeModel(GL_SMOOTH);
		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
		glViewport(0,0,screenWidth,screenHeight);
		
	    //Initialise model view matrix
	    glMatrixMode( GL_MODELVIEW );
	    glLoadIdentity();

		glEnable(GL_TEXTURE_2D);
		glPointSize(1.5f);
		glEnable(GL_POINT_SMOOTH);
		
		glAlphaFunc(GL_GREATER, 0.2f);
		glEnable(GL_ALPHA_TEST);
		
		Display.setVSyncEnabled(true);
		
		whiteTex = LoadTexture("white");
		new Sprite("white");
	}
	
	static public Texture LoadTexture(String fileName) {
		Texture rettexture = null;
		try {
			rettexture = TextureLoader.getTexture(
					"PNG",
					ResourceLoader.getResourceAsStream("res/images/"
							+ fileName + ".png"));			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rettexture;
	}	
	
	static public void ApplySprite(Sprite sprite, float imX1, float imY1,
		float imZ, int frameNum, int animNum, float[] objColour, float zoomX,
		float rotationX) {
		int frameWidth = sprite.frameWidth;
		int frameHeight = sprite.frameHeight;
		float imX2 = imX1 + frameWidth;
		float imY2 = imY1 + frameHeight;
		glPushMatrix();
		{

			glTranslatef(imX2, imY2, imZ); // Translate into place
			glScalef(zoomX, zoomX, 1);// Scale X and Y, but not Z
			glRotatef(rotationX, 0, 0, 1);// Rotate along the Z axis, i.e.
											// facing us
			glTranslatef(-imX2, -imY2, -imZ); // Translate back

			glTranslatef(0, 0, imZ); // Translate into the z axis

			double highX = ((double) sprite.texture.getImageWidth() / sprite.texture
					.getTextureWidth());
			double highY = ((double) sprite.texture.getImageHeight() / sprite.texture
					.getTextureHeight());

			double frX1 = (highX / ((double) sprite.texture.getImageWidth() / frameWidth))
					* frameNum;
			double frX2 = frX1
					+ (highX / ((double) sprite.texture.getImageWidth() / frameWidth));

			double frY1 = (highY / ((double) sprite.texture.getImageHeight() / frameHeight))
					* animNum;
			double frY2 = frY1
					+ (highY / ((double) sprite.texture.getImageHeight() / frameHeight));

			glEnable(GL_BLEND);
			glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
			glColor4f(objColour[0], objColour[1], objColour[2], objColour[3]);

			//Color.white.bind();
			sprite.texture.bind();

			glBegin(GL_QUADS);
			glTexCoord2d(frX1, frY1);
			glVertex2f(imX1, imY1);
			glTexCoord2d(frX2, frY1);
			glVertex2f(imX2, imY1);
			glTexCoord2d(frX2, frY2);
			glVertex2f(imX2, imY2);
			glTexCoord2d(frX1, frY2);
			glVertex2f(imX1, imY2);
			glEnd();

			glDisable(GL_BLEND);
			glColor4f(1, 1, 1, 1);

		}
		glPopMatrix();
	
	}
	
	static public void ApplySprite(GameObject object, int frameNumber, int animNumber) {
		ApplySprite(object.GetSprite(), object.GetX(), object.GetY(), object.GetZ(), frameNumber, animNumber, object.GetColour(), object.GetZoom(), object.GetRotation());
	}
	
	static public void ApplySprite(Sprite sprite, float xx, float yy) {
		float [] tempColour = {1,1,1,1};
		ApplySprite(sprite, xx, yy, 0, 0, 0, tempColour, 1, 0);
	}
	
	static public void ApplySprite(Sprite sprite, float xx, float yy, float zz) {
		float [] tempColour = {1,1,1,1};
		ApplySprite(sprite, xx, yy, zz, 0, 0, tempColour, 1, 0);
	}
	
	static public void ApplySprite(Sprite sprite, float xx, float yy, float zz, float zoom) {
		float [] tempColour = {1,1,1,1};
		ApplySprite(sprite, xx, yy, zz, 0, 0, tempColour, zoom, 0);
	}
//	
	static public void DrawBox2D_withDepth(float x1, float x2, float y1, float y2, float z1, Texture TextureX, boolean hollow)
	{
		TextureX.bind();
		glTranslatef( 0, 0, z1); //Translate into the z axis

		if(hollow == false)
		{	glBegin( GL_QUADS ); }
		else
		{   glBegin( GL_LINE_LOOP); };

	        //Draw square
		    glTexCoord2f(0,0); glVertex3f( x1, y1, 0);
		    glTexCoord2f(1,0); glVertex3f( x2, y1, 0);
		    glTexCoord2f(1,1); glVertex3f( x2, y2, 0);
		    glTexCoord2f(0,1); glVertex3f( x1, y2, 0);

	    //End quad
	    glEnd();
	    
	    glTranslatef( 0, 0, -z1); //Translate into the z axis
	};
	
	static public void DrawBox2D(float x1, float x2, float y1, float y2, Texture TextureX, boolean hollow)
	{
		DrawBox2D_withDepth(x1,x2,y1,y2,0,TextureX,hollow);
	};
	
	static public void DrawBox2D(float x1, float x2, float y1, float y2, Texture TextureX)
	{
		DrawBox2D_withDepth(x1,x2,y1,y2,0,TextureX,false);
	};
	
	static public void DrawBox2D(float x1, float x2, float y1, float y2)
	{
		DrawBox2D_withDepth(x1,x2,y1,y2,0,getWhiteTex(),false);
	};
	
//	static public void DrawSpriteFlat(Sprite spriteToDraw, float x, float y) {
//		Color.white.bind();
//		spriteToDraw.texture.bind();
//		
//		float topX = (1.0f / spriteToDraw.texture.getTextureWidth()) * spriteToDraw.texture.getImageWidth();
//		float topY = (1.0f / spriteToDraw.texture.getTextureHeight()) * spriteToDraw.texture.getImageHeight();
//		
//		glBegin(GL_QUADS);
//		glTexCoord2f(0, 0);
//		glVertex2f(x, y);
//		glTexCoord2f(topX, 0);
//		glVertex2f(x + spriteToDraw.frameWidth, y);
//		glTexCoord2f(topX, topY);
//		glVertex2f(x + spriteToDraw.frameWidth, y + spriteToDraw.frameHeight);
//		glTexCoord2f(0, topY);
//		glVertex2f(x, y + spriteToDraw.frameHeight);
//		glEnd();
//	}
//	
	static public Texture getWhiteTex() {
		return whiteTex;
	}
	
	static public void pixelStretchTexture(Texture tempTex) {
		tempTex.bind();
		glTexParameteri( GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST );
		glTexParameteri( GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST );
	}
	
	static public void linearStretchTexture(Texture tempTex) {
		tempTex.bind();
		glTexParameteri( GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR );
		glTexParameteri( GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR );
	}
	
}