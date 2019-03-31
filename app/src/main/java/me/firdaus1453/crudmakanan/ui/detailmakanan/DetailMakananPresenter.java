package me.firdaus1453.crudmakanan.ui.detailmakanan;

import me.firdaus1453.crudmakanan.data.remote.ApiClient;
import me.firdaus1453.crudmakanan.data.remote.ApiInterface;
import me.firdaus1453.crudmakanan.model.detailmakanan.DetailMakananResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by firdaus1453 on 3/28/2019.
 */
public class DetailMakananPresenter implements DetailMakananContract.Presenter {

    private final DetailMakananContract.View view;
    private ApiInterface mApiInterface = ApiClient.getClient().create(ApiInterface.class);

    public DetailMakananPresenter(DetailMakananContract.View view) {
        this.view = view;
    }

    @Override
    public void getDetailMakanan(String idMakanan) {
        view.showProgress();

        if (idMakanan.isEmpty()) {
            view.showFailureMessage("ID Makanan tidak ada");
            return;
        }

        Call<DetailMakananResponse> call = mApiInterface.getDetailMakanan(Integer.valueOf(idMakanan));
        call.enqueue(new Callback<DetailMakananResponse>() {
            @Override
            public void onResponse(Call<DetailMakananResponse> call, Response<DetailMakananResponse> response) {
                view.hideProgress();
                if (response.body() != null) {
                    if (response.body().getResult() == 1) {
                        view.showDetailMakanan(response.body().getMakananData());
                    } else {
                        view.showFailureMessage(response.body().getMessage());
                    }
                } else {
                    view.showFailureMessage("Data kosong");
                }
            }

            @Override
            public void onFailure(Call<DetailMakananResponse> call, Throwable t) {
                view.showFailureMessage(t.getMessage());
            }
        });


    }
}
