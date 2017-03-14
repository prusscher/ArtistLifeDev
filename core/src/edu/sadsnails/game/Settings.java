package edu.sadsnails.game;

import java.io.File;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

/**
 * Created by Parker on 2/22/2017.
 */
public class Settings {
	private int width;
    private int height;
    private float musicVolume;
    private float sfxVolume;
    private float masterVolume;
    private DisplayMode mode;
    private DisplayMode[] modes;
    
    private String prefsFolder;
    private String fileName;
    private Pair set;
    
    public Settings() {
    	long startTime = System.currentTimeMillis();
    	
    	// Get to the settings directory and create a new file
    	prefsFolder = System.getenv("LOCALAPPDATA") + "\\sadsnails\\settings";
    	fileName = prefsFolder + "\\settings.cfg";
    	File dir = new File(prefsFolder);
    	boolean result = false;
		
    	// Check if the directory exists
    	if(!dir.exists()) {
    		// Can't find the directory, creating it
    		System.out.println("Creating Directory: " + prefsFolder);
    		
    		try {
	    		dir.mkdirs();
	    		result = true;
    		} catch (SecurityException e) { 
    			System.out.println("PROBLEM: Can't create the directory, creating default settings");
    		}
    		
    		if(result) {
    			System.out.println("We Good: " + prefsFolder + " created");
    		}
    	}
    	
    	// Create the settings object and read the settings config file
    	set = new Pair();
    	boolean fileExists = set.read(fileName);
    	
    	// Get Available Display Modes
    	modes = Lwjgl3ApplicationConfiguration.getDisplayModes();
    	
    	if(fileExists && set.size() > 0 || !result) { 
    		// Get the variables from the settings file
    		int tWidth = 	set.getInt("Width");
    		int tHeight =	set.getInt("Height");
    		float tMusic = 	set.getFloat("MusicVolume");
    		float tSfx = 	set.getFloat("SfxVolume");
    		float tMaster = set.getFloat("MasterVolume");
    		
    		// If the current w/h settings cant be found in the settings file
    		if(tWidth == -1 || tHeight == -1) {
    			mode = setInitialMode();
    			width = mode.width;
    			height = mode.height;
    			set.putInt("Width", mode.width);
    			set.putInt("Height", mode.height);
    		} else if(modes[modes.length-1].width < tWidth || modes[modes.length-1].height < tHeight) { 
    			// The Width and height were greater than the screen, set to the max resoultion
    			mode = modes[modes.length-1];
    			width = mode.width;
    			height = mode.height;
    			set.putInt("Width", mode.width);
    			set.putInt("Height", mode.height);
    		} else {
    			width = tWidth;
    			height = tHeight;
    		}
    		
    		// If the music is not set in the settings, create the settings
    		if(tMusic == -1.0 || tMusic < 0 || tMusic > 100) {
    			musicVolume = 100;
    			System.out.println(musicVolume + " : Music Volume Not Set/Out of bounds");
    		} else {
    			musicVolume = tMusic;
    		}
    		
    		if(tSfx == -1.0 || tSfx < 0 ||  tSfx > 100) {
    			sfxVolume = 100;
    			System.out.println(sfxVolume + " : Sfx Volume Not Set/Out of bounds");
    		} else {
    			sfxVolume = tSfx;
    		}
    		
    		if(tMaster == -1.0 || tMaster < 0 ||  tMaster > 100) {
    			masterVolume = 100;
    			System.out.println(masterVolume + " : Master Volume Not Set/Out of bounds");
    		} else {
    			masterVolume = tMaster;
    		}
    		
    		
	    	set.putFloat("MasterVolume", masterVolume);
	    	set.putFloat("MusicVolume", musicVolume);
	    	set.putFloat("SfxVolume", sfxVolume);
	    	set.putString("TestString", "I started correctly, lol");
    	} else { // If settings file doesnt exist create a new one
    		System.out.println("No Settings File: Creating and setting defaults");
    		
	    	// TEMP: Set the displaymode to 720p
	    	mode = setInitialMode();
	    	
	    	// Set the internal variables
	    	width = mode.width;
	    	height = mode.height;
	    	
	    	masterVolume = 100;
	    	musicVolume = 100;
	    	sfxVolume = 100;
    	
	    	set.putInt("Width", width);
	    	set.putInt("Height", height);
	    	set.putFloat("MasterVolume", masterVolume);
	    	set.putFloat("MusicVolume", musicVolume);
	    	set.putFloat("SfxVolume", sfxVolume);
	    	set.putString("TestString", "Just created, lol");
    	}	
    	
    	System.out.println("Done Loading Settings: Screen [" + width + ", " + height + "]");
    	System.out.println(set.toString());
    	System.out.println("Time to load: " + (System.currentTimeMillis()-startTime));
    	
    	set.write(fileName);
    }

    public int width() 		{ return width; }
    public int height() 	{ return height; }
    
    public float musicVol() 	{ return musicVolume; }
    public float sfxVol() 		{ return sfxVolume; }
    public float masterVol() 	{ return masterVolume; }
    
    public void setMusicVol(float vol) 	{ musicVolume = vol; }
    public void setSfxVol(float vol) 	{ sfxVolume = vol; }
    public void setMasterVol(float vol) { masterVolume = vol; }
    
    public void close() {
    	System.out.println("CLOSING APP, SAVING SETTINGS");
    	set.putInt("Width", Gdx.graphics.getWidth());
    	set.putInt("Height", Gdx.graphics.getHeight());

    	set.putFloat("MasterVolume", masterVolume);
    	set.putFloat("MusicVolume", musicVolume);
    	set.putFloat("SfxVolume", sfxVolume);
   
    	set.write(fileName);
    }
    
    private DisplayMode setInitialMode() {
    	// Check if 720 exists : Default Mode
    	for(int i = modes.length-1; i >= 0; i--) {
    		System.out.println(i + ": " + modes[i].width + " " + modes[i].height);
    		if(modes[i].height == 720) {
    			mode = modes[i];
    			break;
    		} else if(modes[i].height < 720) {
    			mode = modes[i];
    			break;
    		}
    	}
    	
    	return mode;
    }
    

}
