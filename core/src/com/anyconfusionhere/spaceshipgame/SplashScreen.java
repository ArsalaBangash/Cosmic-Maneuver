package com.anyconfusionhere.spaceshipgame;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SplashScreen implements Screen {
    private SpriteBatch batch;
    private Texture ttrSplash;
    public Assets assets;
    boolean oneTimeAssetLoader = false;
    CosmicManeuver game;

    public SplashScreen(CosmicManeuver g) {
        super();
        batch = new SpriteBatch();
        ttrSplash = new Texture("anyconfusionhere.png");
        assets = new Assets();
        this.game = g;

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.adHandler.showAds(false);
        batch.begin();
        batch.draw(ttrSplash, Gdx.graphics.getWidth() / 2 - 538 / 2,
                Gdx.graphics.getHeight() / 2 - 301 / 2, 538, 301);
        batch.end();
        if (!oneTimeAssetLoader) {
            assets.load();
            oneTimeAssetLoader = true;
        }
        Gdx.app.log("Progress: ", String.valueOf(assets.assetManager.getProgress()));
        Gdx.app.log("Easy", String.valueOf(assets.preferences.getInteger("easyHighScore")));
        Gdx.app.log("Medium", String.valueOf(assets.preferences.getInteger("mediumHighScore")));
        Gdx.app.log("Hard", String.valueOf(assets.preferences.getInteger("hardHighScore")));

    }

    @Override
    public void hide() { }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) { }

    @Override
    public void dispose() {
        ttrSplash.dispose();
        batch.dispose();
    }
}
