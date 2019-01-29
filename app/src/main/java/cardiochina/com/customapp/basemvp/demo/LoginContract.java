package cardiochina.com.customapp.basemvp.demo;

import cardiochina.com.customapp.basemvp.BasePresenter;
import cardiochina.com.customapp.basemvp.IBaseView;

/**
 * Created by yx on 2019/1/25.
 * Description
 */
public interface LoginContract {
    abstract class LoginPresenter extends BasePresenter<LoginView> {
        public abstract void login(String name, String password);
    }

    interface LoginView extends IBaseView {
        void showTip(String str);
    }
}
