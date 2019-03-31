package me.firdaus1453.crudmakanan.ui.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.firdaus1453.crudmakanan.R;
import me.firdaus1453.crudmakanan.model.login.LoginData;
import me.firdaus1453.crudmakanan.ui.main.MainActivity;
import me.firdaus1453.crudmakanan.ui.register.RegisterActivity;
import me.firdaus1453.crudmakanan.utils.Constants;

public class LoginActivity extends AppCompatActivity implements LoginContract.View{

    @BindView(R.id.txt_judul)
    TextView txtJudul;
    @BindView(R.id.edt_username)
    EditText edtUsername;
    @BindView(R.id.edt_password)
    EditText edtPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.txt_register)
    TextView txtRegister;

    private ProgressDialog progressDialog;
    private LoginPresenter loginPresenter = new LoginPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        loginPresenter.checkLogin(this);
    }

    @OnClick({R.id.btn_login, R.id.txt_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                loginPresenter.doLogin(edtUsername.getText().toString(),edtPassword.getText().toString());
                break;
            case R.id.txt_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
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
    public void loginSuccess(String message, LoginData loginData) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        // Menyimpan data user ke dalam SharedPreference
        loginPresenter.saveDataUser(this, loginData);

        LoginData mLoginData = new LoginData();
        mLoginData.setId_user(loginData.getId_user());
        mLoginData.setNamaUser(loginData.getNamaUser());
        mLoginData.setAlamat(loginData.getAlamat());
        mLoginData.setJenkel(loginData.getJenkel());
        mLoginData.setNoTelp(loginData.getNoTelp());
        mLoginData.setUsername(loginData.getUsername());
        mLoginData.setPassword(loginData.getPassword());
        mLoginData.setLevel(loginData.getLevel());

        startActivity(new Intent(this, MainActivity.class).putExtra(Constants.KEY_LOGIN,mLoginData));
        finish();
    }

    @Override
    public void loginFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void usernameError(String message) {
        edtUsername.setError(message);
        edtUsername.setFocusable(true);
    }

    @Override
    public void passwordError(String message) {
        edtPassword.setError(message);
        edtPassword.setFocusable(true);
    }

    @Override
    public void isLogin() {
        // Berpindah halaman apabila user sudah login
        startActivity(new Intent(this, MainActivity.class));
        // Menutup loginActivity
        finish();
    }
}
