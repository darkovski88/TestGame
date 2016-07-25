package com.example.darko.testgame.colision_models;

import android.util.Log;

import com.example.darko.testgame.ColisionGameActivity;

import org.andengine.entity.Entity;
import org.andengine.entity.modifier.MoveYModifier;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created by darko on 7/7/2016.
 */
public class EnemyLayer extends Entity {

    private static final String TAG = EnemyLayer.class.getSimpleName();
    private LinkedList<Enemy> enemies;
    public static EnemyLayer instance;
    public int enemyCount;

    public static EnemyLayer getSharedInstance() {
        return instance;
    }

    public static boolean isEmpty() {
        if (instance.enemies.size() == 0)
            return true;
        return false;
    }

    public static Iterator getIterator() {
        return instance.enemies.iterator();
    }


    public EnemyLayer(int enemyCount) {
        enemies = new LinkedList();
        instance = this;
        this.enemyCount = enemyCount;
        restart();
    }

    public void restart() {
        enemies.clear();
        clearEntityModifiers();
        clearUpdateHandlers();
        int startingX = (int) (ColisionGameActivity.getSharedInstance().mCamera.getWidth() / 3);

        for (int i = 0; i < enemyCount; i++) {
            Enemy e = (Enemy) EnemyPool.sharedEnemyPool().obtainPoolItem();
            float finalPosX =/* (i % 3) * 4 * e.sprite.getWidth()*/ ((startingX) * i) + e.sprite.getWidth() + 80;
            float finalPosY = ((int) (i / 3)) * e.sprite.getHeight() * 2;

            Log.d(TAG, "enemy " + finalPosX + " | " + finalPosY + " | width: " + e.sprite.getWidth() + "| i " + ((startingX) * i) + " | ASD " +ColisionGameActivity.getSharedInstance().mCamera.getWidth());

            Random r = new Random();
//            e.sprite.setPosition(r.nextInt(2) == 0 ? -e.sprite.getWidth() * 2
//                            : ColisionGameActivity.CAMERA_WIDTH + e.sprite.getWidth() * 2,
//                    (r.nextInt(5) + 1) * e.sprite.getHeight());
            e.sprite.setPosition(finalPosX, finalPosY);
            e.sprite.setVisible(true);

            attachChild(e.sprite);
//            e.sprite.registerEntityModifier(new MoveModifier(2,
//                    e.sprite.getX(), finalPosX, e.sprite.getY(), finalPosY));

            enemies.add(e);
        }

        setVisible(true);
        setPosition(0, 30);

        MoveYModifier mod1 = new MoveYModifier(30, 00, 900);
        registerEntityModifier(mod1);

//        MoveXModifier movRight = new MoveXModifier(1, 50, 120);
//        MoveXModifier movLeft = new MoveXModifier(1, 120, 50);
//        MoveYModifier moveDown = new MoveYModifier(1, 30, 100);
//        MoveYModifier moveUp = new MoveYModifier(1, 100, 30);
//
//        registerEntityModifier(new LoopEntityModifier(
//                new SequenceEntityModifier(movRight, moveDown, movLeft, moveUp)));
    }

    public void purge() {
        detachChildren();
        for (Enemy e : enemies) {
            EnemyPool.sharedEnemyPool().recyclePoolItem(e);
        }
        enemies.clear();
    }

    public static void purgeAndRestart() {
        instance.purge();
        instance.restart();
    }

    @Override
    public void onDetached() {
        purge();
        super.onDetached();
    }
}
