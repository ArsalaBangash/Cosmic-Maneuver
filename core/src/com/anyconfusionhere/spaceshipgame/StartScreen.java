package com.anyconfusionhere.spaceshipgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
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

public class StartScreen implements Screen {
    float WORLD_WIDTH = 1920;
    float WORLD_HEIGHT = 1080;
    SpriteBatch batch;
    public ImageButton easyButton, mediumButton, hardButton, menuButton;
    public com.anyconfusionhere.spaceshipgame.CosmicManeuver game;
    public Assets assets;
    OrthographicCamera camera;
    Viewport viewport;
    private String GAME_LEVEL = "";

    Stage stage;
    Skin radioButtonSkin;
    ImageButton.ImageButtonStyle radioButtonStyle;

    Skin getDiamondsSkin;
    TextureAtlas getDiamondsAtlas;
    ImageButton.ImageButtonStyle getDiamondsStyle;
    ImageButton getDiamondsButton;


    public StartScreen(CosmicManeuver game){
        this.game = game;
        GAME_LEVEL = assets.preferences.getString("LastDifficulty");
        Gdx.app.log("Whether", String.valueOf(GAME_LEVEL + "HELLO"));
        show();

    }
    @Override
    public void dispose() {
        stage.dispose();

    }

    @Override
    public void show() {

        game.adHandler.showAds(true);
        Gdx.input.setCatchBackKey(true);
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("neuropol.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 200;
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_WIDTH,WORLD_HEIGHT, camera);
        viewport.apply();
        stage = new Stage(viewport);
        stage.clear();

        Gdx.input.setInputProcessor(stage);


        DelayAction delayLabel3 = new DelayAction();
        delayLabel3.setDuration(3.0f);
        DelayAction delayLabel2 = new DelayAction();
        delayLabel2.setDuration(2.5f);
        DelayAction delayLabel1 = new DelayAction();
        delayLabel1.setDuration(12f);
        DelayAction delayButton = new DelayAction();
        delayButton.setDuration(3f);
        DelayAction delayAction = new DelayAction();
        delayAction.setDuration(4f);

        Label.LabelStyle gameOverFont = new Label.LabelStyle(assets.fontInstructionsPages,
                com.badlogic.gdx.graphics.Color.WHITE);
        Label.LabelStyle font = new Label.LabelStyle(assets.fontInstructions,
                com.badlogic.gdx.graphics.Color.WHITE);

        Label gameOverTitle = new Label("START GAME",gameOverFont);
        gameOverTitle.addAction(Actions.sequence(Actions.sequence(Actions.alpha(0),
                Actions.fadeIn(1.5f), Actions.moveBy(0, 290, 0.5f))));
        gameOverTitle.setPosition((1920 / 2 - 350) + 120, 1080 / 2 + 70);

        Label easyLabel = new Label("EASY", font);
        easyLabel.addAction(Actions.sequence(Actions.alpha(0f),
                delayLabel1, Actions.alpha(0f), Actions.fadeIn(0.5f)));
        easyLabel.setPosition(410 + 85, 710);

        Label mediumLabel = new Label("MEDIUM", font);
        mediumLabel.addAction(Actions.sequence(Actions.alpha(0f),
                delayLabel1, Actions.alpha(0f), Actions.fadeIn(0.5f)));
        mediumLabel.setPosition(780 + 85, 710);

        Label hardLabel = new Label("HARD", font);
        hardLabel.addAction(Actions.sequence(Actions.alpha(0f),
                delayLabel1,Actions.alpha(0f),Actions.fadeIn(0.5f)));
        hardLabel.setPosition(1250 + 85, 710);

        final Label highScoreLabel = new Label("HIGH SCORE: " +
                String.valueOf(assets.preferences.getInteger(GAME_LEVEL + "HighScore")), font);
        highScoreLabel.addAction(Actions.sequence(Actions.alpha(0f),
                delayLabel2, Actions.alpha(0f), Actions.fadeIn(0.5f)));
        highScoreLabel.setPosition(410, 500);

        Label levelLabel = new Label("FOR OPTIMAL GAME-PLAY, HOLD \nYOUR DEVICE AT A 45° or 0° ANGLE ",
                font);
        levelLabel.addAction(Actions.sequence(Actions.alpha(0f),
                delayLabel3,Actions.alpha(0f),Actions.fadeIn(0.5f)));
        levelLabel.setPosition(410, 300);

        Skin menuSkin = new Skin();
        TextureAtlas menuAtlas = new TextureAtlas("MenuButton.atlas");
        menuSkin.addRegions(menuAtlas);
        ImageButton.ImageButtonStyle menuStyle = new ImageButton.ImageButtonStyle();
        menuStyle.up = menuSkin.getDrawable("menuButtonPressed");
        menuStyle.down = menuSkin.getDrawable("menuButtonNotPressed");
        menuButton = new ImageButton(menuStyle);
        menuButton.setPosition(30, 220);

        menuButton.addAction(Actions.sequence(Actions.alpha(0), delayButton,
                Actions.alpha(0), Actions.fadeIn(1.5f)));
        menuButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("my app", "Pressed");
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new MainMenu(game));
                stage.clear();
            }
        });


        radioButtonSkin = new Skin();
        TextureAtlas radioButtonAtlas = new TextureAtlas("RadioButton.atlas");
        radioButtonSkin.addRegions(radioButtonAtlas);
        radioButtonStyle = new ImageButton.ImageButtonStyle();
        radioButtonStyle.up = radioButtonSkin.getDrawable("RadioNotPressed");
        radioButtonStyle.down = radioButtonSkin.getDrawable("RadioPressed");
        radioButtonStyle.checked = radioButtonStyle.down;
        radioButtonStyle.checkedOver = radioButtonStyle.up;
        easyButton = new ImageButton(radioButtonStyle);
        easyButton.addAction(Actions.sequence(Actions.alpha(0f),
                delayLabel1, Actions.alpha(0f), Actions.fadeIn(0.5f)));
        easyButton.setPosition(410, 700);


        mediumButton = new ImageButton(radioButtonStyle);
        mediumButton.addAction(Actions.sequence(Actions.alpha(0f),
                delayLabel1, Actions.alpha(0f), Actions.fadeIn(0.5f)));
        mediumButton.setPosition(780, 700);

        hardButton = new ImageButton(radioButtonStyle);
        hardButton.addAction(Actions.sequence(Actions.alpha(0f),
                delayLabel1,Actions.alpha(0f),Actions.fadeIn(0.5f)));
        hardButton.setPosition(1250, 700);

        easyButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                easyButton.setChecked(true);
                mediumButton.setChecked(false);
                hardButton.setChecked(false);
                GAME_LEVEL = "easy";
                highScoreLabel.setText("HIGH SCORE: " +
                        String.valueOf(assets.preferences.getInteger("easyHighScore")));
            }
        });

        mediumButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                easyButton.setChecked(false);
                mediumButton.setChecked(true);
                hardButton.setChecked(false);
                GAME_LEVEL = "medium";
                highScoreLabel.setText("HIGH SCORE: " +
                        String.valueOf(assets.preferences.getInteger("mediumHighScore")));


            }
        });
        hardButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                easyButton.setChecked(false);
                mediumButton.setChecked(false);
                hardButton.setChecked(true);
                GAME_LEVEL = "hard";
                highScoreLabel.setText("HIGH SCORE: " +
                        String.valueOf(assets.preferences.getInteger("hardHighScore")));

            }
        });

        if (GAME_LEVEL.equals("easy")) {
            easyButton.setChecked(true);
        } else if (GAME_LEVEL.equals("medium")) {
            mediumButton.setChecked(true);
        }else if (GAME_LEVEL.equals("hard")) {
            hardButton.setChecked(true);
        }


        Skin playSkin = new Skin();
        TextureAtlas playAtlas = new TextureAtlas("PlayButton.atlas");
        playSkin.addRegions(playAtlas);
        ImageButton.ImageButtonStyle playStyle = new ImageButton.ImageButtonStyle();
        playStyle.up = playSkin.getDrawable("PlayNotPressed");
        playStyle.down = playSkin.getDrawable("PlayPressed");
        ImageButton playButton = new ImageButton(playStyle);
        playButton.setPosition(1690, 220);


        playButton.addAction(Actions.sequence(Actions.alpha(0), delayButton, Actions.alpha(0),
                Actions.fadeIn(1.5f)));
        playButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                assets.preferences.putString("LastDifficulty", GAME_LEVEL);
                assets.preferences.flush();
                game.setScreen(new GameScreen(game, GAME_LEVEL));
                stage.clear();
            }
        });

        getDiamondsSkin = new Skin();
        getDiamondsAtlas = new TextureAtlas("getDiamondsButton.atlas");
        getDiamondsSkin.addRegions(getDiamondsAtlas);
        getDiamondsStyle = new ImageButton.ImageButtonStyle();
        getDiamondsStyle.up = getDiamondsSkin.getDrawable("GetDiamonds");
        getDiamondsStyle.down = getDiamondsSkin.getDrawable("GetDiamondsPressed");
        getDiamondsButton = new ImageButton(getDiamondsStyle);
        getDiamondsButton.setPosition(1700, 580);
        getDiamondsButton.setSize(182, 200);
        stage.addActor(getDiamondsButton);

        getDiamondsButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (game.playServices.isSignedIn()) {

                    int diamondsNumber = assets.preferences.getInteger("diamonds");
                    assets.preferences.putInteger("diamonds", diamondsNumber + 1);
                    assets.preferences.flush();
                    game.adHandler.showInterstitialAd(new Runnable() {
                        @Override
                        public void run() {
                        }
                    });
                }
            }
        });


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


        stage.addActor(gameOverTitle);
        stage.addActor(levelLabel);
        stage.addActor(easyButton);
        stage.addActor(mediumButton);
        stage.addActor(hardButton);
        stage.addActor(easyLabel);
        stage.addActor(mediumLabel);
        stage.addActor(hardLabel);
        stage.addActor(highScoreLabel);
        stage.addActor(playButton);
        stage.addActor(menuButton);
        stage.addActor(windowTop);
        stage.addActor(windowBottom);

    }

    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(camera.combined);
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            game.setScreen(new MainMenu(game));
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        batch.begin();
        if (assets.preferences.getInteger("backgroundCheck") == 0){
            batch.draw(assets.background, 0, 0, 1920, 1080);
        }
        assets.fontChange.draw(batch, "GET MORE \nDIAMONDS", 1700, 550);
        batch.end();
        stage.draw();


    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);

    }
}
