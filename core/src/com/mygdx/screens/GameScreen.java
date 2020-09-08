package com.mygdx.screens;

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
import com.mygdx.game.SpaceBattle;
import com.mygdx.model.Asteroid;
import com.mygdx.model.SpaceShip;

import java.util.Iterator;


public class GameScreen implements Screen {

    final SpaceBattle game;

    private Texture shotImage;
    private Texture explosionImage;
    private TextureRegion[][] explosionAnimation;
    private Sprite explosionSprite;

    private OrthographicCamera camera;

    private long lastFiredTime;

    private SpaceShip spaceShip;
    private Asteroid asteroid;

    private int frame;
    private int initialExplosionX;
    private int initialExplosionY;
    private int score;

    private Sound shotSound;
    private Sound explosionSound;

    private Array<Rectangle> singleShots;

    public GameScreen(final SpaceBattle game){
        this.game = game;
        spaceShip = new SpaceShip();
        asteroid  = new Asteroid();

        shotImage = new Texture("shot.png");
        explosionImage = new Texture("explosion.png");

        shotSound = Gdx.audio.newSound(Gdx.files.internal("shotSound.wav"));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("explosionSound.wav"));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 600, 800);

        score = 0;

        singleShots = new Array<>();

        initialExplosionX = 650;
        initialExplosionY = 850;

        explosionAnimation = TextureRegion.split(explosionImage,96,96);
        explosionSprite = new Sprite(explosionAnimation[0][0]);
        explosionSprite.setY(initialExplosionY);
        explosionSprite.setX(initialExplosionX);

        asteroid.spawnAsteroid(asteroid.getAsteroids());
    }

    private void spaceShipSingleFire(){
        Rectangle singleShot = new Rectangle();
        singleShot.width = 16;
        singleShot.height = 16;
        singleShot.x = spaceShip.getSpaceShipRectangle().x + spaceShip.getSpaceShipRectangle().width/2 - singleShot.width/2;
        singleShot.y = spaceShip.getSpaceShipRectangle().y + singleShot.height*4;
        singleShots.add(singleShot);
        lastFiredTime = TimeUtils.nanoTime();
        shotSound.play(0.2f);
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
        for(Rectangle shot : singleShots){
            game.batch.draw(shotImage, shot.x, shot.y, shot.width, shot.height);
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
        shotImage.dispose();
        spaceShip.getSpaceShipImage().dispose();
        asteroid.getAsteroidImage().dispose();
        explosionImage.dispose();
        shotSound.dispose();
        explosionSound.dispose();
    }

}
