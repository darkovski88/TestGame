package com.example.darko.testgame.nemo;

import android.util.Log;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;

/**
 * Created by darko on 7/29/2016.
 */
public class NemoGameScene extends Scene {

    private static final String TAG = NemoGameScene.class.getSimpleName();

    public NemoGameScene() {
        TextureRegion backgroundTextureRegion = TextureRegionFactory.extractFromTexture(NemoGameActivity.getSharedInstance().background);

        final Sprite background = new Sprite(NemoGameActivity.getSharedInstance().CAMERA_WIDTH / 2f - backgroundTextureRegion.getWidth() / 2f,
                NemoGameActivity.getSharedInstance().CAMERA_HEIGHT / 2f - backgroundTextureRegion.getHeight() / 2f, backgroundTextureRegion, NemoGameActivity.getSharedInstance().getVertexBufferObjectManager());
        attachChild(background);
        Log.d(TAG, "adding circle layer");
        attachChild(new CircleLayer(5, 4, this,2));
        Log.d(TAG, "added circle layer");
    }
}
