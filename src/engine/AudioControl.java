package engine;

import game.LoadGameSounds_thread;

import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

public class AudioControl {

	static ArrayList<Audio> audioList = new ArrayList<Audio>();
	static ArrayList<String> audioList_names = new ArrayList<String>();	
	static public boolean loadedAllSounds = false;
	static public boolean useSounds = false;
	
	public static void LoadSounds() {
		LoadGameSounds_thread loadSoundsThread = new LoadGameSounds_thread();
		loadSoundsThread.start();
	}

	/**Load music files directly from their source and add it to the audioList.*/
	public static void AddAudioDir(String fileLocation, String type) {
		Audio newAudio = null;
		try {
			newAudio = AudioLoader.getAudio(type,
					ResourceLoader.getResourceAsStream(fileLocation));
		} catch (IOException e) {
			e.printStackTrace();
		}
		audioList.add(newAudio);
		audioList_names.add(fileLocation);
	}
	
	public static void AddAudio(String filename, String type) {
		if(type == "OGG" || type == "WAV") {
			AddAudioDir("res/audio/"+filename+"."+type,type);
		}
	}
	
	public static int findAudioPositionDir(String filename) {
		return audioList_names.indexOf(filename);
	}
	
	public static int findAudioPosition(String filename, String type) {
		return findAudioPositionDir("res/audio/"+filename+"."+type);
	}
	
	public static Audio findAudio(String filename, String type) {
		if ( findAudioPosition(filename, type) != -1)
		  return audioList.get( findAudioPosition(filename, type) );
		return null;
	}
	
	public static void playAudio(String filename, String type) {
		int audioPos = findAudioPosition(filename, type);
		if(audioPos >= 0) {
			audioList.get(audioPos).playAsSoundEffect(1, 1, false);
		}
	}
	
	public static void playAudio(String filename, String type, float pitch, float gain, boolean loop) {
		int audioPos = findAudioPosition(filename, type);
		if(audioPos >= 0) {
			audioList.get(audioPos).playAsSoundEffect(pitch, gain, loop);
		}
	}
	
	public static void changeVolume(String filename, String type, float newVolume) {
		float currentPos = findAudio(filename, type).getPosition();
		findAudio(filename, type).stop();
		findAudio(filename, type).playAsSoundEffect(1, newVolume, false);
		findAudio(filename, type).setPosition(currentPos);
	}
}