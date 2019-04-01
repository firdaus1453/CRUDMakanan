package me.firdaus1453.crudmakanan.ui.makanan;

import android.content.Context;

import java.util.List;

import me.firdaus1453.crudmakanan.model.makanan.MakananData;

/**
 * Created by firdaus1453 on 3/26/2019.
 */
public interface MakananContract {

    interface View{
        void showProgress();
        void hideProgress();
        void showFoodNewsList(List<MakananData> foodNewsList);
        void showFoodPopulerList(List<MakananData> foodPopulerList);
        void showFoodKategoryList(List<MakananData> foodKategoryList);
        void showFailureMessage(String msg);
    }

    interface Presenter{
        void getListFoodNews();
        void getListFoodPopular();
        void getListFoodKategory();
    }
}
