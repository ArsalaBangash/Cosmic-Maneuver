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
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;


public class Instructions implements Screen {
    OrthographicCamera camera;
    Viewport viewport;
    public Assets assets;
    public CosmicManeuver game;
    SpriteBatch batch;
    Stage stage;
    ImageButton.ImageButtonStyle arrowLeftButtonStyle, arrowRightButtonStyle;
    ImageButton arrowLeftButton, arrowRightButton;
    int page = 1;
    int pages = 4;
    float timePassed;
    com.anyconfusionhere.spaceshipgame.Asteroid asteroid1, asteroid2, asteroid3, asteroid4;
    ArrayList<com.anyconfusionhere.spaceshipgame.Asteroid> asteroidArrayList;
    final float WORLD_WIDTH = 1920;
    final float WORLD_HEIGHT = 1080;

    public Instructions(CosmicManeuver g){
        game = g;
        show();

    }
    @Override
    public void dispose() {
    }

    @Override
    public void show() {
        Gdx.input.setCatchBackKey(true);
        game.adHandler.showAds(false);
        Label.LabelStyle bigLabelFont = new Label.LabelStyle(assets.fontInstructionsPages,
                com.badlogic.gdx.graphics.Color.WHITE);
        final Label instructionsLabel = new Label("INSTRUCTIONS", bigLabelFont);
        instructionsLabel.setPosition((1920 / 2 - 300), 1080 - 205);


        batch = new SpriteBatch();

        camera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_WIDTH,WORLD_HEIGHT, camera);
        viewport.apply();
        stage = new Stage(viewport);
        stage.clear();



        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        Gdx.input.setInputProcessor(stage);
        asteroidArrayList = new ArrayList<com.anyconfusionhere.spaceshipgame.Asteroid>();

        asteroid1 = new com.anyconfusionhere.spaceshipgame.Asteroid("b", 240, 180,0,0);
        asteroid2 = new com.anyconfusionhere.spaceshipgame.Asteroid("a", 240, 180,0,0);
        asteroid3 = new com.anyconfusionhere.spaceshipgame.Asteroid("c", 240, 180,0,0);
        asteroid4 = new com.anyconfusionhere.spaceshipgame.Asteroid("d", 240, 180,0,0);


        asteroidArrayList.add(asteroid1);
        asteroidArrayList.add(asteroid2);
        asteroidArrayList.add(asteroid3);
        asteroidArrayList.add(asteroid4);


        ////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////LEFT BUTTON///////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////

        Skin buttonLeftSkin = new Skin();
        TextureAtlas buttonLeftAtlas = new TextureAtlas("left.atlas");
        buttonLeftSkin.addRegions(buttonLeftAtlas);
        arrowLeftButtonStyle = new ImageButton.ImageButtonStyle();
        arrowLeftButtonStyle.up = buttonLeftSkin.getDrawable("arrow_left");
        arrowLeftButtonStyle.down = buttonLeftSkin.getDrawable("arrow_left_green");
        arrowLeftButton = new ImageButton(arrowLeftButtonStyle);
        arrowLeftButton.setPosition(450, 50);
        arrowLeftButton.setSize(120, 157);
        arrowLeftButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                page--;

