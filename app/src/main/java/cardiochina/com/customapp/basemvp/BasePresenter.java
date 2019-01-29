package cardiochina.com.customapp.basemvp;

import java.lang.ref.WeakReference;

/**
 * Created by yx on 2019/1/25.
 * Description
 */
public class BasePresenter<V extends IBaseView> {
    private WeakReference<V> mViewRef;
    private V mView;

    public void attachView(V view) {
        mViewRef = new WeakReference<V>(view);
        mView = mViewRef.get();
    }

    public void detachView() {
        mViewRef.clear();
        mView = null;
    }
}
