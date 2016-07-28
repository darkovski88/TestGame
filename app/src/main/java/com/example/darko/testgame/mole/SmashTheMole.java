package com.example.darko.testgame.mole;


import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.RepeatingSpriteBackground;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.atlas.bitmap.source.AssetBitmapTextureAtlasSource;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.io.in.IInputStreamOpener;

import java.io.IOException;
import java.io.InputStream;

public class SmashTheMole extends SimpleBaseGameActivity {
    private final String TAG = SmashTheMole.class.getSimpleName();
    private final String PATH = "gfx/mole_images/";
    //    // Camera size
    private static final int CAMERA_WIDTH = 800;
    private static final int CAMERA_HEIGHT = 480;

    public Camera mCamera;
    private MoleScene mCurrentScene;
    public static SmashTheMole instance;
    RepeatingSpriteBackground mGrassBackground;
    protected ITexture mole, moleHole;


    @Override
    protected void onCreateResources() {
        try {
            mole = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open(PATH + "mole.png");
                }
            });
            moleHole = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open(PATH + "molehole.png");
                }
            });
            mGrassBackground = new RepeatingSpriteBackground(mCamera.getWidth(), mCamera.getHeight(),
                    getTextureManager(), AssetBitmapTextureAtlasSource.create(getAssets(), PATH + "background2.png"), getVertexBufferObjectManager());
        } catch (IOException e) {
            e.printStackTrace();
        }

        mole.load();
        moleHole.load();
    }


    @Override
    protected Scene onCreateScene() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            mRenderSurfaceView.setPreserveEGLContextOnPause(true);
        }
        mRenderSurfaceView.refreshDrawableState();
        mEngine.registerUpdateHandler(new FPSLogger());
        mCurrentScene = new MoleScene();
        mCurrentScene.setBackground(mGrassBackground);
        return mCurrentScene;
    }

    @Override
    public EngineOptions onCreateEngineOptions() {
        instance = this;
        mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        return new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);
    }

    public static SmashTheMole getSharedInstance() {
        return instance;
    }
}