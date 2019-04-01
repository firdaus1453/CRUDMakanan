package me.firdaus1453.crudmakanan.ui.makananbyuser;

import java.util.List;

import me.firdaus1453.crudmakanan.model.makanan.MakananData;

/**
 * Created by firdaus1453 on 4/1/2019.
 */
public interface MakananByUserContract {
    interface View{
        void showProgress();
        void hideProgress();
        void showFoodByUser(List<MakananData> foodByUserList);
        void showFailureMessage(String msg);
    }

    interface Presenter{
        void getListFoodByUser(String idUser);
    }
}
