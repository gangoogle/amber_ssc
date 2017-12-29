package com.butter.amberssc.ui.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.butter.amberssc.R;
import com.butter.amberssc.utils.DisplayUtil;
import com.butter.amberssc.utils.ScreenUtil;


public class TextShowView extends View {

    private Context mContext;
    private PorterDuffXfermode mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    private Bitmap backBitmap;

    private int mCurW, mCurH, mCurTop, mCurBelow;
    private Rect topRect, belowRect;
    private Paint mPaint, bPaint;
    private int height, width;
    private OnViewLoadComplete mOnViewLoadComplete;

    public TextShowView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public TextShowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mCurW = ScreenUtil.getScreenW(context);
        mCurH = ScreenUtil.getScreenH(context);
        height = mCurH;
        width = mCurW;
        init();
    }

    public TextShowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {

        //画笔初始化：
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setFilterBitmap(true);
        mPaint.setDither(true);
        mPaint.setColor(ContextCompat.getColor(mContext, R.color.colorMain));
        String familyName = "宋体";
        Typeface font = Typeface.create(familyName, Typeface.BOLD);
        mPaint.setTypeface(font);
        mPaint.setTextSize(DisplayUtil.sp2px(mContext, 50));
        mPaint.setTextAlign(Paint.Align.CENTER);

        //背部图片的初始化
        backBitmap = getDescBitmap();
        int mBitH = backBitmap.getHeight();
        int mBitW = backBitmap.getWidth();
        bPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bPaint.setFilterBitmap(true);
        bPaint.setDither(true);
        bPaint.setColor(ContextCompat.getColor(mContext, R.color.card_title_a));

        //设置当前的高度
        mCurTop = mBitH / 2;
        mCurBelow = mCurTop;

        int topRectH = mCurTop;
        int belowRectH = mCurBelow;
        topRect = new Rect(0,
                topRectH,
                mBitW,
                topRectH);  //初始化原图

        belowRect = new Rect(0,
                belowRectH,
                mBitW,
                belowRectH);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int saveLayerCount = canvas.saveLayer(0, 0, mCurW, mCurH, bPaint, Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(backBitmap, 0, 0, bPaint);// 绘制目标图
        bPaint.setXfermode(mXfermode);    //设置混排模式
        canvas.drawRect(topRect, bPaint);   //绘制源图
        canvas.drawRect(belowRect, bPaint);
        bPaint.setXfermode(null);         //清除混排模式
        canvas.restoreToCount(saveLayerCount);    //恢复保存的图层

        // 改变Rect区域，假如
        mCurTop -= 5;
        mCurBelow += 5;
        topRect.top = mCurTop;
        belowRect.bottom = mCurBelow;
        if (mCurTop >= height / 2 - getPx(150)) {
            invalidate();
        } else {
            if (mOnViewLoadComplete != null) {
                mOnViewLoadComplete.onComplete();
            }
        }
    }

    private Bitmap getDescBitmap() {

        int l = (width - 7 * DisplayUtil.sp2px(mContext, 50)) / 2;
        Bitmap descBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvasTemp = new Canvas(descBitmap);
        int[] easyPosition = {width / 2, height / 2 - getPx(8.0)};
        int[] weatherPosition = {width / 2, height / 2 + getPx(42.0)};
//        int [] easyPosition = {l, height / 2 - getPx(8.0)};
//        int [] weatherPosition = {l,  height / 2 + getPx(42.0)};
        canvasTemp.drawText(mContext.getResources().getString(R.string.splash_text_top), easyPosition[0],
                easyPosition[1], mPaint);
        canvasTemp.drawText(mContext.getResources().getString(R.string.splash_text_bottom), weatherPosition[0],
                weatherPosition[1], mPaint);
        return descBitmap;
    }

    private int getPx(int dp) {
        return DisplayUtil.dip2px(mContext, dp);
    }

    private int getPx(double sp) {
        return DisplayUtil.sp2px(mContext, (float) sp);
    }

    public interface OnViewLoadComplete {
        void onComplete();
    }

    public void setOnViewLoadCompleteListener(OnViewLoadComplete onViewLoadComplete) {
        this.mOnViewLoadComplete = onViewLoadComplete;
    }
}
