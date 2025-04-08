package screenManager;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import gameMasterFolder.GameMaster1;

public class GameOverScreen extends AbstractScreen {
	private Texture backgroundTexture;
    private final GameMaster1 gameMaster;

    public GameOverScreen(GameMaster1 game, ScreenManager screenManager) {
        super(game, screenManager);
        this.gameMaster = game;
    }

    @Override
    public void show() {
        super.show(); 
        
        backgroundTexture = new Texture(Gdx.files.internal("blurBackground.png"));
        table.setBackground(new Image(backgroundTexture).getDrawable());
        
        gameMaster.saveScoreToLeaderboard();
        Table table = new Table();
        Table buttonTable = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // Font setup
        BitmapFont customFont = new BitmapFont(Gdx.files.internal("Font.fnt"));
        Label.LabelStyle customStyle = new Label.LabelStyle(customFont, Color.WHITE);
        
        BitmapFont defaultFont = new BitmapFont();
        Label.LabelStyle defaultStyle = new Label.LabelStyle(defaultFont, Color.WHITE);

        Label gameOverLabel = new Label("Game Over", customStyle);
        gameOverLabel.setFontScale(1.2f);

        int finalScore = gameMaster.getScore();
        Label scoreLabel = new Label("Final Score: " + finalScore, customStyle);
        scoreLabel.setFontScale(0.5f);
        
        // Display Leaderboard
        Label leaderboardTitle = new Label("Leaderboard", defaultStyle);
        leaderboardTitle.setFontScale(2.5f);
        
        Table leaderboardTable = new Table();
        ArrayList<Integer> scores = gameMaster.getScores();
        
        for (int i = 0; i < scores.size(); i++) {
            Label scoreEntry = new Label((i + 1) + ". " + scores.get(i), defaultStyle);
            scoreEntry.setFontScale(2f);
            leaderboardTable.add(scoreEntry).padBottom(10).row();
        }
        
        
        TextButton retryButton = buttonManager.create("Retry", () -> {
            gameMaster.resetGame();
            screenManager.loadScreen("game");
        });

        TextButton menuButton = buttonManager.create("Main Menu", "start");

        // Layout
        table.add(gameOverLabel).padBottom(40).row();
        table.add(scoreLabel).padBottom(40).row();
        table.add(leaderboardTitle).padBottom(20).row();
        table.add(leaderboardTable).padBottom(40).row();
        table.add(buttonTable).padTop(20).center().row();
        buttonTable.add(retryButton).width(150).height(60).padRight(30);  
        buttonTable.add(menuButton).width(150).height(60);
    }

    @Override
    protected void renderContent(float delta) {
        // No custom render content
    }
}
