package me.firdaus1453.crudmakanan.ui.register;

import android.widget.EditText;

import me.firdaus1453.crudmakanan.model.login.LoginData;

/**
 * Created by firdaus1453 on 2/28/2019.
 */
public interface RegisterContract {
    interface View{
        void showProgress();
        void hideProgress();
        void showError(String message);
        void showRegisterSucces(String message);
    }

    interface Presenter{
        void doRegisterUser(LoginData loginData);
    }
}
