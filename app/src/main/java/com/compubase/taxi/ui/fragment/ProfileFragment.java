package com.compubase.taxi.ui.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.compubase.taxi.R;
import com.compubase.taxi.adapter.OlderAdapter;
import com.compubase.taxi.data.API;
import com.compubase.taxi.helper.RetrofitClient;
import com.compubase.taxi.model.OlderModel;
import com.compubase.taxi.ui.activity.HomeActivity;
import com.compubase.taxi.ui.activity.LoginActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    @BindView(R.id.img_pro)
    ImageView imgPro;
    @BindView(R.id.btn_signOut)
    Button btnSignOut;
    @BindView(R.id.ed_f_name)
    EditText edFName;
    @BindView(R.id.ed_l_name)
    EditText edLName;
    @BindView(R.id.ed_phone)
    EditText edPhone;
    @BindView(R.id.ed_mail)
    EditText edMail;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.ed_pass)
    EditText edPass;
    @BindView(R.id.btn_current)
    Button btnCurrent;
    @BindView(R.id.btn_old)
    Button btnOld;
    @BindView(R.id.btn_data)
    Button btnData;
    @BindView(R.id.lin_change)
    LinearLayout linChange;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.rcv_older)
    RecyclerView rcvOlder;
    @BindView(R.id.lin_rcv_older)
    LinearLayout linRcvOlder;
    private Unbinder unbinder;
    private HomeActivity homeActivity;
    private SharedPreferences preferences;
    private String id, fname, lname, email, phone, image;
    private String pass;
    private OlderAdapter olderAdapter;
    private ArrayList<OlderModel> olderList = new ArrayList<>();

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        unbinder = ButterKnife.bind(this, view);

        homeActivity = (HomeActivity) getActivity();

        assert homeActivity != null;
        preferences = homeActivity.getSharedPreferences("user", MODE_PRIVATE);

        id = preferences.getString("id", "");
        fname = preferences.getString("fname", "");
        lname = preferences.getString("lname", "");
        email = preferences.getString("email", "");
        phone = preferences.getString("phone", "");
        image = preferences.getString("image", "");
        pass = preferences.getString("pass", "");

        edFName.setText(fname);
        edLName.setText(lname);
        edMail.setText(email);
        edPhone.setText(phone);
        edPass.setText(pass);

        linChange.setVisibility(View.VISIBLE);

        return view;
    }

    private void updateUser() {

        if (edFName.getText().toString().equals("")) {
            edFName.setError("First name is required");
        } else if (edLName.getText().toString().equals("")) {
            edLName.setError("Last name is required");
        } else if (edMail.getText().toString().equals("")) {
            edMail.setError("Email is required");
        } else if (edPhone.getText().toString().equals("")) {
            edPhone.setError("Phone number is required");
        } else if (edPass.getText().toString().equals("")) {
            edPass.setError("Password is required");
        } else {

            progress.setVisibility(View.VISIBLE);

            RetrofitClient.getInstant().create(API.class).updateUser(id, fname, lname, phone, email, pass, "img")
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {

                                progress.setVisibility(View.GONE);
                                try {
                                    assert response.body() != null;
                                    String string = response.body().string();

                                    if (string.equals("True")) {

                                        Toast.makeText(homeActivity, "تم تغيير بياناتك بنجاح", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (IOException e) {
                                    progress.setVisibility(View.GONE);
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            progress.setVisibility(View.GONE);
                            Toast.makeText(homeActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick({R.id.img_pro, R.id.btn_save, R.id.btn_signOut, R.id.btn_data, R.id.btn_old, R.id.btn_current})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_pro:
                break;
            case R.id.btn_old:
                linChange.setVisibility(View.GONE);
                linRcvOlder.setVisibility(View.VISIBLE);
                setupOlderRecycler();
                fetchDataOld();
                break;
            case R.id.btn_current:
                break;
            case R.id.btn_data:
                linChange.setVisibility(View.VISIBLE);
                linRcvOlder.setVisibility(View.GONE);
                break;
            case R.id.btn_save:
                updateUser();
                break;
            case R.id.btn_signOut:
                SharedPreferences.Editor editor = homeActivity.getSharedPreferences("user", MODE_PRIVATE).edit();

                preferences = homeActivity.getSharedPreferences("user", MODE_PRIVATE);

                editor.putBoolean("login", false);

                editor.apply();
                startActivity(new Intent(homeActivity, LoginActivity.class));
                homeActivity.finish();
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        edFName.setText(fname);
        edLName.setText(lname);
        edMail.setText(email);
        edPhone.setText(phone);
        edPass.setText(pass);
    }

    private void setupOlderRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rcvOlder.setLayoutManager(linearLayoutManager);
    }

    private void fetchDataOld() {

        progress.setVisibility(View.VISIBLE);
        olderList.clear();

        RetrofitClient.getInstant().create(API.class)
                .Older(id)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        GsonBuilder builder = new GsonBuilder();
                        Gson gson = builder.create();

                        progress.setVisibility(View.GONE);
                        try {
                            assert response.body() != null;
                            List<OlderModel> olderModels =
                                    Arrays.asList(gson.fromJson(response.body().string(), OlderModel[].class));

                            olderList.addAll(olderModels);

                            olderAdapter = new OlderAdapter(olderList);
                            rcvOlder.setAdapter(olderAdapter);
                            olderAdapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            progress.setVisibility(View.GONE);
                            Toast.makeText(homeActivity, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        progress.setVisibility(View.GONE);
                        Toast.makeText(homeActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
