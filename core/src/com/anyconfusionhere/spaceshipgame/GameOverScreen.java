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

public class GameOverScreen implements Screen {
    OrthographicCamera camera;
    Viewport viewport;
    final float WORLD_WIDTH = 1920;
    final float WORLD_HEIGHT = 1080;
    Stage stage;
    public CosmicManeuver game;
    public Assets assets;
    String GAME_LEVEL;
    int GAME_SCORE;
    SpriteBatch batch;



    public GameOverScreen(CosmicManeuver game, int score, String gameLevel){
        this.game = game;

        GAME_LEVEL = gameLevel;
        GAME_SCORE = score;
        game.adHandler.showInterstitialAd(new Runnable() {
            @Override
            public void run() {
            }
        });
        show();
    }
    @Override
    public void dispose() {
        stage.dispose();

    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        Gdx.input.setCatchBackKey(true);
        game.adHandler.showAds(true);
        assets.backgroundMusic.stop();
        if (assets.preferences.getInteger("musica") == 0) {
            if (!assets.menuBackgroundMusic.isPlaying()) {
                assets.menuBackgroundMusic.play();
            }
        }
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("neuropol.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 200;
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
        DelayAction delayLabe2 = new DelayAction();
        delayLabe2.setDuration(7.0f);
        DelayAction delayLabel1 = new DelayAction();
        delayLabel1.setDuration(2.0f);
        DelayAction delayButton = new DelayAction();
        delayButton.setDuration(3f);
        DelayAction delayAction = new DelayAction();
        delayAction.setDuration(4f);

        Label.LabelStyle gameOverFont = new Label.LabelStyle(assets.fontInstructionsPages,
                com.badlogic.gdx.graphics.Color.WHITE);
        Label.LabelStyle font = new Label.LabelStyle(assets.fontInstructions,
                com.badlogic.gdx.graphics.Color.WHITE);
        Label.LabelStyle leaderFont = new Label.LabelStyle(assets.fontChange2,
                com.badlogic.gdx.graphics.Color.valueOf("00f7fd"));


        Label gameOverTitle = new Label("GAME OVER",gameOverFont);
        gameOverTitle.addAction(Actions.sequence(Actions.sequence(Actions.alpha(0),
                Actions.fadeIn(1.5f), Actions.moveBy(0, 290, 0.5f))));
        gameOverTitle.setPosition((1920 / 2 - 350) + 120, 1080 / 2 + 70);

        Label gameOverLabel = new Label("SCORE: " + String.valueOf(GAME_SCORE),font);
        gameOverLabel.addAction(Actions.sequence(Actions.alpha(0f),delayLabel1,
                Actions.alpha(0f),Actions.fadeIn(0.5f)));
        gameOverLabel.setPosition(670, 700);

        Label highScoreLabel = new Label("High Score: " +
                String.valueOf(assets.preferences.getInteger(GAME_LEVEL + "HighScore")), font);
        highScoreLabel.addAction(Actions.sequence(Actions.alpha(0f),
                delayLabel2,Actions.alpha(0f),Actions.fadeIn(0.5f)));
        highScoreLabel.setPosition(670, 500);

        Label leaderLabel = new Label("LEADER\nBOARDS", leaderFont);
        leaderLabel.addAction(Actions.sequence(Actions.alpha(0f),
                delayLabe2,Actions.alpha(0f),Actions.fadeIn(0.5f)));
        leaderLabel.setPosition(1700, 440);

        Label levelLabel = new Label("Level: " + String.valueOf(GAME_LEVEL),font);
        levelLabel.addAction(Actions.sequence(Actions.alpha(0f),
                delayLabel3,Actions.alpha(0f),Actions.fadeIn(0.5f)));
        levelLabel.setPosition(670, 300);

        Skin playAgainSkin = new Skin();
        TextureAtlas playAgainAtlas = new TextureAtlas("playagainbutton.atlas");
        playAgainSkin.addRegions(playAgainAtlas);
        ImageButton.ImageButtonStyle playAgainStyle = new ImageButton.ImageButtonStyle();
        playAgainStyle.up = playAgainSkin.getDrawable("play again");
        playAgainStyle.down = playAgainSkin.getDrawable("play again pressed");
        ImageButton playAgainButton = new ImageButton(playAgainStyle);
        playAgainButton.setPosition(1690, 220);

        Skin leaderboardButtonSkin = new Skin();
        TextureAtlas leaderboardAtlas = new TextureAtlas("button_leaderboard.atlas");
        leaderboardButtonSkin.addRegions(leaderboardAtlas);
        ImageButton.ImageButtonStyle leaderboardStyle = new ImageButton.ImageButtonStyle();
        leaderboardStyle.up = leaderboardButtonSkin.getDrawable("leader");
        leaderboardStyle.down = leaderboardButtonSkin.getDrawable("leaderPressed");
        ImageButton leaderboardButton = new ImageButton(leaderboardStyle);
        leaderboardButton.setPosition(1700, 540);
        leaderboardButton.setSize(182, 200);
        leaderboardButton.addAction(Actions.sequence(Actions.alpha(0f),
                delayLabe2,Actions.alpha(0f),Actions.fadeIn(0.5f)));

        leaderboardButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (game.playServices.isSignedIn()) {
                    game.playServices.showScore(GAME_LEVEL);
                } else {
                    game.adHandler.toast("Please Sign In!");
                }
            }
        });


        playAgainButton.addAction(Actions.sequence(Actions.alpha(0),delayButton, Actions.alpha(0),
                Actions.fadeIn(1.5f)));
        playAgainButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GameScreen(game, GAME_LEVEL));
                stage.clear();
            }
        });


        Skin menuSkin = new Skin();
        TextureAtlas menuAtlas = new TextureAtlas("MenuButton.atlas");
        menuSkin.addRegions(menuAtlas);
        ImageButton.ImageButtonStyle menuStyle = new ImageButton.ImageButtonStyle();
        menuStyle.up = menuSkin.getDrawable("menuButtonPressed");
        menuStyle.down = menuSkin.getDrawable("menuButtonPressed");
        ImageButton menuButton = new ImageButton(menuStyle);
        menuButton.setPosition(30, 220);


        menuButton.addAction(Actions.sequence(Actions.alpha(0),delayButton, Actions.alpha(0),
                Actions.fadeIn(1.5f)));
        menuButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new MainMenu(game));
                stage.clear();
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
        stage.addActor(leaderLabel);
        stage.addActor(gameOverLabel);
        stage.addActor(highScoreLabel);
        stage.addActor(gameOverLabel);
        stage.addActor(playAgainButton);
        stage.addActor(menuButton);
        stage.addActor(windowTop);
        stage.addActor(windowBottom);
        stage.addActor(leaderboardButton);

    }

    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(camera.combined);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        batch.begin();
        batch.end();
        stage.draw();

        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            game.setScreen(new MainMenu(game));
        }
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