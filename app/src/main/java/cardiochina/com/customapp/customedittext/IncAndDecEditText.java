package cardiochina.com.customapp.customedittext;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.EditText;
import cardiochina.com.customapp.R;

/**
 * Created by yx on 2019/1/28.
 * Description
 */
public class IncAndDecEditText extends android.support.v7.widget.AppCompatEditText {
    private int mDefaultCount = 0;//默认数值
    private int mChangeCount = 1;//改变值
    private boolean isLessDefault = false;//是否可以小于默认数值

    private Paint mTextPaint;//text画笔
    private Paint mTextOutLinePaint;//text外框画笔
    private int mMaginLeft;
    private int mMaginRight;
    private Drawable leftBitmap;
    private Drawable rightBitmap;

    private int textColor;
    private int textOutLineColor;
    private float textSize;

    public static final float MARGIN = 20;
    public static final int TEXT_SIZE = 18;

    public IncAndDecEditText(Context context) {
        super(context);
    }

    public IncAndDecEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IncAndDecEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
//        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.IncAndDecEditText);
//        mDefaultCount = array.getInt(R.styleable.IncAndDecEditText_defaultCount, 0);
//        mChangeCount = array.getInt(R.styleable.IncAndDecEditText_changeCount, 1);
//        isLessDefault = array.getBoolean(R.styleable.IncAndDecEditText_lessDefault, false);
//        mMaginLeft = (int) array.getDimension(R.styleable.IncAndDecEditText_textMarginLeft, MARGIN);
//        mMaginRight = (int) array.getDimension(R.styleable.IncAndDecEditText_textMarginRight, MARGIN);
//        leftBitmap = array.getDrawable(R.styleable.IncAndDecEditText_leftBitmap);
//        rightBitmap = array.getDrawable(R.styleable.IncAndDecEditText_rightBitmap);
//        if (leftBitmap == null)
//            leftBitmap = context.getResources().getDrawable(R.mipmap.icon_js);
//        if (rightBitmap == null)
//            rightBitmap = context.getResources().getDrawable(R.mipmap.icon_tj);
//        textColor = array.getColor(R.styleable.IncAndDecEditText_edittextColor, Color.BLACK);
//        textOutLineColor = array.getColor(R.styleable.IncAndDecEditText_textOutLineColor, Color.BLACK);
//        textSize = array.getDimension(R.styleable.IncAndDecEditText_edittextSize, TEXT_SIZE);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(textColor);
        mTextPaint.setTextSize(textSize);
        mTextPaint.setStyle(Paint.Style.STROKE);

        //设置左右图片的位置
        leftBitmap.setBounds(0, 0, leftBitmap.getIntrinsicWidth(), leftBitmap.getIntrinsicHeight());
        int rightBitmap_left = leftBitmap.getIntrinsicWidth() + mMaginLeft + mMaginRight;
        rightBitmap.setBounds(rightBitmap_left, 0, rightBitmap.getIntrinsicWidth(), rightBitmap.getIntrinsicHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }
}
