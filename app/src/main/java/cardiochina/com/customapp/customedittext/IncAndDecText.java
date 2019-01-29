package cardiochina.com.customapp.customedittext;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import cardiochina.com.customapp.R;
import org.jetbrains.annotations.Nullable;

/**
 * Created by yx on 2019/1/28.
 * Description
 */
public class IncAndDecText extends LinearLayout {
    private Context context;
    private LinearLayout mainLinearLayout;
    private LinearLayout centerLinearLayout;
    private LinearLayout leftLinearLayout;
    private LinearLayout rightLinearLayout;
    private OnNumChangeListener onNumChangeListener;

    private Button incButton;
    private Button decButton;
    private EditText editText;

    private int defalutCount;//edittext默认数值
    private int num = 0;//edittext中的数值
    private int changeCount;//增加或者递减的数值
    private boolean isLessDefalutCount;//能否小于默认值

    private int btnWidthAndHeight;//按钮宽度和高度
    private float editTextSize;
    private int btnMarginleftAndRight;

    private int editTextLayoutWidth;
    private int editTextLayoutHeight;
    private int editTextMinLayoutWidth;//最小宽度
    private int editTextMinLayoutHeight;//最小高度
    private int editTextMinHeight;//文本框区域的最小高度
    private int editTextHeight;//文本框区域的高度

    public IncAndDecText(Context context) {
        super(context);
    }

    public IncAndDecText(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IncAndDecText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.IncAndDecText);

        defalutCount = array.getInt(R.styleable.IncAndDecText_defalutCount, 0);
        changeCount = array.getInt(R.styleable.IncAndDecText_changeCount, 1);
        btnWidthAndHeight = (int) array.getDimension(R.styleable.IncAndDecText_btnWidthAndHeight, 40);
        editTextHeight = (int) array.getDimension(R.styleable.IncAndDecText_edittextHeight, 100);
        editTextLayoutWidth = getWidth();
        editTextLayoutHeight = getHeight();
        editTextSize = array.getDimension(R.styleable.IncAndDecText_editTextSize, 18);
        btnMarginleftAndRight = (int) array.getDimension(R.styleable.IncAndDecText_btnPaddingLeftAndRight, 40);
        isLessDefalutCount = array.getBoolean(R.styleable.IncAndDecText_isLessDefaultCount, false);


        initTextWithHeight();   //初始化edittext宽高参数
        initialise();          //实例化内部view
        setViewsLayoutParm();  //设置内部view的布局参数
        insertView();            //将子view放入linearlayout中
        setViewListener();
    }

    /**
     * 初始化edittext宽高参数
     */
    private void initTextWithHeight() {
//        editTextLayoutWidth = -1;
//        editTextLayoutHeight = -1;
        editTextMinHeight = -1;
        editTextMinLayoutHeight = -1;
//        editTextHeight = -1;
        editTextMinLayoutWidth = -1;
    }

    /**
     * 实例化内部view
     */
    private void initialise() {
        mainLinearLayout = new LinearLayout(context);
        leftLinearLayout = new LinearLayout(context);
        rightLinearLayout = new LinearLayout(context);
        centerLinearLayout = new LinearLayout(context);
        incButton = new Button(context);
        decButton = new Button(context);
        editText = new EditText(context);
        incButton.setTag("inc");
        decButton.setTag("dec");

        incButton.setBackground(context.getResources().getDrawable(R.mipmap.icon_tj));
        decButton.setBackground(context.getResources().getDrawable(R.mipmap.icon_js));

        //edittext输入类型为数字
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setText(String.valueOf(defalutCount));
        editText.setTextSize(editTextSize);
    }

    /**
     * 设置内部view的布局参数
     */
    private void setViewsLayoutParm() {
        LayoutParams layoutParams = new LayoutParams(btnWidthAndHeight, btnWidthAndHeight);
        incButton.setLayoutParams(layoutParams);
        decButton.setLayoutParams(layoutParams);
        incButton.setGravity(Gravity.CENTER);
        layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        editText.setLayoutParams(layoutParams);
        editText.setGravity(Gravity.CENTER);
        editText.setBackground(context.getResources().getDrawable(R.drawable.et_bg));
        setTextWidthHeight();

        layoutParams.gravity = Gravity.CENTER;
        centerLinearLayout.setLayoutParams(layoutParams);
        //让eidttext不自动获取焦点
        centerLinearLayout.setFocusable(true);
        centerLinearLayout.setFocusableInTouchMode(true);

        layoutParams.width = LayoutParams.WRAP_CONTENT;
        layoutParams.weight = 1.0f;
        //设置宽高比重为1.0
        leftLinearLayout.setLayoutParams(layoutParams);
        rightLinearLayout.setLayoutParams(layoutParams);
        leftLinearLayout.setGravity(Gravity.CENTER);
        rightLinearLayout.setGravity(Gravity.CENTER);
        leftLinearLayout.setPadding(btnMarginleftAndRight, 0, btnMarginleftAndRight, 0);
        rightLinearLayout.setPadding(btnMarginleftAndRight, 0, btnMarginleftAndRight, 0);

        layoutParams.width = LayoutParams.MATCH_PARENT;
        mainLinearLayout.setLayoutParams(layoutParams);
        mainLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
    }

    /**
     * 设置edittext视图和文本宽高
     */
    private void setTextWidthHeight() {
        float fpx;
        //设置视图最小宽度
        if (editTextMinLayoutWidth < 0) {
            fpx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80f, context.getResources().getDisplayMetrics());
            editTextMinLayoutWidth = Math.round(fpx);
        }
        editText.setMinimumWidth(editTextMinLayoutWidth);
