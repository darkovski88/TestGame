package carousel.test.myapplication;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
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
    private Bitmap changeColorBitmap;

    private ImageView paletteImageView;
    RelativeLayout relativeLayout;
    private int layerWithColorId = -1;
    private ImageView showColorImageView;

    private LruCache<Integer, BitmapDrawable> mBitmapCache;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        // Use 1/8th of the available memory for this memory cache.
        final int cacheSize = maxMemory / 8;

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

        colorLayerMap.put(R.id.palette, new ColorViewPair(Color.parseColor("#ffffff"),findViewById(R.id.palette)));

        mBitmapCache = new LruCache<Integer, BitmapDrawable>(cacheSize) {};

        BitmapDrawable currentBitmap;

        for (int layerNumber : colorLayerMap.keySet()) {
            currentBitmap =  ((BitmapDrawable) ((ImageView) colorLayerMap.get(layerNumber).view).getDrawable());
            if (mBitmapCache.get(layerNumber) == null ) {
                mBitmapCache.put(layerNumber, currentBitmap);
            }
        }

        relativeLayout = (RelativeLayout) findViewById(R.id.relative);
        relativeLayout.setOnTouchListener(this);

        paletteImageView = (ImageView) findViewById(R.id.palette);
        paletteImageView.setOnTouchListener(this);

//        showColorImageView = (ImageView) findViewById(R.id.showcolor);

    }

    public boolean onTouch(View v, MotionEvent ev) {
        final int action = ev.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:

                final int evX = (int) ev.getX();
                final int evY = (int) ev.getY();

                if (v.getId() == paletteImageView.getId()) {
                    changeToColor = getHotspotColor(R.id.palette, evX, evY);
//                    showColorImageView.setBackgroundColor(changeToColor);

                    Log.v("test", "we hit palette color " + changeToColor);
                    if (changeToColor == Color.WHITE || changeToColor == Color.BLACK) {
                        changeToColor = 0; // click outside the color circles
                    }

                    return true;
                }

                if (getHotspotColor(R.id.layer10, evX, evY) != 0) {
                    layerWithColorId = R.id.layer10;
                } else if (getHotspotColor(R.id.layer9, evX, evY) != 0) {
                    layerWithColorId = R.id.layer9;
                } else if (getHotspotColor(R.id.layer8, evX, evY) != 0) {
                    layerWithColorId = R.id.layer8;
                } else if (getHotspotColor(R.id.layer7, evX, evY) != 0) {
                    layerWithColorId = R.id.layer7;
                } else if (getHotspotColor(R.id.layer6, evX, evY) != 0) {
                    layerWithColorId = R.id.layer6;
                } else if (getHotspotColor(R.id.layer5, evX, evY) != 0) {
                    layerWithColorId = R.id.layer5;
                } else if (getHotspotColor(R.id.layer4, evX, evY) != 0) {
                    layerWithColorId = R.id.layer4;
                } else if (getHotspotColor(R.id.layer3, evX, evY) != 0) {
                    layerWithColorId = R.id.layer3;
                } else if (getHotspotColor(R.id.layer2, evX, evY) != 0) {
                    layerWithColorId = R.id.layer2;
                } else if (getHotspotColor(R.id.layer1, evX, evY) != 0) {
                    layerWithColorId = R.id.layer1;
                }

                // we don't have view with that id, probably hit on no-color pixel
                if (colorLayerMap.get(layerWithColorId) == null) return false;

                ImageView currentView = (ImageView) colorLayerMap.get(layerWithColorId).view;
                if (layerWithColorId != -1 && currentView.getVisibility() == View.INVISIBLE) {
                    currentView.setVisibility(View.VISIBLE);
                }

                if (changeToColor != 0 && changeToColor != colorLayerMap.get(layerWithColorId).color
                        && currentView.getVisibility() == View.VISIBLE) {

                    currentView.invalidate();
                    currentView.setImageResource(0);
                    currentView.setImageBitmap(null);
                    currentView.destroyDrawingCache();

                    ((ImageView) colorLayerMap.get(layerWithColorId).view).setImageBitmap(
                    changeToColor(mBitmapCache.get(layerWithColorId).getBitmap(),  layerWithColorId)
                    );
                }

            break;

            default:
        } // end onTouch switch
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * Get the color from the hotspot image at point x-y.
     */

    public int getHotspotColor(int hotspotId, int x, int y) {
        ImageView img = (ImageView) colorLayerMap.get(hotspotId).view;

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
                int retval = hotspots.getPixel(x, y);

                hotspots = null;

                return retval;
            }
        }
    }

    private Bitmap changeToColor(Bitmap sourceBitmap, int resourceLayoutId) {

        colorLayerMap.get(resourceLayoutId).color = changeToColor;

        if (changeColorBitmap != null) {
            changeColorBitmap.recycle();
            changeColorBitmap = null;
        }
        changeColorBitmap = Bitmap.createBitmap(sourceBitmap.getWidth(), sourceBitmap.getHeight(), Bitmap.Config.ARGB_8888);


//        (new Thread() {
//            @Override
//            public void run() {
//                super.run();

        int bmpWidth = sourceBitmap.getWidth() - 1;
        int bmpHeight = sourceBitmap.getHeight() - 1;

        int[] allPixels = new int[sourceBitmap.getHeight() * sourceBitmap.getWidth()];

        sourceBitmap.getPixels(allPixels, 0, bmpWidth, 0, 0, bmpWidth, bmpHeight);
        int alpha = 0;

        for (int i = 0; i < allPixels.length; i++) {
            if (allPixels[i] != 0) {
                alpha = allPixels[i] & 0xFF0000;
                allPixels[i] = changeToColor;
                if (alpha != 0) {
                    allPixels[i] = allPixels[i] & 0x00FFFF;
                    allPixels[i] = allPixels[i] & alpha;
                    alpha = 0;
                }
            }
        }

        changeColorBitmap.setPixels(allPixels, 0, bmpWidth, 0, 0, bmpWidth, bmpHeight);
//                changeColorBitmap.setPixels(allPixels, 0, bmpWidth, 0, 0, bmpWidth, bmpHeight);
//            }
//        }).start();


        return changeColorBitmap;
    }

    private boolean isColorOnThisLayer(int layerId) {
        return layerWithColorId == layerId;
    }

}
