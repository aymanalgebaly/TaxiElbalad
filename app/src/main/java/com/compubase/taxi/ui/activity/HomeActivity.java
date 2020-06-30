package com.compubase.taxi.ui.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.compubase.taxi.R;
import com.compubase.taxi.ui.fragment.AboutFragment;
import com.compubase.taxi.ui.fragment.HomeFragment;
import com.compubase.taxi.ui.fragment.ProfileFragment;
import com.compubase.taxi.ui.fragment.TicketFragment;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {
    @BindView(R.id.call_support)
    ImageView callSupport;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.frame)
    FrameLayout frame;
    @BindView(R.id.nav_view)
    BottomNavigationView navView;
    @BindView(R.id.container)
    RelativeLayout container;
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    displaySelectedFragment(new HomeFragment());
//                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_add:
                    displaySelectedFragment(new TicketFragment());
//                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_info:
                    displaySelectedFragment(new AboutFragment());
//                    mTextMessage.setText(R.string.title_notifications);
                    return true;
//                case R.id.navigation_menu:
////                    mTextMessage.setText(R.string.title_notifications);
//                    return true;
                case R.id.navigation_profile:
                    displaySelectedFragment(new ProfileFragment());
//                    mTextMessage.setText(R.string.title_notifications);
                    return true;

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navView.setSelectedItemId(R.id.navigation_home);

    }

    public void displaySelectedFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.addToBackStack(null).commit();
    }

    @OnClick(R.id.call_support)
    public void onViewClicked() {
    }
}
