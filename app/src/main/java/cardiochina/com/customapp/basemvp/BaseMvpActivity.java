package cardiochina.com.customapp.basemvp;

import android.os.Bundle;

/**
 * Created by yx on 2019/1/25.
 * Description
 */
public abstract class BaseMvpActivity<T extends BasePresenter> extends BaseActivity implements IBaseView {
    protected T mPresenter;

    protected abstract T bindPresenter();

    @Override
    protected void init(Bundle saveInstanceState) {
        mPresenter = bindPresenter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
