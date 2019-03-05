package me.firdaus1453.crudmakanan.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.firdaus1453.crudmakanan.R;
import me.firdaus1453.crudmakanan.ui.favorite.FavoriteFragment;
import me.firdaus1453.crudmakanan.ui.makanan.MakananFragment;
import me.firdaus1453.crudmakanan.ui.profil.ProfilFragment;

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
                    FavoriteFragment favoriteFragment = new FavoriteFragment();
                    loadFragment(favoriteFragment);
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
                // Melakukan perintah logout ke presenter
                mMainPresenter.logoutSession(this);
                // Menutup mainactivity
                finish();
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
