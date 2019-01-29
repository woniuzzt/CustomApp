package cardiochina.com.customapp.seekbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import cardiochina.com.customapp.R;

/**
 * Created by yx on 2019/1/25.
 * Description
 */
public class DouYuView extends android.support.v7.widget.AppCompatImageView {

    private Paint mPaint;//定义画笔
    private Bitmap mBitmap;//验证的图像
    private int mUintHeight;//滑块的高度
    private int mUintWidth;//滑块的宽度
    private int mUintWidthScale;//验证滑块宽占用整体图片大小的比例，默认1/5
    private int mUintHeightScale;//验证滑块高占用整体图片大小的比例，默认1/4
    private int mUintRandomX;//随机生成滑块的x坐标
    private int mUintRandomY;//随机生成滑块的y坐标
    private int mUintMoveDistance = 0;//滑块移动的距离
    private Bitmap mUintBp;//滑块图像
    private Bitmap mShowBp;//验证位置图像
    private Bitmap mShadeBp;//背景阴影图像
    private boolean needRotate;//是否需要旋转
    private int rotate;//旋转的角度
    private int DEFAULT_DEVIATE;//判断是否完成的偏移量，默认为10
    private boolean isReSet = true;//判断是否重新绘制图像

    public DouYuView(Context context) {
        this(context, null);
    }

