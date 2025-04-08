package gameMasterFolder;

import MovementManager.MovementManager;
import entityManager.EntityManager;
import inputOutputManager.AbstractInput;
import inputOutputManager.IOManager;
import inputOutputManager.KeyboardInput;
import inputOutputManager.MouseInput;
import screenManager.ScreenManager;
import soundManager.SoundManager;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import java.util.ArrayList;
import java.util.List;

public class Lwjgl3Launcher {
    public static void main(String[] args) {
        if (StartupHelper.startNewJvmIfRequired()) return;
        createApplication();
    }

    private static Lwjgl3Application createApplication() {
        ScreenManager screenManager = new ScreenManager(null);
        SoundManager soundManager = new SoundManager(screenManager);
        EntityManager entityManager = new EntityManager(soundManager, true);
        MovementManager movementManager = new MovementManager(entityManager);

        MouseInput mouseInput = new MouseInput(null); 
        List<AbstractInput> inputs = new ArrayList<>();
        inputs.add(new KeyboardInput(movementManager, entityManager));
        inputs.add(mouseInput);

        IOManager ioManager = new IOManager(inputs);

        GameMaster1 game = new GameMaster1(screenManager, soundManager, ioManager, movementManager, entityManager);
        screenManager.setGame(game); 
        mouseInput.setSoundManager(soundManager); 

        return new Lwjgl3Application(game, getDefaultConfiguration());
    }

    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("OOP Project");
        config.useVsync(true);
        config.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate + 1);
        config.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());
        return config;
    }
}
