package com.anyconfusionhere.spaceshipgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.Random;


class MyShapeRenderer extends ShapeRenderer {

    public void roundedRect(float x, float y, float width, float height, float radius) {
        super.rect(x + radius, y + radius, width - 2 * radius, height - 2 * radius);

        super.rect(x + radius, y, width - 2 * radius, radius);
        super.rect(x + width - radius, y + radius, radius, height - 2 * radius);
        super.rect(x + radius, y + height - radius, width - 2 * radius, radius);
        super.rect(x, y + radius, radius, height - 2 * radius);

        super.arc(x + radius, y + radius, radius, 180f, 90f);
        super.arc(x + width - radius, y + radius, radius, 270f, 90f);
        super.arc(x + width - radius, y + height - radius, radius, 0f, 90f);
        super.arc(x + radius, y + height - radius, radius, 90f, 90f);
    }
}


public class GameScreen implements Screen {
    Stage stage;
    MyShapeRenderer msr;
    public Assets assets;
    OrthographicCamera camera;
    Viewport viewport;
    SpriteBatch batch;
    float alpha = 0.75f;
    float lastAccelerometerX = 0, currentAccelerometerX;
    float lastAccelerometerY = 0, currentAccelerometerY, startAccelerometerY;
    float coinX, coinY, heartX, heartY;
    Circle bombCircle, coinCircle, heartCircle;
    Rectangle bombRectangle, boxRectangle;
    com.anyconfusionhere.spaceshipgame.Asteroid asteroid1, asteroid2, asteroid3, asteroid4;
    ArrayList<com.anyconfusionhere.spaceshipgame.Asteroid> asteroidArrayList, asteroidsExploded;
    BitmapFont font;
    float timePassed = 0, asteroidTime = 0, mysteryExplosionTime = 0, progressBarTime = 0;
    public int score = 0;
    public int timeUntilAsteroid = 0;
    Random randomLetterGenerator;
    char letter;
    public boolean heartAppear = false, boxAppear = true, invincibility = false,
            alienSmaller = false, slowDownAlien = false, largerAlien = false, bombExploded = false,
            dead = false, firstRun = true, fontTimerStart, mysteryExplosionBoolean = false;
    int lives = 3, coinCount = 0;
    float timeSinceCollision = 3f;
    public float timeSinceBox = 11f, bombTimer = 0f;
    float timeHit;
    public boolean hit = false;
    public boolean explosionBoolean = false;
    public float toRemoveX, toRemoveY, toRemoveWidth, toRemoveHeight;
    public String power = "";
    com.anyconfusionhere.spaceshipgame.Alien alien;
    int coinsUntilHeart;
    int deltaCoins;
    float boxX, boxY;
    int boxItem;
    CosmicManeuver game;
    int bombs;
    int counter = 3;
    float time = 0f, time2 = 0f;
    float[] fontTimer = new float[4];
    float progressBarWidth;
    int fixedTimeUntilAsteroid;
    float timing = 0;
    int asteroidMinSpeed, asteroidMaxSpeed, asteroidScale, asteroidSizeRandom;
    int diamondsNumber;
    double diamondsNeeded = 1;
    double timesRevived = 0;
    Skin tickSkin;
    TextureAtlas tickAtlas;
    ImageButton.ImageButtonStyle tickStyle;
    ImageButton tickButton;
    ImageButton pauseButton;

    float LARGE_ALIEN_WIDTH = 165, LARGE_ALIEN_HEIGHT = 165;
    float NORMAL_ALIEN_WIDTH = 135, NORMAL_ALIEN_HEIGHT = 135;
    float SMALL_ALIEN_WIDTH = 68, SMALL_ALIEN_HEIGHT = 68;
    float SLOW_ALIEN_SPEED = 1 / 2f, NORMAL_ALIEN_SPEED = 1f;
    int POWER_UP_DURATION = 10;

    String GAME_LEVEL = "";
    public boolean buttonsVisible = false;


    public GameScreen(CosmicManeuver g, String level) {
        game = g;
        GAME_LEVEL = level;
        show();
    }