                Gdx.app.log("Width: ", String.valueOf(viewport.getWorldWidth()));
                Gdx.app.log("Height: ", String.valueOf(viewport.getWorldHeight()));
                Gdx.app.log("Density: ", String.valueOf(Gdx.graphics.getDensity()));
            }
        });

        ////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////RIGHT BUTTON//////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////

        Skin buttonRightSkin = new Skin();
        TextureAtlas buttonRightAtlas = new TextureAtlas("right.atlas");
        buttonRightSkin.addRegions(buttonRightAtlas);
        arrowRightButtonStyle = new ImageButton.ImageButtonStyle();
        arrowRightButtonStyle.up = buttonRightSkin.getDrawable("arrow_right");
        arrowRightButtonStyle.down = buttonRightSkin.getDrawable("arrow_right_green");
        arrowRightButton = new ImageButton(arrowRightButtonStyle);
        arrowRightButton.setPosition(1300, 50);
        arrowRightButton.setSize(120, 157);

        arrowRightButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (page < pages) {
                    page++;
                }
                Gdx.app.log("Width: ", String.valueOf(viewport.getWorldWidth()));
                Gdx.app.log("Height: ", String.valueOf(viewport.getWorldHeight()));
                Gdx.app.log("Density: ", String.valueOf((1920f / Gdx.graphics.getWidth())));

            }
        });
        stage.addActor(arrowLeftButton);
        stage.addActor(arrowRightButton);
        stage.addActor(instructionsLabel);
    }

    @Override
    public void render(float delta) {

        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            game.setScreen(new MainMenu(game));
        }
        camera.update();
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        stage.act();
        batch.begin();
        batch.draw(assets.windowMid, (1920 / 2 - 650) + 70, 1080 -250,1198,250);
        assets.fontInstructionsPages.draw(batch, "PAGE " + String.valueOf(page)
                + " / " + String.valueOf(pages),750, 150);

        timePassed += Gdx.graphics.getDeltaTime();

        ////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////PAGE 1////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////

        if (page == 0) {
            game.setScreen(new MainMenu(game));
        }

        if (page == 1) {

            assets.fontInstructions.draw(batch, "THIS IS YOUR SPACESHIP", 510, 820 - 100);
            batch.draw(assets.alienAnimation.getKeyFrame(timePassed, true), 1470 , 745 - 100, 135, 135);
            assets.fontInstructions.draw(batch, "ROTATE YOUR DEVICE TO MOVE YOUR SPACESHIP", 180, 705 - 100);
            for (int i = 0; i < asteroidArrayList.size(); i++) {
                batch.draw(asteroidArrayList.get(i).AsteroidAnimation.getKeyFrame(timePassed, true),
                        (300) + (300 * i), 440 - 100,
                        asteroidArrayList.get(i).width, asteroidArrayList.get(i).height);
            }
            assets.fontInstructions.draw(batch, "AVOID CRASHING INTO THE INCOMING ASTEROIDS", 180, 370 - 100);
        }

        ////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////PAGE 2////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////

        if (page == 2) {

            assets.fontInstructions.draw(batch, "YOU START THE GAME WITH 3 LIVES", 260, 820 - 100);
            batch.draw(assets.curved_arrow, 1510 + 40, 780 - 100,300, 225);

            for (int i = 0; i < 3;i++ ) {
                batch.draw(assets.alienLife, 1845 - (60 * i), 1020, 54, 54);
            }
            assets.fontInstructions.draw(batch, "COLLECTING A COIN    NETS YOU 1000 POINTS", (258), 630);
            assets.fontInstructions.draw(batch, "COLLECTING A HEART    NETS YOU 1 EXTRA LIFE", (258), 555);
            batch.draw(assets.coinAnimation.getKeyFrame(timePassed, true),933, 610, 50,50);
            batch.draw(assets.heartAnimation.getKeyFrame(timePassed, true),1010, 510);

            assets.fontInstructions.draw(batch, "TOUCH THE SCREEN TO ACTIVATE A BOMB", (258), 370);
            assets.fontInstructions.draw(batch, "BOMBS DESTROY ALL ON-SCREEN ASTEROIDS", (258), 310);
            batch.draw(assets.curved_arrow, 19, 125,300,225,0,0,400, 300, true, true);
            batch.draw(assets.bombTexture, (15), 15, 112, 110);

        }

        if (page == 3) {

            assets.fontInstructions.draw(batch, "MYSTERY BOXES NET YOU 5000 POINTS!", 180, 820 - 100);
            batch.draw(assets.boxAnimation.getKeyFrame(timePassed, true), 1650 , 745 - 100, 135, 135);
            assets.fontInstructions.draw(batch, "HOWEVER THEY MAY COME AT A COST", 180, 705 - 100);
            assets.fontInstructions.draw(batch, "YOU MAY RECEIVE A BOOST OR A NERF", (180), 555);

            assets.fontInstructions.draw(batch, "BOOSTS: EXTRA BOMB, INVINCIBILITY, SMALL SHIP", (180), 410);
            assets.fontInstructions.draw(batch, "NERFS: LARGE SHIP, SLOW ALIEN", (180), 310);

        }

        if (page == 4) {

            assets.fontInstructions.draw(batch, "AFTER YOU LOSE ALL THREE LIVES,", 180, 820 - 100);
            batch.draw(assets.diamondTexture, 1470 , 645, 135, 135);
            assets.fontInstructions.draw(batch, "DIAMONDS WILL ALLOW YOU TO CONTINUE", 180, 705 - 100);
            assets.fontInstructions.draw(batch, "YOUR GAME BY REVIVING YOUR SPACE SHIP", (180), 555);

            assets.fontInstructions.draw(batch, "USE DIAMONDS TO ACHIEVE HIGHER SCORES", (180), 410);
            assets.fontInstructions.draw(batch, "GET MORE DIAMONDS VIA THE MAIN MENU", (180), 310);

        }

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
