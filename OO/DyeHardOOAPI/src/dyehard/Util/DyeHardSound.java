/*
 * 
 */
package dyehard.Util;

import Engine.BaseCode;
import dyehard.Resources.ConfigurationFileParser;

// TODO: Auto-generated Javadoc
/**
 * The Class DyeHardSound.
 */
// make a singleton
public class DyeHardSound {
    
    /** The sound play. */
    private static boolean soundPlay = true;
    
    /** The music play. */
    private static boolean musicPlay = true;
    
    /** The bg stopped once. */
    private static boolean bgStoppedOnce = false;
    
    /** The Constant bgMusicPath. */
    public final static String bgMusicPath = "Audio/BgMusic.wav"; 
    
    /** The Constant pickUpSound. */
    public final static String pickUpSound = "Audio/PickupSound.wav";
    
    /** The Constant powerUpSound. */
    public final static String powerUpSound = "Audio/Powerup.wav";
    
    /** The Constant paintSpraySound. */
    public final static String paintSpraySound = "Audio/PaintSpraySound.wav";
    
    /** The Constant enemySpaceship1. */
    public final static String enemySpaceship1 = "Audio/EnemySpaceship1.wav";
    
    /** The Constant loseSound. */
    public final static String loseSound = "Audio/DyeLose.wav";
    
    /** The Constant winSound. */
    public final static String winSound = "Audio/DyeWin.wav";
    
    /** The Constant lifeLostSound. */
    public final static String lifeLostSound = "Audio/LifeLost.wav";
    
    /** The Constant portalEnter. */
    public final static String portalEnter = "Audio/PortalEnter.wav";
    
    /** The Constant portalExit. */
    public final static String portalExit = "Audio/PortalExit.wav";
    
    /** The Constant portalLoop. */
    public final static String portalLoop = "Audio/PortalLoop.wav";
    
    /** The Constant shieldSound. */
    public final static String shieldSound = "Audio/ShieldSound.wav";
    
    /** The Constant shotgunSound. */
    public final static String shotgunSound = "Audio/shotgun.mp3"; // used to test mp3 functionality

    static {
        // load sounds for later interactions
        BaseCode.resources.preloadSound(bgMusicPath);
        BaseCode.resources.preloadSound(pickUpSound);
        BaseCode.resources.preloadSound(powerUpSound);
        BaseCode.resources.preloadSound(paintSpraySound);
        BaseCode.resources.preloadSound(enemySpaceship1);
        BaseCode.resources.preloadSound(loseSound);
        BaseCode.resources.preloadSound(winSound);
        BaseCode.resources.preloadSound(lifeLostSound);
        BaseCode.resources.preloadSound(portalEnter);
        BaseCode.resources.preloadSound(portalExit);
        BaseCode.resources.preloadSound(portalLoop);
        BaseCode.resources.preloadSound(shieldSound);
        BaseCode.resources.preloadSound(shotgunSound);

        BaseCode.resources.setSoundVolume(pickUpSound,
        		ConfigurationFileParser.getInstance().getSoundData().getPickUpSound());
        BaseCode.resources.setSoundVolume(powerUpSound,
        		ConfigurationFileParser.getInstance().getSoundData().getPowerUpSound());
        BaseCode.resources.setSoundVolume(paintSpraySound,
        		ConfigurationFileParser.getInstance().getSoundData().getPaintSpraySound());
        BaseCode.resources.setSoundVolume(enemySpaceship1,
        		ConfigurationFileParser.getInstance().getSoundData().getEnemySpaceship1());
        BaseCode.resources.setSoundVolume(loseSound, ConfigurationFileParser.getInstance().getSoundData().getLoseSound());
        BaseCode.resources.setSoundVolume(winSound, ConfigurationFileParser.getInstance().getSoundData().getWinSound());
        BaseCode.resources.setSoundVolume(lifeLostSound,
        		ConfigurationFileParser.getInstance().getSoundData().getLifeLostSound());
        BaseCode.resources.setSoundVolume(portalEnter,
        		ConfigurationFileParser.getInstance().getSoundData().getPortalEnter());
        BaseCode.resources.setSoundVolume(portalExit, ConfigurationFileParser.getInstance().getSoundData().getPortalExit());
        BaseCode.resources.setSoundVolume(portalLoop, ConfigurationFileParser.getInstance().getSoundData().getPortalLoop());
        BaseCode.resources.setSoundVolume(shieldSound,
        		ConfigurationFileParser.getInstance().getSoundData().getShieldSound());
        BaseCode.resources.setSoundVolume(shotgunSound,
        		ConfigurationFileParser.getInstance().getSoundData().getShieldSound());
    }

    /**
     * Play.
     *
     * @param path the file path of the sound to play
     */
    public static void play(String path) {
        if ((soundPlay) && (!BaseCode.resources.isSoundPlaying(path))) {
            BaseCode.resources.playSound(path);
        }
    }

    /**
     * Play multi.
     *
     * @param path the file path of the sound to play
     */
    public static void playMulti(String path) {
        if (soundPlay) {
            BaseCode.resources.playSound(path);
            BaseCode.resources.setSoundVolume(paintSpraySound,
            		ConfigurationFileParser.getInstance().getSoundData().getPaintSpraySound());
        }
    }

    /**
     * Play loop.
     *
     * @param path the file path of the sound to play
     */
    public static void playLoop(String path) {
        if ((soundPlay) && (!BaseCode.resources.isSoundPlaying(path))) {
            BaseCode.resources.playSoundLooping(path);
        }
    }

    /**
     * Stop sound.
     *
     * @param path the file path of the sound to play
     */
    public static void stopSound(String path) {
        BaseCode.resources.stopSound(path);
    }

    /**
     * Play bg music.
     */
    public static void playBgMusic() {
        if (musicPlay) {
            BaseCode.resources.setSoundVolume(bgMusicPath,
            		ConfigurationFileParser.getInstance().getSoundData().getBgMusicPath());
            if (!bgStoppedOnce) {
                BaseCode.resources.playSoundLooping(bgMusicPath);
            }
        }
    }
    
    /**
     * Stop bg music.
     */
    public static void stopBgMusic() {
        if (!musicPlay) {
            BaseCode.resources.setSoundVolume(bgMusicPath, 0f);
            bgStoppedOnce = true;
        }
    }

    /**
     * Sets the sound.
     *
     * @param bool sets sound to true or false, false stops sound
     */
    public static void setSound(boolean bool) {
        soundPlay = bool;
        if (!bool) {
            DyeHardSound.stopSound(DyeHardSound.portalLoop);
            DyeHardSound.stopSound(DyeHardSound.shieldSound);
            DyeHardSound.stopSound(DyeHardSound.enemySpaceship1);
        }
    }

    /**
     * Gets the sound.
     *
     * @return the sound bool
     */
    public static boolean getSound() {
        return soundPlay;
    }

    /**
     * Sets the music.
     *
     * @param bool the new music
     */
    public static void setMusic(boolean bool) {
        musicPlay = bool;
    }

    /**
     * Gets the music.
     *
     * @return the music
     */
    public static boolean getMusic() {
        return musicPlay;
    }
}
