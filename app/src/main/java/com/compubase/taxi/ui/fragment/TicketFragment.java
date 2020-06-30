package com.compubase.taxi.ui.fragment;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.compubase.taxi.R;
import com.compubase.taxi.data.API;
import com.compubase.taxi.helper.RetrofitClient;
import com.compubase.taxi.helper.SpinnerUtils;
import com.compubase.taxi.helper.SpinnerUtilsInteger;
import com.compubase.taxi.ui.activity.HomeActivity;
import com.paytabs.paytabs_sdk.payment.ui.activities.PayTabActivity;
import com.paytabs.paytabs_sdk.utils.PaymentParams;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class TicketFragment extends Fragment {


    @BindView(R.id.sp_under_five)
    Spinner spUnderFive;
    @BindView(R.id.sp_over_five)
    Spinner spOverFive;
    @BindView(R.id.sp_ma7ata)
    Spinner spMa7ata;
    @BindView(R.id.ed_last_name)
    RelativeLayout edLastName;
    @BindView(R.id.txt_date)
    TextView txtDate;
    @BindView(R.id.txt_time)
    TextView txtTime;
    @BindView(R.id.sp_type_ticket)
    Spinner spTypeTicket;
    @BindView(R.id.ed_pass)
    RelativeLayout edPass;
    @BindView(R.id.price_value)
    TextView priceValue;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    @BindView(R.id.progress)
    ProgressBar progress;

    private Unbinder unbinder;
    private SharedPreferences preferences;

    private ArrayList<Integer> numberUnderFive = new ArrayList<Integer>();
    private ArrayList<Integer> numberOverFive = new ArrayList<Integer>();
    private ArrayList<String> ticketType = new ArrayList<String>();
    private ArrayList<String> ma7ata = new ArrayList<String>();
    private String item_ma7ata;
    private int num_over_five;
    private String item_tkt_postion;
    private int num_under_five;
    private Calendar myCalendar;
    private Calendar mmyCalendar;
    private Calendar mmmyCalendar;
    private HomeActivity homeActivity;
    private String id;
    private int price ;
    private int price_over;
    private int price_under;
    private int total_price;
    private int tkt_type_price;

    public TicketFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ticket, container, false);
        unbinder = ButterKnife.bind(this, view);

        homeActivity = (HomeActivity) getActivity();

        preferences = homeActivity.getSharedPreferences("user", Context.MODE_PRIVATE);
        id = preferences.getString("id", "");

        myCalendar = Calendar.getInstance();
        final int hour = myCalendar.get(Calendar.HOUR_OF_DAY);
        final int minute = myCalendar.get(Calendar.MINUTE);

        numberOverFive.add(0);
        numberOverFive.add(1);
        numberOverFive.add(2);
        numberOverFive.add(3);
        numberOverFive.add(4);
        numberOverFive.add(5);
        numberOverFive.add(6);
        numberOverFive.add(7);
        numberOverFive.add(8);
        numberOverFive.add(9);
        numberOverFive.add(10);

        numberUnderFive.add(0);
        numberUnderFive.add(1);
        numberUnderFive.add(2);
        numberUnderFive.add(3);
        numberUnderFive.add(4);
        numberUnderFive.add(5);
        numberUnderFive.add(6);
        numberUnderFive.add(7);
        numberUnderFive.add(8);
        numberUnderFive.add(9);
        numberUnderFive.add(10);

        ticketType.add("نوع التذكرة");
        ticketType.add("جولة سياحية في المنطقة التاريخية مع مرشد سياحي مع الوقوف عند المعالم التاريخية و الدخول لكل معلم");
        ticketType.add("جولة سياحية في المنطقة التاريخية مع مرشد سياحي فقط الرؤية من داخل السياره دون الدخول الي المعالم");
        ticketType.add("توصيل فقط الي معلم معين");
        ticketType.add("جولة لمدة ساعة في جدة التاريخية");
        ticketType.add("جولة لمدة نصف ساعة في جدة التاريخية");
        ticketType.add("المسار الاول من ابراج الباشا الي سوق قابل");
        ticketType.add("المسار الثاني من سوق المحمل الي سوق البدو");
        ticketType.add("المسار الثالث من شارع الذهب الي برحة بيت نصيف");
        ticketType.add("المسار الرابع من باب المدينة الي مسجد الشافعي");

        ma7ata.add("محطة الوصول");
        ma7ata.add("محطة شارع قابل امام مركز المحمل");

        SpinnerUtilsInteger.SetSpinnerAdapter(getContext(), spOverFive, numberOverFive, R.layout.spinner_item_black);
        SpinnerUtilsInteger.SetSpinnerAdapter(getContext(), spUnderFive, numberUnderFive, R.layout.spinner_item_black);
        SpinnerUtils.SetSpinnerAdapter(getContext(), spTypeTicket, ticketType, R.layout.spinner_item_black);
        SpinnerUtils.SetSpinnerAdapter(getContext(), spMa7ata, ma7ata, R.layout.spinner_item_black);

        spMa7ata.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                item_ma7ata = ma7ata.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spOverFive.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                num_over_five = numberOverFive.get(i);

                total_price = (spUnderFive.getSelectedItemPosition() + spOverFive.getSelectedItemPosition()) * price;
