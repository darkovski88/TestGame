package com.example.darko.testgame.colision_scenes;

import com.example.darko.testgame.ColisionGameActivity;
import com.example.darko.testgame.colision_listeners.CoolDown;
import com.example.darko.testgame.colision_listeners.GameLoopUpdateHandler;
import com.example.darko.testgame.colision_models.Bullet;
import com.example.darko.testgame.colision_models.BulletPool;
import com.example.darko.testgame.colision_models.Enemy;
import com.example.darko.testgame.colision_models.EnemyLayer;
import com.example.darko.testgame.colision_models.EnemyPool;
import com.example.darko.testgame.colision_models.Ship;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.IEntityFactory;
import org.andengine.entity.particle.ParticleSystem;
import org.andengine.entity.particle.emitter.PointParticleEmitter;
import org.andengine.entity.particle.initializer.VelocityParticleInitializer;
import org.andengine.entity.particle.modifier.AlphaParticleModifier;
import org.andengine.entity.particle.modifier.ColorParticleModifier;
import org.andengine.entity.particle.modifier.RotationParticleModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.color.Color;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created by darko on 7/7/2016.
 */
public class GameScene extends Scene implements IOnSceneTouchListener {
    private static final String TAG = GameScene.class.getSimpleName();
    //    private final SensorManager sensorManager;
    public Ship ship;
    private Camera mCamera;

    public float accelerometerSpeedX;
    public LinkedList bulletList;
    public int bulletCount;

    public GameScene() {

        TextureRegion backgroundTextureRegion = TextureRegionFactory.extractFromTexture(ColisionGameActivity.getSharedInstance().mBackgroundTexture);
        final Sprite background = new Sprite(ColisionGameActivity.getSharedInstance().CAMERA_WIDTH / 2f - backgroundTextureRegion.getWidth() / 2f,
                ColisionGameActivity.getSharedInstance().CAMERA_HEIGHT / 2f - backgroundTextureRegion.getHeight() / 2f, backgroundTextureRegion, ColisionGameActivity.getSharedInstance().getVertexBufferObjectManager());
        attachChild(background);
//        setBackground(new Background(0.09804f, 0.6274f, 0.8784f));

        mCamera = ColisionGameActivity.getSharedInstance().mCamera;
        ship = Ship.getSharedInstance();
        attachChild(ship.sprite);
        final Random rand = new Random();
//        attachChild(new EnemyLayer(rand.nextInt(5)));

//        sensorManager = (SensorManager) ColisionGameActivity.getSharedInstance()
//                .getSystemService(ColisionGameActivity.SENSOR_SERVICE);
//        SensorListener.getSharedInstance();
//        sensorManager.registerListener(SensorListener.getSharedInstance(),
//                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
//                SensorManager.SENSOR_DELAY_GAME);
//        EnemyLayer enemyLayer = new EnemyLayer(3);
//        enemyLayer.setPosition(ColisionGameActivity.CAMERA_WIDTH / 6f, ColisionGameActivity.CAMERA_HEIGHT);
        attachChild(new EnemyLayer(3));
        bulletList = new LinkedList();
        ColisionGameActivity.getSharedInstance().setCurrentScene(GameScene.this);
        registerUpdateHandler(new GameLoopUpdateHandler());
        createControllers();

//        setOnSceneTouchListener(this);

//        registerUpdateHandler(new TimerHandler(3.5f, true, new ITimerCallback() {
//
//            @Override
//            public void onTimePassed(TimerHandler pTimerHandler) {
//            TODO
//            }
//        }));
    }

    @Override
    public void onDetached() {
        super.onDetached();

    }

    public void createControllers() {
        ITextureRegion left, right, shootControl;
        Sprite shootSprite, leftSprite, rightSprite;

        shootControl = TextureRegionFactory.extractFromTexture(ColisionGameActivity.getSharedInstance().shoot);
        left = TextureRegionFactory.extractFromTexture(ColisionGameActivity.getSharedInstance().left);
        right = TextureRegionFactory.extractFromTexture(ColisionGameActivity.getSharedInstance().right);

        shootSprite = new Sprite(80, 50, shootControl, ColisionGameActivity.getSharedInstance().getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
//                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
                return shootBullet();
            }
        };
        shootSprite.setPosition(700, 400);

