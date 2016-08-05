package com.example.darko.testgame.nemo;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.io.in.IInputStreamOpener;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by darko on 7/29/2016.
 */
public class NemoGameActivity extends SimpleBaseGameActivity {
    public static final int CAMERA_WIDTH = 1024;
    public static final int CAMERA_HEIGHT = 512;
    private static NemoGameActivity instance;

    private final String PATH = "gfx/nemo/";
    private final String TAG = NemoGameActivity.class.getSimpleName();

    ITexture background, whiteCircle, yellowCircle, blueCircle, redCircle;
    Camera mCamera;
    private NemoGameScene mCurrentScene;

    @Override
    protected void onCreateResources() {
        try {
            whiteCircle = new BitmapTexture(getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open(PATH + "white_circle.png");
                }
            });
            yellowCircle = new BitmapTexture(getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open(PATH + "yellow_circle.png");
                }
            });
            blueCircle = new BitmapTexture(getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open(PATH + "blue_circle.png");
                }
            });
            redCircle = new BitmapTexture(getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open(PATH + "red_circle.png");
                }
            });

            background = new BitmapTexture(getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open(PATH + "512.png");
                }
            });

            background.load();
            yellowCircle.load();
            blueCircle.load();
            whiteCircle.load();
            redCircle.load();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected Scene onCreateScene() {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            mRenderSurfaceView.setPreserveEGLContextOnPause(true);
        }
        mRenderSurfaceView.refreshDrawableState();

        mEngine.registerUpdateHandler(new FPSLogger());
        mCurrentScene = new NemoGameScene();
        return mCurrentScene;
    }

    public Scene getCurrentScene() {
        return mCurrentScene;
    }

    @Override
    public EngineOptions onCreateEngineOptions() {
        instance = this;
        mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

        return new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);
    }

    public static NemoGameActivity getSharedInstance() {
        return instance;
    }
}
