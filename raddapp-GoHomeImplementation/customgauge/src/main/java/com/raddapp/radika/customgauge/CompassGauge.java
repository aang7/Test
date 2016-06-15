package com.raddapp.radika.customgauge;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.text.TextUtils;
import android.util.AttributeSet;

/**
 * Created by greenapsis on 4/04/16.
 */
public class CompassGauge extends CustomGauge {

    public CompassGauge(Context context) {
        super(context);
        init();
    }

    public CompassGauge(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void init(){
       super.init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        Path path = new Path();
        //path.moveTo(centerX - advance/2, centerY);
        //path.lineTo(centerX + advance/2, centerY);
        float paddingLeft = getPaddingLeft();
        float paddingRight= getPaddingRight();
        float paddingTop = getPaddingTop();
        float paddingBottom = getPaddingBottom();
        float width = getWidth() - (paddingLeft+paddingRight);
        float height = getHeight() - (paddingTop+paddingBottom);
        float radius = (width > height ? width/2 : height/2);

        mRectLeft = width/2 - radius + paddingLeft;
        mRectTop = height/2 - radius + paddingTop;
        mRectRight = width/2 - radius + paddingLeft + width;
        mRectBottom = height/2 - radius + paddingTop + height;

        mRect.set(mRectLeft, mRectTop, mRectRight, mRectBottom);

        mPaint.setColor(mStrokeColor);
        mPaint.setShader(null);
        canvas.drawArc(mRect, mStartAngel, mSweepAngel, false, mPaint);
        mPaint.setColor(mPointStartColor);
        mPaint.setShader(new LinearGradient(getWidth(), getHeight(), 0, 0, mPointEndColor, mPointStartColor, Shader.TileMode.CLAMP));
        if (mPointSize>0) {//if size of pointer is defined
            if (mPoint > mStartAngel + mPointSize/2) {
                canvas.drawArc(mRect, mPoint - mPointSize/2, mPointSize, false, mPaint);
            }
            else { //to avoid excedding start/zero point
                canvas.drawArc(mRect, mPoint, mPointSize, false, mPaint);
            }
        }
        else { //draw from start point to value point (long pointer)
            if (mValue==mStartValue) //use non-zero default value for start point (to avoid lack of pointer for start/zero value)
                canvas.drawArc(mRect, mStartAngel, DEFAULT_LONG_POINTER_SIZE, false, mPaint);
            else
                canvas.drawArc(mRect, mStartAngel, mPoint - mStartAngel, false, mPaint);
        }

        if (mDividerSize > 0) {
            mPaint.setColor(mDividerColor);
            mPaint.setShader(null);
            int i = mDividerDrawFirst ? 0 : 1;
            int max = mDividerDrawLast ? mDividersCount + 1 : mDividersCount;
            for (; i < max; i++) {
                canvas.drawArc(mRect, mStartAngel + i*mDividerStepAngel, mDividerSize, false, mPaint);
            }
        }

    }

    @Override
    public void setValue(int value) {
        mValue = value;

        //NON FLIPED
        /*mPoint = (int) (mStartAngel - (mValue-mStartValue) * mPointAngel)-90;
        if(mPoint<0)
            mPoint=360+mPoint;
        */

        //FLIPED
        mPoint = (int) (360-(mStartAngel + (mValue-mStartValue)*mPointAngel));
        //if(mPoint<0)
          //  mPoint=360-mPoint;
        invalidate();
    }

}
