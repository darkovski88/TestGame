package com.example.darko.testgame.colision_models;

import com.example.darko.testgame.ColisionGameActivity;
import com.example.darko.testgame.colision_scenes.GameScene;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;

/**
 * Created by darko on 7/7/2016.
 */
public class Ship {
    public Sprite sprite;
    public static Ship instance;
    Camera mCamera;
    private boolean moveable;
    ITextureRegion mShipTextureRegion;

    public static Ship getSharedInstance() {
        if (instance == null)
            instance = new Ship();
        return instance;
    }

    private Ship() {

            this.mShipTextureRegion = TextureRegionFactory.extractFromTexture(ColisionGameActivity.getSharedInstance().myShipTexture);
            sprite = new Sprite(70, 30, this.mShipTextureRegion, ColisionGameActivity.getSharedInstance().getVertexBufferObjectManager());

            moveable = true;
            mCamera = ColisionGameActivity.getSharedInstance().mCamera;
            sprite.setPosition((mCamera.getWidth() / 2)-10 - sprite.getWidth() / 2,
                    mCamera.getHeight() - sprite.getHeight() - 10);

    }

    public void moveShip(float accelerometerSpeedX) {
        if (!moveable)
            return;

        if (accelerometerSpeedX != 0) {
            int lL = 0;
            int rL = (int) (mCamera.getWidth() - (int) sprite.getWidth());
            float newX;

            // Calculate New X,Y Coordinates within Limits
            if (sprite.getX() >= lL)
                newX = sprite.getX() + accelerometerSpeedX;
            else
                newX = lL;
            if (newX <= rL)
                newX = sprite.getX() + accelerometerSpeedX;
            else
                newX = rL;

            // Double Check That New X,Y Coordinates are within Limits
            if (newX < lL)
                newX = lL;
            else if (newX > rL)
                newX = rL;
            sprite.setPosition(newX, sprite.getY());
        }
    }

    // shoots bullets
    public void shoot() {
        if (!moveable)
            return;
        GameScene scene = (GameScene) ColisionGameActivity.getSharedInstance().mCurrentScene;

        Bullet b = (Bullet) BulletPool.sharedBulletPool().obtainPoolItem();
        b.sprite.setPosition(sprite.getX() + sprite.getWidth() / 2,
                sprite.getY());
        MoveYModifier mod = new MoveYModifier(1.5f, b.sprite.getY(),
                -b.sprite.getHeight());

        b.sprite.setVisible(true);
        b.sprite.detachSelf();
        scene.attachChild(b.sprite);
        scene.bulletList.add(b);
        b.sprite.registerEntityModifier(mod);
        scene.bulletCount++;
    }
}