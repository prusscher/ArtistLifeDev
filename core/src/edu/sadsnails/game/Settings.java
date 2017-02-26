package edu.sadsnails.game;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Parker on 2/22/2017.
 */
public class Settings {
    private class settingsManager {
        private Properties prop;
        private final String file = "game.settings";

        public settingsManager() {

        }
    }

    private int width;
    private int height;
    private float musicVolume;
    private float sfxVolume;

    public Settings() {

        if(!true)
        {
           System.out.println("Not Set");
        }
        else
        {
            System.out.println("Yeah it works");
        }
    }

    public void setMusicVolume(float v) {

    }

    public void setSfxVolume(float v) {

    }
}
