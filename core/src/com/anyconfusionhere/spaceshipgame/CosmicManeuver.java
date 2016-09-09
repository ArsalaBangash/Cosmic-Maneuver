package com.anyconfusionhere.spaceshipgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Timer;


public class CosmicManeuver extends Game{
    public MainMenu scrManager;
    private static long SPLASH_MINIMUM_MILLIS = 5000L;
    AdHandler adHandler;
    public static PlayServices playServices;


    public CosmicManeuver(AdHandler handler, PlayServices playServices) {
        super();
        this.playServices = playServices;
        this.adHandler = handler;
    }

    public void create() {
        scrManager = new MainMenu(this);

        setScreen(new SplashScreen(this));

        final long splash_start_time = System.currentTimeMillis();
        new Thread(new Runnable() {
            @Override
            public void run() {

                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {

                        long splash_elapsed_time = System.currentTimeMillis() - splash_start_time;

                        if (splash_elapsed_time < SPLASH_MINIMUM_MILLIS){

                            Timer.schedule(
                                    new Timer.Task() {
                                        @Override
                                        public void run() {
                                            CosmicManeuver.this.setScreen(scrManager);
                                        }
                                    }, (float) (SPLASH_MINIMUM_MILLIS - splash_elapsed_time) / 1000f);
                        } else {
                            CosmicManeuver.this.setScreen(scrManager);
                        }
                    }
                });
            }
        }).start();
    }
}

