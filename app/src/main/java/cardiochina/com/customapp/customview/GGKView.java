package cardiochina.com.customapp.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import cardiochina.com.customapp.R;
import org.jetbrains.annotations.Nullable;

/**
 * Created by yx on 2019/1/21.
 * Description
 */
public class GGKView extends View {
    private Paint mTransparentPaint;//透明画笔
    private Paint mTextPaint;//底层文字画笔
    private Path mFingerPath;//记录手指划过的路劲
    private Bitmap mBgBitmap;//第一张图的bitmap
    private Bitmap mFbBitmap;//第二张图的bitmap
    private Canvas mCanvas;//新建画布，操作mFbBitmap
    private boolean isOver = false;

    private String mText;//底层文字
    private float mTextSize;//字体大小
    private int mTextColor;//字体颜色
    private final static int TEXT_SIZE = 18;


    public GGKView(Context context) {
        super(context);
    }

    public GGKView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }


    public GGKView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mTransparentPaint = new Paint();
        mTransparentPaint.setAntiAlias(true);
        mTransparentPaint.setAlpha(0);//设置成透明
        mTransparentPaint.setStyle(Paint.Style.STROKE);
        mTransparentPaint.setStrokeJoin(Paint.Join.ROUND);//更加圆滑
        mTransparentPaint.setStrokeCap(Paint.Cap.ROUND);
        mTransparentPaint.setStrokeWidth(50);
        mTransparentPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        mFingerPath = new Path();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.GGKView);
        mBgBitmap = ((BitmapDrawable) typedArray.getDrawable(R.styleable.GGKView_bg)).getBitmap();
        mFbBitmap = Bitmap.createBitmap(mBgBitmap.getWidth(), mBgBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        mText = typedArray.getString(R.styleable.GGKView_text);
        mTextSize = typedArray.getDimension(R.styleable.GGKView_textSize, TEXT_SIZE);
        mTextColor = typedArray.getColor(R.styleable.GGKView_textColor, Color.BLACK);

        mTextPaint = new Paint();
//        mTextPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));//给画笔上色不能设置过度模式，不然画笔上色失效
        mTextPaint.setAntiAlias(true);
        mTextPaint.setDither(true);
        mTextPaint.setStyle(Paint.Style.STROKE);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mTextColor);

        mCanvas = new Canvas(mFbBitmap);
        mCanvas.drawColor(Color.GRAY);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isOver) {
            canvas.drawBitmap(mBgBitmap, 0, 0, null);
            canvas.drawText(mText, (getWidth() - mTextSize * mText.length()) / 2, (mBgBitmap.getHeight() + mTextSize) / 2, mTextPaint);
        } else {
            canvas.drawBitmap(mBgBitmap, 0, 0, null);
            canvas.drawText(mText, (getWidth() - mTextSize * mText.length()) / 2, (mBgBitmap.getHeight() + mTextSize) / 2, mTextPaint);
            canvas.drawBitmap(mFbBitmap, 0, 0, null);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mFingerPath.reset();
                mFingerPath.moveTo(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                if (event.getX() > 3 && event.getY() > 3)
                    mFingerPath.lineTo(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_UP:
                initHideFB();
                break;
        }
        mCanvas.drawPath(mFingerPath, mTransparentPaint);
        invalidate();
        return true;
    }

    private void initHideFB() {
        int h = mBgBitmap.getHeight();
        int w = mBgBitmap.getWidth();
        float wiperaea = 0;
        float totalraea = h * w;
        int pix[] = new int[h * w];
        //获取所有的像素信息
        mFbBitmap.getPixels(pix, 0, w, 0, 0, w, h);
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                int index = i + j * w;
                if (pix[index] == 0) {
                    wiperaea++;
                }
//                int pixel = mFbBitmap.getPixel(i, j);
//                if (pixel == 0) {
//                    wiperaea++;
//                }
            }
        }
        if (wiperaea > 0 && totalraea > 0) {
            int pencent = (int) (wiperaea * 100 / totalraea);
            if (pencent > 40) {
                isOver = true;
                postInvalidate();
            }
        }
    }

    public void setDefault() {
        isOver = false;
        mCanvas.drawColor(Color.GRAY);
        postInvalidate();
    }

}
