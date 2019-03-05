package me.firdaus1453.crudmakanan.ui.profil;

import android.content.Context;

import me.firdaus1453.crudmakanan.model.login.LoginData;

/**
 * Created by firdaus1453 on 3/4/2019.
 */
public interface ProfilContract {

    interface View{
        void showProgress();
        void hideProgress();
        void showDataUser(LoginData loginData);
        void showSuccesUpdateUser(String message);
    }

    interface Presenter{
        void updateDataUser(Context context, LoginData loginData);
        void getDataUser(Context context);
        void logoutSession(Context context);
    }
}
