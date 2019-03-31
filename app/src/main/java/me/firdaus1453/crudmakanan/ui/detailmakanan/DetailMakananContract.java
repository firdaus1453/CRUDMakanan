package me.firdaus1453.crudmakanan.ui.detailmakanan;

import me.firdaus1453.crudmakanan.model.makanan.MakananData;

/**
 * Created by firdaus1453 on 3/28/2019.
 */
public interface DetailMakananContract {
    interface View{
        void showProgress();
        void hideProgress();
        void showDetailMakanan(MakananData makananData);
        void showFailureMessage(String msg);
    }

    interface Presenter{
        void getDetailMakanan(String idMakanan);
    }
}
