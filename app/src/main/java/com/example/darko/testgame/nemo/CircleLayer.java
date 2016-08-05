package com.example.darko.testgame.nemo;

import org.andengine.entity.Entity;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created by darko on 7/29/2016.
 */
public class CircleLayer extends Entity {
    private Scene scene;
    private int circleCount, color, requestedColor;
    private static LinkedList<ColoredCircle> circles;

    public CircleLayer(int circleCount, int color, Scene scene, int requestedColor) {
        this.scene = scene;
        this.requestedColor = requestedColor;
        this.circleCount = circleCount;
        this.color = color;
        circles = new LinkedList<>();
        addCircles();

    }

    public static Iterator getIterator() {
        return circles.iterator();
    }

    private void addCircles() {
        clearEntityModifiers();
        clearUpdateHandlers();
        int startingX = (int) (NemoGameActivity.getSharedInstance().mCamera.getWidth() / 4);
        for (int i = 1; i <= 4; i++) {
            for (int m = 0; m < 5; m++) {
                ColoredCircle e = (ColoredCircle) NemoCirclesPool.sharedEnemyPool().obtainPoolItem();
                e.setRequestedColor(requestedColor);
//                e.setCircleColor(new Random().nextInt(4));
                float finalPosX = startingX + (m * (e.circleSprite.getWidth() * 2));
                float finalPosY = (float) (i * (e.circleSprite.getHeight() * 1.3));

                e.circleSprite.setPosition(finalPosX, finalPosY);
                e.circleSprite.setVisible(true);

                attachChild(e.circleSprite);
                scene.registerTouchArea(e.circleSprite);
                circles.add(e);
                MoveYModifier moveDown = new MoveYModifier(1, 10, 20);
                MoveYModifier moveUp = new MoveYModifier(1, 20, 10);

                registerEntityModifier(new LoopEntityModifier(
                        new SequenceEntityModifier(moveDown, moveUp)));
            }
        }
    }

}
