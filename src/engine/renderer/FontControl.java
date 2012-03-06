package engine.renderer;

import static org.lwjgl.opengl.GL11.*;

import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.UnicodeFont;

public class FontControl {

	static Texture fontTexture = GraphicsControl.LoadTexture("font_fixedsys");	
	static float epsilon = 1.0f / 16;
	static public UnicodeFont wFont;
	static public UnicodeFont bFont;
	
	@SuppressWarnings("unchecked")
	static public void InitFonts() {
		fontTexture.bind();
		glTexParameteri( GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST );
		glTexParameteri( GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST );
		
		String fontPath = "res/Fixedsys500c.ttf";
		int fontSize = 256;
		//UnicodeFont uFont;
		try {
			wFont = new UnicodeFont(fontPath , fontSize, false, false);
			wFont.addAsciiGlyphs();   //Add Glyphs
			wFont.addGlyphs(400, 600); //Add Glyphs
			wFont.getEffects().add(new ColorEffect(java.awt.Color.WHITE)); //Add Effects
			wFont.loadGlyphs();  //Load Glyphs			
			
			bFont = new UnicodeFont(fontPath , fontSize, false, false);
			bFont.addAsciiGlyphs();   //Add Glyphs
			bFont.addGlyphs(400, 600); //Add Glyphs
			bFont.getEffects().add(new ColorEffect(java.awt.Color.BLACK)); //Add Effects
			bFont.loadGlyphs();  //Load Glyphs		
			
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //Create Instance
		
		
	}
	
	static public void printStringShadow(String stringToPrint, float xx, float yy) {
		printStringShadow(stringToPrint, xx, yy, 1,1);
	}
	
	static public void printStringShadow(String stringToPrint, float xx, float yy, float scaleX, float scaleY) {
		glPushMatrix();
		glTranslatef(xx,yy,0);
		glScalef(0.075f,0.075f,1);
		glTranslatef(-xx,-yy,0);
		glScalef(scaleX,scaleY,1);
		bFont.drawString(xx+2, yy+2, stringToPrint, Color.white);
		wFont.drawString(xx, yy, stringToPrint, Color.white);
		glPopMatrix();
	}
	
	static public void printString(String stringToPrint, float xx, float yy) {
		glPushMatrix();
		glTranslatef(xx,yy,0);
		glScalef(0.075f,0.075f,1);
		glTranslatef(-xx,-yy,0);
		wFont.drawString(xx, yy, stringToPrint);
		glPopMatrix();
	}
}