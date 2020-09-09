package com.mygdx.actions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.model.SpaceShip;

public class ShipMovementListener {

    public void checkForInputs(SpaceShip spaceShip){
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
    }

}