//                total_price = total_price + (num_over_five * price);
                priceValue.setText(String.valueOf(total_price));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spTypeTicket.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                item_tkt_postion = ticketType.get(i);

                if (item_tkt_postion.equals
                        ("جولة سياحية في المنطقة التاريخية مع مرشد سياحي مع الوقوف عند المعالم التاريخية و الدخول لكل معلم")){

                    price = 50;

                } else if (item_tkt_postion.equals("جولة سياحية في المنطقة التاريخية مع مرشد سياحي فقط الرؤية من داخل السياره دون الدخول الي المعالم")){

                    price = 25;

                }else if (item_tkt_postion.equals("نوع التذكرة")){

                    price = 0;

                }else {

                    price = 10;
                }

                total_price = (spUnderFive.getSelectedItemPosition() + spOverFive.getSelectedItemPosition()) * price;
//                total_price = num_under_five * price;
                priceValue.setText(String.valueOf(total_price));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spUnderFive.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                num_under_five = numberUnderFive.get(i);

                total_price = (spUnderFive.getSelectedItemPosition() + spOverFive.getSelectedItemPosition()) * price;
//                total_price = num_under_five * price;
                priceValue.setText(String.valueOf(total_price));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        txtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        view.setIs24HourView(false);
                        String AM_PM;

                        if (hourOfDay > 12) {
                            hourOfDay -= 12;
                            AM_PM = "PM";
                        } else if (hour == 0) {
                            hourOfDay += 12;
                            AM_PM = "AM";
                        } else if (hour == 12) {
                            AM_PM = "PM";
                        } else {
                            AM_PM = "AM";
                        }

                        String min = "";
                        if (minute < 10)
                            min = "0" + minute;
                        else
                            min = String.valueOf(minute);

                        String aTime = new StringBuilder().append(hourOfDay).append(':')
                                .append(min).append(" ").append(AM_PM).toString();
                        txtTime.setText(aTime);
                    }
                }, hour, minute, false);
                timePickerDialog.show();
            }
        });

        mmyCalendar = Calendar.getInstance();
        mmmyCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                mmyCalendar.set(Calendar.YEAR, year);
                mmyCalendar.set(Calendar.MONTH, monthOfYear);
                mmyCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateDateFrom();
            }

        };

        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Objects.requireNonNull(homeActivity), date, mmyCalendar
                        .get(Calendar.YEAR), mmyCalendar.get(Calendar.MONTH),
                        mmyCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        return view;

    }

    private void InsertTicket() {

        if (spTypeTicket.getSelectedItemPosition() == 0) {
            Toast.makeText(homeActivity, "اختر نوع التذكره", Toast.LENGTH_SHORT).show();
        } else if (spMa7ata.getSelectedItemPosition() == 0) {
            Toast.makeText(homeActivity, "اختر محطة الوصول", Toast.LENGTH_SHORT).show();
        } else if (spUnderFive.getSelectedItemPosition() == 0 && spOverFive.getSelectedItemPosition() == 0) {
            Toast.makeText(homeActivity, "اختر عدد الافراد", Toast.LENGTH_SHORT).show();
        } else {

            progress.setVisibility(View.VISIBLE);

            RetrofitClient.getInstant().create(API.class).insertTicket(id, String.valueOf(num_over_five), String.valueOf(num_under_five), item_ma7ata
                    , txtDate.getText().toString(), txtTime.getText().toString(), item_tkt_postion)
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                progress.setVisibility(View.GONE);
                                try {
                                    assert response.body() != null;
                                    String string = response.body().string();

                                    if (string.equals("True")) {

                                        Toast.makeText(homeActivity, "تم الحجز", Toast.LENGTH_SHORT).show();

                                        homeActivity.displaySelectedFragment(new HomeFragment());


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

    private void payment() {

        Intent in = new Intent(homeActivity, PayTabActivity.class);
        in.putExtra(PaymentParams.MERCHANT_EMAIL, "info@TaxiAlbalad.com"); //this a demo account for testing the sdk
        in.putExtra(PaymentParams.SECRET_KEY,"PvvG9ol3AUb1mfPoCmeGbWKk7BUfCp4sUA83Zu7ApjBLyraaOoRZkBksdfIQENiznrDmVLWl4y7DZhONFFN0h7tvnbl2YEBl5fI0");//Add your Secret Key Here
        in.putExtra(PaymentParams.LANGUAGE,PaymentParams.ENGLISH);
        in.putExtra(PaymentParams.TRANSACTION_TITLE, "Payment");
        in.putExtra(PaymentParams.AMOUNT,Double.valueOf(total_price));

        in.putExtra(PaymentParams.CURRENCY_CODE, "SAR");
        in.putExtra(PaymentParams.CUSTOMER_PHONE_NUMBER, "01111828535");
        in.putExtra(PaymentParams.CUSTOMER_EMAIL, "customer-email@example.com");
        in.putExtra(PaymentParams.ORDER_ID, "123456");
        in.putExtra(PaymentParams.PRODUCT_NAME, "Product 1, Product 2");

//Billing Address
        in.putExtra(PaymentParams.ADDRESS_BILLING, "Flat 1,Building 123, Road 2345");
        in.putExtra(PaymentParams.CITY_BILLING, "jeddah");
        in.putExtra(PaymentParams.STATE_BILLING, "jeddah");
        in.putExtra(PaymentParams.COUNTRY_BILLING, "SAR");
        in.putExtra(PaymentParams.POSTAL_CODE_BILLING, "00973"); //Put Country Phone code if Postal code not available '00973'

//Shipping Address
        in.putExtra(PaymentParams.ADDRESS_SHIPPING, "Flat 1,Building 123, Road 2345");
        in.putExtra(PaymentParams.CITY_SHIPPING, "jeddah");
        in.putExtra(PaymentParams.STATE_SHIPPING, "jeddah");
        in.putExtra(PaymentParams.COUNTRY_SHIPPING, "SAR");
        in.putExtra(PaymentParams.POSTAL_CODE_SHIPPING, "00973"); //Put Country Phone code if Postal code not available '00973'

//Payment Page Style
        in.putExtra(PaymentParams.PAY_BUTTON_COLOR, "#FDC901");

//Tokenization
        in.putExtra(PaymentParams.IS_TOKENIZATION, true);
        startActivityForResult(in, PaymentParams.PAYMENT_REQUEST_CODE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PaymentParams.PAYMENT_REQUEST_CODE) {


            InsertTicket();


            Log.e("Tag", data.getStringExtra(PaymentParams.RESPONSE_CODE));
            Log.e("Tag", data.getStringExtra(PaymentParams.TRANSACTION_ID));

            if (data.hasExtra(PaymentParams.TOKEN) && !data.getStringExtra(PaymentParams.TOKEN).isEmpty()) {

                Log.e("Tag", data.getStringExtra(PaymentParams.TOKEN));
                Log.e("Tag", data.getStringExtra(PaymentParams.CUSTOMER_EMAIL));
                Log.e("Tag", data.getStringExtra(PaymentParams.CUSTOMER_PASSWORD));
            }
        }
    }


    private void updateDateFrom() {

        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        txtDate.setText(sdf.format(mmyCalendar.getTime()));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_confirm)
    public void onViewClicked() {
        payment();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
