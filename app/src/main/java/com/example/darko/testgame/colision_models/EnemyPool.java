package com.example.darko.testgame.colision_models;

import org.andengine.util.adt.pool.GenericPool;

/**
 * Created by darko on 7/7/2016.
 */
public class EnemyPool extends GenericPool {

    public static EnemyPool instance;

    public static EnemyPool sharedEnemyPool() {
        if (instance == null)
            instance = new EnemyPool();
        return instance;
    }

    private EnemyPool() {
        super();
    }

    @Override
    protected Object onAllocatePoolItem() {
        return new Enemy();
    }

    @Override
    protected void onHandleObtainItem(Object pItem) {
        super.onHandleObtainItem(pItem);
        ((Enemy) pItem).init();
    }

    @Override
    protected void onHandleRecycleItem(Object pItem) {
        super.onHandleRecycleItem(pItem);
        ((Enemy) pItem).sprite.setVisible(false);
        ((Enemy) pItem).sprite.detachSelf();
        ((Enemy) pItem).clean();
    }
}
