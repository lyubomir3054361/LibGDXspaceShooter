package com.mygdx.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Shot {


    private Sound shotSound;
    private Texture shotImage;

    public Shot(){
        shotImage = new Texture("shot.png");
        shotSound = Gdx.audio.newSound(Gdx.files.internal("shotSound.wav"));
    }

    public Sound getShotSound() {
        return shotSound;
    }

    public void setShotSound(Sound shotSound) {
        this.shotSound = shotSound;
    }

    public Texture getShotImage() {
        return shotImage;
    }

    public void setShotImage(Texture shotImage) {
        this.shotImage = shotImage;
    }
}
