package com.mygdx.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.actionlistener.AsteroidListener;
import com.mygdx.actionlistener.ShipFireListener;
import com.mygdx.actionlistener.ShipMovementListener;
import com.mygdx.game.SpaceBattle;
import com.mygdx.model.Asteroid;
import com.mygdx.model.Explosion;
import com.mygdx.model.Shot;
import com.mygdx.model.SpaceShip;


public class GameScreen implements Screen {

    final SpaceBattle game;
    private OrthographicCamera camera;
    private SpaceShip spaceShip;
    private Asteroid asteroid;
    private Shot shot;
    private Explosion explosion;
    private ShipMovementListener shipMovementListener;
    private ShipFireListener shipFireListener;
    private AsteroidListener asteroidListener;
    private int score;

    public GameScreen(final SpaceBattle game){
        this.game = game;
        spaceShip = new SpaceShip();
        asteroid  = new Asteroid();
        shot = new Shot();
        explosion = new Explosion();
        shipMovementListener = new ShipMovementListener();
        shipFireListener = new ShipFireListener();
        asteroidListener = new AsteroidListener();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 600, 800);
        score = 0;
        asteroid.spawnAsteroid(asteroid.getAsteroids());
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        asteroidListener.checkForInputs(spaceShip, asteroid, explosion, game, this);
        shipFireListener.checkForInputs(spaceShip, asteroid, shot, explosion, this);
        shipMovementListener.checkForInputs(spaceShip);

        game.batch.begin();
        game.batch.draw(spaceShip.getSpaceShipImage(), spaceShip.getSpaceShipRectangle().x, spaceShip.getSpaceShipRectangle().y, spaceShip.getSpaceShipRectangle().width, spaceShip.getSpaceShipRectangle().height);
        for(Rectangle s : spaceShip.getSingleShots()){
            game.batch.draw(shot.getShotImage(), s.x, s.y, s.width, s.height);
        }
        for(Rectangle a : asteroid.getAsteroids()){
            game.batch.draw(asteroid.getAsteroidImage(), a.x, a.y, a.width, a.height);
        }
        game.batch.draw(explosion.getExplosionSprite(), explosion.getExplosionSprite().getX(), explosion.getExplosionSprite().getY(), explosion.getExplosionSprite().getWidth(), explosion.getExplosionSprite().getHeight());

        game.font.draw(game.batch, "Ammunition   " + spaceShip.getAmuSingleShot(), 50, 100);
        game.font.draw(game.batch, "Score  " + score, 200, 100);
        game.font.draw(game.batch, "Lifes  " + spaceShip.getLifesLeft(), 300, 100);

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
        explosion.getExplosionImage().dispose();
        shot.getShotSound().dispose();
        explosion.getExplosionSound().dispose();
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
