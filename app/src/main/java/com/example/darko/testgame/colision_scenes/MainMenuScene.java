package com.example.darko.testgame.colision_scenes;

import com.example.darko.testgame.ColisionGameActivity;
import com.example.darko.testgame.R;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;

/**
 * Created by darko on 7/7/2016.
 */
public class MainMenuScene extends MenuScene implements MenuScene.IOnMenuItemClickListener {
    final int MENU_START = 0;

    private ColisionGameActivity activity;

    public MainMenuScene() {
        super(ColisionGameActivity.getSharedInstance().mCamera);
        activity = ColisionGameActivity.getSharedInstance();

        setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
        IMenuItem startButton = new TextMenuItem(MENU_START, activity.mFont, activity.getString(R.string.start), activity.getVertexBufferObjectManager());
        startButton.setPosition(mCamera.getWidth() / 2 - startButton.getWidth() / 2, mCamera.getHeight() / 2 - startButton.getHeight() / 2);
        addMenuItem(startButton);
        setOnMenuItemClickListener(this);

    }

    @Override
    public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
        switch (pMenuItem.getID()) {
            case MENU_START:
                activity.setCurrentScene(new GameScene());
                return true;
            default:
                break;
        }
        return false;
    }
}
