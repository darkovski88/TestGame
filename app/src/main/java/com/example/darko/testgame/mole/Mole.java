//package com.example.darko.testgame.mole;
//
//import java.util.Random;
//
//import org.andengine.entity.sprite.Sprite;
//import org.andengine.input.touch.TouchEvent;
//import org.andengine.opengl.texture.region.TextureRegion;
//
//
//
//public class Mole extends Sprite {
//	private int pX, pY;
//	private float secOnMole;
//	private boolean moleTouch;
//
//	public Mole(TextureRegion pTextureRegion) {
//		super(0, 0, pTextureRegion);
//		setZIndex(2);
//		resetPosition();
//		moleTouch = false;
//	}
//
//	public void resetPosition()
//	{
//		setVisible(false);
//		secOnMole = 0;
//		Random random = new Random();
//		do {
//			pX = random.nextInt(6);
//			pY = random.nextInt(3);
//		} while (SmashTheMole.existMoleInMap(pX, pY));
//		SmashTheMole.putMoleInMap(pX, pY);
//		setPosition(pX * 133 + 15, pY * 160 + 55);
//	}
//
//	@Override
//	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,	final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
//		if (!moleTouch) {
//			SmashTheMole.delMoleInMap(pX, pY);
//			SmashTheMole.vScore += 1;
//			moleTouch = true;
//			detachSelf(); // resetPosition();
//			return false;
//		} else {
//			return true;
//		}
//	}
//
//	@Override
//	protected void onManagedUpdate(float pSecondsElapsed) {
//		// TODO Auto-generated method stub
//		super.onManagedUpdate(pSecondsElapsed);
//
//		secOnMole += 2 * pSecondsElapsed;
//
//		if (!isVisible() && secOnMole > 5) {
//			setVisible(true);
//		} else if (isVisible() && secOnMole > 7) {
//			SmashTheMole.delMoleInMap(pX, pY);
//			SmashTheMole.vLoses += 1;
//			resetPosition();
//		}
//	}
//}
