package me.firdaus1453.crudmakanan.ui.login;

import android.content.Context;
import android.util.Log;

import me.firdaus1453.crudmakanan.data.remote.ApiClient;
import me.firdaus1453.crudmakanan.data.remote.ApiInterface;
import me.firdaus1453.crudmakanan.model.login.LoginData;
import me.firdaus1453.crudmakanan.model.login.LoginResponse;
import me.firdaus1453.crudmakanan.utils.SessionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by firdaus1453 on 2/28/2019.
 */
public class LoginPresenter implements LoginContract.Presenter {

    private final LoginContract.View view;
    private ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
    private SessionManager mSessionManager;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
    }

    @Override
    public void doLogin(String username, String password) {
        // Mencek username dan password
        if (username.isEmpty()) {
            view.usernameError("Username is empty");
            return;
        }

        if (password.isEmpty()){
            view.passwordError("Password is empty");
            return;
        }

        view.showProgress();

        Call<LoginResponse> call = apiInterface.loginUser(username, password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                view.hideProgress();
                if (response.body() != null){
                    if (response.body().getResult() == 1){
                        if (response.body().getData() != null){
                            LoginData loginData = response.body().getData();
                            String message = response.body().getMessage();
                            view.loginSuccess(message, loginData);
                            Log.i("login onResponse", response.body().getMessage());
                        }else {
                            view.loginFailure("Data tidak ada");
                            Log.i("login onResponse", response.body().getMessage());
                        }
                    }else {
                        view.loginFailure(response.body().getMessage());
                        Log.i("login onResponse", response.body().getMessage());
                    }
                }else {
                    view.loginFailure("Data tidak ada");
                    Log.i("login onResponse", "body null");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable throwable) {
                view.hideProgress();
                view.loginFailure(throwable.getMessage());
                Log.d("onFailure", "pesan : " +throwable.getMessage());

            }
        });
    }

    @Override
    public void saveDataUser(Context context, LoginData loginData) {
        // Membuat object SessionManager
        mSessionManager = new SessionManager(context);
        // Mensave data ke SharedPreference dengan menggunakan method dari class SessionManager
        mSessionManager.createSession(loginData);
    }

    @Override
    public void checkLogin(Context context) {
        // Membuat object SessionManager
        mSessionManager = new SessionManager(context);
        // Mengambil data KEY_IS_LOGIN lalu memasukkan ke dalam variable isLogin
        Boolean isLogin = mSessionManager.isLogin();
        // Mengecek apakah KEY_IS_LOGIN bernilai true
        if (isLogin){
            // Menyuruh view untuk melakukan perpindahan ke MainAcivity
            view.isLogin();
        }
    }
}
