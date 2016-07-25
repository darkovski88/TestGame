package com.example.darko.testgame.colision_models;

import com.example.darko.testgame.ColisionGameActivity;

import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;

/**
 * Created by darko on 7/7/2016.
 */
public class Enemy {

    public Sprite sprite;
    public int hp;
    //the max health for each enemy
    protected final int MAX_HEALTH = 1;
    ITextureRegion enemyShipRegion;

    public Enemy() {

        this.enemyShipRegion = TextureRegionFactory.extractFromTexture(ColisionGameActivity.getSharedInstance().enemyShipTexture);
        sprite = new Sprite(10, 10, this.enemyShipRegion, ColisionGameActivity.getSharedInstance().getVertexBufferObjectManager());
//        sprite.setColor(0.09904f, 0.8574f, 0.1786f);
        init();
    }

    // method for initializing the Enemy object , used by the constructor and
// the EnemyPool class
    public void init() {
        hp = MAX_HEALTH;
//        sprite.registerEntityModifier(new LoopEntityModifier(
//                new RotationModifier(5, 0, 360)));
    }

    public void clean() {
        sprite.clearEntityModifiers();
        sprite.clearUpdateHandlers();
    }

    // method for applying hit and checking if enemy died or not
// returns false if enemy died
    public boolean gotHit() {
        synchronized (this) {
            hp--;
            if (hp <= 0)
                return false;
            else
                return true;
        }
    }
}
