package me.firdaus1453.crudmakanan.ui.makananbycategory;

import java.util.List;

import me.firdaus1453.crudmakanan.model.makanan.MakananData;

/**
 * Created by firdaus1453 on 3/28/2019.
 */
public interface MakananByCategoryContract {
    interface View{
        void showProgress();
        void hideProgress();
        void showFoodByCategory(List<MakananData> foodNewsList);
        void showFailureMessage(String msg);
    }

    interface Presenter{
        void getListFoodByCategory(String idCategory);
    }
}
