package me.firdaus1453.crudmakanan.ui.main;

import android.content.Context;

/**
 * Created by firdaus1453 on 2/28/2019.
 */
public interface MainContract {
    interface View{

    }

    interface Presenter{
        void logoutSession(Context context);
    }
}
