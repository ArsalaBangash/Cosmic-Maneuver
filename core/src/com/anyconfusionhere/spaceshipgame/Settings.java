package com.anyconfusionhere.spaceshipgame;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class Settings implements Screen {
    OrthographicCamera camera;
    Viewport viewport;
    public Assets assets;
    public CosmicManeuver game;
    SpriteBatch batch;
    Stage stage;
    float WORLD_WIDTH = 1920;
    float WORLD_HEIGHT = 1080;

    public Settings(CosmicManeuver g){
        game = g;
        show();
    }
    @Override
    public void show() {
        game.adHandler.showAds(true);
        Gdx.input.setCatchBackKey(true);
        if (assets.preferences.getInteger("musica") == 0) {
            if (!assets.menuBackgroundMusic.isPlaying()) {
                assets.menuBackgroundMusic.play();
            }
        }
        batch = new SpriteBatch();

        camera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_WIDTH,WORLD_HEIGHT, camera);
        viewport.apply();
        stage = new Stage(viewport);
        stage.clear();



        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        Gdx.input.setInputProcessor(stage);


        DelayAction delayLabel2 = new DelayAction();
        delayLabel2.setDuration(20f);
        DelayAction delayButton = new DelayAction();
        delayButton.setDuration(3f);


        Image windowTop = new Image(assets.windowTop);
        Image windowBottom = new Image(assets.windowBottom);

        windowTop.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(1.5f),
                Actions.moveBy(0, 290, 0.5f)));
        windowTop.setPosition((1920 / 2 - 700) + 70, 1080 / 2);
        windowTop.setWidth(1324);
        windowTop.setHeight(250);


        windowBottom.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(1.5f),
                Actions.moveBy(0, -300, 0.5f)));
        windowBottom.setPosition((1920 / 2 - 795) + 70, 1080 / 2 - 75);
        windowBottom.setWidth(1424);
        windowBottom.setHeight(102);
        stage.addActor(windowTop);
        stage.addActor(windowBottom);

        Label.LabelStyle settingsFont = new Label.LabelStyle(assets.fontInstructionsPages,
                com.badlogic.gdx.graphics.Color.WHITE);
        Label.LabelStyle font = new Label.LabelStyle(assets.fontInstructions,
                com.badlogic.gdx.graphics.Color.WHITE);

        Label settingsTitle = new Label("SETTINGS",settingsFont);
        settingsTitle.addAction(Actions.sequence(Actions.sequence(Actions.alpha(0),
                Actions.fadeIn(1.5f), Actions.moveBy(0, 290, 0.5f))));
        settingsTitle.setPosition((1920 / 2 - 350) + 170, 1080 / 2 + 55);

        Label musicLabel = new Label("MUSIC: ",font);
        musicLabel.addAction(Actions.sequence(Actions.alpha(0f),delayLabel2,
                Actions.alpha(0f),Actions.fadeIn(0.5f)));
        musicLabel.setPosition(690, 590);

        Label onLabel = new Label("ON", font);
        onLabel.addAction(Actions.sequence(Actions.alpha(0f),delayLabel2,
                Actions.alpha(0f),Actions.fadeIn(0.5f)));
        onLabel.setPosition(970, 590);

        Label offLabel = new Label("OFF",font);
        offLabel.addAction(Actions.sequence(Actions.alpha(0f),delayLabel2,
                Actions.alpha(0f),Actions.fadeIn(0.5f)));
        offLabel.setPosition(1170, 590);
        stage.addActor(settingsTitle);
        stage.addActor(musicLabel);
        stage.addActor(onLabel);
        stage.addActor(offLabel);
        Label backgroundLabel = new Label("BACKGROUND: ",font);
        backgroundLabel.addAction(Actions.sequence(Actions.alpha(0f),delayLabel2,
                Actions.alpha(0f),Actions.fadeIn(0.5f)));
        backgroundLabel.setPosition(420, 390);

        Label onBackgroundLabel = new Label("ON", font);
        onBackgroundLabel.addAction(Actions.sequence(Actions.alpha(0f),delayLabel2,
                Actions.alpha(0f),Actions.fadeIn(0.5f)));
        onBackgroundLabel.setPosition(970, 390);

        Label offBackgroundLabel = new Label("OFF",font);
        offBackgroundLabel.addAction(Actions.sequence(Actions.alpha(0f),delayLabel2,
                Actions.alpha(0f),Actions.fadeIn(0.5f)));
        offBackgroundLabel.setPosition(1170, 390);
        stage.addActor(backgroundLabel);
        stage.addActor(onBackgroundLabel);
        stage.addActor(offBackgroundLabel);


        Skin radioButtonSkin = new Skin();
        TextureAtlas radioButtonAtlas = new TextureAtlas("RadioButton.atlas");
        radioButtonSkin.addRegions(radioButtonAtlas);
        ImageButton.ImageButtonStyle radioButtonStyle = new ImageButton.ImageButtonStyle();
        radioButtonStyle.up = radioButtonSkin.getDrawable("RadioNotPressed");
        radioButtonStyle.down = radioButtonSkin.getDrawable("RadioPressed");
        radioButtonStyle.checked = radioButtonStyle.down;
        radioButtonStyle.checkedOver = radioButtonStyle.up;
        final ImageButton onButton = new ImageButton(radioButtonStyle);
        final ImageButton offButton = new ImageButton(radioButtonStyle);
        final ImageButton backgroundOn = new  ImageButton(radioButtonStyle);
        final ImageButton backgroundOff = new ImageButton(radioButtonStyle);
        stage.addActor(onButton);
        stage.addActor(offButton);
        stage.addActor(backgroundOn);
        stage.addActor(backgroundOff);

        backgroundOn.setPosition(1080, 390);
        backgroundOn.addAction(Actions.sequence(Actions.alpha(0f), delayLabel2,
                Actions.alpha(0f), Actions.fadeIn(0.5f)));
        backgroundOff.setPosition(1320, 390);
        backgroundOff.addAction(Actions.sequence(Actions.alpha(0f), delayLabel2,
                Actions.alpha(0f), Actions.fadeIn(0.5f)));
        onButton.setPosition(1080, 590);
        onButton.addAction(Actions.sequence(Actions.alpha(0f), delayLabel2,
                Actions.alpha(0f), Actions.fadeIn(0.5f)));
        offButton.setPosition(1320, 590);
        offButton.addAction(Actions.sequence(Actions.alpha(0f), delayLabel2,
                Actions.alpha(0f), Actions.fadeIn(0.5f)));

        backgroundOn.addListener( new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                backgroundOff.setChecked(false);
                backgroundOn.setChecked(true);
                assets.preferences.putInteger("backgroundCheck", 0);
                assets.preferences.flush();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });


        backgroundOff.addListener( new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                backgroundOff.setChecked(true);
                backgroundOn.setChecked(false);
                assets.preferences.putInteger("backgroundCheck", -1);
                assets.preferences.flush();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        onButton.addListener( new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                offButton.setChecked(false);
                onButton.setChecked(true);
                assets.preferences.putInteger("musica", 0);
                assets.preferences.flush();
                if (!assets.menuBackgroundMusic.isPlaying()) {
                assets.menuBackgroundMusic.play();
            }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });


        offButton.addListener( new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                offButton.setChecked(true);
                onButton.setChecked(false);
                assets.preferences.putInteger("musica", -1);
                assets.preferences.flush();
                assets.menuBackgroundMusic.stop();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        if (assets.preferences.getInteger("backgroundCheck") == 0) {
            backgroundOn.setChecked(true);
            backgroundOff.setChecked(false);
        } else {
            backgroundOn.setChecked(false);
            backgroundOff.setChecked(true);
        }
        if (assets.preferences.getInteger("musica") == 0) {
            onButton.setChecked(true);
            offButton.setChecked(false);
        } else{
            onButton.setChecked(false);
            offButton.setChecked(true);
        }


        Skin menuSkin = new Skin();
        TextureAtlas menuAtlas = new TextureAtlas("MenuButton.atlas");
        menuSkin.addRegions(menuAtlas);
        ImageButton.ImageButtonStyle menuStyle = new ImageButton.ImageButtonStyle();
        menuStyle.up = menuSkin.getDrawable("menuButtonPressed");
        menuStyle.down = menuSkin.getDrawable("menuButtonNotPressed");
        ImageButton menuButton = new ImageButton(menuStyle);
        menuButton.setPosition(30, 220);
        stage.addActor(menuButton);

        menuButton.addAction(Actions.sequence(Actions.alpha(0), delayButton, Actions.alpha(0), Actions.fadeIn(1.5f)));
        menuButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new MainMenu(game));
                stage.clear();
            }
        });

    }
    @Override
    public void render(float delta) {

        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            game.setScreen(new MainMenu(game));
        }
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        if (assets.preferences.getInteger("backgroundCheck") == 0){
            batch.draw(assets.background, 0, 0, 1920, 1080);
        }
        batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
