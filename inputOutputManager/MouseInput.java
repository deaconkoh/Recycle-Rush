package inputOutputManager;

import soundManager.SoundManager;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class MouseInput extends AbstractInput {
    private SoundManager soundManager;
    private Map<String, Runnable> mouseActions;

    public MouseInput(SoundManager soundManager) {
        this.soundManager = soundManager;
        if (soundManager != null) {
            defineActions();  // Only define actions if soundManager is set
        }
    }

    public void defineActions() {
        if (soundManager != null) {
            this.mouseActions = soundManager.getMouseSoundActions(); 
        }
    }

    public void setSoundManager(SoundManager soundManager) {
        this.soundManager = soundManager;
        defineActions();
    }

    @Override
    public void handleInput() {
        if (mouseActions != null && Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {  
            mouseActions.get("leftClick").run();  
        }
    }


	@Override
	void defineBindings() {
		setBinding("leftClick", Input.Keys.LEFT);
		
	}
}
