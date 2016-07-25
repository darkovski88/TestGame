package com.example.darko.testgame.colision_models;

import org.andengine.util.adt.pool.GenericPool;

/**
 * Created by darko on 7/7/2016.
 */
public class BulletPool extends GenericPool {

    public static BulletPool instance;

    public static BulletPool sharedBulletPool() {
        if (instance == null)
            instance = new BulletPool();
        return instance;
    }

    private BulletPool() {
        super();
    }


    @Override
    protected Object onAllocatePoolItem() {
        return new Bullet();
    }

    @Override
    protected void onHandleRecycleItem(Object pItem) {
        super.onHandleRecycleItem(pItem);
        ((Bullet)pItem).sprite.clearEntityModifiers();
        ((Bullet)pItem).sprite.clearUpdateHandlers();
        ((Bullet)pItem).sprite.setVisible(false);
        ((Bullet)pItem).sprite.detachSelf();
    }
}
