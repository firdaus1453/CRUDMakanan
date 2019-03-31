package me.firdaus1453.crudmakanan.ui.uploadmakanan;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.firdaus1453.crudmakanan.R;
import me.firdaus1453.crudmakanan.model.makanan.MakananData;
import me.firdaus1453.crudmakanan.model.makanan.MakananResponse;
import me.firdaus1453.crudmakanan.utils.Constants;

public class UploadMakananActivity extends AppCompatActivity implements UploadMakananContract.View {

    @BindView(R.id.img_picture)
    ImageView imgPicture;
    @BindView(R.id.fab_choose_picture)
    FloatingActionButton fabChoosePicture;
    @BindView(R.id.layoutPicture)
    CardView layoutPicture;
    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.edt_desc)
    EditText edtDesc;
    @BindView(R.id.layoutUploadMakanan)
    CardView layoutUploadMakanan;
    @BindView(R.id.btn_upload)
    Button btnUpload;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.spin_category)
    Spinner spinCategory;

    private Uri filePath, selectedImage;
    private UploadMakananPresenter mUploadMakananPresenter = new UploadMakananPresenter(this);
    private String mIdCategory;
    private String part_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_makanan);
        ButterKnife.bind(this);
        PermissionGalery();
        mUploadMakananPresenter.getCategory();
    }

    @Override
    public void showProgress() {
        swipeRefresh.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void showMessage(String msg) {

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successUpload() {
        finish();
    }

    @Override
    public void showSpinnerCategory(List<MakananData> categoryDataList) {
        List<String> listSpinner = new ArrayList<String>();
        for (int i = 0; i < categoryDataList.size(); i++) {
            listSpinner.add(categoryDataList.get(i).getNamaKategori());
        }

        // Membuat adapter spinner
        ArrayAdapter<String> categorySpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listSpinner);
        // Menampilkan spinner 1 line
        categorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        // Memasukkan adapter spinner ke dalam widget spinner kita
        spinCategory.setAdapter(categorySpinnerAdapter);

        spinCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mIdCategory = categoryDataList.get(position).getIdKategori();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @OnClick({R.id.fab_choose_picture, R.id.btn_upload})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fab_choose_picture:
                ShowFileChooser();
                break;
            case R.id.btn_upload:
                Log.d("Isi selected part", "selectedimage: " + part_image);
                mUploadMakananPresenter.uploadImage(this,
                        filePath,
                        edtName.getText().toString(),
                        edtDesc.getText().toString(),
                        mIdCategory);
                break;
        }
    }

    private void ShowFileChooser() {
        Intent intentgalery = new Intent(Intent.ACTION_PICK);
        intentgalery.setType("image/*");
        intentgalery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intentgalery, "select Pictures"), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();

            Log.d("Isi selected image", "selectedimage: " + String.valueOf(selectedImage));
            Log.d("Isi filepath image", "getData file path: " + String.valueOf(filePath));

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imgPicture.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void PermissionGalery() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Constants.STORAGE_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constants.STORAGE_PERMISSION_CODE) {
            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                showMessage("Permission granted now you can read the storage");
                Log.i("Permission on", "onRequestPermissionsResult: " + String.valueOf(grantResults));
            } else {
                //Displaying another toast if permission is not granted
                showMessage("Oops you just denied the permission");
                Log.i("Permission off", "onRequestPermissionsResult: " + String.valueOf(grantResults));
            }
        }
    }

//    private String getPath(Uri filepath) {
//        Cursor cursor = getContentResolver().query(filepath, null, null, null, null);
//        cursor.moveToFirst();
//        String document_id = cursor.getString(0);
//        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
//        cursor.close();
//
//        cursor = getContentResolver().query(
//                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                null, MediaStore.Images.Media._ID + " = ? ",
//                new String[]{document_id}, null);
//        cursor.moveToFirst();
//        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
//        cursor.close();
//
//        return path;
//    }

//    public Uri getImageContentUri(Context context, File imageFile) {
//        String fileAbsolutePath = imageFile.getAbsolutePath();
//        Cursor cursor = context.getContentResolver().query(
//                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                new String[]{MediaStore.Images.Media._ID},
//                MediaStore.Images.Media.DATA + "=? ",
//                new String[]{fileAbsolutePath}, null);
//
//        if (cursor != null && cursor.moveToFirst()) {
//            // Apabila file gambar sudah pernah diapakai namun ada kondisi lain yang belum diketahui
//            // Apabila file gambar sudah pernah dipakai pengambilan bukan di galery
//
//            Log.i("Isi Selected if", "Masuk cursor ada");
//            return filePath;
//
//        } else {
//            Log.i("Isi Selected else", "cursor tidak ada");
//            if (imageFile.exists()) {
//                // Apabila file gambar baru belum pernah di pakai
//                Log.i("Isi Selected else", "imagefile exists");
//                ContentValues values = new ContentValues();
//                values.put(MediaStore.Images.Media.DATA, fileAbsolutePath);
//                return context.getContentResolver().insert(
//                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//            } else {
//                // Apabila file gambar sudah pernah dipakai
//                // Apabila file gambar sudah pernah dipakai di galery
//                Log.i("Isi Selected else", "imagefile tidak exists");
//                return filePath;
//            }
//        }
//    }

}
