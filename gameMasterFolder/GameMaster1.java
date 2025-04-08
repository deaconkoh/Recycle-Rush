package gameMasterFolder;

import java.util.ArrayList;
import java.util.Collections;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import MovementManager.MovementManager;
import entityManager.EntityManager;
import inputOutputManager.IOManager;
import screenManager.ScreenManager;
import soundManager.SoundManager;

public class GameMaster1 extends Game {

    public SpriteBatch batch;
    private final ScreenManager screenManager;
    private final IOManager ioManager;
    private final EntityManager entityManager;
    private final SoundManager soundManager;
    private int score;
    private Preferences prefs;
    private String selectedMap;

    public GameMaster1(ScreenManager screenManager,
            SoundManager soundManager,
            IOManager ioManager,
            MovementManager movementManager,
            EntityManager entityManager) {
			this.screenManager = screenManager;
			this.soundManager = soundManager;
			this.ioManager = ioManager;
			this.entityManager = entityManager;
			}


    @Override
    public void create() {
        batch = new SpriteBatch();
        score = 0;
        prefs = Gdx.app.getPreferences("Leaderboard");

        screenManager.init();  
        soundManager.loadSounds();
        
        setupScreens();  
    }

    private void setupScreens() {
        screenManager.addScreen("start", entityManager, soundManager);
        screenManager.addScreen("game", entityManager, soundManager);
        screenManager.addScreen("howtoplay", entityManager, soundManager);
        screenManager.addScreen("settings", entityManager, soundManager);
        screenManager.addScreen("instructions", entityManager, soundManager);
        screenManager.addScreen("mapselection", entityManager, soundManager);
        screenManager.addScreen("gameover", entityManager, soundManager);
        screenManager.loadScreen("start");
    }

    @Override
    public void render() {
        ioManager.handleInput();
        soundManager.update();
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        screenManager.dispose();
        soundManager.dispose();
    }

    public void setSelectedMap(String selectedMap) {
        this.selectedMap = selectedMap;
    }

    public String getSelectedMap() {
        return selectedMap;
    }

    public void updateScore(int amount) {
        score += amount;
        if (score < 0) score = 0;
        System.out.println("Score: " + score);
    }

    public int getScore() {
        return score;
    }

    public void saveScoreToLeaderboard() {
        if (prefs == null) return;
        ArrayList<Integer> scores = getScores();
        scores.add(score);
        Collections.sort(scores, Collections.reverseOrder());
        while (scores.size() > 5) {
            scores.remove(scores.size() - 1);
        }
        for (int i = 0; i < scores.size(); i++) {
            prefs.putInteger("score_" + i, scores.get(i));
        }
        prefs.flush();
    }

    public ArrayList<Integer> getScores() {
        ArrayList<Integer> scores = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            scores.add(prefs.getInteger("score_" + i, 0));
        }
        return scores;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void resetGame() {
        score = 0;
        entityManager.resetEntities();
        screenManager.setTimer(60f);
        screenManager.loadScreen("game");
    }

    public void endGame() {
        saveScoreToLeaderboard();
        resetGame();
        screenManager.loadScreen("gameover");
    }
}
