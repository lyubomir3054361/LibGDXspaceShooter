package com.mygdx.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.actions.AsteroidActionListener;
import com.mygdx.actions.ParallaxBackgroundMovement;
import com.mygdx.actions.ShipFireListener;
import com.mygdx.actions.ShipMovementListener;
import com.mygdx.game.SpaceBattle;
import com.mygdx.model.*;


public class GameScreen implements Screen {

    private final SpaceBattle game;
    private OrthographicCamera camera;

    private SpaceShip spaceShip;
    private Asteroid asteroid;
    private Shot shot;
    private Explosion explosion;
    private ParallaxStarBackground parallaxStarBackground;

    private ShipMovementListener shipMovementListener;
    private ShipFireListener shipFireListener;
    private AsteroidActionListener asteroidActionListener;
    private ParallaxBackgroundMovement parallaxBackgroundMovement;

    private int score;

    public GameScreen(final SpaceBattle game){
        this.game = game;

        spaceShip = new SpaceShip();
        asteroid  = new Asteroid();
        shot = new Shot();
        explosion = new Explosion();
        parallaxStarBackground = new ParallaxStarBackground();

        shipMovementListener = new ShipMovementListener();
        shipFireListener = new ShipFireListener();
        asteroidActionListener = new AsteroidActionListener();
        parallaxBackgroundMovement = new ParallaxBackgroundMovement();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 600, 800);
        score = 0;
        asteroid.spawn(asteroid.getAllAsteroids());
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        asteroidActionListener.checkForInputs(spaceShip, asteroid, explosion, game, this);
        shipFireListener.checkForInputs(spaceShip, asteroid, shot, explosion, this);
        shipMovementListener.checkForInputs(spaceShip);
        parallaxBackgroundMovement.move(parallaxStarBackground);

        game.batch.begin();

        game.batch.draw(parallaxStarBackground.getStarsFastImage(), parallaxStarBackground.getStarsFastRectangle().x, parallaxStarBackground.getStarsFastRectangle().y, parallaxStarBackground.getStarsFastRectangle().width, parallaxStarBackground.getStarsFastRectangle().height);
        game.batch.draw(parallaxStarBackground.getStarsFastImage(), parallaxStarBackground.getStarsFastRectangle().x, parallaxStarBackground.getStarsFastRectangle().y + 800, parallaxStarBackground.getStarsFastRectangle().width, parallaxStarBackground.getStarsFastRectangle().height);
        game.batch.draw(parallaxStarBackground.getStarsSlowImage(), parallaxStarBackground.getStarsSlowRectangle().x, parallaxStarBackground.getStarsSlowRectangle().y, parallaxStarBackground.getStarsSlowRectangle().width, parallaxStarBackground.getStarsSlowRectangle().height);
        game.batch.draw(parallaxStarBackground.getStarsSlowImage(), parallaxStarBackground.getStarsSlowRectangle().x, parallaxStarBackground.getStarsSlowRectangle().y + 800, parallaxStarBackground.getStarsSlowRectangle().width, parallaxStarBackground.getStarsSlowRectangle().height);
        game.batch.draw(spaceShip.getSpaceShipImage(), spaceShip.getSpaceShipRectangle().x, spaceShip.getSpaceShipRectangle().y, spaceShip.getSpaceShipRectangle().width, spaceShip.getSpaceShipRectangle().height);
        game.batch.draw(explosion.getExplosionSprite(), explosion.getExplosionSprite().getX(), explosion.getExplosionSprite().getY(), explosion.getExplosionSprite().getWidth(), explosion.getExplosionSprite().getHeight());

        for(Rectangle s : spaceShip.getSingleShots()){
            game.batch.draw(shot.getShotImage(), s.x, s.y, s.width, s.height);
        }

        for(Rectangle a : asteroid.getAllAsteroids()){
            game.batch.draw(asteroid.getAsteroidImage(), a.x, a.y, a.width, a.height);
        }

        game.font.draw(game.batch, "Ammunition   " + spaceShip.getAmmunition(), 50, 100);
        game.font.draw(game.batch, "Score  " + score, 200, 100);
        game.font.draw(game.batch, "Lifes  " + spaceShip.getLifesLeft(), 300, 100);
        game.font.draw(game.batch, spaceShip.getNeedToReload(), 400, 100);

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
        parallaxStarBackground.getStarsFastImage().dispose();
        parallaxStarBackground.getStarsSlowImage().dispose();
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
