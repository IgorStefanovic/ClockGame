package rtrk.pnrs.clockbutton;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import rtrk.pnrs.clockgame.R;

/**
 * Created by Igor1 on 24/04/2016.
 */
public class AnalogClockView extends View {

    private Paint mPaint = new Paint();
    private Path mPath = new Path();
    private float radius = 160;
    private float hours, min;
    private float cx;
    private float cy;
    private final float degreeHours = 360 / 12;
    private final float degreeMin = 360 / 60;
    private int alpha = 100;
    public AnalogClockView(Context context) {
        super(context);
    }

    public AnalogClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AnalogClockView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        cx = getWidth() / 2;
        cy = getHeight() / 2;
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        drawCircles(canvas);
        drawNumbers(canvas);
        drawHands(canvas);
        if(!isEnabled()) {
            drawBlackCircle(canvas);
        }

    }
    //Kazaljke
    private void drawHands(Canvas canvas) {

        mPaint.setColor(Color.WHITE); //BLACK
        mPath.reset();
        mPath.moveTo(cx + 3, cy);
        mPath.lineTo(cx + 8, cy - radius + 70);
        mPath.lineTo(cx, cy - radius + 55);
        mPath.lineTo(cx - 8, cy - radius + 70);
        mPath.lineTo(cx - 3, cy);
        mPath.close();
        //Rotation on 18min, later updated
        canvas.save();
        canvas.rotate(degreeMin * min, cx, cy);
        canvas.drawPath(mPath, mPaint);
        canvas.restore();

        mPath.reset();
        mPath.moveTo(cx + 3, cy);
        mPath.lineTo(cx + 8, cy - radius + 105);
        mPath.lineTo(cx, cy - radius + 95);
        mPath.lineTo(cx - 8, cy - radius + 105);
        mPath.lineTo(cx - 3, cy);
        mPath.close();
        //Rotation on 11hour, later updated
        canvas.save();
        canvas.rotate(degreeHours * hours, cx, cy);
        canvas.drawPath(mPath, mPaint);
        canvas.restore();

        canvas.drawCircle(cx, cy, 8, mPaint);
        mPaint.setColor(Color.BLACK); //WHITE
        canvas.drawCircle(cx, cy, 2, mPaint);
    }

    //Brojevi i 12 tacaka i 60 manjih tacaka
    private void drawNumbers(Canvas canvas) {
        int[] numbers = {12, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        mPaint.setColor(Color.WHITE); //BLACK
        mPaint.setTextSize(25);
        mPaint.setFakeBoldText(true);
        mPaint.setTextAlign(Paint.Align.CENTER);

        for(int i = 0; i < 60; i++) {
            canvas.save();
            canvas.rotate(degreeMin * i, cx, cy);
            if(360 / 60 * i % 5 == 0) {
                canvas.drawCircle(cx, cy - radius + 15, 3, mPaint);
                canvas.rotate(-degreeMin * i, cx, cy - radius + 38);
                canvas.drawText(Integer.toString(numbers[i/5]), cx, cy - radius + 50, mPaint);
            } else {
                canvas.drawCircle(cx, cy - radius + 15, 1, mPaint);
            }
            canvas.restore();
        }
    }
    //Circle
    private void drawCircles(Canvas canvas) {
        //Frame
        mPaint.setColor(getResources().getColor(R.color.darkgreybutton));
        canvas.drawCircle(cx, cy, radius + 10, mPaint);

        //Circle
        mPaint.setColor(getResources().getColor(R.color.greybutton));
        canvas.drawCircle(cx, cy, radius, mPaint);
    }
    //Black circle
    private void drawBlackCircle(Canvas canvas) {
        mPaint.setColor(Color.BLACK);
        mPaint.setAlpha(alpha);
        canvas.drawCircle(cx, cy, radius + 10, mPaint);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = (float)Math.pow(cx - event.getX(), 2);
        float y = (float)Math.pow(cy - event.getY(), 2);
        float d = (float)Math.sqrt(x + y);

        if(d <= radius) {
            if(event.getAction() == MotionEvent.ACTION_DOWN) {
                callOnClick();
                invalidate();
                return true;
            }
        }
        return false; //super.onTouchEvent(event);
    }

    public float getMin() {
        return min;
    }

    public float getHours() {
        return hours;
    }

    public void setTime(float hours, float mins) {
        this.hours = hours;
        this.min = mins;
        invalidate();
    }
}
