package com.example.darko.testgame.colision_listeners;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import com.example.darko.testgame.ColisionGameActivity;
import com.example.darko.testgame.colision_scenes.GameScene;

/**
 * Created by darko on 7/7/2016.
 */
public class SensorListener implements SensorEventListener {
    static SensorListener instance;
    GameScene scene;

    public SensorListener() {
        instance = this;
        scene = (GameScene) ColisionGameActivity.getSharedInstance().mCurrentScene;
    }

    public static SensorListener getSharedInstance() {
        if (instance == null)
            instance = new SensorListener();
        return instance;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        synchronized (this) {
            switch (sensorEvent.sensor.getType()) {
                case Sensor.TYPE_ACCELEROMETER:
                    scene.accelerometerSpeedX = sensorEvent.values[1];
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
