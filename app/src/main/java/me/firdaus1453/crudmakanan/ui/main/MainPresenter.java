package me.firdaus1453.crudmakanan.ui.main;

import android.content.Context;

import me.firdaus1453.crudmakanan.utils.SessionManager;

/**
 * Created by firdaus1453 on 2/28/2019.
 */
public class MainPresenter implements MainContract.Presenter {
    @Override
    public void logoutSession(Context context) {
        // Membuat object SessionManager untuk dapat digunakan
        SessionManager mSessionManager = new SessionManager(context);
        mSessionManager.logout();
    }
}
