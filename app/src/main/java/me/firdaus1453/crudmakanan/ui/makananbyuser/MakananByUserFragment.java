package me.firdaus1453.crudmakanan.ui.makananbyuser;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Optional;
import butterknife.Unbinder;
import me.firdaus1453.crudmakanan.R;
import me.firdaus1453.crudmakanan.adapter.MakananAdapter;
import me.firdaus1453.crudmakanan.model.makanan.MakananData;
import me.firdaus1453.crudmakanan.utils.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class MakananByUserFragment extends Fragment implements MakananByUserContract.View {

    @BindView(R.id.pb_loading)
    ProgressBar pbLoading;

    @BindView(R.id.txt_info)
    TextView txtInfo;
    @BindView(R.id.rl_progress_by_user)
    RelativeLayout rlProgressByUser;
    @BindView(R.id.rv_makanan)
    RecyclerView rvMakanan;
    @BindView(R.id.sr_makanan_by_user)
    SwipeRefreshLayout srMakananByUser;
    Unbinder unbinder;


    private MakananByUserPresenter mMakananByUserPresenter = new MakananByUserPresenter(this);
    private String idUser;

    public MakananByUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_makanan_by_user, container, false);
        unbinder = ButterKnife.bind(this, view);

        // Mengambil id user dari sharedpref
        SharedPreferences pref = getContext().getSharedPreferences(Constants.pref_name, 0);
        idUser = pref.getString(Constants.KEY_USER_ID, "");

        mMakananByUserPresenter.getListFoodByUser(idUser);

        srMakananByUser.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srMakananByUser.setRefreshing(false);
                mMakananByUserPresenter.getListFoodByUser(idUser);

            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void showProgress() {
        rlProgressByUser.setVisibility(View.VISIBLE);
        srMakananByUser.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        rlProgressByUser.setVisibility(View.GONE);
        rvMakanan.setVisibility(View.VISIBLE);
        srMakananByUser.setVisibility(View.VISIBLE);
    }

    @Override
    public void showFoodByUser(List<MakananData> foodByUserList) {
        rvMakanan.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMakanan.setAdapter(new MakananAdapter(MakananAdapter.TYPE_4, getContext(), foodByUserList));
    }

    @Override
    public void showFailureMessage(String msg) {
        srMakananByUser.setVisibility(View.VISIBLE);
        rlProgressByUser.setVisibility(View.VISIBLE);
        rvMakanan.setVisibility(View.GONE);
        pbLoading.setVisibility(View.GONE);
        txtInfo.setText(msg);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMakananByUserPresenter.getListFoodByUser(idUser);
    }
}
