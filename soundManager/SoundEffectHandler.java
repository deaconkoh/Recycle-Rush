package soundManager;

import java.util.HashMap;
import java.util.Map;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundEffectHandler implements ISoundHandler{
    private final Map<String, Sound> soundMap = new HashMap<>();
    private float sfxVolume = 1.0f;
    private boolean muteStatus = false;
    
    @Override
    public void loadSound() {
        try {
            soundMap.put("clickSound", Gdx.audio.newSound(Gdx.files.internal("OnClickSound.wav")));
            soundMap.put("collisionSound", Gdx.audio.newSound(Gdx.files.internal("collision_sound_effect.mp3")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void playSound(String key) {
        if (soundMap.containsKey(key)) {
            soundMap.get(key).play(sfxVolume);
        }
    }
    
    @Override
	public void applyVolume(float volume) {
    	for (Sound so : soundMap.values()) {
    		sfxVolume = volume;
            so.stop();
        }
    }

    @Override
    public void dispose() {
        for (Sound sound : soundMap.values()) {
            sound.dispose();
        }
        soundMap.clear();
    }
    
    @Override
    public void unmute() {
    	muteStatus = false;
    	sfxVolume = 1.0f;
    }
    @Override
    public void mute() {
    	muteStatus = true;
    	sfxVolume = 0.0f;
    }

	@Override
	public void setVol(float value) {
		this.sfxVolume = Math.max(0.0f, Math.min(1.0f, value));
		
	}

	@Override
	public boolean getMuteStatus() {
		return muteStatus;
		
	}

	@Override
	public float getVolume() {
		return sfxVolume;
	}
    
    
    
}
