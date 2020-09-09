package com.mygdx.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.SpaceBattle;
import com.mygdx.model.ParallaxStarBackground;

public class MainMenuScreen implements Screen {

    private final SpaceBattle game;
    private OrthographicCamera camera;
    private ParallaxStarBackground parallaxStarBackground;

    public MainMenuScreen(SpaceBattle game){
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 600, 800);
        parallaxStarBackground = new ParallaxStarBackground();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        game.batch.begin();

        game.batch.draw(parallaxStarBackground.getStarsFastImage(), parallaxStarBackground.getStarsFastRectangle().x, parallaxStarBackground.getStarsFastRectangle().y, parallaxStarBackground.getStarsFastRectangle().width, parallaxStarBackground.getStarsFastRectangle().height);
        game.batch.draw(parallaxStarBackground.getStarsFastImage(), parallaxStarBackground.getStarsFastRectangle().x, parallaxStarBackground.getStarsFastRectangle().y + 800, parallaxStarBackground.getStarsFastRectangle().width, parallaxStarBackground.getStarsFastRectangle().height);
        game.batch.draw(parallaxStarBackground.getStarsSlowImage(), parallaxStarBackground.getStarsSlowRectangle().x, parallaxStarBackground.getStarsSlowRectangle().y, parallaxStarBackground.getStarsSlowRectangle().width, parallaxStarBackground.getStarsSlowRectangle().height);
        game.batch.draw(parallaxStarBackground.getStarsSlowImage(), parallaxStarBackground.getStarsSlowRectangle().x, parallaxStarBackground.getStarsSlowRectangle().y + 800, parallaxStarBackground.getStarsSlowRectangle().width, parallaxStarBackground.getStarsSlowRectangle().height);

        game.font.draw(game.batch, "Fun Project to practice Java", 200, 400);
        game.font.draw(game.batch, "Press any key to start", 200, 300);

        game.batch.end();

        if(Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)){
            game.setScreen(new GameScreen(game));
            dispose();
        }
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

    }
}