    public void show() {

        Gdx.input.setCatchBackKey(true);
        game.adHandler.showAds(false);
        assets.menuBackgroundMusic.stop();
        if (assets.preferences.getInteger("musica") == 0) {
            assets.backgroundMusic.play();
        }


        Label.LabelStyle labelFont = new Label.LabelStyle(assets.fontInstructions,
                com.badlogic.gdx.graphics.Color.WHITE);
        Label.LabelStyle bigLabelFont = new Label.LabelStyle(assets.fontInstructionsPages,
                com.badlogic.gdx.graphics.Color.WHITE);

        msr = new MyShapeRenderer();
        camera = new OrthographicCamera();
        viewport = new ScalingViewport(Scaling.fit, 1920, 1080, camera);
        viewport.apply();
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        bombCircle = new Circle();
        coinCircle = new Circle();
        heartCircle = new Circle();
        bombRectangle = new Rectangle();
        boxRectangle = new Rectangle();
        batch = new SpriteBatch();
        stage = new Stage(viewport);
        stage.clear();
        Gdx.input.setInputProcessor(stage);
        Gdx.graphics.setContinuousRendering(true);
        Gdx.graphics.requestRendering();
        diamondsNumber = assets.preferences.getInteger("diamonds");


        if (GAME_LEVEL.equals("easy")) {
            coinsUntilHeart = 5;
            deltaCoins = 4;
            bombs = 3;
            fixedTimeUntilAsteroid = 400;
            asteroidMinSpeed = 2;
            asteroidMaxSpeed = 6;
            asteroidScale = 1;
        } else if (GAME_LEVEL.equals("medium")) {
            coinsUntilHeart = 10;
            deltaCoins = 6;
            bombs = 2;
            fixedTimeUntilAsteroid = 250;
            asteroidMinSpeed = 4;
            asteroidMaxSpeed = 12;
            asteroidScale = 2;
        } else if (GAME_LEVEL.equals("hard")) {
            coinsUntilHeart = 15;
            deltaCoins = 10;
            bombs = 1;
            fixedTimeUntilAsteroid = 100;
            asteroidMinSpeed = 6;
            asteroidMaxSpeed = 18;
            asteroidScale = 3;
        }

        final Label mainMenuLabel = new Label("MAIN MENU", labelFont);
        mainMenuLabel.setPosition(30, 220);
        final Label playLabel = new Label("RETURN", labelFont);
        playLabel.setPosition(1600, 220);
        final Label pauseLabel = new Label("GAME PAUSED", bigLabelFont);
        pauseLabel.setPosition(1920 / 2 - 300, 1000);

        mainMenuLabel.setVisible(false);
        playLabel.setVisible(false);
        pauseLabel.setVisible(false);

        Skin playSkin = new Skin();
        TextureAtlas playAtlas = new TextureAtlas("PlayButton.atlas");
        playSkin.addRegions(playAtlas);
        ImageButton.ImageButtonStyle playStyle = new ImageButton.ImageButtonStyle();
        playStyle.up = playSkin.getDrawable("PlayNotPressed");
        playStyle.down = playSkin.getDrawable("PlayPressed");
        final ImageButton playButton = new ImageButton(playStyle);
        playButton.setPosition(1700, 20);
        playButton.setVisible(false);

        Skin menuSkin = new Skin();
        TextureAtlas menuAtlas = new TextureAtlas("MenuButton.atlas");
        menuSkin.addRegions(menuAtlas);
        ImageButton.ImageButtonStyle menuStyle = new ImageButton.ImageButtonStyle();
        menuStyle.up = menuSkin.getDrawable("menuButtonPressed");
        menuStyle.down = menuSkin.getDrawable("menuButtonNotPressed");
        final ImageButton menuButton = new ImageButton(menuStyle);
        menuButton.setPosition(30, 20);
        menuButton.setVisible(false);


        Skin pauseSkin = new Skin();
        TextureAtlas pauseAtlas = new TextureAtlas("pauseButton.atlas");
        pauseSkin.addRegions(pauseAtlas);
        ImageButton.ImageButtonStyle pauseStyle = new ImageButton.ImageButtonStyle();
        pauseStyle.up = pauseSkin.getDrawable("pause");
        pauseStyle.down = pauseSkin.getDrawable("pausePressed");
        pauseButton = new ImageButton(pauseStyle);
        pauseButton.setPosition(1700, 0);
        pauseButton.setSize(300,345);

        tickSkin = new Skin();
        tickAtlas = new TextureAtlas("tickButton.atlas");
        tickSkin.addRegions(tickAtlas);
        tickStyle = new ImageButton.ImageButtonStyle();
        tickStyle.up = tickSkin.getDrawable("tick");
        tickStyle.down = tickSkin.getDrawable("tickPressed");
        tickButton = new ImageButton(tickStyle);
        tickButton.setPosition(900, 100);
        tickButton.setSize(228, 250);
        tickButton.setVisible(false);

        tickButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                dead = false;
                pauseButton.setVisible(true);
                timeSinceCollision = 0f;
                hit = true;
                counter = 3;
                timesRevived++;
                tickButton.setVisible(false);
                diamondsNumber = diamondsNumber - (int) diamondsNeeded;
                diamondsNeeded = Math.pow(2, timesRevived);
                assets.preferences.putInteger("diamonds", diamondsNumber);
                assets.preferences.flush();
            }
        });


        pauseButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.graphics.setContinuousRendering(false);
                Gdx.graphics.requestRendering();
                playButton.setVisible(true);
                pauseButton.setVisible(false);
                menuButton.setVisible(true);
                buttonsVisible = true;
                mainMenuLabel.setVisible(true);
                playLabel.setVisible(true);
                pauseLabel.setVisible(true);


            }
        });

        playButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.graphics.setContinuousRendering(true);
                Gdx.graphics.requestRendering();
                pauseButton.setVisible(true);
                playButton.setVisible(false);
                menuButton.setVisible(false);
                mainMenuLabel.setVisible(false);
                playLabel.setVisible(false);
                pauseLabel.setVisible(false);
                buttonsVisible = false;

            }
        });

        menuButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.graphics.setContinuousRendering(true);
                Gdx.graphics.requestRendering();
                assets.backgroundMusic.stop();
                pauseButton.setVisible(true);
                menuButton.setVisible(false);
                playButton.setVisible(false);
                game.setScreen(new MainMenu(game));
                stage.clear();

            }
        });

        asteroidArrayList = new ArrayList<com.anyconfusionhere.spaceshipgame.Asteroid>();
        asteroidsExploded = new ArrayList<com.anyconfusionhere.spaceshipgame.Asteroid>();


        asteroid1 = new com.anyconfusionhere.spaceshipgame.Asteroid("b", 200, 140,
                asteroidMinSpeed, asteroidMaxSpeed);
        asteroid2 = new com.anyconfusionhere.spaceshipgame.Asteroid("a", 200, 140,
                asteroidMinSpeed, asteroidMaxSpeed);
        asteroid3 = new com.anyconfusionhere.spaceshipgame.Asteroid("c", 200, 140,
                asteroidMinSpeed, asteroidMaxSpeed);
        asteroid4 = new com.anyconfusionhere.spaceshipgame.Asteroid("d", 200, 140,
                asteroidMinSpeed, asteroidMaxSpeed);

        alien = new com.anyconfusionhere.spaceshipgame.Alien();
        asteroidArrayList.add(asteroid1);
        asteroidArrayList.add(asteroid2);
        asteroidArrayList.add(asteroid3);
        asteroidArrayList.add(asteroid4);


        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(3);


        for (int i = 0; i < asteroidArrayList.size(); i++) {
            asteroidArrayList.get(i).asteroidGenerate();
        }

        coinX = MathUtils.random(75, 1920 - 150);
        coinY = MathUtils.random(75, 1080 - 150);

        boxX = MathUtils.random(75, 1920 - 150);
        boxY = MathUtils.random(75, 1080 - 150);

        boxItem = MathUtils.random(5);

        startAccelerometerY = Gdx.input.getAccelerometerX();

        if ((startAccelerometerY >= 6.5 || startAccelerometerY <= 3.5) &&
                (startAccelerometerY >= 1.5 || startAccelerometerY <= -1)) {
            game.adHandler.toast("Note:Your Device may not be at optimal gaming position");

        }

        stage.addActor(pauseButton);
        stage.addActor(tickButton);
        stage.addActor(playButton);
        stage.addActor(menuButton);
        stage.addActor(mainMenuLabel);
        stage.addActor(pauseLabel);
        stage.addActor(playLabel);


    }

    public void hide() {
    }

    public void explosionAnimation(com.anyconfusionhere.spaceshipgame.Asteroid asteroid) {
        asteroid.bombTime += Gdx.graphics.getDeltaTime();
        batch.draw(assets.explosionAnimation.getKeyFrame(asteroid.bombTime, false),
                asteroid.asteroidX, asteroid.asteroidY, asteroid.width, asteroid.height);
    }

    public void explosionAnimation(float toRemoveX, float toRemoveY, float toRemoveWidth,
                                   float toRemoveHeight) {
        if (assets.explosionAnimation.isAnimationFinished(asteroidTime + 0.05f)) {
            explosionBoolean = false;
            asteroidTime = 0;
        } else {
            asteroidTime += Gdx.graphics.getDeltaTime();
            batch.draw(assets.explosionAnimation.getKeyFrame(asteroidTime, true), toRemoveX,
                    toRemoveY, toRemoveWidth, toRemoveHeight);
        }
    }

    public void atGameStart(float dt) {
        batch.draw(assets.background, 0, 0, 1920, 1080);
        assets.fontMain.draw(batch, String.valueOf(counter), 1920 / 2, 1080 / 2);
        time += Gdx.graphics.getDeltaTime();
        if (time > 1.5f) {
            counter = counter - 1;
            assets.fontMain.draw(batch, String.valueOf(counter), 1920 / 2, 1080 / 2);
            time = 0f;
        }
        if (counter == 0) {
            firstRun = false;
            counter = 3;
        }
    }

    public void render(float dt) {

        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            assets.backgroundMusic.stop();
            game.setScreen(new MainMenu(game));
        }
        camera.update();
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        stage.act();
        batch.begin();
        if (firstRun) {
            atGameStart(dt);
        } else {

            //////////////////////////////////////////////////////////////////////////////////////
            /////////////////////////////////SCORING /////////////////////////////////////////////
            //////////////////////////////////////////////////////////////////////////////////////


            if (coinCount == coinsUntilHeart) {
                heartX = MathUtils.random(75, 1920 - 150);
                heartY = MathUtils.random(75, 1080 - 150);
                heartCircle.set(heartX + 30, heartY + 30, 30);
                coinCount = 0;
                heartAppear = true;
                coinsUntilHeart += deltaCoins;
            }
            if (!dead) {
                score++;
            }
            timeUntilAsteroid++;
            timing += Gdx.graphics.getDeltaTime();
            if (timing > 45) {
                timing = 0;
                fixedTimeUntilAsteroid -= 50;
                asteroidMaxSpeed++;
                asteroidMinSpeed++;
                if (asteroidScale < 10) {
                    asteroidScale++;
                }
            }

            ///////////////////////////////////////////////////////////////////////////////////////
            /////////////////////////////////ACCELEROMETER DATA & BOUNDARY CHECK///////////////////
            ///////////////////////////////////////////////////////////////////////////////////////

            currentAccelerometerX = Gdx.input.getAccelerometerY();
            currentAccelerometerX = currentAccelerometerX * alpha + lastAccelerometerX * (1 - alpha);
            currentAccelerometerY = Gdx.input.getAccelerometerX() - startAccelerometerY;
            currentAccelerometerY = currentAccelerometerY * alpha + lastAccelerometerY * (1 - alpha);

            if (currentAccelerometerY < 0) {
                currentAccelerometerY = currentAccelerometerY * (1 - (startAccelerometerY / 10));
            }
            if (alien.alienX + alien.alienWidth > 1920 && currentAccelerometerX > 0) {
                currentAccelerometerX = 0f;
            }
            if (alien.alienX < 0 && currentAccelerometerX < 0f) {
                currentAccelerometerX = 0f;
            }
            if (alien.alienY < 0 && currentAccelerometerY > 0f) {
                currentAccelerometerY = 0f;
            }
            if (alien.alienY + alien.alienHeight > 1080 && currentAccelerometerY < 0) {
                currentAccelerometerY = 0f;
            }

            alien.alienX += (currentAccelerometerX) * 5 * alien.alienSpeed;
            alien.alienY -= (currentAccelerometerY) * 5 * alien.alienSpeed;


            batch.draw(assets.background, 0, 0, 1920, 1080);

            //////////////////////////////////////////////////////////////////////////////////////
            /////////////////////////////////ALIEN ANIMATION AND CIRCLE//////////////////////////
            /////////////////////////////////////////////////////////////////////////////////////

            if (!dead) {

                if (alienSmaller && timeSinceBox < POWER_UP_DURATION) {
                    alien.setHeight(SMALL_ALIEN_HEIGHT);
                    alien.setWidth(SMALL_ALIEN_WIDTH);
                } else if (largerAlien && timeSinceBox < POWER_UP_DURATION) {
                    alien.setHeight(LARGE_ALIEN_HEIGHT);
                    alien.setWidth(LARGE_ALIEN_WIDTH);
                } else {
                    alien.setHeight(NORMAL_ALIEN_HEIGHT);
                    alien.setWidth(NORMAL_ALIEN_WIDTH);
                }

                if (slowDownAlien && timeSinceBox < POWER_UP_DURATION) {
                    alien.setSpeed(SLOW_ALIEN_SPEED);
                } else {
                    alien.setSpeed(NORMAL_ALIEN_SPEED);
                }

                if (hit) {
                    batch.draw(assets.alienHitAnimation.getKeyFrame(timePassed, true),
                            alien.alienX, alien.alienY, alien.alienWidth, alien.alienHeight);
                } else if (invincibility && timeSinceBox < POWER_UP_DURATION) {
                    batch.draw(assets.shieldAlienAnimation.getKeyFrame(timePassed, true),
                            alien.alienX, alien.alienY, alien.alienWidth, alien.alienHeight);
                } else {
                    batch.draw(assets.alienAnimation.getKeyFrame(timePassed, true),
                            alien.alienX, alien.alienY, alien.alienWidth, alien.alienHeight);
                }
            }
            ///////////////////////////////////////////////////////////////////////////////////////
            /////////////////////////////////BOMB ANIMATION AND CIRCLE/////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////


            if (Gdx.input.justTouched() && !dead) {
                if (!buttonsVisible && !pauseButton.isPressed()) {
                    if (bombs > 0) {
                        bombs--;
                        assets.blastSound.play();
                        bombExploded = true;
                        bombTimer = 0f;
                        while (asteroidArrayList.size() > 0) {
                            com.anyconfusionhere.spaceshipgame.Asteroid asteroid =
                                    asteroidArrayList.get(asteroidArrayList.size() - 1);
                            asteroidsExploded.add(asteroid);
                            asteroidArrayList.remove(asteroid);
                        }
                    }
                }
            }
            if (bombTimer >= 13 / 26f) {
                bombExploded = false;
                bombTimer = 0f;
                asteroidsExploded.clear();
            }
            if (bombExploded) {
                bombTimer += Gdx.graphics.getDeltaTime();
                for (int i = 0; asteroidsExploded.size() > i; i++) {
                    com.anyconfusionhere.spaceshipgame.Asteroid asteroid = asteroidsExploded.get(i);
                    explosionAnimation(asteroid);
                }

            }
            /////////////////////////////////////////////////////////////////////////////////////
            /////////////////////////////////HEART AND COIN ANIMATION AND CIRCLE/////////////////
            /////////////////////////////////////////////////////////////////////////////////////

            coinCircle.set(coinX + (23), coinY + (23), 23);
            batch.draw(assets.coinAnimation.getKeyFrame(timePassed, true), coinX, coinY, 46, 46);
            if (heartAppear) {
                batch.draw(assets.heartAnimation.getKeyFrame(timePassed, true), heartX, heartY, 46, 46);
            }


            ////////////////////////////////////////////////////////////////////////////////////
            /////////////////////////////////ASTEROID GENERATION ANIMATION AND CIRCLE///////////
            ////////////////////////////////////////////////////////////////////////////////////

            if (timeUntilAsteroid == fixedTimeUntilAsteroid || asteroidArrayList.size() < 3) {
                if (asteroidArrayList.size() <= 12) {
                    randomLetterGenerator = new Random();
                    letter = (char) (randomLetterGenerator.nextInt(3) + 'a');
                    asteroidSizeRandom = MathUtils.random(asteroidScale);
                    asteroidArrayList.add(new com.anyconfusionhere.spaceshipgame.Asteroid(String.valueOf(letter),
                            200 + (asteroidScale * 20), 140 + (asteroidScale * 30),
                            asteroidMinSpeed, asteroidMaxSpeed));
                    asteroidArrayList.get(asteroidArrayList.size() - 1).asteroidGenerate();
                }
                timeUntilAsteroid = 0;
            }
            timePassed += Gdx.graphics.getDeltaTime();

            for (int i = 0; i < asteroidArrayList.size(); i++) {

                batch.draw(asteroidArrayList.get(i).AsteroidAnimation.getKeyFrame(timePassed, true),
                        asteroidArrayList.get(i).asteroidX, asteroidArrayList.get(i).asteroidY,
                        asteroidArrayList.get(i).width, asteroidArrayList.get(i).height);
            }
            for (int i = 0; i < asteroidArrayList.size(); i++) {

                asteroidArrayList.get(i).asteroidCircle.set(asteroidArrayList.get(i).asteroidX
                        + asteroidArrayList.get(i).width / 2,
                        asteroidArrayList.get(i).asteroidY + asteroidArrayList.get(i).height / 2,
                        asteroidArrayList.get(i).asteroidRadius);
            }
            for (int i = 0; i < asteroidArrayList.size(); i++) {
                com.anyconfusionhere.spaceshipgame.Asteroid current = asteroidArrayList.get(i);
                current.minSpeed = asteroidMinSpeed;
                current.maxSpeed = asteroidMaxSpeed;
                current.timeSinceBox += Gdx.graphics.getDeltaTime();
                current.asteroidUpdate();
                if (current.asteroidBoundary()) {
                    current.asteroidGenerate();
                }
            }
            ///////////////////////////////////////////////////////////////////////////////////////
            /////////////////////////////////BOX ANIMATION AND RECTANGLE///////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////

            if (boxAppear && timeSinceBox >= 10f) {
                boxRectangle.set(boxX, boxY, 54, 54);
                power = "";
                batch.draw(assets.boxAnimation.getKeyFrame(timePassed, true), boxX, boxY);
            }
            alien.alienCircle.set(alien.alienX + (alien.alienWidth / 2), alien.alienY +
                    (alien.alienHeight / 2), alien.alienWidth / 2);

            lastAccelerometerY = currentAccelerometerY;
            lastAccelerometerX = currentAccelerometerX;

            timeSinceCollision += Gdx.graphics.getDeltaTime();
            timeSinceBox += Gdx.graphics.getDeltaTime();
            if (hit) {
                timeHit += Gdx.graphics.getDeltaTime();
            }
            if (timeHit >= 3f) {
                hit = false;
                timeHit = 0f;
            }
            if (explosionBoolean) {
                explosionAnimation(toRemoveX, toRemoveY, toRemoveWidth, toRemoveHeight);
            }

            //////////////////////////////////////////////////////////////////////////////////////
            /////////////////////////////////INTERSECTORS/////////////////////////////////////////
            //////////////////////////////////////////////////////////////////////////////////////

            if (fontTimer[0] < 0.2f && fontTimerStart) {
                fontTimer[0] += dt;
                if (fontTimer[2] == 1f) {
                    assets.fontCoins.draw(batch, "+1000", fontTimer[1], fontTimer[3]);
                } else if (fontTimer[2] == 2f) {
                    assets.fontLives.draw(batch, "+1", fontTimer[1], fontTimer[3]);
                } else {
                    assets.fontCoins.draw(batch, "+5000", fontTimer[1], fontTimer[3]);
                }

            } else {
                fontTimerStart = false;
                fontTimer[0] = 0f;
                fontTimer[1] = 0f;
                fontTimer[2] = 0f;
            }
            if (!dead) {
                if (timeSinceCollision >= 3.0f) {
                    for (int i = 0; i < asteroidArrayList.size(); i++) {
                        if (Intersector.overlaps(asteroidArrayList.get(i).asteroidCircle,
                                alien.alienCircle)) {
                            if (!invincibility) {
                                if (lives > 0) {
                                    lives--;
                                } else {
                                    dead = true;
                                }
                                hit = true;
                                timeSinceCollision = 0f;
                            }
                            assets.blastSound.play();
                            toRemoveX = asteroidArrayList.get(i).asteroidX;
                            toRemoveY = asteroidArrayList.get(i).asteroidY;
                            toRemoveWidth = asteroidArrayList.get(i).width;
                            toRemoveHeight = asteroidArrayList.get(i).height;
                            asteroidArrayList.remove(i);
                            explosionBoolean = true;
                        }
                    }
                }
                if (mysteryExplosionTime > 24 / 26f) {
                    mysteryExplosionBoolean = false;
                }
                if (mysteryExplosionBoolean) {
                    mysteryExplosionTime += dt;
                    batch.draw(assets.mysteryExplosionAnimation.getKeyFrame(mysteryExplosionTime,
                            false), alien.alienX - 100f, alien.alienY - 100f, 400f, 400f);
                } else {
                    mysteryExplosionTime = 0f;
                }

                if (Intersector.overlaps(alien.alienCircle, coinCircle)) {
                    fontTimer[0] = 0f;
                    fontTimerStart = true;
                    fontTimer[1] = coinX;
                    fontTimer[2] = 1f;
                    fontTimer[3] = alien.alienY + (188);

                    assets.fontCoins.draw(batch, "+1000", coinX, alien.alienY + (188));

                    coinX = MathUtils.random(75, 1920 - (150));
                    coinY = MathUtils.random(75, 1080 - (150));
                    score += 1000;
                    coinCount++;
                    assets.coinSound.play();
                }
                if (Intersector.overlaps(alien.alienCircle, heartCircle)) {
                    fontTimer[0] = 0f;
                    fontTimer[1] = heartX;
                    fontTimer[2] = 2f;
                    fontTimerStart = true;
                    fontTimer[3] = alien.alienY + (188);
                    assets.fontLives.draw(batch, "+1", heartX, alien.alienY + (188));
                    heartAppear = false;
                    heartCircle.set(-100, -100, -1);
                    if (lives < 5) {
                        lives++;
                    }
                    assets.lifeSound.play();
                }
                if (Intersector.overlaps(alien.alienCircle, boxRectangle)) {
                    mysteryExplosionBoolean = true;
                    fontTimer[0] = 0f;
                    fontTimer[1] = boxX;
                    fontTimer[2] = 3f;
                    fontTimerStart = true;
                    fontTimer[3] = alien.alienY + (188);

                    boxAppear = false;
                    timeSinceBox = 0;
                    score += 5000;
                    assets.fontCoins.draw(batch, "+5000", boxX, alien.alienY + (188));
                    if (boxItem == 0) {
                        invincibility = true;
                        power = "INVINCIBILITY";
                    } else if (boxItem == 1) {
                        alienSmaller = true;
                        power = "SMALL SIZE ALIEN";
                    } else if (boxItem == 2) {
                        if (bombs < 3) {
                            bombs += 1;
                        }
                    } else if (boxItem == 3) {
                        slowDownAlien = true;
                        power = "SLOW ALIEN";
                    } else if (boxItem == 4) {
                        largerAlien = true;
                        power = "LARGE ALIEN";
                    }
                    boxX = MathUtils.random(75, 1920 - (150));
                    boxY = MathUtils.random(75, 1080 - (150));

                    boxRectangle.set(-100, -100, -1, -1);
                }
                if (timeSinceBox >= POWER_UP_DURATION) {
                    boxItem = MathUtils.random(4);
                    boxAppear = true;
                    invincibility = false;
                    alienSmaller = false;
                    slowDownAlien = false;
                    largerAlien = false;
                }
            }
            assets.fontMain.draw(batch, "Score: " + String.valueOf(score), 15, 1080 - 30);

            for (int i = 0; i < lives; i++) {
                batch.draw(assets.alienLife, 1920 - (90) - ((85) * i), 1080 - (80), 83, 77);
            }
            if (buttonsVisible == false) {
                for (int i = 0; i < bombs; i++) {
                    batch.draw(assets.bombTexture, (15) + ((100) * i), 15, 112, 110);
                }
            }

            if (dead) {
                power = "";
                if (diamondsNumber >= diamondsNeeded) {
                    pauseButton.setVisible(false);
                    Gdx.gl.glClearColor(0, 0, 0, 0);
                    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                    tickButton.setVisible(true);
                    assets.fontExtraLife.draw(batch, String.valueOf(counter), 600, 250);
                    stage.addActor(tickButton);
                    assets.fontMain.draw(batch, "YOU HAVE " + String.valueOf(diamondsNumber), 200 +
                            300, 540);
                    batch.draw(assets.diamondTexture, 640 + 300, 470, 100, 100);
                    batch.draw(assets.diamondTexture, 415 + 300, 330, 100, 100);
                    batch.draw(assets.windowMid, 100 + 300, 600, 1198, 250);
                    batch.draw(assets.windowBottom, 120 + 300, 80, 1116, 101);
                    assets.fontInstructionsPages.draw(batch, "Continue Playing?", 330 + 300, 700);

                    assets.fontMain.draw(batch, "USE " + String.valueOf((int) diamondsNeeded)
                            + "         TO KEEP PLAYING?", 200 + 300, 400);
                    time2 += Gdx.graphics.getDeltaTime();
                    if (time2 > 2f) {
                        counter--;
                        assets.fontExtraLife.draw(batch, String.valueOf(counter), 600, 250);
                        time2 = 0f;
                    }
                    if (counter == 0) {
                        game.playServices.submitScore(score, GAME_LEVEL);
                        game.setScreen(new GameOverScreen(game, score, GAME_LEVEL));
                        counter = 3;
                    }
                } else {
                    time2 += Gdx.graphics.getDeltaTime();
                    if (time2 > 2f) {
                        game.playServices.submitScore(score, GAME_LEVEL);
                        game.setScreen(new GameOverScreen(game, score, GAME_LEVEL));

                    }
                }
            }
        }
        batch.end();
        stage.draw();

        if (score > assets.preferences.getInteger(GAME_LEVEL + "HighScore")) {
            assets.preferences.putInteger(GAME_LEVEL + "HighScore", score);
            assets.preferences.flush();
        }
        if (!power.equals("")) {
            progressBarTime += dt;
            progressBarWidth = progressBarWidth + alien.alienWidth / 600;
            msr.setProjectionMatrix(camera.combined);
            msr.begin(ShapeRenderer.ShapeType.Filled);
            msr.setColor(Color.RED);
            msr.roundedRect(alien.alienX, alien.alienY - 30, progressBarWidth, 5, 5);
            msr.end();
        } else {
            progressBarWidth = 0f;
        }
    }

    public void dispose() {
        batch.dispose();
    }

    public void resume() {
    }

    public void pause() {
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
    }
}
