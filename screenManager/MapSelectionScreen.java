package screenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import gameMasterFolder.GameMaster1;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;


public class MapSelectionScreen extends AbstractScreen {

    private Texture cityTexture;
    private Texture beachTexture;
    private BitmapFont titleFont;
    private Rectangle leftRect;
    private Rectangle rightRect;

    public MapSelectionScreen(GameMaster1 game, ScreenManager screenManager) {
        super(game, screenManager);
    }

    @Override
    public void show() {
        super.show();

        cityTexture = new Texture(Gdx.files.internal("citybackground.jpg"));
        beachTexture = new Texture(Gdx.files.internal("beachbackground.jpg"));

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        leftRect = new Rectangle(0, 0, screenWidth / 2f, screenHeight);
        rightRect = new Rectangle(screenWidth / 2f, 0, screenWidth / 2f, screenHeight);
        
        // Load the custom font
        titleFont = new BitmapFont(Gdx.files.internal("Font.fnt"));

        // Create a new LabelStyle using the font
        LabelStyle labelStyle = new LabelStyle();
        labelStyle.font = titleFont;
        
        // === UI Layout ===
        Label title = new Label("Select a Map", labelStyle);
        title.setAlignment(Align.center); 

 
        Table titleTable = new Table();
        titleTable.setFillParent(true);     
        titleTable.top();                   
        titleTable.add(title).padTop(80).center(); 

        stage.addActor(titleTable);

        stage.addActor(table);

        Texture backIcon = new Texture(Gdx.files.internal("back.png"));
        ImageButton backButton = new ImageButton(new TextureRegionDrawable(backIcon));
        backButton.addListener(changeListener(() -> screenManager.loadScreen("start")));

        Table topBar = new Table();
        topBar.top().left().setFillParent(true);
        topBar.add(backButton).pad(15).width(50).height(50);
        stage.addActor(topBar);

        // Input handling (use multiplexer so both UI and map clicks are handled)
        InputProcessor mapClickProcessor = new InputAdapter() {
            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                float y = Gdx.graphics.getHeight() - screenY;

                // If user clicked a UI element, ignore map selection
                if (stage.hit(screenX, y, true) != null) {
                    return false;
                }

                if (leftRect.contains(screenX, y)) {
                    game.setSelectedMap("city_park");
                    screenManager.loadScreen("game");
                    return true;
                } else if (rightRect.contains(screenX, y)) {
                    game.setSelectedMap("beach");
                    screenManager.loadScreen("game");
                    return true;
                }
                return false;
            }
        };

        //Combine both stage + custom input using InputMultiplexer
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);             // UI input
        multiplexer.addProcessor(mapClickProcessor); // Map selection input
        Gdx.input.setInputProcessor(multiplexer);
    }


    
    @Override
    protected void renderContent(float delta) {
        game.batch.begin();

        float halfWidth = Gdx.graphics.getWidth() / 2f;
        float height = Gdx.graphics.getHeight();

        game.batch.draw(cityTexture, 0, 0, halfWidth, height);
        game.batch.draw(beachTexture, halfWidth, 0, halfWidth, height);

        game.batch.end();
    }

    @Override
    public void render(float delta) {
        renderContent(delta);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        super.dispose();
        if (cityTexture != null) cityTexture.dispose();
        if (beachTexture != null) beachTexture.dispose();
    }
}
