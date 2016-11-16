package com.example.aishna.imagegallery.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.aishna.imagegallery.R;
import com.example.aishna.imagegallery.fragments.ImageSearchFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ImageDisplayActivity extends AppCompatActivity implements OnTouchListener {

    public final static String EXTRA_MESSAGE = "com.example.aishna.imagegallery.MESSAGE";
    @Bind(R.id.display_imageview)
    protected ImageView mImageView;
    private Matrix mMatrix = new Matrix();
    private Matrix mSavedMatrix = new Matrix();
    private int modeNone = 0;
    private int modeDrag = 1;
    private int modeZoom = 2;
    private float initDistance = 1f;
    private float floatDistance = 5f;
    private int mMode = modeNone;

    private PointF mFirstTouch = new PointF();
    private PointF mMidpoint = new PointF();
    private float firstDistance = initDistance;

    public static void callIntent(Context context, String mImage) {

        Intent intent = new Intent(context, ImageDisplayActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra(EXTRA_MESSAGE, "" + mImage);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);

        ButterKnife.bind(this);

        mImageView.setOnTouchListener(this);
        String url = getUrl();
        Glide.with(this).load(url).into(mImageView);
        mImageView.setScaleType(ImageView.ScaleType.MATRIX);
    }

    @Override
    public void onBackPressed() {
        this.finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        float scale;

        switch (event.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN: //first finger down only
                mSavedMatrix.set(mMatrix);
                mFirstTouch.set(event.getX(), event.getY());
                mMode = modeDrag;
                break;

            case MotionEvent.ACTION_UP: //first finger lifted
            case MotionEvent.ACTION_POINTER_UP: //second finger lifted
                mMode = modeNone;
                break;

            case MotionEvent.ACTION_POINTER_DOWN: //second finger down
                firstDistance = computeDistance(event); // calculates the distance between two points where user touched.
                // minimal distance between both the fingers
                if (firstDistance > floatDistance) {
                    mSavedMatrix.set(mMatrix);
                    setMidpoint(mMidpoint, event);
                    // sets the mid-point of the straight line between two points where user touched.
                    mMode = modeZoom;
                }
                break;

            case MotionEvent.ACTION_MOVE:
                if (mMode == modeDrag) { //movement of first finger
                    mMatrix.set(mSavedMatrix);
                    mMatrix.postTranslate(event.getX() - mFirstTouch.x, event.getY() - mFirstTouch.y);

                } else if (mMode == modeZoom) { //pinch zooming

                    float newDistance = computeDistance(event);

                    if (newDistance > floatDistance) {
                        mMatrix.set(mSavedMatrix);
                        scale = newDistance / firstDistance;
                        mMatrix.postScale(scale, scale, mMidpoint.x, mMidpoint.y);
                    }
                }
                break;
        }
        // Perform the transformation
        mImageView.setImageMatrix(mMatrix);
        return true;
    }

    private float computeDistance(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    private void setMidpoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    private String getUrl() {
        Intent intent = getIntent();
        String message = intent.getStringExtra(ImageSearchFragment.EXTRA_MESSAGE);
        return message;
    }
}