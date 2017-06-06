package gradient.mylibrary;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by 29028 on 2017/6/6.
 */

public class GradienTextView extends TextView {

    private int mOriginalColor= Color.BLACK;
    private int mChangeColor=Color.RED;
    private Paint mOriginalPaint,mChangePaint;
    private Orientation orientation=Orientation.LEFT_TO_RIGHT;
    private int baseLine;
    private float mCurrentProgress;
    public GradienTextView(Context context) {
        this(context,null);
    }
    public GradienTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }
    public GradienTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray td = context.obtainStyledAttributes(attrs, R.styleable.GradienTextView);
        mOriginalColor = td.getColor(R.styleable.GradienTextView_original_color, mOriginalColor);
        mChangeColor = td.getColor(R.styleable.GradienTextView_change_color, mChangeColor);
        td.recycle();
        //根据颜色获取画笔
        mOriginalPaint = getPaintByColor(mOriginalColor);
        mChangePaint=getPaintByColor(mChangeColor);
    }
    private Paint getPaintByColor(int color){
        Paint paint=new Paint();
        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setDither(true);//防抖动
        paint.setTextSize(getTextSize());
        return paint;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float middle = mCurrentProgress * getWidth();
        Paint.FontMetricsInt fontMetricsInt = mOriginalPaint.getFontMetricsInt();
        baseLine = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom + getHeight()/2
                +getPaddingTop()/2-getPaddingBottom()/2;
        //前半部分
        if(orientation==Orientation.LEFT_TO_RIGHT){

            clipRect(canvas,0,middle,mChangePaint);
            clipRect(canvas,middle,getWidth(),mOriginalPaint);

        }else if(orientation==Orientation.INNER_TO_OUTER){

            clipRect(canvas,getWidth()-middle,middle,mChangePaint);
            clipRect(canvas,middle,getWidth()-middle,mOriginalPaint);

        }else if(orientation==Orientation.RIGHT_TO_LEFT){

            clipRect(canvas,getWidth()-middle,getWidth(),mChangePaint);
            clipRect(canvas,0,getWidth()-middle,mOriginalPaint);

        }else if(orientation==Orientation.RIGHT_TO_LEFT_FROM_NONE){

            clipRect(canvas,getWidth()-middle,getWidth(),mChangePaint);
            clipRect(canvas,getWidth(),getWidth()-middle,mOriginalPaint);

        }else if(orientation==Orientation.LEFT_TO_RIGHT_FORME_NONE){
            clipRect(canvas,0,middle,mChangePaint);
            clipRect(canvas,middle,0,mOriginalPaint);
        }
    }
    private void clipRect(Canvas canvas,float start,float region,Paint paint){
        //改变的颜色
        canvas.save();
        canvas.clipRect(start+getPaddingLeft(),0,region,getHeight());
        canvas.drawText(getText().toString(),getPaddingLeft(),baseLine,paint);
        canvas.restore();
    }

    public void setOrientation(Orientation orientation){
        this.orientation=orientation;
    }

    public void setCurrentProgress(float currentProgress){
        mCurrentProgress=currentProgress;
        invalidate();
    }

    public void start(final Orientation orientation,long duration){
        ValueAnimator animator=ValueAnimator.ofFloat(0,1);
        animator.setDuration(duration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value= (float) animation.getAnimatedValue();
                setOrientation(orientation);
                setCurrentProgress(value);
            }
        });
        animator.start();
    }

}
