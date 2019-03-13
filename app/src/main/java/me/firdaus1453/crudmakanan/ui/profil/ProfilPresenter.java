package me.firdaus1453.crudmakanan.ui.profil;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import me.firdaus1453.crudmakanan.data.remote.ApiClient;
import me.firdaus1453.crudmakanan.data.remote.ApiInterface;
import me.firdaus1453.crudmakanan.model.login.LoginData;
import me.firdaus1453.crudmakanan.model.login.LoginResponse;
import me.firdaus1453.crudmakanan.utils.Constants;
import me.firdaus1453.crudmakanan.utils.SessionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by firdaus1453 on 3/5/2019.
 */
public class ProfilPresenter implements ProfilContract.Presenter{

    private final ProfilContract.View view;
    private SharedPreferences pref;
    private ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

    public ProfilPresenter(ProfilContract.View view) {
        this.view = view;
    }

    @Override
    public void updateDataUser(final Context context, final LoginData loginData) {

        // Show progress
        view.showProgress();

        // Membuat object call dan memanggil method updateUser serta mengirimkan datanya
        Call<LoginResponse> call = apiInterface.updateUser(
                Integer.valueOf(loginData.getId_user()),
                loginData.getNamaUser(),
                loginData.getAlamat(),
                loginData.getJenkel(),
                loginData.getNoTelp()
                );

        // Mengeksekusi call
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                view.hideProgress();
                // Mencek respon dan isi body
                if (response.isSuccessful() && response.body() != null){
                    // Mencek result apakah 1
                    if (response.body().getResult() == 1){
                        // Setelah berhasil update ke server online, lalu update ke SharedPref
                        // Membuat object sharedpref yang sudah ada di sessionmanager
                        pref = context.getSharedPreferences(Constants.pref_name,0);
                        // Mengubah mode sharedpref menjadi edit
                        SharedPreferences.Editor editor = pref.edit();
                        // Memasukkan data ke dalam sharedpref
                        editor.putString(Constants.KEY_USER_NAMA,loginData.getNamaUser());
                        editor.putString(Constants.KEY_USER_ALAMAT,loginData.getAlamat());
                        editor.putString(Constants.KEY_USER_NOTELP,loginData.getNoTelp());
                        editor.putString(Constants.KEY_USER_JENKEL,loginData.getJenkel());
                        // Apply perubahan
                        editor.apply();
                        view.showSuccessUpdateUser(response.body().getMessage());
                    }else{
                        view.showSuccessUpdateUser(response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable throwable) {
                view.hideProgress();
                view.showSuccessUpdateUser(throwable.getMessage());
            }
        });
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
