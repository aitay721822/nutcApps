package com.nutcunofficial.nutcapps.Home.Activitys;

import android.graphics.Bitmap;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nutcunofficial.nutcapps.Base.BaseActivity;
import com.nutcunofficial.nutcapps.Base.BaseFragment;
import com.nutcunofficial.nutcapps.Home.Adapters.viewPager;
import com.nutcunofficial.nutcapps.Home.Contracts.SignInContract;
import com.nutcunofficial.nutcapps.Home.Fragments.AbsentFragment;
import com.nutcunofficial.nutcapps.Home.Fragments.GradeFragment;
import com.nutcunofficial.nutcapps.Home.Fragments.HomeFragment;
import com.nutcunofficial.nutcapps.Home.Fragments.userFragment;
import com.nutcunofficial.nutcapps.Home.Presenters.SignInPresenter;
import com.nutcunofficial.nutcapps.R;
import com.nutcunofficial.nutcapps.util.nutcStatusCode;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener,ViewPager.OnPageChangeListener, SignInContract.View {

    private static final String TAG = HomeActivity.class.getSimpleName();

    private static Handler mHandler = new Handler(Looper.getMainLooper());

    private FragmentManager fragmentManager;

    private List<Class<?>> fragments;

    //private FrameLayout mViewPaper;

    private SignInPresenter presenter;

    BottomNavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new SignInPresenter(this);

        setContentView(R.layout.activity_home);

        fragmentManager = getSupportFragmentManager();

        //mViewPaper = findViewById(R.id.viewPager);
        //mViewPaper.addOnPageChangeListener(this);

        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(this);

        //viewPager viewPagerAdapter = new viewPager(fragmentManager,viewPager.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        //viewPagerAdapter.add(HomeFragment.newInstance());
        //viewPagerAdapter.add(GradeFragment.newInstance());
        //viewPagerAdapter.add(AbsentFragment.newInstance());
        //viewPagerAdapter.add(userFragment.newInstance());

        //mViewPaper.setOffscreenPageLimit(1);
        //mViewPaper.setAdapter(viewPagerAdapter);

        fragments = new ArrayList<>();

        fragments.add(HomeFragment.class);
        fragments.add(GradeFragment.class);
        fragments.add(AbsentFragment.class);
        fragments.add(userFragment.class);

        if (navView.getMenu().size() > 0 && savedInstanceState == null) {
            navView.setSelectedItemId(R.id.navigation_home);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        presenter.checkSignIn();
        super.onResume();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        //mViewPaper.setCurrentItem(menuItem.getOrder());

        BaseFragment selected = null;

        try {
            selected = (BaseFragment)fragments.get(menuItem.getOrder()).newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        if(selected != null ) {
            changeFragment(selected,selected.getClass().getSimpleName());
            return true;
        }else{
            return false;
        }
    }

    public void changeFragment(Fragment fragment, String tagFragmentName) {

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment currentFragment = fragmentManager.getPrimaryNavigationFragment();
        Fragment fragmentTemp = fragmentManager.findFragmentByTag(tagFragmentName);

        if (currentFragment != null) {
            transaction.hide(currentFragment);
        }

        if (fragmentTemp == null) {
            fragmentTemp = fragment;
            transaction.add(R.id.frameLayout, fragmentTemp, tagFragmentName);
        } else {
            transaction.show(fragmentTemp);
            fragmentTemp.onResume();
        }

        transaction.setPrimaryNavigationFragment(fragmentTemp);
        transaction.commitNowAllowingStateLoss();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        navView.getMenu().getItem(position).setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        // DO STH YOU LIKE
    }

    @Override
    public void showSignInStatus(final int SignInStatus) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if(SignInStatus == nutcStatusCode.NOT_SIGN_IN || SignInStatus == nutcStatusCode.SIGN_IN_FAILURE || SignInStatus == nutcStatusCode.NO_INTERNET_ERROR_CODE) {
                    Log.d(TAG,">> is Redirected SIGN_IN_ACTIVITY >> ");
                    start(SignInActivity.class);
                }
                else if(SignInStatus == nutcStatusCode.NEED_CHANGE_PASS) {
                    Log.d(TAG,">> is Redirected CHANGE_PASSWORD_CLASS >> ");
                    start(ChangePassword.class);
                }
                Log.d(TAG,">> is executed Sign In Status Checker >> ");
            }
        });
    }

    @Override
    public void showValidationCode(Bitmap bitmap) {
        // DO STH YOU LIKE
    }

    @Override
    public void showValidationError(int errorCode) {
        // DO STH YOU LIKE
    }

    @Override
    public void showResetPasswordSend(String msg) {
        // DO STH YOU LIKE
    }

    @Override
    public void showProgress(final boolean isShow) {
        if (isShow)
            showProgressDialog();
        else
            hideProgressDialog();
    }
}