package engine;

import engine.classes.Cube;
import engine.renderer.GraphicsControl;
//import game.engine.renderer.GraphicsControl;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class Input {	
	
	final static int numberOfKeys = 256;
	final static int numberOfMouseButtons = 32;
	static Cube mouseCube;
	
	public static enum keyEvent {KeyNoEvent, KeyPressed, KeyReleased, KeyDown, KeyUp};
	
	static keyEvent [] keyValues = new keyEvent[numberOfKeys];
	static keyEvent [] mouseValues = new keyEvent[numberOfMouseButtons];
	
	static public void setupKeys() {
		for (int i = 0; i != numberOfKeys; i++) {
			keyValues[i] = keyEvent.KeyNoEvent;
		}
		for (int i = 0; i != numberOfMouseButtons; i++) {
			mouseValues[i] = keyEvent.KeyNoEvent;
		}
		mouseCube = new Cube(0,0,-10,1,1,10);
	}
	
	static public void pollEvents() {
		
		for (int keyNum = 0; keyNum != numberOfKeys; keyNum++) {
			if (keyValues[keyNum] == keyEvent.KeyPressed)
				keyValues[keyNum] = keyEvent.KeyDown;
			else if (keyValues[keyNum] == keyEvent.KeyReleased)
				keyValues[keyNum] = keyEvent.KeyUp;	
		}
		
		for (int mouseNum = 0; mouseNum != numberOfMouseButtons; mouseNum++) {
			if (mouseValues[mouseNum] == keyEvent.KeyPressed)
				mouseValues[mouseNum] = keyEvent.KeyDown;
			else if (mouseValues[mouseNum] == keyEvent.KeyReleased)
				mouseValues[mouseNum] = keyEvent.KeyUp;
		}
		
		while(Keyboard.next()) {			
			int keyNum = Keyboard.getEventKey();
			if(Keyboard.getEventKeyState()) 
				keyValues[keyNum] = keyEvent.KeyPressed;
			else
				keyValues[keyNum] = keyEvent.KeyReleased;			
		}
		
		while(Mouse.next()) {
			int mouseNum = Mouse.getEventButton()+1;
			if(Mouse.getEventButtonState())
				mouseValues[mouseNum] = keyEvent.KeyPressed;
			else
				mouseValues[mouseNum] = keyEvent.KeyReleased;	
		}
		
		mouseCube.x1 = Mouse.getX();
		mouseCube.width = 1;
		mouseCube.y1 = GraphicsControl.returnScrHeight()-Mouse.getY();
		mouseCube.height = 1;
		
		mouseCube.x1 *= GraphicsControl.mouseMultiplier;
		mouseCube.y1 *= GraphicsControl.mouseMultiplier;
	}
	
	static public keyEvent getKey(int keyNum) { return keyValues[keyNum]; };
	static public keyEvent getMouseButton(int mouseNum) { return mouseValues[mouseNum]; };
	
	static public Cube getMouseCube() { return mouseCube; };
	
	static public int getMouseX() { return Mouse.getX(); };
	static public int getMouseY() { return GraphicsControl.returnScrHeight() - Mouse.getY(); };
	
}