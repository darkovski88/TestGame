//package com.example.darko.testgame.mole;
///**
// * Class for make buttons with AndEngine. Very simple
// * @author Pablo Garc�a �lvarez
// * @since 03/29/2012
// */
//
//import org.andengine.entity.text.Text;
//import org.andengine.opengl.font.Font;
//import org.andengine.opengl.texture.region.TextureRegion;
//import org.andengine.util.HorizontalAlign;
//import org.andengine.entity.sprite.Sprite;
//
//public class SpriteButton extends Sprite {
//
//	/**
//	 * Constructor method
//	 * @author Pablo Garc�a �lvarez
//	 * @param pTextureRegion Texture region for the button
//	 * @param pFont Font for the text
//	 * @param pText String for the text
//	 * @param pRed Red component for text color
//	 * @param pGreen Green component for text color
//	 * @param pBlue Blue component for text color
//	 * @param  pAlpha Alpha (0.0f to transparent, 1.0f to opacque) component for texture
//	 */
//	public SpriteButton(TextureRegion pTextureRegion, Font pFont, String pText, float pRed, float pGreen, float pBlue, float pAlpha) {
//		super(0, 0, pTextureRegion);
//		Text buttonText = new Text(0, 0, pFont, pText, HorizontalAlign.CENTER);
//		buttonText.setPosition((this.getX() + this.getWidth() - buttonText.getWidth()) / 2, (this.getY() + this.getHeight() - buttonText.getHeight()) / 2);
//		buttonText.setColor(pRed, pGreen, pBlue);
//		this.setAlpha(pAlpha);
//		this.attachChild(buttonText);
//	}
//}
