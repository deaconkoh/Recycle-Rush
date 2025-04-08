package screenManager;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import entityManager.EntityManager;
import gameMasterFolder.GameMaster1;
import soundManager.SoundManager;

public class ScreenManager {
    private GameMaster1 game;
    private final HashMap<String, Screen> screens;
    private Screen currentScreen;
    private String activeScreen;
    public Skin skin;
    public BitmapFont font;
    private boolean isMultiplayer;
    private float gameTimeRemaining = 60f;

    public ScreenManager(GameMaster1 game) {
        this.game = game;
        this.screens = new HashMap<>();
    }

    public void init() {
        if (skin == null) {
            skin = new Skin(Gdx.files.internal("uiskin.json"));
        }
        if (font == null) {
            font = new BitmapFont();
            font.getData().setScale(2f);
        }
    }

    public void setGame(GameMaster1 game) {
        this.game = game;
    }

    public void addScreen(String name, EntityManager entityManager, SoundManager soundManager) {
        if (!screens.containsKey(name)) {
            AbstractScreen screen = ScreenFactory.getInstance(game, this, entityManager, soundManager).createScreen(name);
            screens.put(name, screen);
            System.out.println("Screen added: " + name);
        }
    }

    public void loadScreen(String name) {
        if (screens.containsKey(name)) {
            if (currentScreen != null) {
                currentScreen.hide();
            }

            if (name.equals("start")) {
                game.resetGame();
            }

            currentScreen = screens.get(name);
            game.setScreen(currentScreen);
            activeScreen = name;
            System.out.println("Switched to screen: " + name);
        } else {
            System.out.println("Screen not found: " + name);
        }
    }

    public String getActiveScreen() {
        return activeScreen;
    }

    public EventListener changeListener(Runnable action) {
        return new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                action.run();
            }
        };
    }

    public boolean getMultiplayer() {
        return isMultiplayer;
    }

    public void setMultiplayer(boolean value) {
        this.isMultiplayer = value;
    }

    public float getTimer() {
        return gameTimeRemaining;
    }

    public void setTimer(float value) {
        this.gameTimeRemaining = value;
    }

    public void dispose() {
        for (Screen screen : screens.values()) {
            if (screen != currentScreen) {
                screen.dispose();
            }
        }
        screens.clear();
        if (skin != null) skin.dispose();
        if (font != null) font.dispose();
    }
}
