package screenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import gameMasterFolder.GameMaster1;

public class HowToPlayScreen extends AbstractScreen {

    private Texture background;
    private Texture arrowkeys, wasdkeys, back;
    private BitmapFont font;
    private Window howtoplayWindow; 
    private Label.LabelStyle customStyle;

    public HowToPlayScreen(GameMaster1 game, ScreenManager screenManager) {
        super(game, screenManager);
    }

    private Table icon(Texture texture, String tooltipText) {
        Image img = new Image(texture);
        img.setScaling(Scaling.fit);
        img.setSize(100, 100);

        Label tooltipLabel = new Label(tooltipText, customStyle);
        tooltipLabel.setWrap(false);
        tooltipLabel.setFontScale(0.3f);
        tooltipLabel.setAlignment(Align.center);

        Table tooltipBox = new Table();
        tooltipBox.setBackground(screenManager.skin.newDrawable("white", new Color(0, 0, 0, 0.8f)));
        tooltipBox.add(tooltipLabel).pad(10);

        Tooltip<Table> tooltip = new Tooltip<>(tooltipBox);
        TooltipManager.getInstance().instant();
        img.addListener(tooltip);

        Table wrapper = new Table();
        wrapper.add(img).size(100, 100);
        return wrapper;
    }

    private Table createIconBox(Table icons, float boxWidth, float boxHeight) {
        Table box = new Table();
        box.setBackground(screenManager.skin.newDrawable("white", new Color(1, 1, 1, 0.05f)));
        box.add(icons).width(boxWidth).height(boxHeight).center();
        return box;
    }

    @Override
    public void show() {
        super.show();

        background = new Texture(Gdx.files.internal("background.jpg"));
        arrowkeys = new Texture("arrowkeys.png");
        wasdkeys = new Texture("wasdkeys.png");
        back = new Texture(Gdx.files.internal("back.png"));
        
        font = new BitmapFont(Gdx.files.internal("Font.fnt"));
        customStyle = new Label.LabelStyle(font, Color.WHITE);

        BitmapFont defaultFont = new BitmapFont();
        Label.LabelStyle defaultStyle = new Label.LabelStyle(defaultFont, Color.WHITE);

        ImageButton backButton = new ImageButton(new TextureRegionDrawable(back));
        backButton.addListener(changeListener(() -> screenManager.loadScreen("start")));
        Table topBar = new Table();
        topBar.top().left().setFillParent(true);
        topBar.add(backButton).pad(15).width(50).height(50);
        stage.addActor(topBar);

        Label singleplayerLabel = new Label("Player 1", customStyle);
        singleplayerLabel.setFontScale(0.5f);
        singleplayerLabel.setAlignment(Align.center);

        Label multiplayerLabel = new Label("Player 2", customStyle);
        multiplayerLabel.setFontScale(0.5f);
        multiplayerLabel.setAlignment(Align.center);

        Table singleplayerPic = new Table();
        singleplayerPic.defaults().pad(5);
        singleplayerPic.add(icon(wasdkeys, "WASD KEYS"));

        Table multiplayerPic = new Table();
        multiplayerPic.defaults().pad(5);
        multiplayerPic.add(icon(arrowkeys, "ARROW KEYS"));

        Table singleplayerBox = createIconBox(singleplayerPic, 420, 120);
        Table multiplayerBox = createIconBox(multiplayerPic, 420, 120);

        Label descSingle = new Label("W - Move Player 1 Up\nA - Move Player 1 Left\nS - Move Player 1 Down\nD - Move Player 1 Right\n",
        		defaultStyle);
        descSingle.setWrap(true);
        descSingle.setFontScale(2f);
        descSingle.setAlignment(Align.left);
     

        Label descMulti = new Label("^  - Move Player 2 Up\n< - Move Player 2 Left\nv - Move Player 2 Down\n> - Move Player 2 Right\n",
        		defaultStyle);
        descMulti.setWrap(true);
        descMulti.setFontScale(2f);
        descMulti.setAlignment(Align.left);

        Table leftColumn = new Table();
        leftColumn.add(singleplayerLabel).padBottom(5).center().row();
        leftColumn.add(singleplayerBox).padBottom(5).center().row();
        leftColumn.add(descSingle).width(420).center();

        Table rightColumn = new Table();
        rightColumn.add(multiplayerLabel).padBottom(5).center().row();
        rightColumn.add(multiplayerBox).padBottom(5).center().row();
        rightColumn.add(descMulti).width(420).center();

        Table combinedTable = new Table();
        combinedTable.defaults().pad(10).top();
        combinedTable.add(leftColumn).width(440).top();
        combinedTable.add(rightColumn).width(440).top();
        combinedTable.row();

        TextButton mainMenu = buttonManager.create("Main Menu", "start");
        combinedTable.add(mainMenu).colspan(3).center().padTop(25);

        howtoplayWindow = new Window("", screenManager.skin);
        howtoplayWindow.setMovable(false);
        howtoplayWindow.setModal(false);
        howtoplayWindow.setSize(1080, 670);
        howtoplayWindow.pad(25);
        howtoplayWindow.setPosition(
                (Gdx.graphics.getWidth() - howtoplayWindow.getWidth()) / 2f,
                (Gdx.graphics.getHeight() - howtoplayWindow.getHeight()) / 2f
        );
        howtoplayWindow.add(combinedTable).center();
        stage.addActor(howtoplayWindow);

        Label title = new Label("CONTROLS", customStyle);
        title.setFontScale(1f);
        Table titleTable = new Table();
        titleTable.setFillParent(true);
        titleTable.top().padTop(70);
        titleTable.add(title).center();
        stage.addActor(titleTable);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        if (howtoplayWindow != null) {
            howtoplayWindow.setPosition(
                    (width - howtoplayWindow.getWidth()) / 2f,
                    (height - howtoplayWindow.getHeight()) / 2f
            );
        }
    }

    @Override
    protected void renderContent(float delta) {
        game.batch.begin();
        if (background != null) {
            game.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
        game.batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        if (background != null) background.dispose();  // Null check for background texture
        if (wasdkeys != null) wasdkeys.dispose();  // Null check for wasdkeys texture
        if (arrowkeys != null) arrowkeys.dispose();  // Null check for arrowkeys texture
    }
}
