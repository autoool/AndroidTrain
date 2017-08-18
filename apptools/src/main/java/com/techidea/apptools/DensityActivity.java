package com.techidea.apptools;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sam.zhang on 2017/8/10.
 */

public class DensityActivity extends AppCompatActivity {

    private static final String TAG = DensityActivity.class.getSimpleName();

    @Bind(R.id.textview_physical_dimensions)
    TextView textViewdimensions;
    @Bind(R.id.textview_DPI)
    TextView textViewDpi;
    @Bind(R.id.textview_px)
    TextView textViewPx;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_density);
        ButterKnife.bind(this);
        textViewDpi.setText(getDensity() + getScreenSizeOfDevice2());
    }

    private void getRealSize() {

    }

    private String getDensity() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        Log.d(TAG, "Density is " + displayMetrics.density +
                " densityDpi is " + displayMetrics.densityDpi +
                " height: " + displayMetrics.heightPixels +
                " width: " + displayMetrics.widthPixels);

        return "Density is " + displayMetrics.density +
                " densityDpi is " + displayMetrics.densityDpi +
                " height: " + displayMetrics.heightPixels +
                " width: " + displayMetrics.widthPixels;
    }

    private String getScreenSizeOfDevice2() {
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        double x = Math.pow(point.x / dm.xdpi, 2);
        double y = Math.pow(point.y / dm.ydpi, 2);
        double screenInches = Math.sqrt(x + y);
        Log.d(TAG, " Screen inches : " + screenInches);
        //dp*(dpi/160)  = px
        return " Screen inches : " + screenInches;
    }
}
