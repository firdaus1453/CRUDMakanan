package me.firdaus1453.crudmakanan.ui.register;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputEditText;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.firdaus1453.crudmakanan.R;
import me.firdaus1453.crudmakanan.model.login.LoginData;
import me.firdaus1453.crudmakanan.ui.login.LoginActivity;

public class RegisterActivity extends AppCompatActivity implements RegisterContract.View {

    @BindView(R.id.edtnama)
    EditText edtnama;
    @BindView(R.id.edtalamat)
    EditText edtalamat;
    @BindView(R.id.edtnotelp)
    EditText edtnotelp;
    @BindView(R.id.radioLaki)
    RadioButton radioLaki;
    @BindView(R.id.radioPerempuan)
    RadioButton radioPerempuan;
    @BindView(R.id.edtusername)
    EditText edtusername;
    @BindView(R.id.edtpassword)
    TextInputEditText edtpassword;
    @BindView(R.id.edtpasswordconfirm)
    TextInputEditText edtpasswordconfirm;
    @BindView(R.id.radioAdmin)
    RadioButton radioAdmin;
    @BindView(R.id.radioUserbiasa)
    RadioButton radioUserbiasa;
    @BindView(R.id.btnregister)
    Button btnregister;

    private ProgressDialog progressDialog;
    private RegisterPresenter mRegisterPresenter = new RegisterPresenter(this);
    private String jenkel;
    private String level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        // set jenkel dan level default
        setRadio();
    }

    private void setRadio() {
        if (radioAdmin.isChecked()){
            level = "1";
        }else {
            level = "0";
        }

        if (radioLaki.isChecked()){
            jenkel = "L";
        }else {
            jenkel = "P";
        }
    }

    @Override
    public void showProgress() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading . . .");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showRegisterSucces(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @OnClick({R.id.radioLaki, R.id.radioPerempuan, R.id.radioAdmin, R.id.radioUserbiasa, R.id.btnregister})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.radioLaki:
                jenkel = "L";
                break;
            case R.id.radioPerempuan:
                jenkel = "P";
                break;
            case R.id.radioAdmin:
                level = "1";
                break;
            case R.id.radioUserbiasa:
                level = "0";
                break;
            case R.id.btnregister:
                // Membuat object model call LoginData
                LoginData mLoginData = new LoginData();

                // Memasukkan data ke dalam model LoginData
                mLoginData.setUsername(edtusername.getText().toString());
                mLoginData.setPassword(edtpassword.getText().toString());
                mLoginData.setNamaUser(edtnama.getText().toString());
                mLoginData.setAlamat(edtalamat.getText().toString());
                mLoginData.setNoTelp(edtnotelp.getText().toString());
                mLoginData.setJenkel(jenkel);
                mLoginData.setLevel(level);

                // Kirimkan data ke presenter
                mRegisterPresenter.doRegisterUser(mLoginData);
                break;
        }
    }
}
