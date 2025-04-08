package soundManager;

public interface ISoundHandler {
	
	void loadSound();
	void playSound(String key);
	void applyVolume(float volume);
	void dispose();
	void setVol(float delta);
	void mute();
	void unmute();
	boolean getMuteStatus();
	float getVolume();
    
}
