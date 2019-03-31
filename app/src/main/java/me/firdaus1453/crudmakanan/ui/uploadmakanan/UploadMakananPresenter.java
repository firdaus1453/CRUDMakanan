package me.firdaus1453.crudmakanan.ui.uploadmakanan;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import me.firdaus1453.crudmakanan.data.remote.ApiClient;
import me.firdaus1453.crudmakanan.data.remote.ApiInterface;
import me.firdaus1453.crudmakanan.model.makanan.MakananResponse;
import me.firdaus1453.crudmakanan.model.uploadmakanan.UploadMakananResponse;
import me.firdaus1453.crudmakanan.utils.Constants;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by firdaus1453 on 3/27/2019.
 */
public class UploadMakananPresenter implements UploadMakananContract.Presenter {

    private final UploadMakananContract.View view;
    private ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

    public UploadMakananPresenter(UploadMakananContract.View view) {
        this.view = view;
    }

    @Override
    public void getCategory() {
        view.showProgress();
        Call<MakananResponse> call = apiInterface.getKategoriMakanan();
        call.enqueue(new Callback<MakananResponse>() {
            @Override
            public void onResponse(Call<MakananResponse> call, Response<MakananResponse> response) {
                view.hideProgress();
                if (response.body() != null){
                    if (response.body().getResult() == 1) {
                        view.showSpinnerCategory(response.body().getMakananDataList());
                    }else {
                        view.showMessage(response.body().getMessage());
                    }
                }else {
                    view.showMessage("Data kosong");
                }
            }

            @Override
            public void onFailure(Call<MakananResponse> call, Throwable t) {
                view.hideProgress();
                view.showMessage(t.getMessage());
            }
        });

    }

    @Override
    public void uploadImage(Context context,Uri filePath, String namaMakanan, String descMakanan, String idKategori) {
        view.showProgress();

        // Mencek foto, nameMakanan dan DescMakanan
        if (namaMakanan.isEmpty()) {
            view.showMessage("Nama makanan is empty");
            view.hideProgress();
            return;
        }

        if (descMakanan.isEmpty()){
            view.showMessage("Desc makanan is empty");
            view.hideProgress();
            return;
        }

        if (filePath == null){
            view.showMessage("Foto harus di isi");
            view.hideProgress();
            return;
        }

        // Mengambil alamat file image
        File myFile = new File(filePath.getPath());
        Uri selectedImage = getImageContentUri(context, myFile, filePath);
        String partImage = getPath(context,selectedImage);
        File imagefile = new File(partImage);

        // Mengambil id user dari sharedpref
        SharedPreferences pref = context.getSharedPreferences(Constants.pref_name,0);
        String idUser = pref.getString(Constants.KEY_USER_ID,"");

        // Mengambil tanggal sekarang dengan format default yyyy-MM-dd HH:mm:ss
        String sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());


        // Memasukkan data yang diperlukan ke dalam request body dengan tipe form-data untuk di kirim ke API
        RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-data"),imagefile);
        MultipartBody.Part mPartImage = MultipartBody.Part.createFormData("image", imagefile.getName(),reqBody);

        RequestBody mNamaMakanan = RequestBody.create(MediaType.parse("multipart/form-data"),namaMakanan);
        RequestBody mDescMakanan = RequestBody.create(MediaType.parse("multipart/form-data"),descMakanan);
        RequestBody datetime = RequestBody.create(MediaType.parse("multipart/form-data"),sdf);

        // Mengirim data ke API
        Call<UploadMakananResponse> call = apiInterface.uploadMakanan(Integer.valueOf(idUser),Integer.valueOf(idKategori), mNamaMakanan,mDescMakanan,datetime,mPartImage);
        call.enqueue(new Callback<UploadMakananResponse>() {
            @Override
            public void onResponse(Call<UploadMakananResponse> call, Response<UploadMakananResponse> response) {
               view.hideProgress();
                if(response.body().getResult() == 1)
                {
                    view.showMessage(response.body().getMessage());
                    view.successUpload();
                }else
                {
                    view.showMessage(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<UploadMakananResponse> call, Throwable t) {
                view.hideProgress();
                view.showMessage(t.getMessage());
                Log.d("Gagal", "ON FAILURE : " + t.getMessage());
            }
        });
    }

    public String getPath(Context context, Uri filepath) {
        Cursor cursor = context.getContentResolver().query(filepath, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ",
                new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }

    public Uri getImageContentUri(Context context, File imageFile, Uri filePath) {
        String fileAbsolutePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{fileAbsolutePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            // Apabila file gambar sudah pernah diapakai namun ada kondisi lain yang belum diketahui
            // Apabila file gambar sudah pernah dipakai pengambilan bukan di galery

            Log.i("Isi Selected if", "Masuk cursor ada");
            return filePath;

        } else {
            Log.i("Isi Selected else", "cursor tidak ada");
            if (imageFile.exists()) {
                // Apabila file gambar baru belum pernah di pakai
                Log.i("Isi Selected else", "imagefile exists");
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, fileAbsolutePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                // Apabila file gambar sudah pernah dipakai
                // Apabila file gambar sudah pernah dipakai di galery
                Log.i("Isi Selected else", "imagefile tidak exists");
                return filePath;
            }
        }
    }
}
