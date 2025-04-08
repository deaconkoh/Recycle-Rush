package screenManager;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import gameMasterFolder.GameMaster1;

public class StartScreen extends AbstractScreen {
    private Texture backgroundTexture;

    public StartScreen(GameMaster1 game, ScreenManager screenManager) {
        super(game, screenManager);
    }

    @Override
    public void show() {
        super.show(); // Initializes table, stage, and buttonManager

        backgroundTexture = new Texture(Gdx.files.internal("background.jpg"));
        table.setBackground(new Image(backgroundTexture).getDrawable());
        
        // Title label
        Label titleLabel = new Label("Recycle Rush", screenManager.skin);
        titleLabel.setFontScale(3.5f);
        titleLabel.setAlignment(Align.center);
        
        TextButton playButton = buttonManager.create("Single Player", "instructions", () -> {
            screenManager.setMultiplayer(false);
        });
        TextButton multiplayerButton = buttonManager.create("MultiPlayer", "instructions", () -> {
            screenManager.setMultiplayer(true);
        });
        
        TextButton howtoplayButton = buttonManager.create("How To Play", "howtoplay"); 
        
        TextButton settingsButton = buttonManager.create("Settings", "settings");
        TextButton quitButton = buttonManager.create("Quit", () -> {
            System.out.println("Quitting game...");
            Gdx.app.exit();
        });

     // Layout
        table.add(titleLabel).expandX().padBottom(50).padTop(0).center().row();
        table.add(playButton).padBottom(35).width(150).height(60).row();
        table.add(multiplayerButton).padBottom(35).width(150).height(60).row();
        table.add(howtoplayButton).padBottom(35).width(150).height(60).row();
        table.add(settingsButton).padBottom(35).width(150).height(60).row();
        table.add(quitButton).padBottom(35).width(150).height(60).row();
    }

    @Override
    public void renderContent(float delta) {
        // No custom rendering needed; UI handled by stage
    }

    @Override
    public void dispose() {
        super.dispose();
        if (backgroundTexture != null) {
            backgroundTexture.dispose();
        }
    }
}
