package com.example.darko.testgame.colision_listeners;

import com.example.darko.testgame.ColisionGameActivity;
import com.example.darko.testgame.colision_scenes.GameScene;

import org.andengine.engine.handler.IUpdateHandler;

/**
 * Created by darko on 7/7/2016.
 */
public class GameLoopUpdateHandler implements IUpdateHandler {

    @Override
    public void onUpdate(float pSecondsElapsed) {
//        Log.d("GameLoopUpdateHandler", "is in Game Scene " + pSecondsElapsed);
        if (ColisionGameActivity.getSharedInstance().mCurrentScene.getClass().isAssignableFrom(GameScene.class)) {
            ((GameScene) ColisionGameActivity.getSharedInstance().mCurrentScene).moveShip();
            ((GameScene) ColisionGameActivity.getSharedInstance().mCurrentScene).cleaner();
        }
    }

    @Override
    public void reset() {

    }
}