        leftSprite = new Sprite(80, 80, left, ColisionGameActivity.getSharedInstance().getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (pSceneTouchEvent.isActionDown() && ship.sprite.getX() - 266 > 0)
                    ship.moveShip(-266);
                return true;
            }
        };
        leftSprite.setPosition(20, 400);
        rightSprite = new Sprite(50, 50, right, ColisionGameActivity.getSharedInstance().getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (pSceneTouchEvent.isActionDown() && ColisionGameActivity.getSharedInstance().mCamera.getWidth() - ship.sprite.getX() > 266)
                    ship.moveShip(266);
                return true;
            }
        };
        rightSprite.setPosition(100, 400);


        attachChild(shootSprite);
        attachChild(leftSprite);
        attachChild(rightSprite);

        registerTouchArea(shootSprite);
        registerTouchArea(leftSprite);
        registerTouchArea(rightSprite);

    }

    public void moveShip() {
        ship.moveShip(accelerometerSpeedX);
    }

    @Override
    public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
        shootBullet();
        return true;
    }

    private boolean shootBullet() {
        if (!CoolDown.getSharedInstance().checkValidity())
            return false;
        synchronized (this) {
            ship.shoot();
        }
        return true;
    }

//    public void cleaner() {
//        synchronized (this) {
//            Iterator it = bulletList.iterator();
//            while (it.hasNext()) {
//                Bullet b = (Bullet) it.next();
//                if (b.sprite.getY() <= -b.sprite.getHeight()) {
//                    BulletPool.sharedBulletPool().recyclePoolItem(b);
//                    it.remove();
//                    continue;
//                }
//            }
//        }
//    }


    public void cleaner() {
        synchronized (this) {
            Iterator eIt = EnemyLayer.getIterator();
            while (eIt.hasNext()) {
                Enemy e = (Enemy) eIt.next();
                Iterator it = bulletList.iterator();
                while (it.hasNext()) {
                    Bullet b = (Bullet) it.next();
                    if (b.sprite.getY() <= -b.sprite.getHeight()) {
                        BulletPool.sharedBulletPool().recyclePoolItem(b);
                        it.remove();
                        continue;
                    }
                    if (b.sprite.collidesWith(e.sprite)) {
                        if (!e.gotHit()) {
                            createExplosion(e.sprite.getX(), e.sprite.getY(), e.sprite.getParent(), ColisionGameActivity.getSharedInstance());
                            EnemyPool.sharedEnemyPool().recyclePoolItem(e);
                            eIt.remove();

                        }
                        BulletPool.sharedBulletPool().recyclePoolItem(b);
                        it.remove();
                        break;
                    }
                }
            }
        }
    }

    private void createExplosion(final float posX, final float posY, final IEntity target, final SimpleBaseGameActivity activity) {
        int mNumPart = 15;
        int mTimePart = 2;

        PointParticleEmitter particleEmitter = new PointParticleEmitter(posX, posY);
        IEntityFactory recFact = new IEntityFactory() {
            @Override
            public Rectangle create(float pX, float pY) {
                Rectangle rect = new Rectangle(posX, posY, 10, 10, activity.getVertexBufferObjectManager());
                rect.setColor(Color.GREEN);
                return rect;
            }
        };
        final ParticleSystem particleSystem = new ParticleSystem(recFact, particleEmitter, 500, 500, mNumPart);
        particleSystem.addParticleInitializer(new VelocityParticleInitializer(-50, 50, -50, 50));

        particleSystem.addParticleModifier(new AlphaParticleModifier(0, 0.6f * mTimePart, 1, 0));
        particleSystem.addParticleModifier(new RotationParticleModifier(0, mTimePart, 0, 360));
        particleSystem.addParticleModifier(new ColorParticleModifier(0, 0.6f * mTimePart, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f));
        target.attachChild(particleSystem);
        target.registerUpdateHandler(new TimerHandler(mTimePart, new ITimerCallback() {
            @Override
            public void onTimePassed(final TimerHandler pTimerHandler) {
                particleSystem.detachSelf();
                target.sortChildren();
                target.unregisterUpdateHandler(pTimerHandler);
            }
        }));
    }
}
