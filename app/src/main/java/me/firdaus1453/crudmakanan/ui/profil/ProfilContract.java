package me.firdaus1453.crudmakanan.ui.profil;

import android.content.Context;

import me.firdaus1453.crudmakanan.model.login.LoginData;

/**
 * Created by firdaus1453 on 3/5/2019.
 */
public interface ProfilContract {
    interface View{
        void showProgress();
        void hideProgress();
        void showSuccessUpdateUser(String message);
        void showDataUser(LoginData loginData);
    }

    interface Presenter{
        void updateDataUser(Context context, LoginData loginData);
        void getDataUser(Context context);
        void logoutSession(Context context);
    }
}
