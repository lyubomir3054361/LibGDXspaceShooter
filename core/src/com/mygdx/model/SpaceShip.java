package com.mygdx.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class SpaceShip {

    private Texture spaceShipImage;
    private Rectangle spaceShipRectangle;
    private int spaceShipSpeed;
    private int lifes;
    private Array<Rectangle> singleShots;
    private int ammuSingleShot;


    public SpaceShip(){
        spaceShipImage = new Texture("spaceship.png");
        spaceShipRectangle = new Rectangle();
        spaceShipRectangle.x = 300 - 64;
        spaceShipRectangle.y = 200;
        spaceShipRectangle.width = 64;
        spaceShipRectangle.height = 64;
        spaceShipSpeed = 220;
        lifes = 3;
        singleShots = new Array<>();
        ammuSingleShot = 3;
    }



    public void spaceShipSingleFire(){
        Rectangle singleShot = new Rectangle();
        singleShot.width = 16;
        singleShot.height = 16;
        singleShot.x = spaceShipRectangle.x + spaceShipRectangle.width/2 - singleShot.width/2;
        singleShot.y = spaceShipRectangle.y + singleShot.height*4;
        singleShots.add(singleShot);
     //   lastFiredTime = TimeUtils.nanoTime();
       // shotSound.play(0.2f);
    }

    public Texture getSpaceShipImage() {
        return spaceShipImage;
    }

    public void setSpaceShipImage(Texture spaceShipImage) {
        this.spaceShipImage = spaceShipImage;
    }

    public Rectangle getSpaceShipRectangle() {
        return spaceShipRectangle;
    }

    public void setSpaceShipRectangle(Rectangle spaceShipRectangle) {
        this.spaceShipRectangle = spaceShipRectangle;
    }

    public int getSpaceShipSpeed() {
        return spaceShipSpeed;
    }

    public void setSpaceShipSpeed(int spaceShipSpeed) {
        this.spaceShipSpeed = spaceShipSpeed;
    }

    public int getLifes() {
        return lifes;
    }

    public void setLifes(int lifes) {
        this.lifes = lifes;
    }

    public Array<Rectangle> getSingleShots() {
        return singleShots;
    }

    public void setSingleShots(Array<Rectangle> singleShots) {
        this.singleShots = singleShots;
    }

    public int getAmmuSingleShot() {
        return ammuSingleShot;
    }

    public void setAmmuSingleShot(int ammuSingleShot) {
        this.ammuSingleShot = ammuSingleShot;
    }
}
