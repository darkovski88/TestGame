package carousel.test.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.HashMap;

/**
 * This activity displays an image on the screen.
 * The image has 10 different regions that can be clicked / touched.
 * When a region is touched, the activity changes the view to show a different
 * image.
 */

public class ImageAreasActivity extends Activity implements View.OnTouchListener {

    private HashMap<Integer, ColorViewPair> colorLayerMap = new HashMap<Integer, ColorViewPair>();
    private int changeToColor = 0;
    private ImageView paletteImageView;
    RelativeLayout relativeLayout;
    private int layerWithColorId = -1;
    private ImageView showColorImageView;

    /**
     * Create the view for the activity.
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        colorLayerMap.put(R.id.layer1, new ColorViewPair(Color.parseColor("#f2f06a"),findViewById(R.id.layer1)));
        colorLayerMap.put(R.id.layer2, new ColorViewPair(Color.parseColor("#f2f06a"),findViewById(R.id.layer2)));
        colorLayerMap.put(R.id.layer3, new ColorViewPair(Color.parseColor("#f2f06a"),findViewById(R.id.layer3)));
        colorLayerMap.put(R.id.layer4, new ColorViewPair(Color.parseColor("#334ad9"),findViewById(R.id.layer4)));
        colorLayerMap.put(R.id.layer5, new ColorViewPair(Color.parseColor("#16e8eb"),findViewById(R.id.layer5)));
        colorLayerMap.put(R.id.layer6, new ColorViewPair(Color.parseColor("#f89911"),findViewById(R.id.layer6)));
        colorLayerMap.put(R.id.layer7, new ColorViewPair(Color.parseColor("#6f6f6f"),findViewById(R.id.layer7)));
        colorLayerMap.put(R.id.layer8, new ColorViewPair(Color.parseColor("#ae3e07"),findViewById(R.id.layer8)));
        colorLayerMap.put(R.id.layer9, new ColorViewPair(Color.parseColor("#f8d99d"),findViewById(R.id.layer9)));
        colorLayerMap.put(R.id.layer10, new ColorViewPair(Color.parseColor("#f89dea"),findViewById(R.id.layer10)));
        colorLayerMap.put(R.id.layer11, new ColorViewPair(Color.parseColor("#f0d708"),findViewById(R.id.layer11)));

        relativeLayout = (RelativeLayout) findViewById(R.id.relative);
        relativeLayout.setOnTouchListener(this);
//        paletteImageView = (ImageView) findViewById(R.id.palette);
//        paletteImageView.setOnTouchListener(this);
//        showColorImageView = (ImageView) findViewById(R.id.showcolor);
    }

    /**
     * Respond to the user touching the screen.
     * Change images to make things appear and disappear from the screen.
     */

