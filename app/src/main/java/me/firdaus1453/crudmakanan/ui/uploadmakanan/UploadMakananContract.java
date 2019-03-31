package me.firdaus1453.crudmakanan.ui.uploadmakanan;

import android.content.Context;
import android.net.Uri;

import java.util.List;

import me.firdaus1453.crudmakanan.model.makanan.MakananData;

/**
 * Created by firdaus1453 on 3/27/2019.
 */
public interface UploadMakananContract {
    interface View{
        void showProgress();
        void hideProgress();
        void showMessage(String msg);
        void successUpload();
        void showSpinnerCategory(List<MakananData> categoryDataList);
    }

    interface Presenter{
        void getCategory();
        void uploadImage(Context context, Uri filePath, String namaMakanan, String descMakanan, String idKategori);
    }
}
