package screenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import entityManager.Entity;
import entityManager.EntityManager;
import entityManager.MainC;
import entityManager.SideC;
import entityManager.TextureObject;
import gameMasterFolder.GameMaster1;

public class InteractiveScreen extends AbstractScreen {
	
	private boolean showWarning = false; // Whether to show the warning text
	private float warningFlashTimer = 0f; // Timer for flashing effect
	private boolean timerStarted = false;
	private boolean multiplied = false; 
    private final EntityManager entityManager;
    private Texture citybackgroundTexture;
    private Texture beachbackgroundTexture;

    private BitmapFont scoreFont;

    private ImageButton pauseButton;
    private Table pauseOverlay;
    private boolean isPaused = false;

    public InteractiveScreen(GameMaster1 game, ScreenManager screenManager, EntityManager entityManager) {
        super(game, screenManager);
        this.entityManager = entityManager;
        this.scoreFont = new BitmapFont();
        scoreFont.getData().setScale(2f);
        scoreFont.setColor(Color.WHITE);
        
        initializeEntities();
 
    }

    @Override
    public void show() {
        super.show();
        
        citybackgroundTexture = new Texture(Gdx.files.internal("citybackground.jpg"));
        beachbackgroundTexture = new Texture(Gdx.files.internal("beachbackground.jpg"));

        scoreFont = new BitmapFont(Gdx.files.internal("Font.fnt"));
        scoreFont.getData().setScale(0.5f);
        
        // Pause Overlay setup
        pauseOverlay = new Table();
        pauseOverlay.setFillParent(true);
        pauseOverlay.setVisible(false);

        Window pauseWindow = new Window("", screenManager.skin); 
        pauseWindow.setMovable(true);
        pauseWindow.setModal(true);
        pauseWindow.setSize(400, 250);
        pauseWindow.pad(20);
        pauseWindow.defaults().pad(15).minWidth(200);

        Label pausedLabel = new Label("Paused", screenManager.skin);
        pausedLabel.setAlignment(Align.center);
        pausedLabel.setFontScale(1.5f);
        pauseWindow.add(pausedLabel).center().padBottom(20).row();

        TextButton resumeButton = buttonManager.create("Resume", this::hidePauseOverlay);
        TextButton backButton = buttonManager.create("Back", "start");

        pauseWindow.add(resumeButton).row();
        pauseWindow.add(backButton).row();
        
        pauseOverlay.add(pauseWindow).center();
        stage.addActor(pauseOverlay);

        Texture pauseIcon = new Texture(Gdx.files.internal("pause.png"));
        pauseButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(pauseIcon)));
        table.top().right().add(pauseButton).pad(20).width(60).height(60);
        pauseButton.addListener(changeListener(this::showPauseOverlay));
        backButton.addListener(changeListener(() ->{ isPaused = false;}));
    }


    private void showPauseOverlay() {
        isPaused = true;
        pauseOverlay.setVisible(true);
    }

    private void hidePauseOverlay() {
        isPaused = false;
        pauseOverlay.setVisible(false);
    }

    private void initializeEntities() {
        entityManager.addEntity(new MainC("bin.png", 0.3f, 100, 100, 350f));
        entityManager.addEntity(new SideC("bin2.png", 0.35f, 1800, 100, 350f));
        entityManager.addEntity(new TextureObject("tray.png", 300f, 0.3f, entityManager.getEntities(), 10));     // Increase score
        entityManager.addEntity(new TextureObject("bag.png", 300f, 0.8f, entityManager.getEntities(), 10));      // Increase score
        entityManager.addEntity(new TextureObject("can.png", 300f, 0.8f, entityManager.getEntities(), 10));      // Increase score
        entityManager.addEntity(new TextureObject("crayon.png", 200f, 0.8f, entityManager.getEntities(), 10));   // Increase score
        entityManager.addEntity(new TextureObject("card.png", 200f, 0.8f, entityManager.getEntities(), -10));    // Decrease score
        entityManager.addEntity(new TextureObject("chips.png", 200f, 0.8f, entityManager.getEntities(), -10));   // Decrease score
        entityManager.addEntity(new TextureObject("foam.png", 200f, 0.3f, entityManager.getEntities(), -10));    // Decrease score
        entityManager.addEntity(new TextureObject("glass.png", 200f, 0.3f, entityManager.getEntities(), -10));   // Decrease score
    }

    @Override
    public void renderContent(float delta) {
        game.batch.begin();
        
        // Background texture based on selected map
        String selectedMap = game.getSelectedMap();
        if ("city_park".equals(selectedMap)) {
            game.batch.draw(citybackgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        } else if ("beach".equals(selectedMap)) {
            game.batch.draw(beachbackgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
        // Draw score
        scoreFont.draw(game.batch, "Score: " + game.getScore(), 20, Gdx.graphics.getHeight() - 35);
        // Draw timer
        scoreFont.draw(game.batch, "Time: " + String.format("%.1f", screenManager.getTimer()) + "s", Gdx.graphics.getWidth() - 325, Gdx.graphics.getHeight() - 35); 
       
        if (screenManager.getTimer() < 25 && screenManager.getTimer() > 20 && showWarning) {
            scoreFont.setColor(Color.RED); // Change color to red
            scoreFont.draw(game.batch, "WARNING: SPEED UP SOON!", Gdx.graphics.getWidth() / 2 - 275, Gdx.graphics.getHeight() - 100);
            scoreFont.setColor(Color.WHITE); // Reset color
        }
        
        game.batch.end();
        
        // Start timer when the game starts
        if (!timerStarted) {
            startTimer();
        }

        // Pause and update logic
        if (!isPaused) {
            entityManager.updateEntities(game);
            if (timerStarted) {
                updateTimer(delta);
            }
        }
        entityManager.drawEntities(game.batch, screenManager.getMultiplayer());
    }

    public void startTimer() {
        if (!timerStarted) {
            timerStarted = true;
            screenManager.setTimer(60f); // Reset timer

            System.out.println("Timer Started!");
        }
    }
    
    private void updateTimer(float deltaTime) {
        if (screenManager.getTimer() > 0) {
            screenManager.setTimer(screenManager.getTimer() - deltaTime);
            resetMultiplied();
            
         // Flash "20 SECONDS REMAINING" when below 20s
            if (screenManager.getTimer() < 25) {
                warningFlashTimer += deltaTime;
                if (warningFlashTimer > 0.5f) { // Toggle every 0.5 seconds
                    showWarning = !showWarning;
                    warningFlashTimer = 0f; // Reset timer
                }
            }
  
            
        	if(screenManager.getTimer() < 20 && multiplied == false) {
	        	for (Entity entity : entityManager.getEntities()) { // Loop through all entities
	                if (entity instanceof TextureObject) { 
	                    ((TextureObject) entity).multiplySpeed(2f); // Increase speed
	                    multiplied = true;
	                }
	            }
        	}
        }
        else {
        	screenManager.setTimer(0f); // Prevent negative values
            if (timerStarted) {
                timerStarted = false;
                onTimeUp();
            }
        }
    }
    
    public void resetMultiplied() {
    	multiplied = false;
    	for (Entity entity : entityManager.getEntities()) { // Loop through all entities
            if (entity instanceof TextureObject) { 
                ((TextureObject) entity).resetSpeed(); // reset speed
            }
        }
    }
    
    private void onTimeUp() {
        System.out.println("Game Over! Time's up.");
        screenManager.loadScreen("gameover"); // Transition to a Game Over screen
    }
    
    @Override
    public void dispose() {
        super.dispose();
        if (citybackgroundTexture != null) citybackgroundTexture.dispose();
        if (beachbackgroundTexture != null) beachbackgroundTexture.dispose();
        if (scoreFont != null) scoreFont.dispose();
    }
}
