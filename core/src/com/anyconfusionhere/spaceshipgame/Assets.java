package com.anyconfusionhere.spaceshipgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class Assets {

    public AssetManager assetManager;

    public static Preferences preferences;

    public static TextureAtlas buttonRightAtlas;
    public static TextureAtlas buttonLeftAtlas;

    public static Animation coinAnimation;
    public static Animation heartAnimation;
    public static Animation explosionAnimation;
    public static Animation boxAnimation;

    public static Animation alienAnimation;
    public static Animation alienHitAnimation;
    public static Animation shieldAlienAnimation, mysteryExplosionAnimation;
    public static Texture background, windowTop,windowMid, windowBottom;
    public static Texture alienLife;
    public static Texture curved_arrow;
    public static Texture bombTexture;
    public static Texture diamondTexture;
    public static TextureAtlas alienAtlas;
    public static TextureAtlas alienHitAtlas;
    public static TextureAtlas shieldAlienAtlas;
    public static TextureAtlas mysteryExplosionAtlas;
    public static TextureAtlas coinAtlas;
    public static TextureAtlas explosionAtlas;
    public static TextureAtlas heartAtlas;
    public static TextureAtlas boxAtlas;
    public static TextureAtlas AsteroidAtlasTypeA,AsteroidAtlasTypeB,AsteroidAtlasTypeC,AsteroidAtlasTypeD;
    public static Sound coinSound;
    public static Sound lifeSound;
    public static Sound blastSound;
    public static Music backgroundMusic, menuBackgroundMusic;
    public static BitmapFont fontMain, fontCoins, fontChange,fontChange2,
            fontLives, countDown, fontEnd, fontInstructions, fontInstructionsPages, fontExtraLife;




    public Assets() {}

    public void load() {



        assetManager = new AssetManager();

        assetManager.load("window_bottom.png", Texture.class);
        assetManager.load("window_top.png", Texture.class);
        assetManager.load("window_mid.png", Texture.class);
        assetManager.load("mysteryExplosion.atlas",TextureAtlas.class);
        assetManager.load("background2.png", Texture.class);
        assetManager.load("curved_arrow.png", Texture.class);
        assetManager.load("diamond.png", Texture.class);
        assetManager.load("alien.atlas", TextureAtlas.class);
        assetManager.load("alienHit.atlas", TextureAtlas.class);
        assetManager.load("shieldAlien.atlas", TextureAtlas.class);
        assetManager.load("coin.atlas", TextureAtlas.class);
        assetManager.load("explosions.atlas", TextureAtlas.class);
        assetManager.load("heart.atlas", TextureAtlas.class);
        assetManager.load("box.atlas", TextureAtlas.class);
        assetManager.load("CoinSound.wav", Sound.class);
        assetManager.load("LifeSound.wav", Sound.class);
        assetManager.load("BlastSound.mp3", Sound.class);
        assetManager.load("gameAppMusic.mp3",Music.class);
        assetManager.load("backgroundMusic1.mp3", Music.class);
        assetManager.load("alienLife.png", Texture.class);
        assetManager.load("left.atlas", TextureAtlas.class);
        assetManager.load("right.atlas", TextureAtlas.class);
        assetManager.load("bomb.png", Texture.class);

        while (!assetManager.update()){}

        windowBottom = assetManager.get("window_bottom.png",Texture.class);
        windowTop = assetManager.get("window_top.png",Texture.class);
        windowMid = assetManager.get("window_mid.png",Texture.class);
        alienLife = assetManager.get("alienLife.png", Texture.class);
        curved_arrow = assetManager.get("curved_arrow.png", Texture.class);
        diamondTexture = assetManager.get("diamond.png", Texture.class);
        mysteryExplosionAtlas = assetManager.get("mysteryExplosion.atlas",TextureAtlas.class);
        bombTexture = assetManager.get("bomb.png", Texture.class);
        background = assetManager.get("background2.png", Texture.class);
        buttonLeftAtlas = assetManager.get("left.atlas", TextureAtlas.class);
        buttonRightAtlas = assetManager.get("right.atlas", TextureAtlas.class);
        alienAtlas = assetManager.get("alien.atlas", TextureAtlas.class);
        alienHitAtlas = assetManager.get("alienHit.atlas", TextureAtlas.class);
        shieldAlienAtlas = assetManager.get("shieldAlien.atlas", TextureAtlas.class);
        coinAtlas = assetManager.get("coin.atlas", TextureAtlas.class);
        explosionAtlas = assetManager.get("explosions.atlas", TextureAtlas.class);
        heartAtlas = assetManager.get("heart.atlas", TextureAtlas.class);
        boxAtlas = assetManager.get("box.atlas", TextureAtlas.class);
        coinSound = assetManager.get("CoinSound.wav", Sound.class);
        lifeSound = assetManager.get("LifeSound.wav", Sound.class);
        blastSound = assetManager.get("BlastSound.mp3", Sound.class);
        backgroundMusic = assetManager.get("backgroundMusic1.mp3", Music.class);
        menuBackgroundMusic = assetManager.get("gameAppMusic.mp3",Music.class);

        AsteroidAtlasTypeA = new TextureAtlas("asteroid_a.atlas");
        AsteroidAtlasTypeB = new TextureAtlas("asteroid_b.atlas");
        AsteroidAtlasTypeC = new TextureAtlas("asteroid_c.atlas");
        AsteroidAtlasTypeD = new TextureAtlas("asteroid_d.atlas");

        backgroundMusic.setLooping(true);
        menuBackgroundMusic.setLooping(true);

        coinAnimation = new Animation(1 / 12f, coinAtlas.getRegions());
        explosionAnimation = new Animation(1 / 26f, explosionAtlas.getRegions());
        heartAnimation = new Animation(1 / 12f, heartAtlas.getRegions());
        boxAnimation = new Animation(1 / 12f, boxAtlas.getRegions());
        mysteryExplosionAnimation = new Animation(1/13f, mysteryExplosionAtlas.getRegions());


        alienAnimation = new Animation(1 / 30f, alienAtlas.getRegions());
        alienHitAnimation = new Animation(1 / 10f, alienHitAtlas.getRegions());
        shieldAlienAnimation = new Animation(1 / 30f, shieldAlienAtlas.getRegions());

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("neuropol.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter parameter2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter parameter3 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter parameter4 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter parameter5 = new FreeTypeFontGenerator.FreeTypeFontParameter();


        parameter2.size = Math.round(60);
        parameter.size = Math.round(50);
        parameter3.size = Math.round(30);
        parameter4.size = Math.round(30);
        parameter5.size = Math.round(80);
        fontMain = generator.generateFont(parameter);
        fontLives = generator.generateFont(parameter);
        fontEnd = generator.generateFont(parameter);
        fontCoins = generator.generateFont(parameter);
        countDown = generator.generateFont(parameter2);
        fontInstructions = generator.generateFont(parameter);
        fontChange = generator.generateFont(parameter4);
        fontChange2 = generator.generateFont(parameter4);
        fontChange2.setColor(Color.valueOf("00f7fd"));
        fontInstructionsPages = generator.generateFont(parameter2);
        fontCoins.setColor(Color.GOLD);
        fontLives.setColor(Color.SCARLET);
        fontChange.setColor(Color.GREEN);
        fontExtraLife = generator.generateFont(parameter5);

        preferences = Gdx.app.getPreferences("GameData");

        if (preferences.getInteger("easyHighScore") == 0) {
            preferences.putInteger("easyHighScore", 0);
        }
        if (preferences.getInteger("backgroundCheck") == 0) {
            preferences.putInteger("backgroundCheck", 0);
        }
        if (preferences.getInteger("musica") == 0) {
            preferences.putInteger("musica", 0);
        }
        if (preferences.getInteger("firstTime") == 0) {
            preferences.putInteger("firstTime", 0);
        }
        if (preferences.getInteger("mediumHighScore") == 0) {
            preferences.putInteger("mediumHighScore", 0);
        }
        if (preferences.getInteger("hardHighScore") == 0) {
            preferences.putInteger("hardHighScore", 0);
        }
        if (preferences.getString("LastDifficulty") == "") {
            preferences.putString("LastDifficulty", "medium");
        }
        if (preferences.getInteger("diamonds") == 0) {
            preferences.putInteger("diamonds", 3);
        }

        preferences.flush();

        generator.dispose();
    }

}