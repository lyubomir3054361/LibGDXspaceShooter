package com.mygdx.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Asteroid {

    private Texture asteroidImage;
    private int asteroidSpeed;
    private long lastAsteroidSpawnTime;
    private Array<Rectangle> allAsteroids;


    public Asteroid(){
        asteroidImage = new Texture("asteroid.png");
        asteroidSpeed = 200;
        allAsteroids = new Array<>();

    }

    public void spawn(Array<Rectangle> asteroids){
        Rectangle asteroid = new Rectangle();
        asteroid.width = MathUtils.random(48, 96);
        asteroid.height = MathUtils.random(48,96);
        asteroid.x = MathUtils.random(0, 600 - asteroid.width);
        asteroid.y = 800;
        asteroids.add(asteroid);
        lastAsteroidSpawnTime = TimeUtils.nanoTime();
    }

    public Texture getAsteroidImage() {
        return asteroidImage;
    }

    public void setAsteroidImage(Texture asteroidImage) {
        this.asteroidImage = asteroidImage;
    }

    public int getAsteroidSpeed() {
        return asteroidSpeed;
    }

    public void setAsteroidSpeed(int asteroidSpeed) {
        this.asteroidSpeed = asteroidSpeed;
    }

    public long getLastAsteroidSpawnTime() {
        return lastAsteroidSpawnTime;
    }

    public void setLastAsteroidSpawnTime(long lastAsteroidSpawnTime) {
        this.lastAsteroidSpawnTime = lastAsteroidSpawnTime;
    }

    public Array<Rectangle> getAllAsteroids() {
        return allAsteroids;
    }

    public void setAllAsteroids(Array<Rectangle> allAsteroids) {
        this.allAsteroids = allAsteroids;
    }
}
