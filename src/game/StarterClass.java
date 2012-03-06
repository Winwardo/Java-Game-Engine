package game;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Canvas;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.input.Keyboard;

import engine.AudioControl;
import engine.Debug;
import engine.Input;
import engine.classes.GameObject;
import engine.renderer.FontControl;
import engine.renderer.GraphicsControl;
import engine.renderer.Sprite;
import game.classes.basePlayer;
import static engine.Input.keyEvent;
import static engine.Debug.*;

import static org.lwjgl.opengl.GL11.*;

public class StarterClass extends Applet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The Canvas where the LWJGL Display is added */
	public static Canvas display_parent;

	/** Thread which runs the main game loop */
	Thread gameThread;

	/** is the game loop running */
	boolean running;
	
	boolean keyDown;


	/**
	 * Once the Canvas is created its add notify method will call this method to
	 * start the LWJGL Display and game loop in another thread.
	 */
	public void startLWJGL() {
		gameThread = new Thread() {
			public void run() {
				running = true;
				try {
					Display.setParent(display_parent);
					//Display.setVSyncEnabled(true);
					Display.create();					
				} catch (LWJGLException e) {
					e.printStackTrace();
				}
				runGame();
				System.exit(0);
			}
		};
		gameThread.start();
	}

	/**
	 * Tell game loop to stop running, after which the LWJGL Display will be destroyed.
	 * The main thread will wait for the Display.destroy() to complete
	 */
	private void stopLWJGL() {
		running = false;
		try {
			gameThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void start() {	}
	public void stop() {	}

	/**
	 * Applet Destroy method will remove the canvas, before canvas is destroyed it will notify
	 * stopLWJGL() to stop main game loop and to destroy the Display
	 */
	public void destroy() {
		remove(display_parent);
		super.destroy();
		System.out.println("Clear up");
	}

	/**
	 * initialise applet by adding a canvas to it, this canvas will start the LWJGL Display and game loop
	 * in another thread. It will also stop the game loop and destroy the display on canvas removal when
	 * applet is destroyed.
	 */
	public void init() {		
		setLayout(new BorderLayout());
		try {
			display_parent = new Canvas() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
				public void addNotify() {
					super.addNotify();
					startLWJGL();
				}
				public void removeNotify() {
					stopLWJGL();
					super.removeNotify();
				}
			};
			display_parent.setSize(GraphicsControl.returnScrWidth(),GraphicsControl.returnScrHeight());
			add(display_parent);
			display_parent.setFocusable(true);
			display_parent.requestFocus();
			display_parent.setIgnoreRepaint(true);
			setSize(GraphicsControl.returnScrWidth(),GraphicsControl.returnScrHeight());
			setVisible(true);						
		} catch (Exception e) {
			System.err.println(e);
			throw new RuntimeException("Unable to create display");
		}
	}

	public void runGame() {
 		
		Debug.setDebugs();
		
		Sprite.createList();
 		GraphicsControl.Init(); //Set up graphics
		Display.setTitle("Game Engine");
		Input.setupKeys();
		AudioControl.LoadSounds();//Runs a separate thread for loading sounds
		FontControl.InitFonts();		
		
		GameObject.createAllObjs();
		
		basePlayer myPlayer = new basePlayer();
		
		while(running) {
			Input.pollEvents();
			if(Input.getKey(Keyboard.KEY_ESCAPE) == keyEvent.KeyDown) { running = false; break; };
			
			//Update all objects
			GameObject.updateAllObjs();
			
			glClearColor(0.0f,52.0f/255,102.0f/255,1.0f);
			
			//OpenGL draw calls
			//Clear the screen and depth buffer
			glLoadIdentity();
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			glPushMatrix();
			
			GameObject.drawAllObjs();
			
			//Final update and syncing
			Display.sync(60);
			Display.update();			
			
			glPopMatrix();
			
			String DebugString = "Mouse - X: " + String.valueOf(Input.getMouseX()) + " Y: " + String.valueOf(Input.getMouseY());
			debugPrintln(DebugString, dbgMOUSEPOS);
			
		}
		
		//AL.destroy();	 		
 		Display.destroy();
 	}

}
