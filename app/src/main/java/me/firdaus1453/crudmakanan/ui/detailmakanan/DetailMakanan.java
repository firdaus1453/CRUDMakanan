package me.firdaus1453.crudmakanan.ui.detailmakanan;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.firdaus1453.crudmakanan.R;
import me.firdaus1453.crudmakanan.adapter.MakananAdapter;
import me.firdaus1453.crudmakanan.model.makanan.MakananData;
import me.firdaus1453.crudmakanan.utils.Constants;

public class DetailMakanan extends AppCompatActivity implements DetailMakananContract.View {

    @BindView(R.id.img_makanan_detail)
    ImageView imgMakananDetail;
    @BindView(R.id.txt_name_makanan_detail)
    TextView txtNameMakananDetail;
    @BindView(R.id.txt_time_makanan_detail)
    TextView txtTimeMakananDetail;
    @BindView(R.id.txt_name_user)
    TextView txtNameUser;
    @BindView(R.id.txt_desc_makanan)
    TextView txtDescMakanan;
    @BindView(R.id.card_view_detail)
    CardView cardViewDetail;
    @BindView(R.id.sv_detail)
    ScrollView svDetail;
    @BindView(R.id.pb_loading)
    ProgressBar pbLoading;
    @BindView(R.id.rv_progress)
    RelativeLayout rvProgress;
    @BindView(R.id.txt_info)
    TextView txtInfo;

    private DetailMakananPresenter mDetailMakananPresenter = new DetailMakananPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_makanan);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle("Detail Makanan");
        String idMakanan = getIntent().getStringExtra(Constants.KEY_EXTRA_ID_MAKANAN);
        mDetailMakananPresenter.getDetailMakanan(idMakanan);
    }

    @Override
    public void showProgress() {
        rvProgress.setVisibility(View.VISIBLE);
        svDetail.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        rvProgress.setVisibility(View.GONE);
        svDetail.setVisibility(View.VISIBLE);
    }

    @Override
    public void showDetailMakanan(MakananData makananData) {
        RequestOptions options = new RequestOptions().error(R.drawable.ic_broken_image).placeholder(R.drawable.ic_broken_image);
        Glide.with(this).load(makananData.getUrlMakanan()).apply(options).into(imgMakananDetail);

        txtNameMakananDetail.setText(makananData.getNamaMakanan());
        txtDescMakanan.setText(makananData.getDescMakanan());
        txtNameUser.setText(makananData.getNamaUser());
        txtTimeMakananDetail.setText(newDate(makananData.getInsertTime()));
    }

    @Override
    public void showFailureMessage(String msg) {
        svDetail.setVisibility(View.GONE);
        rvProgress.setVisibility(View.VISIBLE);
        pbLoading.setVisibility(View.GONE);
        txtInfo.setText(msg);
    }

    public String newDate(String insertTime) {
        Date date2 = null;
        String newDate = insertTime;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date2 = sdf.parse(insertTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date2 != null) {
            newDate = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss").format(date2);
        }
        return newDate;
    }

}
