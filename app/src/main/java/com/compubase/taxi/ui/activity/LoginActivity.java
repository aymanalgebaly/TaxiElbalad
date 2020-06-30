package com.compubase.taxi.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.compubase.taxi.R;
import com.compubase.taxi.data.API;
import com.compubase.taxi.helper.RetrofitClient;
import com.compubase.taxi.model.LoginModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.btn_sign_up)
    Button btnSignUp;
    @BindView(R.id.img_main)
    ImageView imgMain;
    @BindView(R.id.card_view)
    CardView cardView;
    @BindView(R.id.lin_main)
    LinearLayout linMain;
    @BindView(R.id.ed_email)
    EditText edEmail;
    @BindView(R.id.ed_pass)
    EditText edPass;
    @BindView(R.id.lin_fields)
    LinearLayout linFields;
    @BindView(R.id.btn_signIn)
    Button btnSignIn;
    @BindView(R.id.progress)
    ProgressBar progress;
    private String email, password;
    private Integer id;
    private String fname, lname, phone, img, mail;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.btn_signIn)
    public void onViewClicked() {
        userLogin();
    }

    public void userLogin() {

        progress.setVisibility(View.VISIBLE);
        email = edEmail.getText().toString();
        password = edPass.getText().toString();

        if (!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)) {

            RetrofitClient
                    .getInstant()
                    .create(API.class)
                    .UserLogin(email, password)
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                            ResponseBody body = response.body();

                            if (response.isSuccessful()) {

                                progress.setVisibility(View.GONE);
//                                progressBar.setVisibility(View.GONE);
                                try {

//                                    if (body.string().equals("False")) {
//                                        Toast.makeText(LoginActivity.this, "please check your data", Toast.LENGTH_SHORT).show();
//                                        return;
//                                    }

                                    ResponseBody bodyy = response.body();


                                    GsonBuilder builder = new GsonBuilder();
                                    Gson gson = builder.create();

                                    assert bodyy != null;
                                    List<LoginModel> loginModels =
                                            Objects.requireNonNull(Arrays.asList(gson.fromJson(bodyy.string(), LoginModel[].class)));

                                    id = loginModels.get(0).getId();
                                    fname = loginModels.get(0).getFn();
                                    lname = loginModels.get(0).getLn();
                                    phone = loginModels.get(0).getPhone();
                                    mail = loginModels.get(0).getEmail();
                                    img = loginModels.get(0).getImg();


                                    Toast.makeText(LoginActivity.this, "login successfully", Toast.LENGTH_SHORT).show();

                                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                    finish();

                                    sharedLogin();


                                } catch (IOException e) {
                                    e.printStackTrace();
                                    progress.setVisibility(View.GONE);
                                }


                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            progress.setVisibility(View.GONE);
                        }
                    });

        }
    }

    public void sharedLogin() {

        SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();

        preferences = getSharedPreferences("user", MODE_PRIVATE);


        editor.putBoolean("login", true);
        editor.putString("id", String.valueOf(id));
        editor.putString("fname", fname);
        editor.putString("lname", lname);
        editor.putString("email", mail);
        editor.putString("phone", phone);
        editor.putString("image", img);
        editor.putString("pass", password);

        editor.apply();
    }
}
