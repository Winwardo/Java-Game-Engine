package game.classes;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import static org.lwjgl.opengl.GL11.*;

import engine.Input;
import engine.Input.keyEvent;
import engine.classes.GameObject;

public class basePlayer extends GameObject {
	/////////////////////////////
	//LIST CONTROL
	static ArrayList<GameObject> listThisObj;

	public static void CreateList() {
		listThisObj = new ArrayList<GameObject>();
	};

	public static void UpdateList() {
		int count = 0;
		int deleted = 0;
		while ( (count - deleted) < listThisObj.size() ) {
			listThisObj.get(count - deleted).doUpdate();
			if (listThisObj.get(count - deleted).GetDeleted() == true) {
				listThisObj.remove(count - deleted);
				deleted++;
			}
			count++;
		}
	}
	
	public static void DrawList() {
		for (int i = 0; i != listThisObj.size(); i++) {
			listThisObj.get(i).doDraw();
		}
	}
	
	/////////////////////////////
	//VARIABLE DECLARATIONS
	public boolean isJumping = false;
	
	/////////////////////////////
	//METHOD DEFINITIONS
	public basePlayer() {
		super();
		listThisObj.add(this);
		SetSprite("white", 96, 96);
		SetXY(64, 64);

		updateBoundingBox();
	}
	
	void keyInput() {

		if (Input.getKey(Keyboard.KEY_UP) == keyEvent.KeyPressed) {

		}

		if (Input.getKey(Keyboard.KEY_DOWN) == keyEvent.KeyPressed) {
			
		}	
		
	}
	
	void collideWithGameObjects() {
		ArrayList<GameObject> tempObjList;		
	}
	
	public void doUpdate() {
		//Perform checks, such as if touching the ground	
		
		// KEY INPUT
		keyInput();
		
		//Jumping
		if(isJumping) {
			
		}		
		
		//Clamping values
		
		//Do other operations
		collideWithGameObjects();
		doMotion();
		updateBoundingBox();
	}
	
	public void doDraw() {//Overridden version
		super.doDraw();		
	}
		
}