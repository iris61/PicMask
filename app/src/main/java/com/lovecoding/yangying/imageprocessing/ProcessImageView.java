package com.lovecoding.yangying.imageprocessing;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.lovecoding.yangying.picmask.R;

import java.lang.reflect.Array;

/**
 * Created by yangying on 18/2/20.
 */

public class ProcessImageView extends ImageView {

    private float R_PERCENT = 100;
    private float G_PERCENT = 100;
    private float B_PERCENT = 100;
    private float A_PERCENT = 100;
    private float R_PERCENT_Temp = 100;
    private float G_PERCENT_Temp = 100;
    private float B_PERCENT_Temp = 100;
    private float A_PERCENT_Temp = 100;
    public ProcessImageView(Context context) {
        super(context);
    }

    public ProcessImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProcessImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ProcessImageView, defStyleAttr, 0);
        R_PERCENT = array.getFloat(R.styleable.ProcessImageView_R, 100);
        G_PERCENT = array.getFloat(R.styleable.ProcessImageView_G, 100);
        B_PERCENT = array.getFloat(R.styleable.ProcessImageView_B, 100);
        A_PERCENT = array.getFloat(R.styleable.ProcessImageView_A, 100);
        array.recycle();
    }

    @TargetApi(21)
    public ProcessImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void setArray(float[] array){
        for(int i = 0; i < 20; i++) {
            switch (i){
                case 0 : array[i] = R_PERCENT / 100;
                    break;
                case 6 : array[i] = G_PERCENT / 100;
                    break;
                case 12 : array[i] = B_PERCENT / 100;
                    break;
                case 18 : array[i] = A_PERCENT / 100;
                    break;
                default : array[i] = 0;
            }
        }
    }

    private Bitmap drawableToBitmp(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bd = (BitmapDrawable) drawable;
            return bd.getBitmap();
        }
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        // 创建画布
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

    @TargetApi(16)
    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        Paint mPaint = new Paint();
        ColorMatrix mColorMatrix = new ColorMatrix();   //新建颜色矩阵对象
        float[] array = new float[20];
        setArray(array);
        mColorMatrix.set(array);                        //设置颜色矩阵的值
        mPaint.setColorFilter(new ColorMatrixColorFilter(mColorMatrix));//设置画笔颜色过滤器
        Bitmap mBitmap = drawableToBitmp(drawable);

        if (drawable == null) {
            return; // couldn't resolve the URI
        }

        int mDrawableWidth = drawable.getIntrinsicWidth();
        int mDrawableHeight = drawable.getIntrinsicHeight();

        if (mDrawableWidth == 0 || mDrawableHeight == 0) {
            return;     // nothing to draw (empty bounds)
        }

        Matrix mDrawMatrix = getImageMatrix();
        if (mDrawMatrix == null && getPaddingTop() == 0 && getPaddingLeft() == 0) {
            //drawable.draw(canvas);
            canvas.drawBitmap(mBitmap, 0, 0, mPaint);       //描画（处理后的图片）
        } else {
            final int saveCount = canvas.getSaveCount();
            canvas.save();

            if (getCropToPadding()) {
                final int scrollX = getScrollX();
                final int scrollY = getScrollY();
                canvas.clipRect(scrollX + getPaddingLeft(), scrollY + getPaddingTop(),
                        scrollX + getRight() - getLeft() - getPaddingRight(),
                        scrollY + getBottom() - getTop() - getPaddingBottom());
            }

            canvas.translate(getPaddingLeft(), getPaddingTop());

            if (mDrawMatrix != null) {
                canvas.concat(mDrawMatrix);
            }
            //drawable.draw(canvas);
            canvas.drawBitmap(mBitmap, 0, 0, mPaint);       //描画（处理后的图片）
            canvas.restoreToCount(saveCount);
        }

    }

    public void setRpercent(float R_Percent){
        R_PERCENT = R_Percent;
        invalidate();
    }

    public void setGpercent(float G_Percent){
        G_PERCENT = G_Percent;
        invalidate();
    }

    public void setBpercent(float B_Percent){
        B_PERCENT = B_Percent;
        invalidate();
    }

    public void setApercent(float A_Percent){
        A_PERCENT = A_Percent;
        invalidate();
    }

    public void backToOriginImage(){
        R_PERCENT_Temp = R_PERCENT;
        G_PERCENT_Temp = G_PERCENT;
        B_PERCENT_Temp = B_PERCENT;
        A_PERCENT_Temp = A_PERCENT;
        R_PERCENT = 100;
        G_PERCENT = 100;
        B_PERCENT = 100;
        A_PERCENT = 100;
        invalidate();
    }

    public void backToCurrentImage(){
        R_PERCENT = R_PERCENT_Temp;
        G_PERCENT = G_PERCENT_Temp;
        B_PERCENT = B_PERCENT_Temp;
        A_PERCENT = A_PERCENT_Temp;
        invalidate();
    }
}