    public boolean onTouch(View v, MotionEvent ev) {
        final int action = ev.getAction();


        switch (action) {
            case MotionEvent.ACTION_DOWN:

                final int evX = (int) ev.getX();
                final int evY = (int) ev.getY();

                int touchColor = 0;

                /*
                if (v.getId() == paletteImageView.getId()) {
                    changeToColor = getHotspotColor(R.id.palette, evX, evY);
                    showColorImageView.setBackgroundColor(changeToColor);
                    Log.v("test", "we hit palette color " + changeToColor);
                    if (changeToColor == Color.WHITE || changeToColor == Color.BLACK) {
                        changeToColor = 0; // click outside the color circles
                    }
                }
                */

                if (getHotspotColor(R.id.layer10, evX, evY) != 0) {
                    touchColor = getHotspotColor(R.id.layer10, evX, evY);
                    layerWithColorId = R.id.layer10;
                } else if (getHotspotColor(R.id.layer9, evX, evY) != 0) {
                    touchColor = getHotspotColor(R.id.layer9, evX, evY);
                    layerWithColorId = R.id.layer9;
                } else if (getHotspotColor(R.id.layer8, evX, evY) != 0) {
                    touchColor = getHotspotColor(R.id.layer8, evX, evY);
                    layerWithColorId = R.id.layer8;
                } else if (getHotspotColor(R.id.layer7, evX, evY) != 0) {
                    touchColor = getHotspotColor(R.id.layer7, evX, evY);
                    layerWithColorId = R.id.layer7;
                } else if (getHotspotColor(R.id.layer6, evX, evY) != 0) {
                    touchColor = getHotspotColor(R.id.layer6, evX, evY);
                    layerWithColorId = R.id.layer6;
                } else if (getHotspotColor(R.id.layer5, evX, evY) != 0) {
                    touchColor = getHotspotColor(R.id.layer5, evX, evY);
                    layerWithColorId = R.id.layer5;
                } else if (getHotspotColor(R.id.layer4, evX, evY) != 0) {
                    touchColor = getHotspotColor(R.id.layer4, evX, evY);
                    layerWithColorId = R.id.layer4;
                } else if (getHotspotColor(R.id.layer3, evX, evY) != 0) {
                    touchColor = getHotspotColor(R.id.layer3, evX, evY);
                    layerWithColorId = R.id.layer3;
                } else if (getHotspotColor(R.id.layer2, evX, evY) != 0) {
                    layerWithColorId = R.id.layer2;
                    touchColor = getHotspotColor(R.id.layer2, evX, evY);
                } else if (getHotspotColor(R.id.layer1, evX, evY) != 0) {
                    touchColor = getHotspotColor(R.id.layer1, evX, evY);
                    layerWithColorId = R.id.layer1;
                }

                if (touchColor != 0) {
                    colorLayerMap.get(layerWithColorId).view.setVisibility(View.VISIBLE);
                }


                /*
                ColorTool ct = new ColorTool();
                int tolerance = 0;

                if (ct.closeMatch(colorLayerMap.get(R.id.layer10), touchColor, tolerance) && isColorOnThisLayer(R.id.layer10)) {

                    ImageView imageView10 = (ImageView) findViewById(R.id.layer10);
                    Bitmap bitmap = ((BitmapDrawable) imageView10.getDrawable()).getBitmap();

                    if (changeToColor != 0) {
                        imageView10.setImageBitmap(changeToColor(bitmap, touchColor, R.id.layer10));
                    } else {
                        imageView10.setImageBitmap(bitmap);
                    }

                    imageView10.setVisibility(View.VISIBLE);
                } else if (ct.closeMatch(colorLayerMap.get(R.id.layer9), touchColor, tolerance) && isColorOnThisLayer(R.id.layer9)) {

                    ImageView imageView9 = (ImageView) findViewById(R.id.layer9);
                    Bitmap bitmap = ((BitmapDrawable) imageView9.getDrawable()).getBitmap();

                    if (changeToColor != 0) {
                        imageView9.setImageBitmap(changeToColor(bitmap, touchColor, R.id.layer9));
                    } else {
                        imageView9.setImageBitmap(bitmap);
                    }
                    imageView9.setVisibility(View.VISIBLE);

                } else if (ct.closeMatch(colorLayerMap.get(R.id.layer8), touchColor, tolerance) && isColorOnThisLayer(R.id.layer8)) {

                    ImageView imageView8 = (ImageView) findViewById(R.id.layer8);
                    Bitmap bitmap = ((BitmapDrawable) imageView8.getDrawable()).getBitmap();

                    if (changeToColor != 0) {
                        imageView8.setImageBitmap(changeToColor(bitmap, touchColor, R.id.layer8));
                    } else {
                        imageView8.setImageBitmap(bitmap);
                    }
                    imageView8.setVisibility(View.VISIBLE);

                } else if (ct.closeMatch(colorLayerMap.get(R.id.layer7), touchColor, tolerance) && isColorOnThisLayer(R.id.layer7)) {

                    ImageView imageView7 = (ImageView) findViewById(R.id.layer7);
                    Bitmap bitmap = ((BitmapDrawable) imageView7.getDrawable()).getBitmap();

                    if (changeToColor != 0) {
                        imageView7.setImageBitmap(changeToColor(bitmap, touchColor, R.id.layer7));
                    } else {
                        imageView7.setImageBitmap(bitmap);
                    }
                    imageView7.setVisibility(View.VISIBLE);

                } else if (ct.closeMatch(colorLayerMap.get(R.id.layer6), touchColor, tolerance) && isColorOnThisLayer(R.id.layer6)) {

                    ImageView imageView6 = (ImageView) findViewById(R.id.layer6);
                    Bitmap bitmap = ((BitmapDrawable) imageView6.getDrawable()).getBitmap();

                    if (changeToColor != 0) {
                        imageView6.setImageBitmap(changeToColor(bitmap, touchColor, R.id.layer6));
                    } else {
                        imageView6.setImageBitmap(bitmap);
                    }
                    imageView6.setVisibility(View.VISIBLE);

                } else if (ct.closeMatch(colorLayerMap.get(R.id.layer5), touchColor, tolerance) && isColorOnThisLayer(R.id.layer5)) {

                    ImageView imageView5 = (ImageView) findViewById(R.id.layer5);
                    Bitmap bitmap = ((BitmapDrawable) imageView5.getDrawable()).getBitmap();

                    if (changeToColor != 0) {
                        imageView5.setImageBitmap(changeToColor(bitmap, touchColor, R.id.layer5));
                    } else {
                        imageView5.setImageBitmap(bitmap);
                    }
                    imageView5.setVisibility(View.VISIBLE);

                } else if (ct.closeMatch(colorLayerMap.get(R.id.layer4), touchColor, tolerance) && isColorOnThisLayer(R.id.layer4)) {

                    ImageView imageView4 = (ImageView) findViewById(R.id.layer4);
                    Bitmap bitmap = ((BitmapDrawable) imageView4.getDrawable()).getBitmap();

                    if (changeToColor != 0) {
                        imageView4.setImageBitmap(changeToColor(bitmap, touchColor, R.id.layer4));
                    } else {
                        imageView4.setImageBitmap(bitmap);
                    }
                    imageView4.setVisibility(View.VISIBLE);

                } else
                if (ct.closeMatch(colorLayerMap.get(R.id.layer3), touchColor, tolerance) && isColorOnThisLayer(R.id.layer3)) {

                    ImageView imageView3 = (ImageView) findViewById(R.id.layer3);
                    Bitmap bitmap = ((BitmapDrawable) imageView3.getDrawable()).getBitmap();

                    if (changeToColor != 0) {
                        imageView3.setImageBitmap(changeToColor(bitmap, touchColor, R.id.layer3));
                    } else {
                        imageView3.setImageBitmap(bitmap);
                    }
                    imageView3.setVisibility(View.VISIBLE);

                } else if (ct.closeMatch(colorLayerMap.get(R.id.layer2), touchColor, tolerance) && isColorOnThisLayer(R.id.layer2)) {

                    ImageView imageView2 = (ImageView) findViewById(R.id.layer2);
                    Bitmap bitmap = ((BitmapDrawable) imageView2.getDrawable()).getBitmap();

                    if (changeToColor != 0) {
                        imageView2.setImageBitmap(changeToColor(bitmap, touchColor, R.id.layer2));
                    } else {
                        imageView2.setImageBitmap(bitmap);
                    }
                    imageView2.setVisibility(View.VISIBLE);

                } else if (ct.closeMatch(colorLayerMap.get(R.id.layer1), touchColor, tolerance) && isColorOnThisLayer(R.id.layer1)) {

                    ImageView imageView1 = (ImageView) findViewById(R.id.layer1);
                    Bitmap bitmap = ((BitmapDrawable) imageView1.getDrawable()).getBitmap();

                    if (changeToColor != 0) {
                        imageView1.setImageBitmap(changeToColor(bitmap, touchColor, R.id.layer1));
                    } else {
                        imageView1.setImageBitmap(bitmap);
                    }
                    imageView1.setVisibility(View.VISIBLE);
                } else {
                    Log.d("ImageAreasActivity", "white color hit");
                }
*/
            break;

            default:
        } // end switch
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
//
    }

