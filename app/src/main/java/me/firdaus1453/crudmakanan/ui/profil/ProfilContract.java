package me.firdaus1453.crudmakanan.ui.profil;

import android.content.Context;

import me.firdaus1453.crudmakanan.model.login.LoginData;

/**
 * Created by firdaus1453 on 3/5/2019.
 */
public interface ProfilContract {
    interface View{
        void showDataUser(LoginData loginData);
    }

    interface Presenter{
        void getDataUser(Context context);
        void logoutSession(Context context);
    }
}
