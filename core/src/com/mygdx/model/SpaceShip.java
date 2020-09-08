package com.mygdx.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class SpaceShip {

    private Texture spaceShipImage;
    private Rectangle spaceShipRectangle;
    private Array<Rectangle> singleShots;
    private int spaceShipSpeed;
    private int lifesLeft;
    private int amuSingleShot;
    private long lastFiredTime;


    public SpaceShip(){
        spaceShipImage = new Texture("spaceship2.png");
        spaceShipRectangle = new Rectangle();
        spaceShipRectangle.x = 300 - 64;
        spaceShipRectangle.y = 200;
        spaceShipRectangle.width = 64;
        spaceShipRectangle.height = 64;
        spaceShipSpeed = 220;
        lifesLeft = 3;
        singleShots = new Array<>();
        amuSingleShot = 3;
    }

    public void spaceShipSingleFire(Shot shot){
        Rectangle singleShot = new Rectangle();
        singleShot.width = 16;
        singleShot.height = 16;
        singleShot.x = spaceShipRectangle.x + spaceShipRectangle.width/2 - singleShot.width/2;
        singleShot.y = spaceShipRectangle.y + singleShot.height*4;
        singleShots.add(singleShot);
        lastFiredTime = TimeUtils.nanoTime();
        shot.getShotSound().play(0.2f);
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

    public int getLifesLeft() {
        return lifesLeft;
    }

    public void setLifesLeft(int lifesLeft) {
        this.lifesLeft = lifesLeft;
    }

    public Array<Rectangle> getSingleShots() {
        return singleShots;
    }

    public void setSingleShots(Array<Rectangle> singleShots) {
        this.singleShots = singleShots;
    }

    public int getAmuSingleShot() {
        return amuSingleShot;
    }

    public void setAmuSingleShot(int amuSingleShot) {
        this.amuSingleShot = amuSingleShot;
    }

    public long getLastFiredTime() {
        return lastFiredTime;
    }

    public void setLastFiredTime(long lastFiredTime) {
        this.lastFiredTime = lastFiredTime;
    }
}
