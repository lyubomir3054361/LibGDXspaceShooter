package com.mygdx.model;

import com.badlogic.gdx.graphics.Texture;

import java.awt.*;

public class ParallaxStarBackground {

    private Texture starsFastImage;
    private Texture starsSlowImage;
    private Rectangle starsFastRectangle;
    private Rectangle starsSlowRectangle;
    private int speedFast;
    private int speedSlow;


    public ParallaxStarBackground(){

        starsFastImage = new Texture("stars1.png");
        starsFastRectangle = new Rectangle();
        starsFastRectangle.x = 0;
        starsFastRectangle.y = 0;
        starsFastRectangle.height = 800;
        starsFastRectangle.width = 600;
        speedFast = 150;

        starsSlowImage = new Texture("stars2.png");
        starsSlowRectangle = new Rectangle();
        starsSlowRectangle.x = 0;
        starsSlowRectangle.y = 0;
        starsSlowRectangle.height = 800;
        starsSlowRectangle.width = 600;
        speedSlow = 100;
    }

    public Texture getStarsFastImage() {
        return starsFastImage;
    }

    public void setStarsFastImage(Texture starsFastImage) {
        this.starsFastImage = starsFastImage;
    }

    public Texture getStarsSlowImage() {
        return starsSlowImage;
    }

    public void setStarsSlowImage(Texture starsSlowImage) {
        this.starsSlowImage = starsSlowImage;
    }

    public Rectangle getStarsFastRectangle() {
        return starsFastRectangle;
    }

    public void setStarsFastRectangle(Rectangle starsFastRectangle) {
        this.starsFastRectangle = starsFastRectangle;
    }

    public Rectangle getStarsSlowRectangle() {
        return starsSlowRectangle;
    }

    public void setStarsSlowRectangle(Rectangle starsSlowRectangle) {
        this.starsSlowRectangle = starsSlowRectangle;
    }

    public int getSpeedFast() {
        return speedFast;
    }

    public void setSpeedFast(int speedFast) {
        this.speedFast = speedFast;
    }

    public int getSpeedSlow() {
        return speedSlow;
    }

    public void setSpeedSlow(int speedSlow) {
        this.speedSlow = speedSlow;
    }
}
