package me.firdaus1453.crudmakanan.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.firdaus1453.crudmakanan.R;
import me.firdaus1453.crudmakanan.model.makanan.MakananData;
import me.firdaus1453.crudmakanan.ui.detailmakanan.DetailMakananActivity;
import me.firdaus1453.crudmakanan.ui.makananbycategory.MakananByCategoryActivity;
import me.firdaus1453.crudmakanan.utils.Constants;

/**
 * Created by firdaus1453 on 3/26/2019.
 */
public class MakananAdapter extends RecyclerView.Adapter<MakananAdapter.ViewHolder> {
    public static final int TYPE_1 = 1;
    public static final int TYPE_2 = 2;
    public static final int TYPE_3 = 3;
    public static final int TYPE_4 = 4;

    Integer viewType;
    private final Context context;
    private final List<MakananData> makananDataList;

    public MakananAdapter(Integer viewType, Context context, List<MakananData> makananDataList) {
        this.viewType = viewType;
        this.context = context;
        this.makananDataList = makananDataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case TYPE_1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food_news, null);
                return new FoodNewsViewHolder(view);
            case TYPE_2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food_populer, null);
                return new FoodPopulerViewHolder(view);
            case TYPE_3:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food_kategory, null);
                return new FoodKategoryViewHolder(view);
            case TYPE_4:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food_by_category, null);
                return new FoodNewsViewHolder(view);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Mengambil data lalu memasukkan ke dalam model
        final MakananData makananData = makananDataList.get(position);

        // Mengambil view type dari user atau constractor
        int mViewType = viewType;
        switch (mViewType) {
            case TYPE_1:
                // Membuat holder untuk dapat mengakses widget
                FoodNewsViewHolder foodNewsViewHolder = (FoodNewsViewHolder) holder;

                // Requestoption untuk error dan placeholder gambar
                RequestOptions options = new RequestOptions().error(R.drawable.ic_broken_image).placeholder(R.drawable.ic_broken_image);
                Glide.with(context).load(makananData.getUrlMakanan()).apply(options).into(foodNewsViewHolder.imgMakanan);

                // Menampilkan tittle dan jumlah view
                foodNewsViewHolder.txtTitle.setText(makananData.getNamaMakanan());
                foodNewsViewHolder.txtView.setText(makananData.getView());


                foodNewsViewHolder.txtTime.setText(newDate(makananData.getInsertTime()));


                // Membuat onclick
                foodNewsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context, DetailMakananActivity.class).putExtra(Constants.KEY_EXTRA_ID_MAKANAN, makananData.getIdMakanan()));
                    }
                });

                break;
            case TYPE_2:
                FoodPopulerViewHolder foodPopulerViewHolder = (FoodPopulerViewHolder) holder;

                // Requestoption untuk error dan placeholder gambar
                RequestOptions options2 = new RequestOptions().error(R.drawable.ic_broken_image).placeholder(R.drawable.ic_broken_image);
                Glide.with(context).load(makananData.getUrlMakanan()).apply(options2).into(foodPopulerViewHolder.imgMakanan);
                // Menampilkan tittle dan jumlah view
                foodPopulerViewHolder.txtView.setText(makananData.getView());

                foodPopulerViewHolder.txtTitle.setText(makananData.getNamaMakanan());

                foodPopulerViewHolder.txtTime.setText(newDate(makananData.getInsertTime()));

                foodPopulerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context, DetailMakananActivity.class).putExtra(Constants.KEY_EXTRA_ID_MAKANAN, makananData.getIdMakanan()));
                    }
                });
                break;
            case TYPE_3:
                FoodKategoryViewHolder foodKategoryViewHolder = (FoodKategoryViewHolder) holder;

                // Requestoption untuk error dan placeholder gambar
                RequestOptions options3 = new RequestOptions().error(R.drawable.ic_broken_image).placeholder(R.drawable.ic_broken_image);
                Glide.with(context).load(makananData.getUrlMakanan()).apply(options3).into(foodKategoryViewHolder.image);

                foodKategoryViewHolder.txtNamaKategory.setText(makananData.getNamaKategori());
                foodKategoryViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context, MakananByCategoryActivity.class).putExtra(Constants.KEY_EXTRA_ID_CATEGORY, makananData.getIdKategori()));
                    }
                });
                break;
            case TYPE_4:
                // Membuat holder untuk dapat mengakses widget
                FoodNewsViewHolder foodNewsViewHolder2 = (FoodNewsViewHolder) holder;

                // Requestoption untuk error dan placeholder gambar
                RequestOptions options4 = new RequestOptions().error(R.drawable.ic_broken_image).placeholder(R.drawable.ic_broken_image);
                Glide.with(context).load(makananData.getUrlMakanan()).apply(options4).into(foodNewsViewHolder2.imgMakanan);

                // Menampilkan tittle dan jumlah view
                foodNewsViewHolder2.txtTitle.setText(makananData.getNamaMakanan());
                foodNewsViewHolder2.txtView.setText(makananData.getView());


                foodNewsViewHolder2.txtTime.setText(newDate(makananData.getInsertTime()));


                // Membuat onclick
                foodNewsViewHolder2.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context, DetailMakananActivity.class).putExtra(Constants.KEY_EXTRA_ID_MAKANAN, makananData.getIdMakanan()));
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return makananDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return viewType;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class FoodNewsViewHolder extends ViewHolder {
        @BindView(R.id.img_makanan)
        ImageView imgMakanan;
        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.img_view)
        ImageView imgView;
        @BindView(R.id.txt_view)
        TextView txtView;
        @BindView(R.id.txt_time)
        TextView txtTime;

        public FoodNewsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }

    public class FoodPopulerViewHolder extends ViewHolder {
        @BindView(R.id.img_makanan)
        ImageView imgMakanan;
        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.img_view)
        ImageView imgView;
        @BindView(R.id.txt_view)
        TextView txtView;
        @BindView(R.id.txt_time)
        TextView txtTime;

        public FoodPopulerViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }

    public class FoodKategoryViewHolder extends ViewHolder {
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.txt_nama_kategory)
        TextView txtNamaKategory;

        public FoodKategoryViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
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
