package com.example.darko.testgame;

import android.graphics.Typeface;
import android.util.Log;

import com.example.darko.testgame.colision_scenes.GameScene;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.io.in.IInputStreamOpener;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by darko on 7/7/2016.
 */
public class ColisionGameActivity extends SimpleBaseGameActivity {
    public static final int CAMERA_WIDTH = 800;
    public static final int CAMERA_HEIGHT = 480;
    private static final String TAG = ColisionGameActivity.class.getSimpleName();

    public Font mFont;
    public Camera mCamera;
    public HUD mHud;

    //A reference to the current scene
    public Scene mCurrentScene;
    public static ColisionGameActivity instance;
    public ITexture myShipTexture, enemyShipTexture, left, right, shoot, mBackgroundTexture;


    @Override
    protected void onCreateResources() {
        mFont = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32);

        try {
            enemyShipTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("gfx/enemy_ship.png");
                }
            });
            myShipTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("gfx/r2.png");
                }
            });
            shoot = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("gfx/shoot.png");
                }
            });
            left = new BitmapTexture(getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("gfx/left.png");
                }
            });
            right = new BitmapTexture(getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("gfx/right.png");
                }
            });

            mBackgroundTexture = new BitmapTexture(getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("gfx/shoot_bg.png");
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

        //load textures to vRam

        enemyShipTexture.load();
        mBackgroundTexture.load();
        myShipTexture.load();
        shoot.load();
        left.load();
        right.load();
        mFont.load();
    }

    @Override
    public void onBackPressed() {
        Log.d("back", "backpressed");
        super.onBackPressed();
        System.exit(0);
        mCurrentScene.detachChildren();
        mCurrentScene.reset();

    }

    @Override
    protected Scene onCreateScene() {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            mRenderSurfaceView.setPreserveEGLContextOnPause(true);
        }
        mRenderSurfaceView.refreshDrawableState();

        mEngine.registerUpdateHandler(new FPSLogger());
        mCurrentScene = new GameScene();
        return mCurrentScene;
    }

    //this initializes the screen
    @Override
    public EngineOptions onCreateEngineOptions() {
        instance = this;
        mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        mHud = new HUD();
// Attach the HUD to the camera
        mCamera.setHUD(mHud);
        EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);

        return new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);
    }

    public static ColisionGameActivity getSharedInstance() {
        return instance;
    }

    // to change the current main scene
    public void setCurrentScene(Scene scene) {
        mCurrentScene = scene;
        getEngine().setScene(mCurrentScene);
    }
}
