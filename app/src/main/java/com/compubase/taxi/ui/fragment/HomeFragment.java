package com.compubase.taxi.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
public class HomeFragment extends Fragment {


    @BindView(R.id.img_ticket)
    ImageView imgTicket;
    @BindView(R.id.img_profile)
    ImageView imgProfile;
    @BindView(R.id.img_rout)
    ImageView imgRout;
    @BindView(R.id.img_trip)
    ImageView imgTrip;
    private Unbinder unbinder;
    private HomeActivity homeActivity;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this,view);

        homeActivity = (HomeActivity) getActivity();

        return view;
    }

    @OnClick({R.id.img_ticket, R.id.img_profile, R.id.img_rout, R.id.img_trip})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_ticket:
                homeActivity.displaySelectedFragment(new TicketFragment());
                break;
            case R.id.img_profile:
                homeActivity.displaySelectedFragment(new ProfileFragment());
                break;
            case R.id.img_rout:
                homeActivity.displaySelectedFragment(new RoutFragment());
                break;
            case R.id.img_trip:
                homeActivity.displaySelectedFragment(new TripFragment());
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
