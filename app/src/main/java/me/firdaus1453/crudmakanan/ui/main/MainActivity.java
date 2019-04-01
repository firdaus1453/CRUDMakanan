package me.firdaus1453.crudmakanan.ui.main;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.firdaus1453.crudmakanan.R;
import me.firdaus1453.crudmakanan.ui.favorite.FavoriteFragment;
import me.firdaus1453.crudmakanan.ui.makanan.MakananFragment;
import me.firdaus1453.crudmakanan.ui.makananbyuser.MakananByUserFragment;
import me.firdaus1453.crudmakanan.ui.profil.ProfilFragment;
import me.firdaus1453.crudmakanan.ui.uploadmakanan.UploadMakananActivity;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    @BindView(R.id.fl_container)
    FrameLayout flContainer;
    private TextView mTextMessage;

    private MainPresenter mMainPresenter = new MainPresenter();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_makanan:
                    MakananFragment makananFragment = new MakananFragment();
                    loadFragment(makananFragment);
                    return true;
                case R.id.navigation_favorite:
                    MakananByUserFragment makananByUserFragment = new MakananByUserFragment();
                    loadFragment(makananByUserFragment);
                    return true;
                case R.id.navigation_profil:
                    ProfilFragment profilFragment = new ProfilFragment();
                    loadFragment(profilFragment);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        MakananFragment makananFragment = new MakananFragment();
        loadFragment(makananFragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_logout:
                mMainPresenter.logoutSession(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Membuat function load fragment
    private void loadFragment(Fragment fragment) {
        // Menampilkan fragment menggunakan fragment transaction
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
