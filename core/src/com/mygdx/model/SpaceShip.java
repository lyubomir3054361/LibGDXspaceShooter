package com.mygdx.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class SpaceShip {

    private Texture spaceShipImage;
    private Rectangle spaceShipRectangle;
    private Array<Rectangle> singleShots;
    private Sound movingSound;
    private int spaceShipSpeed;
    private int lifesLeft;
    private int ammunition;
    private long lastFiredTime;
    private boolean reloading;
    private String needToReload;

    public SpaceShip(){
        spaceShipImage = new Texture("spaceship2.png");
        movingSound = Gdx.audio.newSound(Gdx.files.internal("moving_sound.mp3"));
        spaceShipRectangle = new Rectangle();
        spaceShipRectangle.x = 300 - 64;
        spaceShipRectangle.y = 200;
        spaceShipRectangle.width = 64;
        spaceShipRectangle.height = 64;
        spaceShipSpeed = 220;
        lifesLeft = 3;
        singleShots = new Array<>();
        ammunition = 3;
        needToReload = "Ready to fire!";
    }

    public void fire(Shot shot){
        Rectangle singleShot = new Rectangle();
        singleShot.width = 16;
        singleShot.height = 16;
        singleShot.x = spaceShipRectangle.x + spaceShipRectangle.width/2 - singleShot.width/2;
        singleShot.y = spaceShipRectangle.y + singleShot.height*4;
        singleShots.add(singleShot);
        lastFiredTime = TimeUtils.nanoTime();
        shot.getShotSound().play(0.2f);
    }

    public void reload(){
        needToReload = "reloading...";
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                ammunition++;
                if(ammunition == 3){
                    setReloading(false);
                    needToReload = "Ready to fire!";
                }
            }
        }, 1, 1f, 2);
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

    public int getAmmunition() {
        return ammunition;
    }

    public void setAmmunition(int ammunition) {
        this.ammunition = ammunition;
    }

    public long getLastFiredTime() {
        return lastFiredTime;
    }

    public void setLastFiredTime(long lastFiredTime) {
        this.lastFiredTime = lastFiredTime;
    }

    public Sound getMovingSound() {
        return movingSound;
    }

    public void setMovingSound(Sound movingSound) {
        this.movingSound = movingSound;
    }

    public boolean isReloading() {
        return reloading;
    }

    public void setReloading(boolean reloading) {
        this.reloading = reloading;
    }

    public String getNeedToReload() {
        return needToReload;
    }

    public void setNeedToReload(String needToReload) {
        this.needToReload = needToReload;
    }
}
