package com.mygdx.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;

public class Explosion {

    private Texture explosionImage;
    private TextureRegion[][] explosionAnimation;
    private Sprite explosionSprite;
    private Sound explosionSound;
    private int initialExplosionX;
    private int initialExplosionY;
    private int frame;

    public Explosion(){
        explosionImage = new Texture("explosion.png");
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("explosionSound.wav"));
        initialExplosionX = 650;
        initialExplosionY = 850;
        explosionAnimation = TextureRegion.split(explosionImage,96,96);
        explosionSprite = new Sprite(explosionAnimation[0][0]);
        explosionSprite.setY(initialExplosionY);
        explosionSprite.setX(initialExplosionX);
    }

    public void createExplosion(Rectangle asteroid){
        explosionSound.play();
        explosionSprite.setX(asteroid.x);
        explosionSprite.setY(asteroid.y);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                frame++;
                if(frame > 7){
                    frame = 0;
                    explosionSprite.setY(initialExplosionY);
                    explosionSprite.setX(initialExplosionX);
                }
                explosionSprite.setRegion(explosionAnimation[0][frame]);
            }
        }, 0, 1/20f, 7);

    }

    public Texture getExplosionImage() {
        return explosionImage;
    }

    public void setExplosionImage(Texture explosionImage) {
        this.explosionImage = explosionImage;
    }

    public TextureRegion[][] getExplosionAnimation() {
        return explosionAnimation;
    }

    public void setExplosionAnimation(TextureRegion[][] explosionAnimation) {
        this.explosionAnimation = explosionAnimation;
    }

    public Sprite getExplosionSprite() {
        return explosionSprite;
    }

    public void setExplosionSprite(Sprite explosionSprite) {
        this.explosionSprite = explosionSprite;
    }

    public Sound getExplosionSound() {
        return explosionSound;
    }

    public void setExplosionSound(Sound explosionSound) {
        this.explosionSound = explosionSound;
    }

    public int getInitialExplosionX() {
        return initialExplosionX;
    }

    public void setInitialExplosionX(int initialExplosionX) {
        this.initialExplosionX = initialExplosionX;
    }

    public int getInitialExplosionY() {
        return initialExplosionY;
    }

    public void setInitialExplosionY(int initialExplosionY) {
        this.initialExplosionY = initialExplosionY;
    }

    public int getFrame() {
        return frame;
    }

    public void setFrame(int frame) {
        this.frame = frame;
    }
}
