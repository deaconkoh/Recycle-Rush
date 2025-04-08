package soundManager;

import java.util.HashMap;
import java.util.Map;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicHandler implements ISoundHandler{
    private final Map<String, Music> musicMap = new HashMap<>();
    private Music currentMusic = null;
    private float musicVolume = 1.0f;
    private boolean muteStatus = false;
    
    @Override
    public void loadSound() {
        try {
            musicMap.put("MenuSound", Gdx.audio.newMusic(Gdx.files.internal("MenuMusic.mp3")));
            musicMap.put("GameSound", Gdx.audio.newMusic(Gdx.files.internal("InGameMusic.mp3")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void playSound(String key) {
        stopMusic();
        if (musicMap.containsKey(key)) {
            currentMusic = musicMap.get(key);
            currentMusic.setLooping(true);
            currentMusic.setVolume(musicVolume);
            currentMusic.play();
        }
    }
    
    

    public void stopMusic() {
        if (currentMusic != null) {
            currentMusic.stop();
            currentMusic = null;
        }
    }
  
    @Override
    public void applyVolume(float volume) {
        if (currentMusic != null) {
        	currentMusic.setVolume(volume);
        	//volumeSetter(volume);
        }
    }
    
    @Override
    public void dispose() {
        stopMusic();
        for (Music music : musicMap.values()) {
            music.dispose();
        }
        musicMap.clear();
    }

	@Override
	public void setVol(float value) {
		musicVolume = Math.max(0.0f, Math.min(1.0f, value));
		applyVolume(musicVolume);
		
	}

	@Override
	public void mute() {
		muteStatus = true;
		musicVolume = 0;
		applyVolume(musicVolume);
		
	}

	@Override
	public void unmute() {
		muteStatus = false;
		musicVolume = 1.0f;
		applyVolume(musicVolume);
		
	}

	@Override
	public boolean getMuteStatus() {
		return muteStatus;
		
	}

	@Override
	public float getVolume() {
		return musicVolume;
	}
   
}
