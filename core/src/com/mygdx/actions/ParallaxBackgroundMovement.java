package com.mygdx.actions;

import com.badlogic.gdx.Gdx;
import com.mygdx.model.ParallaxStarBackground;

import java.awt.*;

public class ParallaxBackgroundMovement {

    public void move(ParallaxStarBackground parallaxStarBackground){
        Rectangle starsFast = parallaxStarBackground.getStarsFastRectangle();
        starsFast.y -= parallaxStarBackground.getSpeedFast() * Gdx.graphics.getDeltaTime();
        if(starsFast.y < -800){
            starsFast.y = 0;
        }
        Rectangle starsSlow = parallaxStarBackground.getStarsSlowRectangle();
        starsSlow.y -= parallaxStarBackground.getSpeedSlow() * Gdx.graphics.getDeltaTime();
        if(starsSlow.y < -800){
            starsSlow.y = 0;
        }
    }
}
