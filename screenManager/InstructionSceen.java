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

public class InstructionSceen extends AbstractScreen {

    private Texture background;
    private Texture tray, bag, can, card, chips, crayon, foam, glass, backIcon;
    private BitmapFont font;
    private Window instructionWindow;

    public InstructionSceen(GameMaster1 game, ScreenManager screenManager) {
        super(game, screenManager);
    }

    private Table icon(Texture texture, String tooltipText) {
        Image img = new Image(texture);
        img.setScaling(Scaling.fit);
        img.setSize(100, 100);

        Label tooltipLabel = new Label(tooltipText, new Label.LabelStyle(font, Color.WHITE));
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
        tray = new Texture("tray.png");
        bag = new Texture("bag.png");
        can = new Texture("can.png");
        card = new Texture("card.png");
        chips = new Texture("chips.png");
        crayon = new Texture("crayon.png");
        foam = new Texture("foam.png");
        glass = new Texture("glass.png");
        backIcon = new Texture(Gdx.files.internal("back.png"));
        font = new BitmapFont(Gdx.files.internal("Font.fnt"));

        ImageButton backButton = new ImageButton(new TextureRegionDrawable(backIcon));
        backButton.addListener(changeListener(() -> screenManager.loadScreen("start")));
        Table topBar = new Table();
        topBar.top().left().setFillParent(true);
        topBar.add(backButton).pad(15).width(50).height(50);
        stage.addActor(topBar);

        Label recyclableLabel = new Label("♻️ Recyclable", new Label.LabelStyle(font, Color.GREEN));
        recyclableLabel.setFontScale(0.8f);
        recyclableLabel.setAlignment(Align.center);

        Label nonRecyclableLabel = new Label("❌ Non-Recyclable", new Label.LabelStyle(font, Color.RED));
        nonRecyclableLabel.setFontScale(0.8f);
        nonRecyclableLabel.setAlignment(Align.center);

        Table recyclableIcons = new Table();
        recyclableIcons.defaults().pad(10);
        recyclableIcons.add(icon(tray, "Egg tray"));
        recyclableIcons.add(icon(bag, "Plastic bag"));
        recyclableIcons.add(icon(can, "Aluminium can"));
        recyclableIcons.add(icon(crayon, "Crayon")).row();

        Table nonRecyclableIcons = new Table();
        nonRecyclableIcons.defaults().pad(10);
        nonRecyclableIcons.add(icon(card, "Credit card - Credit cards are composed of plastics and metals which are difficult to separate"));
        nonRecyclableIcons.add(icon(chips, "Chip bag - Chip bags are made out of mixed material, including aluminium and plastic"));
        nonRecyclableIcons.add(icon(foam, "Styrofoam - Expanded polystyrene is not recyclable"));
        nonRecyclableIcons.add(icon(glass, "Glass bottle - Recyclables need to be separated to prevent contamination"));

        Table recyclableBox = createIconBox(recyclableIcons, 420, 220);
        Table nonRecyclableBox = createIconBox(nonRecyclableIcons, 420, 220);

        Label descRecyclable = new Label(
                "These items can be placed in the recycling bin. They help reduce waste and protect the environment.",
                new Label.LabelStyle(font, Color.WHITE));
        descRecyclable.setWrap(true);
        descRecyclable.setFontScale(0.38f);
        descRecyclable.setAlignment(Align.center);

        Label descNonRecyclable = new Label(
                "These items should go in the trash. They cannot be recycled and may contaminate recyclables.",
                new Label.LabelStyle(font, Color.WHITE));
        descNonRecyclable.setWrap(true);
        descNonRecyclable.setFontScale(0.38f);
        descNonRecyclable.setAlignment(Align.center);

        Table leftCol = new Table();
        leftCol.add(recyclableLabel).padBottom(10).center().row();
        leftCol.add(recyclableBox).padBottom(10).center().row();
        leftCol.add(descRecyclable).width(420).center();

        Table rightCol = new Table();
        rightCol.add(nonRecyclableLabel).padBottom(10).center().row();
        rightCol.add(nonRecyclableBox).padBottom(10).center().row();
        rightCol.add(descNonRecyclable).width(420).center();

        Table combinedTable = new Table();
        combinedTable.defaults().pad(20).top();
        combinedTable.add(leftCol).width(440).top();
        combinedTable.add(rightCol).width(440).top();
        combinedTable.row();

        TextButton startButton = buttonManager.create("Start Game", "mapselection");
        combinedTable.add(startButton).colspan(3).center().padTop(30);

        instructionWindow = new Window("", screenManager.skin);
        instructionWindow.setMovable(false);
        instructionWindow.setModal(false);
        instructionWindow.setSize(1080, 670);
        instructionWindow.pad(30);
        instructionWindow.setPosition(
                (Gdx.graphics.getWidth() - instructionWindow.getWidth()) / 2f,
                (Gdx.graphics.getHeight() - instructionWindow.getHeight()) / 2f
        );
        instructionWindow.add(combinedTable).center();
        stage.addActor(instructionWindow);

        Label title = new Label("Learn to Recycle", new Label.LabelStyle(font, Color.WHITE));
        title.setFontScale(1f);
        Table titleTable = new Table();
        titleTable.setFillParent(true);
        titleTable.top().padTop(40);
        titleTable.add(title).center();
        stage.addActor(titleTable);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        if (instructionWindow != null) {
            instructionWindow.setPosition(
                    (width - instructionWindow.getWidth()) / 2f,
                    (height - instructionWindow.getHeight()) / 2f
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
        
        if (tray != null) tray.dispose();
        if (bag != null) bag.dispose();
        if (can != null) can.dispose();
        if (card != null) card.dispose();
        if (chips != null) chips.dispose();
        if (crayon != null) crayon.dispose();
        if (foam != null) foam.dispose();
        if (glass != null) glass.dispose();
        if (backIcon != null) backIcon.dispose();
        if (font != null) font.dispose();
    }
}
