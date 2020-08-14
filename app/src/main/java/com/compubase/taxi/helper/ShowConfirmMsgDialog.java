package com.compubase.taxi.helper;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.compubase.taxi.R;

public class ShowConfirmMsgDialog extends Dialog {

    public Button btn_ok;
    public AlertDialog builder;
    private EditText txt_error;

    public ShowConfirmMsgDialog(Context context) {
        super(context);
    }

    @SuppressLint("InflateParams")
    public void createDilog (Context context , String msg){

        builder = new AlertDialog.Builder(context).create();
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_confirm_code, null);
        builder.setView(view);
        builder.setCancelable(false);
        builder.setCanceledOnTouchOutside(false);

        txt_error = view.findViewById(R.id.ed_confirm_code);
        txt_error.setText(msg);
        btn_ok = view.findViewById(R.id.btn_confirm);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (txt_error.getText().toString().equals("")){
                    txt_error.setError("Confirm code is required");
                }else {
                    builder.dismiss();
                }
            }
        });

        builder.show();

    }
}
