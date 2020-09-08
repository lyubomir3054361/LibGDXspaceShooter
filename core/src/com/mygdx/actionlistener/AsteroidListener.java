package com.mygdx.actionlistener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.SpaceBattle;
import com.mygdx.model.Asteroid;
import com.mygdx.model.Explosion;
import com.mygdx.model.SpaceShip;
import com.mygdx.screen.GameOverScreen;
import com.mygdx.screen.GameScreen;

import java.util.Iterator;

public class AsteroidListener {

    public  void checkForInputs(SpaceShip spaceShip, Asteroid asteroid, Explosion explosion, SpaceBattle game, GameScreen gameScreen){
        if(TimeUtils.nanoTime() - asteroid.getLastAsteroidSpawnTime() > 1000000000){
            asteroid.spawnAsteroid(asteroid.getAsteroids());
        }
        Iterator<Rectangle> asteroidIterator = asteroid.getAsteroids().iterator();
        while(asteroidIterator.hasNext()){

            Rectangle iterator = asteroidIterator.next();
            iterator.y -= asteroid.getAsteroidSpeed() * Gdx.graphics.getDeltaTime();

            if(iterator.y + iterator.height < 0){
                asteroidIterator.remove();
            }
            if(iterator.overlaps(spaceShip.getSpaceShipRectangle())){
                if(spaceShip.getLifesLeft() == 1){
                    game.setScreen(new GameOverScreen(game));
                    gameScreen.dispose();
                }
                else{
                    spaceShip.setLifesLeft(spaceShip.getLifesLeft() - 1);
                    explosion.createExplosion(iterator);
                    asteroidIterator.remove();
                }
            }
        }
    }

}
