package game;

import static engine.AudioControl.AddAudio;
import engine.AudioControl;

public class LoadGameSounds_thread extends Thread {

	public void run() {
		if(engine.AudioControl.useSounds) {
			AddAudio("Nyan_Intro","OGG");	
		}
		AudioControl.loadedAllSounds = true;
	}
	
}
