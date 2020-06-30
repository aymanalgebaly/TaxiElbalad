package com.compubase.taxi.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.compubase.taxi.R;
import com.compubase.taxi.ui.activity.HomeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class RoutFragment extends Fragment {


    @BindView(R.id.img_ticket_rout)
    ImageView imgTicketRout;
    @BindView(R.id.txt_note_two)
    TextView txtNoteTwo;
    @BindView(R.id.txt_click_two)
    TextView txtClickTwo;
    @BindView(R.id.lin_t)
    RelativeLayout linT;
    @BindView(R.id.txt_note_one)
    TextView txtNoteOne;
    @BindView(R.id.txt_click_one)
    TextView txtClickOne;
    @BindView(R.id.lin_one)
    RelativeLayout linOne;
    @BindView(R.id.txt_note)
    TextView txtNote;
    @BindView(R.id.txt_click_four)
    TextView txtClickFour;
    @BindView(R.id.lin_f)
    RelativeLayout linF;
    @BindView(R.id.txt_noteFour)
    TextView txtNoteFour;
    @BindView(R.id.txt_click_three)
    TextView txtClickThree;
    @BindView(R.id.lin_th)
    RelativeLayout linTh;
    private Unbinder unbinder;
    private HomeActivity homeActivity;

    public RoutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rout, container, false);
        unbinder = ButterKnife.bind(this, view);

        homeActivity = (HomeActivity) getActivity();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick({R.id.txt_click_two, R.id.txt_click_one, R.id.txt_click_four, R.id.txt_click_three})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_click_two:
                homeActivity.displaySelectedFragment(new RoutTwoFragment());
                break;
            case R.id.txt_click_one:
                homeActivity.displaySelectedFragment(new RoutOneFragment());
                break;
            case R.id.txt_click_four:
                homeActivity.displaySelectedFragment(new RoutFourFragment());
                break;
            case R.id.txt_click_three:
                homeActivity.displaySelectedFragment(new RoutThreeFragment());
                break;
        }
    }
}
