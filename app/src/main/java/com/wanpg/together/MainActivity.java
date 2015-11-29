package com.wanpg.together;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.wanpg.together.base.BaseActivity;
import com.wanpg.together.home.HomeFragment;

public class MainActivity extends BaseActivity {

    View tab_home,tab_notice,tab_travel,tab_me;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        tab_home = findViewById(R.id.tab_home);
        tab_notice = findViewById(R.id.tab_notice);
        tab_travel = findViewById(R.id.tab_travel);
        tab_me = findViewById(R.id.tab_me);

        tab_home.setOnClickListener(mTabMenuClickListener);
        tab_notice.setOnClickListener(mTabMenuClickListener);
        tab_travel.setOnClickListener(mTabMenuClickListener);
        tab_me.setOnClickListener(mTabMenuClickListener);
        showHomeFragment();
    }

    private View.OnClickListener mTabMenuClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            tab_home.setEnabled(true);
            tab_notice.setEnabled(true);
            tab_travel.setEnabled(true);
            tab_me.setEnabled(true);
            if(v.getId() == R.id.tab_home){
                tab_home.setEnabled(false);
                showHomeFragment();
            }else if(v.getId() == R.id.tab_notice){
                tab_notice.setEnabled(false);
                showNoticeFragment();
            }else if(v.getId() == R.id.tab_travel){
                tab_travel.setEnabled(false);
                showTravelListFragment();
            }else if(v.getId() == R.id.tab_me){
                tab_me.setEnabled(false);
                showUserInfoFragment();
            }
        }
    };

    private HomeFragment mHomeFragment;
    private void showHomeFragment(){
        FragmentManager fm = getSupportFragmentManager();
        if(mHomeFragment==null){
            mHomeFragment = new HomeFragment();
        }
        fm.beginTransaction().replace(R.id.realtabcontent, mHomeFragment, "home").commit();

        getSupportActionBar().setTitle("主页");
    }

    private NotificationFragment mNoticeFragment;
    private void showNoticeFragment(){
        FragmentManager fm = getSupportFragmentManager();
        if(mNoticeFragment==null){
            mNoticeFragment = new NotificationFragment();
        }
        fm.beginTransaction().replace(R.id.realtabcontent, mNoticeFragment, "notification").commit();

        getSupportActionBar().setTitle("消息");
    }


    private MyTravelListFragment mTravelListFragment;
    private void showTravelListFragment(){
        FragmentManager fm = getSupportFragmentManager();
        if(mTravelListFragment==null){
            mTravelListFragment = new MyTravelListFragment();
        }
        fm.beginTransaction().replace(R.id.realtabcontent, mTravelListFragment, "travellist").commit();

        getSupportActionBar().setTitle("行程");




    }


    private UserInfoFragment mUserInfoFragment;
    private void showUserInfoFragment(){
        FragmentManager fm = getSupportFragmentManager();
        if(mUserInfoFragment==null){
            mUserInfoFragment = new UserInfoFragment();
        }
        fm.beginTransaction().replace(R.id.realtabcontent, mUserInfoFragment, "userinfo").commit();

        getSupportActionBar().setTitle("我");
    }

}
