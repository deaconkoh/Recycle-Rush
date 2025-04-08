package screenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import buttonManager.ButtonManager;
import gameMasterFolder.GameMaster1;

public abstract class AbstractScreen implements Screen {
    protected final GameMaster1 game;
    protected final ScreenManager screenManager;
    protected Stage stage;
    protected Table table;
    protected ButtonManager buttonManager;
    
    public AbstractScreen(GameMaster1 game, ScreenManager screenManager) {
        this.game = game;
        this.screenManager = screenManager;
    }

    @Override
    public void show() {
    	this.buttonManager = new ButtonManager(screenManager);
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        //game.batch.begin();
        renderContent(delta); // Delegate specific rendering to subclasses
        //game.batch.end();

        stage.act(delta);
        stage.draw();
    }

    protected abstract void renderContent(float delta);
    
    public void registerButton(Button button, String screenName, Runnable beforeSwitch) {
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (beforeSwitch != null) {
                    beforeSwitch.run();
                }
                System.out.println(screenName + " button clicked! Switching screen...");
                screenManager.loadScreen(screenName);
            }
        });
    }
    
    protected ChangeListener changeListener(Runnable action) {
        return new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                action.run();
            }
        };
    }


    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
    	if (stage != null) {
    		stage.dispose();
    	}
    }
}
