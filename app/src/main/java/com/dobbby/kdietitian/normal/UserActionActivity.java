package com.dobbby.kdietitian.normal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.dobbby.kdietitian.R;

public class UserActionActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_action);

        init();
    }

    private void init() {
        findViewById(R.id.close).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close:
                finish();
                break;
            default:
                break;
        }
    }
}
