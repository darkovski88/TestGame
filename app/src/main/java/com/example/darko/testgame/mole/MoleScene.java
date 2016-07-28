package com.example.darko.testgame.mole;


import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;

import java.util.Random;

/**
 * Created by darko on 7/28/2016.
 */
public class MoleScene extends Scene implements ITimerCallback {

    private ITextureRegion mole, moleHole;
    private Sprite moleSprite, moleHoleSprite, moleSprite2, moleHoleSprite2;
    private boolean mole2Touched, moleTouched;
    private Random random;
    private int lastState = 0;

    public MoleScene() {
        random = new Random();
        addTextures();
        TimerHandler timerHandler = new TimerHandler(.5f, true, this);
        registerUpdateHandler(timerHandler);
    }

    private void addTextures() {
        mole = TextureRegionFactory.extractFromTexture(SmashTheMole.getSharedInstance().mole);
        moleHole = TextureRegionFactory.extractFromTexture(SmashTheMole.getSharedInstance().moleHole);

        moleSprite = new Sprite(100, 100, mole, SmashTheMole.getSharedInstance().getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (moleTouched)
                    return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
                if (isVisible()) {
                    moleTouched = true;
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        moleSprite2 = new Sprite(100, 100, mole, SmashTheMole.getSharedInstance().getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (mole2Touched)
                    return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
                if (isVisible()) {
                    mole2Touched = true;
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        moleHoleSprite = new Sprite(100, 100, moleHole, SmashTheMole.getSharedInstance().getVertexBufferObjectManager());
        moleHoleSprite2 = new Sprite(100, 100, moleHole, SmashTheMole.getSharedInstance().getVertexBufferObjectManager());

        moleSprite.setPosition(100, 200);
        moleHoleSprite.setPosition(100, 200);

        moleSprite2.setPosition(400, 200);
        moleHoleSprite2.setPosition(400, 200);

        attachChild(moleHoleSprite);
        attachChild(moleHoleSprite2);
        attachChild(moleSprite);
        attachChild(moleSprite2);

        registerTouchArea(moleSprite);
        registerTouchArea(moleSprite2);

    }

    @Override
    public void onTimePassed(TimerHandler pTimerHandler) {
        int newState = random.nextInt(4);
        while (newState == lastState) {
            newState = random.nextInt(4);
        }
        lastState = newState;
        switch (lastState) {
            case 0:
                toggleMoles(false, false);
                break;
            case 1:
                toggleMoles(false, true);
                break;
            case 2:
                toggleMoles(true, false);
                break;
            case 3:
                toggleMoles(true, true);
                break;
        }
    }

    private void toggleMoles(boolean mole1Visible, boolean mole2Visible) {
        if (!moleTouched)
            moleSprite.setVisible(mole1Visible);
        if (!mole2Touched)
            moleSprite2.setVisible(mole2Visible);
        if (mole2Touched && moleTouched) {
            SmashTheMole.getSharedInstance().finish();
        }
    }
}