    /**
     * Get the color from the hotspot image at point x-y.
     */

    public int getHotspotColor(int hotspotId, int x, int y) {
        ImageView img = (ImageView) findViewById(hotspotId);
        if (img == null) {
            Log.d("ImageAreasActivity", "Hot spot image not found");
            return 0;
        } else {
            img.setDrawingCacheEnabled(true);
            Bitmap hotspots = Bitmap.createBitmap(img.getDrawingCache());
            if (hotspots == null) {
                Log.d("ImageAreasActivity", "Hot spot bitmap was not created");
                return 0;
            } else {
                img.setDrawingCacheEnabled(false);
                return hotspots.getPixel(x, y);
            }
        }
    }

    private Bitmap changeToColor(Bitmap sourceBitmap, int srcColor, int resourceLayoutId) {

        int bmpWidth = sourceBitmap.getWidth() - 1;
        int bmpHeight = sourceBitmap.getHeight() - 1;

        Bitmap resultBitmap = Bitmap.createBitmap(sourceBitmap.getWidth(), sourceBitmap.getHeight(), Bitmap.Config.ARGB_8888);

        int[] allPixels = new int[sourceBitmap.getHeight() * sourceBitmap.getWidth()];

        sourceBitmap.getPixels(allPixels, 0, bmpWidth, 0, 0, bmpWidth, bmpHeight);

        //  changeFromColor = colorLayerMap.get(resourceLayoutId);

        for (int i = 0; i < allPixels.length; i++) {
//            if (allPixels[i] == srcColor) {
            if (allPixels[i] != 0) {
                allPixels[i] = changeToColor;
            }
        }

        colorLayerMap.get(resourceLayoutId).color = changeToColor;

        resultBitmap.setPixels(allPixels, 0, bmpWidth, 0, 0, bmpWidth, bmpHeight);
        return resultBitmap;
    }

    private boolean isColorOnThisLayer(int layerId) {
        return layerWithColorId == layerId;
    }

} // end class