    public DouYuView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DouYuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.DouYuView);
        mUintWidth = (int) array.getDimension(R.styleable.DouYuView_unitWidth, 0);
        mUintHeight = (int) array.getDimension(R.styleable.DouYuView_unitHeight, 0);
        mUintWidthScale = array.getInteger(R.styleable.DouYuView_unitWidthScale, 0);
        mUintHeightScale = array.getInteger(R.styleable.DouYuView_unitHeightScale, 0);
        Drawable showBp = array.getDrawable(R.styleable.DouYuView_unitShowSrc);
        mShowBp = drawableToBitmap(showBp);
        Drawable shadeBp = array.getDrawable(R.styleable.DouYuView_unitShadeSrc);
        mShadeBp = drawableToBitmap(shadeBp);
        needRotate = array.getBoolean(R.styleable.DouYuView_needRotate, true);
        DEFAULT_DEVIATE = array.getInteger(R.styleable.DouYuView_deviate, 10);
        array.recycle();
        //初始化
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        if (needRotate) {
            rotate = (int) (Math.random() * 3) * 90;
        } else {
            rotate = 0;
        }
    }

    //缩放图片
    public Bitmap getBaseBitmap() {
        Bitmap b = drawableToBitmap(getDrawable());
        float scaleX = 1.0f;
        float scaleY = 1.0f;
        //如果图片的宽或高与view的宽高不匹配，计算出需要缩放的比例；缩放后的图片宽高一定要大于我们view 的宽高，所以我们取最大值
        scaleX = getWidth() * 1.0f / b.getWidth();  //宽度缩放比例
        scaleY = getHeight() * 1.0f / b.getHeight();    //高度缩放比例
        Matrix matrix = new Matrix();
        matrix.setScale(scaleX, scaleY);
        Bitmap bitmap = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, true);
        return bitmap;
    }

    /**
     * drawable转bitmap
     *
     * @param drawable
     * @return
     */
    private Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable == null) return null;
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bd = (BitmapDrawable) drawable;
            return bd.getBitmap();
        }
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 缩放图片
     *
     * @param bp
     * @param x
     * @param y
     * @return
     */
    public static Bitmap handleBitmap(Bitmap bp, float x, float y) {
        int w = bp.getWidth();
        int h = bp.getHeight();
        float sx = (float) x / w;
        float sy = (float) y / h;
        Matrix matrix = new Matrix();
        matrix.setScale(sx, sy);
        Bitmap resizeBmp = Bitmap.createBitmap(bp, 0, 0, w, h, matrix, true);
        return resizeBmp;
    }

    /**
     * 创建遮挡的图片（阴影部分）
     *
     * @return
     */
    private Bitmap drawTargetBitmap() {
        //绘制图片
        Bitmap showB;
        if (mShowBp != null)
            showB = handleBitmap(mShowBp, mUintWidth, mUintHeight);
        else
            showB = handleBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.icon_sc), mUintWidth, mUintHeight);
        //如果需要旋转图片，进行旋转，旋转后为了保持和滑块大小一致，需要重新缩放比例
        if (needRotate)
            showB = handleBitmap(rotateBitmap(rotate, showB), mUintWidth, mUintHeight);
        return showB;
    }

    private Bitmap drawResultBitmap(Bitmap bp) {
        //绘制图片
        Bitmap shadeB;
        if (mShadeBp != null)
            shadeB = handleBitmap(mShadeBp, mUintWidth, mUintHeight);
        else
            shadeB = handleBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.icon_sc_s), mUintWidth, mUintHeight);
        //如果需要旋转图片，进行旋转，旋转后为了和画布大小保持一致，避免出现图像显示不全，需要重新绘制
        if (needRotate)
            shadeB = handleBitmap(rotateBitmap(rotate, shadeB), mUintWidth, mUintHeight);
        Bitmap resultBmp = Bitmap.createBitmap(mUintWidth, mUintHeight, Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        paint.setAntiAlias(true);//抗锯齿
        Canvas canvas = new Canvas(resultBmp);
        canvas.drawBitmap(shadeB, new Rect(0, 0, mUintWidth, mUintHeight), new Rect(0, 0, mUintWidth, mUintHeight), paint);
        //选择交集去上层图片
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
        canvas.drawBitmap(bp, new Rect(0, 0, mUintWidth, mUintHeight), new Rect(0, 0, mUintWidth, mUintHeight), paint);
        return resultBmp;
    }

    /**
     * 随机生成滑块的位置（X,Y的坐标）
     */
    private void initUintXY() {
        mUintRandomX = (int) (Math.random() * (mBitmap.getWidth() - mUintWidth));
        mUintRandomY = (int) (Math.random() * (mBitmap.getHeight() - mUintHeight));
        //防止生成的位置距离太近
        if (mUintRandomX <= mBitmap.getWidth() / 2)
            mUintRandomX = mUintRandomX + mBitmap.getWidth() / 4;
        //防止X坐标截图时导致异常
        if ((mUintRandomX + mUintWidth) > getWidth()) {
            initUintXY();
            return;
        }
    }

    /**
     * 旋转图片
     *
     * @param degree
     * @param bitmap
     * @return
     */
    public Bitmap rotateBitmap(int degree, Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap bm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
        return bm;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isReSet) {
            mBitmap = getBaseBitmap();
            if (mUintWidth == 0) {
                mUintWidth = mBitmap.getWidth() / mUintWidthScale;
            }
            if (mUintHeight == 0) {
                mUintHeight = mBitmap.getHeight() / mUintHeightScale;
            }
            initUintXY();
            mUintBp = Bitmap.createBitmap(mBitmap, mUintRandomX, mUintRandomY, mUintWidth, mUintHeight);
        }
        isReSet = false;
        canvas.drawBitmap(drawTargetBitmap(), mUintRandomX, mUintRandomY, mPaint);
        canvas.drawBitmap(drawResultBitmap(mUintBp), mUintMoveDistance, mUintRandomY, mPaint);
    }

    /**
     * 重置
     */
    public void reSet() {
        isReSet = true;
        mUintMoveDistance = 0;
        if (needRotate)
            rotate = (int) (Math.random() * 3) * 90;
        else
            rotate = 0;
        invalidate();
    }

    /**
     * 获取每次的偏移量
     *
     * @param max
     * @return
     */
    public float getAverageDistance(int max) {
        return (float) (mBitmap.getWidth() - mUintWidth) / max;
    }

    /**
     * 滑块移动的距离
     *
     * @param distance
     */
    public void setUintMoveDistance(float distance) {
        mUintMoveDistance = (int) distance;
        //防止滑块滑出图片
        if (mUintMoveDistance > mBitmap.getWidth() - mUintWidth) {
            mUintMoveDistance = mBitmap.getWidth() - mUintWidth;
        }
        invalidate();
    }

    /**
     * 拼图成功的回调
     */
    interface onPuzzleListener {
        public void success();

        public void fail();
    }

    private onPuzzleListener puzzleListener;

    public void setPuzzleListener(onPuzzleListener puzzleListener) {
        this.puzzleListener = puzzleListener;
    }

    /**
     * 验证是否拼接成功
     */
    public void testPuzzle() {
        if (Math.abs(mUintMoveDistance - mUintRandomX) <= DEFAULT_DEVIATE) {
            if (puzzleListener != null) {
                puzzleListener.success();
            }
        } else {
            if (puzzleListener != null) {
                puzzleListener.fail();
            }
        }
    }

    /**
     * 获取滑块图像
     */
    public Bitmap getUintBp() {
        return mUintBp;
    }
}
