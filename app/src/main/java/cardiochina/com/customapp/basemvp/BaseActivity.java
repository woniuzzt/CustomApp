package cardiochina.com.customapp.basemvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by yx on 2019/1/25.
 * Description
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected abstract void initBeforeCreate();

    protected abstract int getLayoutId();

    protected abstract void init(Bundle saveInstanceState);

    protected abstract void initData();

    protected abstract void initView();

    protected abstract void logic();

    protected void showToast(String toastStr) {
        Toast.makeText(this, toastStr, Toast.LENGTH_SHORT).show();
    }
}
