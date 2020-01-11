package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class GameScreen implements Screen {

    final SpaceBattle game;

    Texture spaceShipImage;
    Texture shotImage;
    Texture asteroidImage;

    Rectangle spaceShip;

    OrthographicCamera camera;

    long lastFiredTime;
    long lastAsteroidSpawnTime;
    int spaceShipSpeed;
    int asteroidSpeed;

    Array<Rectangle> singleShots;
    Array<Rectangle> asteroids;

    public GameScreen(final SpaceBattle game){
        this.game = game;

        spaceShipImage = new Texture("spaceship.png");
        shotImage = new Texture("shot.png");
        asteroidImage = new Texture("asteroid.png");

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 600, 800);

        spaceShip = new Rectangle();
        spaceShip.x = 300 - 64;
        spaceShip.y = 200;
        spaceShip.width = 64;
        spaceShip.height = 64;

        spaceShipSpeed = 220;
        asteroidSpeed = 200;

        singleShots = new Array<Rectangle>();
        asteroids = new Array<Rectangle>();
        spawnAsteroid();
    }

    private void spaceShipSingleFire(){
        Rectangle singleShot = new Rectangle();
        singleShot.width = 16;
        singleShot.height = 16;
        singleShot.x = spaceShip.x + spaceShip.width/2 - singleShot.width/2;
        singleShot.y = spaceShip.y + singleShot.height*4;
        singleShots.add(singleShot);
        lastFiredTime = TimeUtils.nanoTime();
    }

    private void spawnAsteroid(){
        Rectangle asteroid = new Rectangle();
        asteroid.width = MathUtils.random(48, 96);
        asteroid.height = MathUtils.random(48,96);
        asteroid.x = MathUtils.random(0, 600 - asteroid.width);
        asteroid.y = 800;
        asteroids.add(asteroid);
        lastAsteroidSpawnTime = TimeUtils.nanoTime();
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        /**
         *
         * Drawing objects
         *
         */
        game.batch.begin();

        game.batch.draw(spaceShipImage, spaceShip.x, spaceShip.y, spaceShip.width, spaceShip.height);

        for(Rectangle shot : singleShots){
            game.batch.draw(shotImage, shot.x, shot.y, shot.width, shot.height);
        }
        for(Rectangle asteroid : asteroids){
            game.batch.draw(asteroidImage, asteroid.x, asteroid.y, asteroid.width, asteroid.height);
        }

        game.batch.end();

        /**
         *
         * Asteroid spawn
         *
         */
        if(TimeUtils.nanoTime() - lastAsteroidSpawnTime > 1000000000){
            spawnAsteroid();
        }
        Iterator<Rectangle> asteroidIterator = asteroids.iterator();
        while(asteroidIterator.hasNext()){
            Rectangle asteroid = asteroidIterator.next();
            asteroid.y -= asteroidSpeed * Gdx.graphics.getDeltaTime();

            if(asteroid.y + asteroid.height < 0){
                asteroidIterator.remove();
            }
            if(asteroid.overlaps(spaceShip)){
                game.setScreen(new GameOverScreen(game));
                dispose();
            }
        }

        /**
         *
         * Fire
         *
         */
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && TimeUtils.nanoTime() - lastFiredTime > 200000000){
            spaceShipSingleFire();
        }
        Iterator<Rectangle> singleShotIterator = singleShots.iterator();
        while(singleShotIterator.hasNext()){
            Rectangle singleShot = singleShotIterator.next();
            singleShot.y += 800 * Gdx.graphics.getDeltaTime();

            if(singleShot.y > 800){
                singleShotIterator.remove();
            }
            Iterator<Rectangle> asteroidShot = asteroids.iterator();
            while(asteroidShot.hasNext()){
                Rectangle asteroid = asteroidShot.next();
                if(asteroid.overlaps(singleShot)){
                    asteroidShot.remove();
                    singleShotIterator.remove();
                }
            }
        }

        /**
         *
         * Ship movement
         *
         */
        if(Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)){
            spaceShip.y += spaceShipSpeed * Gdx.graphics.getDeltaTime();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            spaceShip.y -= spaceShipSpeed * Gdx.graphics.getDeltaTime();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            spaceShip.x -= spaceShipSpeed * Gdx.graphics.getDeltaTime();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            spaceShip.x += spaceShipSpeed * Gdx.graphics.getDeltaTime();
        }
        if(spaceShip.x < 0){
            spaceShip.x = 0;
        }
        if(spaceShip.x > 600 - spaceShip.width){
            spaceShip.x = 600 - spaceShip.width;
        }
        if(spaceShip.y < 0){
            spaceShip.y = 0;
        }
        if(spaceShip.y > 800 - spaceShip.height){
            spaceShip.y = 800 - spaceShip.height;
        }


    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        shotImage.dispose();
        spaceShipImage.dispose();
        asteroidImage.dispose();
    }

}
