package engine.classes;

import java.util.ArrayList;

import engine.renderer.GraphicsControl;
import engine.renderer.Sprite;
import game.classes.basePlayer;

public class GameObject {
	/////////////////////////////
	//LIST CONTROL
	public static ArrayList<GameObject> listGameObjects;
	
	static public void createAllObjs() {
		listGameObjects = new ArrayList<GameObject>();
		
		basePlayer.CreateList();
//		ItemBase.CreateList();		
//		SpawnController.setupQueue();
	};
	
	static public void updateAllObjs() {
		basePlayer.UpdateList();
//		SpawnController.evaluateGameObjects();
//		ItemBase.UpdateList();
	}
	
	static public void drawAllObjs() {				
		basePlayer.DrawList();		
//		ItemBase.DrawList();
	}
	
	/////////////////////////////
	//VARIABLE DECLARATIONS
	boolean enabled = false;
	float x,y,z, xSpeed, ySpeed, zSpeed, rotation;
	float Speed = 1;
	float zoom = 1;
	float zoomSpeed = 0;
	protected Sprite sprite;
	float [] objColour = {1,1,1,1};
	protected Cube boundingBox = new Cube(0,1,0,1,0,1);
	
	//public ArrayList<Component> listComponents;
	protected boolean removeMe;
	
	/////////////////////////////
	//METHOD DEFINITIONS
	public GameObject() { listGameObjects.add(this); /*listComponents = new ArrayList<Component>();*/ };
	public GameObject(boolean addMe) { if(addMe == true) listGameObjects.add(this); /*listComponents = new ArrayList<Component>();*/ };
	
	public void Init() {/*Override this if spawning*/};
	
	public void addToList() { listGameObjects.add(this); };
	 
	public void SetX(float newX) { x = newX; };
	public void SetY(float newY) { y = newY; };
	public void SetZ(float newZ) { z = newZ; };
	public void SetXSpeed(float newXSpeed) { xSpeed = newXSpeed; };
	public void SetYSpeed(float newYSpeed) { ySpeed = newYSpeed; };
	public void SetZSpeed(float newZSpeed) { zSpeed = newZSpeed; };
	public void SetXY(float newX, float newY) { x = newX; y = newY; };
	public void SetXYZ(float newX, float newY, float newZ) { x = newX; y = newY; z = newZ; };
	public void AddX(float newX) { x += newX; };
	public void AddY(float newY) { y += newY; };
	public void AddZ(float newZ) { z += newZ; };
	public void AddXSpeed(float newXSpeed) { xSpeed += newXSpeed; };
	public void AddYSpeed(float newYSpeed) { ySpeed += newYSpeed; };
	public void AddZSpeed(float newZSpeed) { zSpeed += newZSpeed; };
	public void SetEnabled(boolean newEnabled) { enabled = newEnabled; };
	public boolean GetEnabled() { return enabled; };
	public void SetZoom(float newZoom) { zoom = newZoom; };
	public void AddZoom(float newZoom) { zoom += newZoom; };
	public float GetZoom() { return zoom; };
	public void SetZoomSpeed(float newZoomSpeed) { zoomSpeed = newZoomSpeed; };
	public void AddZoomSpeed(float newZoomSpeed) { zoomSpeed += newZoomSpeed; };
	public float GetZoomSpeed() { return zoomSpeed; };
	public void SetRotation(float newRotation) { rotation = newRotation; };
	public void AddRotation(float newRotation) { rotation += newRotation; };
	public float GetRotation() { return rotation; };
	public float GetX() { return x; };
	public float GetY() { return y; };
	public float GetZ() { return z; };
	public float GetXSpeed() { return xSpeed; };
	public float GetYSpeed() { return ySpeed; };
	public float GetZSpeed() { return zSpeed; };
	public void SetColour(float r, float g, float b) { objColour[0] = r; objColour[1] = g; objColour[2] = b; };
	public void SetColour(float r, float g, float b, float a) { objColour[0] = r; objColour[1] = g; objColour[2] = b; objColour[3] = a;};
	public void SetAlpha(float a) { objColour[3] = a; };
	public float [] GetColour() { return objColour; };
	public Sprite GetSprite() { return sprite; };
	public boolean GetDeleted() { return removeMe; }
	public void SetDeleted(boolean removeMe) { this.removeMe = removeMe; };
	public Cube getBoundingBox() { return boundingBox; };
	public boolean collidesWith(Cube otherCube) { return boundingBox.intersects(otherCube); };
	
	//public void AddComponent(Component newComp) { listComponents.add(newComp); };
	
	public void SetSprite(String spriteLocation) {
		sprite = new Sprite(spriteLocation);
	}
	
	public void SetSprite(String spriteLocation, int frameWidthX, int frameWidthY) {
		sprite = new Sprite(spriteLocation, frameWidthX, frameWidthY);
	}
	
	protected void updateBoundingBox() {
		boundingBox.x1 = this.GetX() - (sprite.frameWidth * GetZoom()) + sprite.frameWidth;
		boundingBox.width = sprite.frameWidth * GetZoom();
		boundingBox.y1 = this.GetY() - (sprite.frameHeight * GetZoom()) + sprite.frameHeight;
		boundingBox.height = sprite.frameHeight * GetZoom();
		boundingBox.z1 = this.GetZ() - 24*GetZoom();
		boundingBox.depth = 48*GetZoom();
	}
	
	public void doMotion() {
		if( (xSpeed < 0.05) && (xSpeed > -0.05) ) { xSpeed = 0; };
		if( (ySpeed < 0.05) && (ySpeed > -0.05) ) { ySpeed = 0; };
		if( (zSpeed < 0.05) && (zSpeed > -0.05) ) { zSpeed = 0; };
		
		x += xSpeed;
		y += ySpeed;
		z += zSpeed;
		
		zoom += zoomSpeed;
	}
	
	public void doUpdate() {/*Sub-classes need to redefine this to suit themselves*/};	
	
	public void doDraw() {//Usually overridden		
		GraphicsControl.ApplySprite(sprite, x, y, z, 0, 0, objColour, zoom, rotation);
	}
	
	public void doCustomDraw() { }
	
	public void doDrawBoundingBox() {
		GraphicsControl.DrawBox2D_withDepth(boundingBox.x1, boundingBox.x1+boundingBox.width, boundingBox.y1, boundingBox.y1+boundingBox.height, boundingBox.z1, GraphicsControl.getWhiteTex(), true);
		GraphicsControl.DrawBox2D_withDepth(boundingBox.x1, boundingBox.x1+boundingBox.width, boundingBox.y1, boundingBox.y1+boundingBox.height, boundingBox.z1+boundingBox.depth, GraphicsControl.getWhiteTex(), true);
	}
	
	//protected void clearComponentsList() { listComponents.clear(); }
}