package com.example.darko.testgame.colision_models;

import com.example.darko.testgame.ColisionGameActivity;

import org.andengine.entity.primitive.Rectangle;

/**
 * Created by darko on 7/7/2016.
 */
public class Bullet {
    public Rectangle sprite;
    public Bullet() {
        sprite = new Rectangle(0, 0, 10, 10, ColisionGameActivity.getSharedInstance()
                .getVertexBufferObjectManager());
        sprite.setColor(0.09904f, 0f, 0.1786f);
    }
}