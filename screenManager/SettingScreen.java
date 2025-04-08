package screenManager;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import gameMasterFolder.GameMaster1;
import soundManager.SoundManager;

public class SettingScreen extends AbstractScreen {
    private SoundManager soundManager;
    private Slider musicSlider;
    private Slider sfxSlider;
    private CheckBox muteMusicCheckBox;
    private CheckBox muteSfxCheckBox;
    private Texture backgroundTexture;

    private TextButton backButton;

    public SettingScreen(GameMaster1 game, ScreenManager screenManager, SoundManager soundManager) {
        super(game, screenManager);
        this.soundManager = soundManager;
    }

    @Override
    public void show() {
        super.show(); // Initializes stage and buttonManager
        backgroundTexture = new Texture(Gdx.files.internal("blurBackground.png"));
        table.setBackground(new Image(backgroundTexture).getDrawable());
        
        Label titleLabel = new Label("Settings", screenManager.skin);
        titleLabel.setFontScale(2f);

        // Music Volume
        Label musicLabel = new Label("Music Volume:", screenManager.skin);
        musicSlider = new Slider(0f, 1f, 0.01f, false, screenManager.skin);
        musicSlider.setValue(soundManager.getMusicVolume());

        // SFX Volume
        Label sfxLabel = new Label("Sound Effects Volume:", screenManager.skin);
        sfxSlider = new Slider(0f, 1f, 0.01f, false, screenManager.skin);
        sfxSlider.setValue(soundManager.getSfxVolume());

        // Mute checkboxes
        muteMusicCheckBox = new CheckBox("Mute Music", screenManager.skin);
        muteMusicCheckBox.setChecked(soundManager.isMusicMuted());

        muteSfxCheckBox = new CheckBox("Mute Sound Effects", screenManager.skin);
        muteSfxCheckBox.setChecked(soundManager.isSfxMuted());

        backButton = buttonManager.create("Back", "start");

        // Listeners
        musicSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (!muteMusicCheckBox.isChecked()) {
                    soundManager.setMusicVolume(musicSlider.getValue());
                }
            }
        });

        sfxSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (!muteSfxCheckBox.isChecked()) {
                    soundManager.setSfxVolume(sfxSlider.getValue());
                }
            }
        });

        muteMusicCheckBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (muteMusicCheckBox.isChecked()) {
                    soundManager.muteMusic();
                    musicSlider.setDisabled(true);
                } else {
                    soundManager.unmuteMusic();
                    musicSlider.setDisabled(false);
                    musicSlider.setValue(soundManager.getMusicVolume());
                }
            }
        });

        muteSfxCheckBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (muteSfxCheckBox.isChecked()) {
                    soundManager.muteSfx();
                    sfxSlider.setDisabled(true);
                } else {
                    soundManager.unmuteSfx();
                    sfxSlider.setDisabled(false);
                    sfxSlider.setValue(soundManager.getSfxVolume());
                }
            }
        });

        // UI Layout
        table.add(titleLabel).colspan(2).padBottom(20).row();
        table.add(musicLabel).left();
        table.add(musicSlider).width(200).padBottom(10).row();
        table.add(muteMusicCheckBox).colspan(2).padBottom(10).row();
        table.add(sfxLabel).left();
        table.add(sfxSlider).width(200).padBottom(10).row();
        table.add(muteSfxCheckBox).colspan(2).padBottom(10).row();
        table.add(backButton).colspan(2).padTop(20);

        stage.addActor(table);
    }

    @Override
    protected void renderContent(float delta) {
        // UI handles rendering
    }
}
