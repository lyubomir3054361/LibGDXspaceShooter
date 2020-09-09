package com.mygdx.actions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.model.Asteroid;
import com.mygdx.model.Explosion;
import com.mygdx.model.Shot;
import com.mygdx.model.SpaceShip;
import com.mygdx.screen.GameScreen;

import java.util.Iterator;

public class ShipFireListener {

    public void checkForInputs(SpaceShip spaceShip, Asteroid astroObj, Shot shot, Explosion explosion, GameScreen gameScreen){
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && TimeUtils.nanoTime() - spaceShip.getLastFiredTime() > 200000000 && !spaceShip.isReloading()){
            if(spaceShip.getAmmunition() != 0){
                spaceShip.fire(shot);
                spaceShip.setAmmunition(spaceShip.getAmmunition() - 1);
                if(spaceShip.getAmmunition() == 0){
                    spaceShip.setReloading(true);
                    spaceShip.setNeedToReload("Press R to reload!");
                }
            }
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.R) && spaceShip.getAmmunition() == 0){
            spaceShip.reload();
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
                    gameScreen.setScore(gameScreen.getScore() + 1);
                    explosion.createExplosion(asteroid);
                    asteroidShot.remove();
                    singleShotIterator.remove();
                }
            }
        }
    }
}
