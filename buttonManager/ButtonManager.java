package buttonManager;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import screenManager.ScreenManager;

public class ButtonManager {

    private final ScreenManager screenManager;

    public ButtonManager(ScreenManager screenManager) {
        this.screenManager = screenManager;
    }

    public TextButton create(String label, String targetScreen) {
        TextButton button = new TextButton(label, screenManager.skin);
        button.addListener(screenManager.changeListener(() -> screenManager.loadScreen(targetScreen)));
        return button;
    }
    
    public TextButton create(String label, String targetScreen, Runnable action) {
        TextButton button = new TextButton(label, screenManager.skin);
        button.addListener(screenManager.changeListener(() -> screenManager.loadScreen(targetScreen)));
        button.addListener(screenManager.changeListener(action));
        return button;
    }

    public TextButton create(String label, Runnable action) {
        TextButton button = new TextButton(label, screenManager.skin);
        button.addListener(screenManager.changeListener(action));
        return button;
    }

    public TextButton createDisabled(String label) {
        TextButton button = new TextButton(label, screenManager.skin);
        button.setDisabled(true);
        return button;
    }
}
