package com.mygdx.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.SpaceBattle;
import com.mygdx.model.Asteroid;
import com.mygdx.model.Shot;
import com.mygdx.model.SpaceShip;

import java.util.Iterator;


public class GameScreen implements Screen {

    final SpaceBattle game;

    private Texture explosionImage;
    private TextureRegion[][] explosionAnimation;
    private Sprite explosionSprite;

    private OrthographicCamera camera;

    private SpaceShip spaceShip;
    private Asteroid asteroid;
    private Shot shot;

    private int frame;
    private int initialExplosionX;
    private int initialExplosionY;
    private int score;

    private Sound explosionSound;



    public GameScreen(final SpaceBattle game){
        this.game = game;
        spaceShip = new SpaceShip();
        asteroid  = new Asteroid();
        shot = new Shot();

        explosionImage = new Texture("explosion.png");
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("explosionSound.wav"));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 600, 800);

        score = 0;

        initialExplosionX = 650;
        initialExplosionY = 850;

        explosionAnimation = TextureRegion.split(explosionImage,96,96);
        explosionSprite = new Sprite(explosionAnimation[0][0]);
        explosionSprite.setY(initialExplosionY);
        explosionSprite.setX(initialExplosionX);

        asteroid.spawnAsteroid(asteroid.getAsteroids());
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
         * Asteroid spawn and collision handling
         *
         */
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
                if(spaceShip.getLifes() == 1){
                    game.setScreen(new GameOverScreen(game));
                    dispose();
                }
                else{
                    spaceShip.setLifes(spaceShip.getLifes() - 1);
                    createExplosion(iterator);
                    asteroidIterator.remove();
                }
            }
        }

        /**
         *
         * Fire
         *
         */
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
            Iterator<Rectangle> asteroidShot = asteroid.getAsteroids().iterator();
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
            spaceShip.getSpaceShipRectangle().y += spaceShip.getSpaceShipSpeed() * Gdx.graphics.getDeltaTime();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            spaceShip.getSpaceShipRectangle().y -= spaceShip.getSpaceShipSpeed() * Gdx.graphics.getDeltaTime();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            spaceShip.getSpaceShipRectangle().x -= spaceShip.getSpaceShipSpeed() * Gdx.graphics.getDeltaTime();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            spaceShip.getSpaceShipRectangle().x += spaceShip.getSpaceShipSpeed() * Gdx.graphics.getDeltaTime();
        }
        if(spaceShip.getSpaceShipRectangle().x < 0){
            spaceShip.getSpaceShipRectangle().x = 0;
        }
        if(spaceShip.getSpaceShipRectangle().x > 600 - spaceShip.getSpaceShipRectangle().width){
            spaceShip.getSpaceShipRectangle().x = 600 - spaceShip.getSpaceShipRectangle().width;
        }
        if(spaceShip.getSpaceShipRectangle().y < 100){
            spaceShip.getSpaceShipRectangle().y = 100;
        }
        if(spaceShip.getSpaceShipRectangle().y > 800 - spaceShip.getSpaceShipRectangle().height){
            spaceShip.getSpaceShipRectangle().y = 800 - spaceShip.getSpaceShipRectangle().height;
        }

        /**
         *
         * Drawing objects
         *
         */
        game.batch.begin();

        game.batch.draw(spaceShip.getSpaceShipImage(), spaceShip.getSpaceShipRectangle().x, spaceShip.getSpaceShipRectangle().y, spaceShip.getSpaceShipRectangle().width, spaceShip.getSpaceShipRectangle().height);
        for(Rectangle s : spaceShip.getSingleShots()){
            game.batch.draw(shot.getShotImage(), s.x, s.y, s.width, s.height);
        }
        for(Rectangle a : asteroid.getAsteroids()){
            game.batch.draw(asteroid.getAsteroidImage(), a.x, a.y, a.width, a.height);
        }
        game.batch.draw(explosionSprite, explosionSprite.getX(), explosionSprite.getY(), explosionSprite.getWidth(), explosionSprite.getHeight());

        game.font.draw(game.batch, "Ammunition   " + spaceShip.getAmmuSingleShot(), 50, 100);
        game.font.draw(game.batch, "Score  " + score, 200, 100);
        game.font.draw(game.batch, "Lifes  " + spaceShip.getLifes(), 300, 100);

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
        shot.getShotImage().dispose();
        spaceShip.getSpaceShipImage().dispose();
        asteroid.getAsteroidImage().dispose();
        explosionImage.dispose();
        shot.getShotSound().dispose();
        explosionSound.dispose();
    }

}
