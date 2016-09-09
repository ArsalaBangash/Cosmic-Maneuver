package com.anyconfusionhere.spaceshipgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;

public class Alien {
    public float alienX;
    public float alienY;
    public Circle alienCircle;
    public float alienWidth,alienHeight;
    public float alienSpeed;
    public float d;


    public Alien() {
        alienY = Gdx.graphics.getHeight() / 2;
        alienX = Gdx.graphics.getWidth() / 2;
        alienWidth = 180*d;
        alienHeight = 180*d;
        alienSpeed = 7.5f;
        alienCircle = new Circle();

    }
    public void setWidth(float width){
        alienWidth = width;
    }
    public void setHeight(float height){
        alienHeight = height;
    }
    public void setSpeed(float speed){
        alienSpeed = speed;
    }
}

