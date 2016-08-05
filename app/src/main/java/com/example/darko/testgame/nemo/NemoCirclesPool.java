package com.example.darko.testgame.nemo;

import org.andengine.util.adt.pool.GenericPool;

/**
 * Created by darko on 7/29/2016.
 */
public class NemoCirclesPool extends GenericPool {
    private static NemoCirclesPool instance;

    @Override
    protected Object onAllocatePoolItem() {
        return new ColoredCircle();
    }

    @Override
    protected void onHandleObtainItem(Object pItem) {
        super.onHandleObtainItem(pItem);
//        ((ColoredCircle) pItem).init();
    }

    public static NemoCirclesPool sharedEnemyPool() {
        if (instance == null)
            instance = new NemoCirclesPool();
        return instance;
    }

    @Override
    protected void onHandleRecycleItem(Object pItem) {
        super.onHandleRecycleItem(pItem);

        ((ColoredCircle) pItem).circleSprite.setVisible(false);
        ((ColoredCircle) pItem).circleSprite.detachSelf();
        ((ColoredCircle) pItem).clean();
    }
}
