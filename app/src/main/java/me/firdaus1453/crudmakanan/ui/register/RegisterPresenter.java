package me.firdaus1453.crudmakanan.ui.register;


import android.os.Parcelable;

import me.firdaus1453.crudmakanan.data.remote.ApiClient;
import me.firdaus1453.crudmakanan.data.remote.ApiInterface;
import me.firdaus1453.crudmakanan.model.login.LoginData;
import me.firdaus1453.crudmakanan.model.login.LoginResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by firdaus1453 on 2/28/2019.
 */
public class RegisterPresenter implements RegisterContract.Presenter {

    private final RegisterContract.View view;
    private ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

    public RegisterPresenter(RegisterContract.View view) {
        this.view = view;
    }

    @Override
    public void doRegisterUser(LoginData loginData) {
        if (loginData != null) {
            if (!loginData.getUsername().isEmpty() &&
                    !loginData.getAlamat().isEmpty() &&
                    !loginData.getJenkel().isEmpty() &&
                    !loginData.getLevel().isEmpty() &&
                    !loginData.getNoTelp().isEmpty() &&
                    !loginData.getPassword().isEmpty() &&
                    !loginData.getNamaUser().isEmpty()
                    ) {

                view.showProgress();
                Call<LoginResponse> call = apiInterface.registerUser(
                        loginData.getUsername(),
                        loginData.getPassword(),
                        loginData.getNamaUser(),
                        loginData.getAlamat(),
                        loginData.getJenkel(),
                        loginData.getNoTelp(),
                        loginData.getLevel()
                );
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        view.hideProgress();
                        if (response.body()!= null){
                            if (response.body().getResult() == 1){
                                view.showRegisterSucces(response.body().getMessage());
                            }else {
                                view.showError(response.body().getMessage());
                            }
                        }else {
                            view.showError("Data kosong");
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable throwable) {
                        view.hideProgress();
                        view.showError(throwable.getMessage());
                    }
                });


            }else {
                view.showError("Tidak boleh ada yang kosong");
            }
        }


    }
}
