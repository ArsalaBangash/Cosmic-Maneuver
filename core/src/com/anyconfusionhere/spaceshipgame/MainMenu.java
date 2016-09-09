package com.anyconfusionhere.spaceshipgame;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainMenu implements Screen {
    SpriteBatch batch, logoBatch;
    public ImageButton startGame, instructionsButton;
    public CosmicManeuver game;
    public Assets assets;
    OrthographicCamera camera;
    Viewport viewport;
    boolean startStage = false;
    float transparency = 1f;
    Stage stage;
    Skin buttonSkin, instructionsButtonSkin;
    ImageButton.ImageButtonStyle style,instructionsStyle;
    Texture gameLogo;


    Skin getDiamondsSkin;
    TextureAtlas getDiamondsAtlas;
    ImageButton.ImageButtonStyle getDiamondsStyle;
    ImageButton getDiamondsButton, signOutButton, signInButton;


    public MainMenu(CosmicManeuver g){
        game = g;
        create();

    }

    public void hide(){

    }
    public void show(){

    }

    public void create() {
        Gdx.input.setCatchBackKey(true);
        game.adHandler.showAds(true);
        batch = new SpriteBatch();
        logoBatch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(1920,1080, camera);
        viewport.apply();
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        stage.clear();
        gameLogo = new Texture("anyconfusionhere.png");

        buttonSkin = new Skin();
        TextureAtlas buttonAtlas = new TextureAtlas("startNewButton.atlas");
        buttonSkin.addRegions(buttonAtlas);
        style = new ImageButton.ImageButtonStyle();
        style.up = buttonSkin.getDrawable("startgame");
        style.down = buttonSkin.getDrawable("startgamepressed");
        startGame =  new ImageButton(style);
        startGame.setPosition(520, 540);
        stage.addActor(startGame);

        instructionsButtonSkin = new Skin();
        TextureAtlas instructionsAtlas = new TextureAtlas("instructionsNewButton.atlas");
        instructionsButtonSkin.addRegions(instructionsAtlas);
        instructionsStyle = new ImageButton.ImageButtonStyle();
        instructionsStyle.up = instructionsButtonSkin.getDrawable("instructions");
        instructionsStyle.down = instructionsButtonSkin.getDrawable("instructionspressed");
        instructionsButton = new ImageButton(instructionsStyle);
        instructionsButton.setPosition(630, 360);
        stage.addActor(instructionsButton);


        Skin settingsButtonSkin = new Skin();
        TextureAtlas settingsButtonAtlas = new TextureAtlas("settingsNewButton.atlas");
        settingsButtonSkin.addRegions(settingsButtonAtlas);
        ImageButton.ImageButtonStyle settingsStyle = new ImageButton.ImageButtonStyle();
        settingsStyle.up = settingsButtonSkin.getDrawable("settingsbutton");
        settingsStyle.down = settingsButtonSkin.getDrawable("settingsbuttonpressed");
        final ImageButton settingsButton =  new ImageButton(settingsStyle);
        settingsButton.setPosition(760, 180);
        stage.addActor(settingsButton);

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

        Skin signInSkin = new Skin();
        TextureAtlas signInAtlas = new TextureAtlas("button_sign.atlas");
        signInSkin.addRegions(signInAtlas);
        ImageButton.ImageButtonStyle signInStlye = new ImageButton.ImageButtonStyle();
        signInStlye.up = signInSkin.getDrawable("sign_in");
        signInStlye.down = signInSkin.getDrawable("sign_in_pressed");
        signInButton =  new ImageButton(signInStlye);
        signInButton.setPosition(1444, 936);
        signInButton.setSize(476, 144);
        stage.addActor(signInButton);

        Skin signOutSkin = new Skin();
        TextureAtlas signOutAtlas = new TextureAtlas("sign_out_button.atlas");
        signOutSkin.addRegions(signOutAtlas);
        ImageButton.ImageButtonStyle signOutStlye = new ImageButton.ImageButtonStyle();
        signOutStlye.up = signOutSkin.getDrawable("signout");
        signOutStlye.down = signOutSkin.getDrawable("signout_pressed");
        signOutButton =  new ImageButton(signOutStlye);
        signOutButton.setPosition(1444, 936);
        signOutButton.setSize(476, 144);
        stage.addActor(signOutButton);
        signOutButton.setVisible(false);
        signOutButton.setDisabled(true);
        signOutButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.playServices.signOut();

            }
        });


        signInButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.playServices.signIn();

            }
        });

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
                } else {
                    game.adHandler.toast("Please Sign In!");
                }
            }
        });

        settingsButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("my app", "Pressed");
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                startGame.remove();
                instructionsButton.remove();
                settingsButton.remove();
                startStage = true;
                game.setScreen(new Settings(game));

            }

        });

        startGame.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("my app", "Pressed");
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                startGame.remove();
                instructionsButton.remove();
                settingsButton.remove();
                startStage = true;
                game.setScreen(new StartScreen(game));

            }

        });

        instructionsButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("my app", "Pressed");
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new Instructions(game));
            }
        });


    }
    public void render(float ok) {

        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            Gdx.app.exit();
        }

        if (assets.preferences.getInteger("musica") == 0) {
            if (!assets.menuBackgroundMusic.isPlaying()) {
                assets.menuBackgroundMusic.play();
            }
        }
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        logoBatch.setProjectionMatrix(camera.combined);
        stage.act();
        batch.begin();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (assets.preferences.getInteger("backgroundCheck") == 0){
            batch.draw(assets.background, 0, 0, 1920, 1080);
        }
        assets.fontInstructions.draw(batch, "COSMIC MANEUVER", 1920 / 2 - 500, 840);
        assets.fontChange.draw(batch, "GET MORE \nDIAMONDS", 1700, 550);
        batch.end();
        logoBatch.begin();
        if (transparency >= 0) {
            logoBatch.draw(gameLogo, 20, 930, 269, 150);
        }
        if (game.playServices.isSignedIn()){
            signInButton.setVisible(false);
            signInButton.setDisabled(true);
            signOutButton.setVisible(true);
            signOutButton.setDisabled(false);
        }else{
            signInButton.setVisible(true);
            signInButton.setDisabled(false);
            signOutButton.setVisible(false);
            signOutButton.setDisabled(true);
        }


        transparency -= 0.003;
        logoBatch.setColor(1.0f, 1.0f, 1.0f, transparency);
        logoBatch.end();
        stage.draw();


    }
    public void dispose(){
        stage.clear();
        stage.dispose();

    }
    public void resume(){


    }
    public void pause(){


    }
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
    }



}
