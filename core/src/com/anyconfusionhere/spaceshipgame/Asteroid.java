package com.anyconfusionhere.spaceshipgame;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;

public class Asteroid {

    public float asteroidX;
    public float asteroidY;
    public int xVelocity, yVelocity, side, rand;
    public float width, height, oWidth, oHeight;
    public Animation AsteroidAnimation;
    public Circle asteroidCircle;
    public float asteroidRadius = 75;
    public static boolean asteroidSmaller;
    Boolean once = false;
    public float timeSinceBox;
    public float bombTime;

    public int minSpeed;
    public int maxSpeed;
    public Assets assets;



    public Asteroid(String asteroidType, float asteroidWidth, float asteroidHeight,
                    int minSpeedInit, int maxSpeedInit){
        width = asteroidWidth;
        oWidth = asteroidWidth;
        height = asteroidHeight;
        oHeight = asteroidHeight;
        bombTime = 0f;
        minSpeed = minSpeedInit;
        maxSpeed = maxSpeedInit;


        if (asteroidSmaller && timeSinceBox < 10f) {

            width = oWidth / 1.5f;
            height = oHeight / 1.5f;
            asteroidRadius = asteroidRadius / 1.5f;
        } else if (!asteroidSmaller && timeSinceBox >= 10f){
            width = oWidth;
            height = oHeight;
        }

        if (asteroidType.equals("a")){
            AsteroidAnimation = new Animation(1/18f,assets.AsteroidAtlasTypeA.getRegions(),
                    Animation.PlayMode.LOOP);
        } else if (asteroidType.equals("b")){
            AsteroidAnimation = new Animation(1/18f,assets.AsteroidAtlasTypeB.getRegions(),
                    Animation.PlayMode.LOOP);
        } else if (asteroidType.equals("c")){
            AsteroidAnimation = new Animation(1/18f,assets.AsteroidAtlasTypeC.getRegions(),
                    Animation.PlayMode.LOOP);
        } else {
            AsteroidAnimation = new Animation(1/18f,assets.AsteroidAtlasTypeD.getRegions(),
                    Animation.PlayMode.LOOP);

        }
          asteroidCircle = new Circle();

    };

    public boolean asteroidBoundary() {

        if (asteroidX + width < 0 || asteroidX > 1920) { return true;}
        else if (asteroidY + height < 0 || asteroidY > 1080) { return true;}
        else {return false;}
    }

    public void asteroidGenerate(){
        if (asteroidSmaller &&  !once && timeSinceBox < 10) {
            once = true;
            width = oWidth / 1.5f;
            height = oHeight / 1.5f;
            asteroidRadius = asteroidRadius / 1.5f;
        } else if (!asteroidSmaller && timeSinceBox >= 10f){
            width = oWidth;
            height = oHeight;
        }
        xVelocity = MathUtils.random(minSpeed, maxSpeed);
        yVelocity = MathUtils.random(minSpeed,maxSpeed);
        rand = MathUtils.random(1,2);
        side = MathUtils.random(1,4);
        if (side == 1) {
            asteroidX = 1920;
            asteroidY = MathUtils.random(1080);
        } else if (side == 2) {
            asteroidX = MathUtils.random(1920);
            asteroidY = 1080;
        }else if (side == 3) {
            asteroidX = width * -1;
            asteroidY = MathUtils.random(1080);
        }else if (side == 4) {
            asteroidX = MathUtils.random(1920);
            asteroidY = width * -1;
        }
    }
    public void asteroidUpdate(){
        if (side == 1) {
            asteroidX -= xVelocity;
            if (rand == 1) {
                asteroidY += yVelocity;
            }else{
                asteroidY -= yVelocity;
            }
        }else if(side == 2) {
            asteroidY -= yVelocity;
            if (rand == 1) {
                asteroidX += xVelocity;
            }else{
                asteroidX -= xVelocity;
            }
        }else if (side == 3) {
            asteroidX += xVelocity;
            if (rand == 1) {
                asteroidY += yVelocity;
            }else{
                asteroidY -= yVelocity;
            }
        }else if (side == 4) {
            asteroidY += yVelocity;
            if (rand == 1) {
                asteroidX += xVelocity;
            }else{
                asteroidX -= xVelocity;
            }
        }
    }
}
