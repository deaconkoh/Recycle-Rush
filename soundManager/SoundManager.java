// SoundManager.java
package soundManager;

import java.util.Map;
import screenManager.ScreenManager;

public class SoundManager {
    private final ISoundHandler musicHandler;
    private final ISoundHandler soundEffectHandler;
    private final ScreenManager screenManager;
    private String lastPlayedMusic = null;
    private static final Map<String, String> screenSoundMap = Map.of(
            "start", "MenuSound",
            "game", "GameSound",
            "pause", "PauseSound",
            "settings", "MenuSound"
    );

    public SoundManager(ScreenManager screenManager) {
        this.musicHandler = new MusicHandler();
        this.soundEffectHandler = new SoundEffectHandler();
        this.screenManager = screenManager;
    }

    public void update() {
        String currentScreen = screenManager.getActiveScreen();
        if (currentScreen == null) { // Prevents unwanted crashes
            System.out.println("Warning: screenManager.getActiveScreen() returned null!");
            return;
        }
        // Get the song associated with the current screen
        String soundKey = screenSoundMap.getOrDefault(currentScreen, "MenuSound");
        if (lastPlayedMusic == null || !soundKey.equals(lastPlayedMusic)) { 
            musicHandler.playSound(soundKey);
            lastPlayedMusic = soundKey; // Store the actual song name
        }
    }

    public void setMusicVolume(float volume) {
        musicHandler.setVol(volume);
    }

    public void setSfxVolume(float volume) {
        soundEffectHandler.setVol(volume);
    }

    public float getMusicVolume() {
        return musicHandler.getVolume();
    }

    public float getSfxVolume() {
        return soundEffectHandler.getVolume();
    }

    public void muteMusic() {
        musicHandler.mute();
    }

    public void unmuteMusic() {
        musicHandler.unmute();
    }

    public void muteSfx() {
        soundEffectHandler.mute();
    }

    public void unmuteSfx() {
        soundEffectHandler.unmute();
    }

    public boolean isMusicMuted() {
        return musicHandler.getMuteStatus();
    }

    public boolean isSfxMuted() {
        return soundEffectHandler.getMuteStatus();
    }

    // Mouse Click Sound
    public Map<String, Runnable> getMouseSoundActions() {
        return Map.of(
            "leftClick", () -> {
                if (!soundEffectHandler.getMuteStatus() && soundEffectHandler.getVolume() > 0) {
                    soundEffectHandler.playSound("clickSound");
                }
            }
        );
    }

    public void playCollisionSound() {
        if (!soundEffectHandler.getMuteStatus() && soundEffectHandler.getVolume() > 0) {  
            System.out.println("Playing collision sound...");
            soundEffectHandler.playSound("collisionSound");
        } else {
            System.out.println("Collision sound not played. Muted or low volume.");
        }
    }

    public void dispose() {
        musicHandler.dispose();
        soundEffectHandler.dispose();
    }

	public void loadSounds() {
	    musicHandler.loadSound(); // Call from GameMaster1.create()
	    soundEffectHandler.loadSound();
	}
}
