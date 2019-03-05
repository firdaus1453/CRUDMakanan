package me.firdaus1453.crudmakanan.ui.profil;

import android.content.Context;
import android.content.SharedPreferences;

import me.firdaus1453.crudmakanan.model.login.LoginData;
import me.firdaus1453.crudmakanan.utils.Constants;
import me.firdaus1453.crudmakanan.utils.SessionManager;

/**
 * Created by firdaus1453 on 3/5/2019.
 */
public class ProfilPresenter implements ProfilContract.Presenter{

    private final ProfilContract.View view;
    private SharedPreferences pref;

    public ProfilPresenter(ProfilContract.View view) {
        this.view = view;
    }

    @Override
    public void getDataUser(Context context) {
        // Pengambilan data dari SharedPreference
        pref = context.getSharedPreferences(Constants.pref_name,0);

        // Membuat object model logindata untuk penampung
        LoginData loginData = new LoginData();

        // Memasukkan data sharedpreference ke dalam model logindata
        loginData.setId_user(pref.getString(Constants.KEY_USER_ID,""));
        loginData.setNamaUser(pref.getString(Constants.KEY_USER_NAMA,""));
        loginData.setAlamat(pref.getString(Constants.KEY_USER_ALAMAT,""));
        loginData.setNoTelp(pref.getString(Constants.KEY_USER_NOTELP,""));
        loginData.setJenkel(pref.getString(Constants.KEY_USER_JENKEL,""));

        // Mengirim data model login data ke view
        view.showDataUser(loginData);
    }

    @Override
    public void logoutSession(Context context) {
        // Membuat class session manager untuk memanggil method logout
        SessionManager mSessionManager = new SessionManager(context);
        mSessionManager.logout();
    }
}
