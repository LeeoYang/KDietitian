package com.dobbby.kdietitian.normal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.dobbby.kdietitian.R;
import com.dobbby.kdietitian.login.LoginActivity;
import com.dobbby.kdietitian.util.KDNavigationBar;

public class UserMainActivity extends AppCompatActivity implements KDNavigationBar.OnNavigationItemClickedListener {
    private KDNavigationBar kdNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);
        LoginActivity.finishIfStarted();
        kdNavigationBar = KDNavigationBar.newInstance(this, this);
    }

    @Override
    protected void onDestroy() {
        kdNavigationBar = null;
        super.onDestroy();
    }

    @Override
    public void onHomeSelected() {

    }

    @Override
    public void onFriendsSelected() {

    }

    @Override
    public void onShopSelected() {

    }

    @Override
    public void onMeSelected() {

    }

    @Override
    public void onActionSelected() {
        startActivity(new Intent(this, UserActionActivity.class));
    }
}
