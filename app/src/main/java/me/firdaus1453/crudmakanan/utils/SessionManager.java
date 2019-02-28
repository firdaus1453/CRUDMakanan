package me.firdaus1453.crudmakanan.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import me.firdaus1453.crudmakanan.model.login.LoginData;
import me.firdaus1453.crudmakanan.ui.login.LoginActivity;

/**
 * Created by firdaus1453 on 2/28/2019.
 */
public class SessionManager {
    // Membuat varibale global untuk shared preference
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private final Context context;

    public SessionManager(Context context) {
        this.context = context;
        // Membuat object SharedPreference untuk siap digunakan
        pref = context.getSharedPreferences(Constants.pref_name, 0);
        // Membuat SharedPreference dengan mode edit
        editor = pref.edit();
    }

    // Function untuk membuat session login
    public void createSession(LoginData loginData){
        // memasukkan data user yang sudah login ke dalam SharedPreference
        editor.putBoolean(Constants.KEY_IS_LOGIN, true);
        editor.putString(Constants.KEY_USER_ID, loginData.getId_user());
        editor.putString(Constants.KEY_USER_NAMA, loginData.getNamaUser());
        editor.putString(Constants.KEY_USER_ALAMAT, loginData.getAlamat());
        editor.putString(Constants.KEY_USER_JENKEL, loginData.getJenkel());
        editor.putString(Constants.KEY_USER_NOTELP, loginData.getNoTelp());
        editor.putString(Constants.KEY_USER_USERNAME, loginData.getUsername());
        editor.putString(Constants.KEY_USER_LEVEL, loginData.getLevel());
        // Mengeksekusi penyimpanan
        editor.commit();
    }

    // Function untuk mencek apakah user sudah pernah login
    public boolean isLogin(){
        // Mengembalikan nilai boolean dengan mengambil data dari pref KEY_IS_LOGIN
        return pref.getBoolean(Constants.KEY_IS_LOGIN, false);
    }

    // Function untuk melakukan logout atau menghapus isi di dalam shared preference
    public void logout(){
        // Memanggil method clear untuk menghapus data sharedpreference
        editor.clear();
        // Mengeksekusi perintah clear
        editor.commit();
        // Membuat intent untuk berpindah halaman
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


}
