package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;

import java.util.Iterator;

public class GameScreen implements Screen {

    final SpaceBattle game;

    Texture spaceShipImage;
    Texture shotImage;
    Texture asteroidImage;
    Texture explosionImage;
    TextureRegion[][] explosionAnimation;
    Sprite explosionSprite;
    Rectangle spaceShip;

    OrthographicCamera camera;

    long lastFiredTime;
    long lastAsteroidSpawnTime;
    int spaceShipSpeed;
    int asteroidSpeed;
    int frame;
    int initialExplosionX;
    int initialExplosionY;
    int ammuSingleShot;
    int score;

    Sound shotSound;
    Sound explosionSound;

    Array<Rectangle> singleShots;
    Array<Rectangle> asteroids;

    public GameScreen(final SpaceBattle game){
        this.game = game;

        spaceShipImage = new Texture("spaceship.png");
        shotImage = new Texture("shot.png");
        asteroidImage = new Texture("asteroid.png");
        explosionImage = new Texture("explosion.png");

        shotSound = Gdx.audio.newSound(Gdx.files.internal("shotSound.wav"));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("explosionSound.wav"));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 600, 800);

        spaceShip = new Rectangle();
        spaceShip.x = 300 - 64;
        spaceShip.y = 200;
        spaceShip.width = 64;
        spaceShip.height = 64;

        spaceShipSpeed = 220;
        asteroidSpeed = 200;
        ammuSingleShot = 3;
        score = 0;

        singleShots = new Array<Rectangle>();
        asteroids = new Array<Rectangle>();

        initialExplosionX = 650;
        initialExplosionY = 850;

        explosionAnimation = TextureRegion.split(explosionImage,96,96);
        explosionSprite = new Sprite(explosionAnimation[0][0]);
        explosionSprite.setY(initialExplosionY);
        explosionSprite.setX(initialExplosionX);

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
        shotSound.play(0.2f);
    }

    private int reloadSingleFire(int ammunition){

        ammunition++;

        return ammunition;
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

    private void createExplosion(Rectangle asteroid){

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

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);


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
            if(ammuSingleShot > 0){
                ammuSingleShot--;
                ammuSingleShot = reloadSingleFire(ammuSingleShot);
                spaceShipSingleFire();
            }
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
                    score++;
                    createExplosion(asteroid);
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
        if(spaceShip.y < 100){
            spaceShip.y = 100;
        }
        if(spaceShip.y > 800 - spaceShip.height){
            spaceShip.y = 800 - spaceShip.height;
        }

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
        game.batch.draw(explosionSprite, explosionSprite.getX(), explosionSprite.getY(), explosionSprite.getWidth(), explosionSprite.getHeight());

        game.font.draw(game.batch, "Ammunition   " + ammuSingleShot, 50, 100);
        game.font.draw(game.batch, "Score  " + score, 200, 100);

        game.batch.end();

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
        explosionImage.dispose();
        shotSound.dispose();
        explosionSound.dispose();
    }

}
