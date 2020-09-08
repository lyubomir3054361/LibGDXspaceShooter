package com.mygdx.actionlistener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.model.Asteroid;
import com.mygdx.model.Explosion;
import com.mygdx.model.Shot;
import com.mygdx.model.SpaceShip;

import java.util.Iterator;

public class ShipFireListener {

    public void checkForInputs(SpaceShip spaceShip, Asteroid astroObj, Shot shot, Explosion explosion, int score){
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && TimeUtils.nanoTime() - spaceShip.getLastFiredTime() > 200000000){
            spaceShip.spaceShipSingleFire(shot);
        }
        Iterator<Rectangle> singleShotIterator = spaceShip.getSingleShots().iterator();
        while(singleShotIterator.hasNext()){
            Rectangle singleShot = singleShotIterator.next();
            singleShot.y += 800 * Gdx.graphics.getDeltaTime();

            if(singleShot.y > 800){
                singleShotIterator.remove();
            }
            Iterator<Rectangle> asteroidShot = astroObj.getAsteroids().iterator();
            while(asteroidShot.hasNext()){
                Rectangle asteroid = asteroidShot.next();
                if(asteroid.overlaps(singleShot)){
                    score++;
                    explosion.createExplosion(asteroid);
                    asteroidShot.remove();
                    singleShotIterator.remove();
                }
            }
        }
    }

}
