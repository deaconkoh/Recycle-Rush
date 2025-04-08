package screenManager;

import entityManager.EntityManager;
import gameMasterFolder.GameMaster1;
import soundManager.SoundManager;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ScreenFactory {
    private static ScreenFactory instance;

    private final GameMaster1 game;
    private final ScreenManager screenManager;
    private final EntityManager entityManager;
    private final SoundManager soundManager;

    private final Map<String, Supplier<AbstractScreen>> screenMap = new HashMap<>();

    private ScreenFactory(GameMaster1 game, ScreenManager screenManager,
                          EntityManager entityManager, SoundManager soundManager) {
        this.game = game;
        this.screenManager = screenManager;
        this.entityManager = entityManager;
        this.soundManager = soundManager;
        registerScreens(); // Register screens during initialization
    }

    public static ScreenFactory getInstance(GameMaster1 game, ScreenManager screenManager,
                                            EntityManager entityManager, SoundManager soundManager) {
        if (instance == null) {
            instance = new ScreenFactory(game, screenManager, entityManager, soundManager);
        }
        return instance;
    }

    private void registerScreens() {
        register("start", () -> new StartScreen(game, screenManager));
        register("game", () -> new InteractiveScreen(game, screenManager, entityManager));
        register("settings", () -> new SettingScreen(game, screenManager, soundManager));
        register("instructions", () -> new InstructionSceen(game, screenManager));
        register("mapselection", () -> new MapSelectionScreen(game, screenManager));
        register("howtoplay", () -> new HowToPlayScreen(game, screenManager));
        register("gameover", () -> new GameOverScreen(game, screenManager));
    }

    public void register(String name, Supplier<AbstractScreen> screenSupplier) {
        screenMap.put(name.toLowerCase(), screenSupplier);
    }

    public AbstractScreen createScreen(String name) {
        Supplier<AbstractScreen> supplier = screenMap.get(name.toLowerCase());
        if (supplier != null) {
            return supplier.get();
        }
        throw new IllegalArgumentException("Unknown screen: " + name);
    }
}
