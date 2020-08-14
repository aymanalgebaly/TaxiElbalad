package com.compubase.taxi.ui.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.compubase.taxi.R;
import com.compubase.taxi.data.API;
import com.compubase.taxi.helper.RetrofitClient;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.btn_sign_up)
    Button btnSignUp;
    @BindView(R.id.img_main)
    ImageView imgMain;
    @BindView(R.id.card_view)
    CardView cardView;
    @BindView(R.id.lin_main)
    LinearLayout linMain;
    @BindView(R.id.ed_first_name)
    EditText edFirstName;
    @BindView(R.id.ed_last_name)
    EditText edLastName;
    @BindView(R.id.ed_phone)
    EditText edPhone;
    @BindView(R.id.ed_mail)
    EditText edMail;
    @BindView(R.id.ed_pass)
    EditText edPass;
    @BindView(R.id.ed_con_pass)
    EditText edConPass;
    @BindView(R.id.img_register)
    ImageView imgRegister;
    @BindView(R.id.btn_confirm_confirm)
    Button btnConfirmConfirm;
    @BindView(R.id.progress)
    ProgressBar progress;
    private String f_name, l_name, email, phone, pass, con_pass;
    private String m_Text;
    private AlertDialog dialog;
    private EditText editText;
    private Button button;
    private AlertDialog builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        Glide.with(this).load(R.drawable.boss_biger_biger).into(imgRegister);
    }

    @OnClick({R.id.btn_sign_up, R.id.img_register, R.id.btn_confirm_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_sign_up:
                break;
            case R.id.img_register:
                break;
            case R.id.btn_confirm_confirm:
                validate();
                break;
        }
    }

    private void validate() {

        f_name = edFirstName.getText().toString();
        l_name = edLastName.getText().toString();
        email = edMail.getText().toString();
        phone = edPhone.getText().toString();
        pass = edPass.getText().toString();
        con_pass = edConPass.getText().toString();

        if (TextUtils.isEmpty(f_name)) {
            edFirstName.setError("اسم المستخدم مطلوب");
        } else if (TextUtils.isEmpty(l_name)) {
            edLastName.setError("اسم المستخدم مطلوب");
        } else if (TextUtils.isEmpty(email)) {
            edMail.setError("البريد الالكتروني مطلوب");
        } else if (TextUtils.isEmpty(phone)) {
            edPhone.setError("رقم الهاتف مطلوب");
        } else if (TextUtils.isEmpty(pass)) {
            edPass.setError("كلمة المرور مطلوبه");
        } else if (!TextUtils.equals(pass, con_pass)) {
            edConPass.setError("كلمة المرور غير متطابقة");
        } else {

            progress.setVisibility(View.VISIBLE);

            Call<ResponseBody> call2 = RetrofitClient.getInstant()
                    .create(API.class)
                    .register(f_name, l_name, phone, email, pass, "img");

            call2.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    if (response.isSuccessful()) {

                        progress.setVisibility(View.GONE);
                        assert response.body() != null;
                        try {
                            String string = response.body().string();
                            if (string.equals("True")) {

                                sendMail();
                                showDialog();

                            }
                        } catch (IOException e) {
                            progress.setVisibility(View.GONE);
                            Toast.makeText(RegisterActivity.this, "البريد الالكتروني موجود مسبقا", Toast.LENGTH_SHORT).show();
                        }

                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    progress.setVisibility(View.GONE);
                    Toast.makeText(RegisterActivity.this, "خطأ في كلمة المرور او البريد الالكتروني", Toast.LENGTH_SHORT).show();
//                    Log.i( "onResponse",String.valueOf(t.getMessage()));

                }
            });
        }
    }

    private void sendMail() {
        Retrofit retrofit = RetrofitClient.getInstant();
        API api = retrofit.create(API.class);
        Call<ResponseBody> responseBodyCall = api.SendSMS(email);
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    assert response.body() != null;
                    String string = response.body().string();

                    Log.i( "onResponse: ",string);
                    if (string.equals("True")) {

//                        enterCode();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void enterCode() {

        String ed_confirm = editText.getText().toString();

        if (editText.getText().toString().equals("")) {
            editText.setError("Confirm code is required");

        } else {

            progress.setVisibility(View.VISIBLE);

            Retrofit retrofit = RetrofitClient.getInstant();
            API api = retrofit.create(API.class);
            Call<String> responseBodyCall = api.EnterCode(ed_confirm);
            responseBodyCall.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    if (response.isSuccessful()) {
                        progress.setVisibility(View.GONE);
                        assert response.body() != null;
                        String body = response.body();

                        Log.i("onResponse", body);

                        if (body.equals("True")) {

                            builder.dismiss();
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

                            finish();
                        }
                    }

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    progress.setVisibility(View.GONE);
                    Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void showDialog() {

        View inflate = LayoutInflater.from(this).inflate(R.layout.dialog_confirm_code, null);
        builder = new AlertDialog.Builder(this).create();
        builder.setView(inflate);
        builder.show();

        editText = inflate.findViewById(R.id.ed_confirm_code);
        button = inflate.findViewById(R.id.btn_confirm);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterCode();
            }
        });
    }

}
