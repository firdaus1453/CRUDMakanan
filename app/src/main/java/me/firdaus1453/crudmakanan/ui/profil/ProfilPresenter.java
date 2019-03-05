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
 * Created by firdaus1453 on 3/4/2019.
 */
public class ProfilPresenter implements ProfilContract.Presenter {

    private final ProfilContract.View view;
    private SharedPreferences pref;
    private ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

    public ProfilPresenter(ProfilContract.View view) {
        this.view = view;
    }

    @Override
    public void updateDataUser(final Context context, final LoginData loginData) {
        view.showProgress();
        Log.i("cek telp", loginData.getNoTelp());
        Call<LoginResponse> call = apiInterface.updateUser(Integer.valueOf(loginData.getId_user()),
                loginData.getNamaUser(),
                loginData.getAlamat(),
                loginData.getJenkel(),
                loginData.getNoTelp());
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                view.hideProgress();
                if (response.isSuccessful() && response.body() != null){
                    if (response.body().getResult() == 1){
                        pref = context.getSharedPreferences(Constants.pref_name,0);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString(Constants.KEY_USER_NAMA,loginData.getNamaUser());
                        editor.putString(Constants.KEY_USER_ALAMAT, loginData.getAlamat());
                        editor.putString(Constants.KEY_USER_NOTELP, loginData.getNoTelp());
                        editor.putString(Constants.KEY_USER_JENKEL, loginData.getJenkel());
                        editor.apply();
                        view.showSuccesUpdateUser(response.body().getMessage());
                    }else {
                        view.showSuccesUpdateUser(response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable throwable) {
                view.hideProgress();
                view.showSuccesUpdateUser(throwable.getMessage());
            }
        });
    }

    @Override
    public void getDataUser(Context context) {
        pref = context.getSharedPreferences(Constants.pref_name,0);
        LoginData loginData = new LoginData();
        loginData.setId_user(pref.getString(Constants.KEY_USER_ID, ""));
        loginData.setNamaUser(pref.getString(Constants.KEY_USER_NAMA, ""));
        loginData.setAlamat(pref.getString(Constants.KEY_USER_ALAMAT, ""));
        loginData.setNoTelp(pref.getString(Constants.KEY_USER_NOTELP, ""));
        loginData.setJenkel(pref.getString(Constants.KEY_USER_JENKEL, ""));

        view.showDataUser(loginData);
    }

    @Override
    public void logoutSession(Context context) {
        // Membuat object SessionManager untuk dapat digunakan
        SessionManager mSessionManager = new SessionManager(context);
        mSessionManager.logout();
    }
}
