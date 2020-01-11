package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class GameOverScreen implements Screen {

    final SpaceBattle game;

    OrthographicCamera camera;

    public GameOverScreen(SpaceBattle game){
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 600, 800);
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

        game.font.draw(game.batch, "Game over", 300, 300);
        game.font.draw(game.batch, "Press any key to continue", 300, 200);

        game.batch.end();

        if(Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)){
            game.setScreen(new MainMenuScreen(game));
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