//        //设置文本区域高度
//        if (editTextHeight > 0) {
//            if (editTextMinHeight > 0 && editTextMinHeight > editTextHeight) {
//                editTextHeight = editTextMinHeight;
//            }
//            editText.setHeight(editTextHeight);
//        }
//        //设置视图高度
//        if (editTextLayoutHeight > 0) {
//            if (editTextMinLayoutHeight > 0 && editTextMinLayoutHeight > editTextLayoutHeight) {
//                editTextLayoutHeight = editTextMinLayoutHeight;
//            }
//            LayoutParams layoutParams = (LayoutParams) editText.getLayoutParams();
//            layoutParams.height = editTextLayoutHeight;
//            editText.setLayoutParams(layoutParams);
//        }
//        //设置视图宽度
//        if (editTextLayoutWidth > 0) {
//            if (editTextMinLayoutWidth > 0 && editTextMinLayoutWidth > editTextLayoutWidth) {
//                editTextLayoutWidth = editTextMinLayoutWidth;
//            }
//            LayoutParams layoutParams = (LayoutParams) editText.getLayoutParams();
//            layoutParams.width = editTextLayoutWidth;
//            editText.setLayoutParams(layoutParams);
//        }
    }

    /**
     * 将子view放入linearlayout中
     */
    private void insertView() {
        mainLinearLayout.setGravity(Gravity.CENTER);
        mainLinearLayout.addView(leftLinearLayout, 0);
        mainLinearLayout.addView(centerLinearLayout, 1);
        mainLinearLayout.addView(rightLinearLayout, 2);

        leftLinearLayout.addView(decButton);
        centerLinearLayout.addView(editText);
        rightLinearLayout.addView(incButton);

        addView(mainLinearLayout);//将整块视图添加到当前view中
    }

    private void setViewListener() {
        incButton.setOnClickListener(new OnButtonClickListener());
        decButton.setOnClickListener(new OnButtonClickListener());
        editText.addTextChangedListener(new OnTextChangeListener());
    }

    public interface OnNumChangeListener {
        void onNumChange(View v, int num);
    }

    class OnButtonClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            if (TextUtils.isEmpty(editText.getText().toString())) return;
            num = Integer.parseInt(editText.getText().toString().trim());
            if (v.getTag().equals("inc")) {
                num += changeCount;
            } else if (v.getTag().equals("dec")) {
                num -= changeCount;
                if (!isLessDefalutCount && num < defalutCount) return;
            }
            editText.setText(num + "");
        }
    }

    class OnTextChangeListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!TextUtils.isEmpty(s)) {
                if (!isLessDefalutCount && Integer.parseInt(s.toString()) < defalutCount)
                    editText.setText(defalutCount + "");
            } else {
                editText.setHint("不限");
            }
        }
    }

    public String getText() {
        String value = "";
        if (!TextUtils.isEmpty(editText.getText().toString().trim())) {
            value = editText.getText().toString().trim();
        }
        return value;
    }
}
