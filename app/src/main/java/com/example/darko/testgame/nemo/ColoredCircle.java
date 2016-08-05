package com.example.darko.testgame.nemo;

import android.util.Log;
import android.util.Pair;

import com.example.darko.testgame.colision_scenes.GameScene;

import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;

import java.util.Iterator;
import java.util.Random;

/**
 * Created by darko on 7/29/2016.
 */
public class ColoredCircle {
    private final int BLUE = 0;
    private final int RED = 1;
    private final int YELLOW = 2;
    private final int WHITE = 3;

    private int circleColor;
    ITextureRegion circleTexture;
    Sprite circleSprite;
    private int requestedColor;
    private int blueObjects = 0, redObjects = 0, yellowObjects = 0, whiteObjects = 0;

    public ColoredCircle() {
        setCircleColor(new Random().nextInt(4));
    }

    public void setCircleColor(int color) {
        circleColor = color;

        switch (color) {
            case BLUE:
                if (blueObjects >= 5) {
                    break;
                }
                Log.d("objects", "blue " + blueObjects);
                blueObjects++;
                circleTexture = TextureRegionFactory.extractFromTexture(NemoGameActivity.getSharedInstance().blueCircle);
                setupSprite();
                break;
            case WHITE:
                if (whiteObjects >= 5) {
                    break;
                }
                Log.d("objects", "white " + whiteObjects);
                whiteObjects++;
                circleTexture = TextureRegionFactory.extractFromTexture(NemoGameActivity.getSharedInstance().whiteCircle);
                setupSprite();
                break;
            case YELLOW:
                if (yellowObjects >= 5) {
                    break;
                }
                Log.d("objects", "yellow " + yellowObjects);
                yellowObjects++;
                circleTexture = TextureRegionFactory.extractFromTexture(NemoGameActivity.getSharedInstance().yellowCircle);
                setupSprite();
                break;
            case RED:
                if (redObjects >= 5) {
                    break;
                }
                Log.d("objects", "red " + redObjects);
                redObjects++;
                circleTexture = TextureRegionFactory.extractFromTexture(NemoGameActivity.getSharedInstance().redCircle);
                setupSprite();
                break;
            default:
                return;
        }
        if (blueObjects >= 5 && redObjects >= 5 && whiteObjects >= 5 && yellowObjects >= 5)
            return;

        Random rand = new Random();
        setCircleColor(rand.nextInt(4));

    }

    private void setupSprite() {
        circleSprite = new Sprite(20, 20, circleTexture, NemoGameActivity.getSharedInstance().getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                Log.d("ColoredCircle", "touch req:" + requestedColor + " | current color " + circleColor);
                if (pSceneTouchEvent.isActionDown()) {
                    Iterator eIt = CircleLayer.getIterator();
                    if (requestedColor == circleColor) {
//                        while (eIt.hasNext()) {
//                            if (eIt.equals(this)) {
//                                eIt.remove();
                        circleSprite.setVisible(false);
                        detachSelf();
//                        NemoCirclesPool.sharedEnemyPool().recyclePoolItem(this);
//                            }
//                        }
                    } else {

                        while (eIt.hasNext()) {
                            ColoredCircle circle = (ColoredCircle) eIt.next();
                            if (circle.getCircleColor() == circleColor) {
                                eIt.remove();
                                NemoCirclesPool.sharedEnemyPool().recyclePoolItem(circle);
                            }

                        }
                    }
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
    }

    public void clean() {
        circleSprite.clearEntityModifiers();
        circleSprite.clearUpdateHandlers();
    }

    public void init() {
        circleSprite.registerEntityModifier(new LoopEntityModifier(
                new RotationModifier(5, 0, 360)));
    }

    public int getCircleColor() {
        return circleColor;
    }

    public Sprite getCircleSprite() {
        return circleSprite;
    }


    public void setRequestedColor(int requestedColor) {
        this.requestedColor = requestedColor;
    }
}
