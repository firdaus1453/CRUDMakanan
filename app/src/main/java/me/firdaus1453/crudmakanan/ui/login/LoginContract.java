package me.firdaus1453.crudmakanan.ui.login;

import android.content.Context;

import me.firdaus1453.crudmakanan.model.login.LoginData;

/**
 * Created by firdaus1453 on 2/28/2019.
 */
public interface LoginContract {
    interface View{
        void showProgress();
        void hideProgress();
        void loginSuccess(String message, LoginData loginData);
        void loginFailure(String message);
        void usernameError(String message);
        void passwordError(String message);
        void isLogin();
    }

    interface Presenter{
        void doLogin(String username, String password);
        void saveDataUser(Context context, LoginData loginData);
        void checkLogin(Context context);
    }
}
