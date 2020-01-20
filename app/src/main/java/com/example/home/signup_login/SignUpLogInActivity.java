package com.example.home.signup_login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.home.GlobalVar;
import com.example.home.R;
import com.example.home.signup_login.login.LoginFragment;
import com.example.home.signup_login.signup.SignUpFragment;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpLogInActivity extends AppCompatActivity {
    private static final int NUM_PAGES = 2;
    private ViewPager mPager;

    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_login);

        // Instantiate a ViewPager and a PagerAdapter.

        GlobalVar.mAuth=FirebaseAuth.getInstance();

        mPager = findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(pagerAdapter);
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        GlobalVar.currUser = GlobalVar.mAuth.getCurrentUser();
        if(GlobalVar.currUser!=null)
            finish();
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 1:return new SignUpFragment();
                default:return new LoginFragment();
            }

        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

}
