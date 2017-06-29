package com.dobbby.kdietitian.util;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import com.dobbby.kdietitian.R;

/**
 * Created by Dobbby on 2017/6/22.
 */
public class WbToolbarPlugin {

    public static void attach(AppCompatActivity activity) {
        addToolbar(activity);
    }

    public static void setToolbarTitle(AppCompatActivity activity, CharSequence title) {
        TextView toolbarTitle = (TextView) activity.findViewById(R.id.toolbar_title);

        if (toolbarTitle == null) {
            throw new RuntimeException("did you call WbToolbarPlugin.attach() method before setting title?");
        }

        toolbarTitle.setText(title);
    }

    private static void addToolbar(final AppCompatActivity activity) {
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.base_toolbar);

        if (toolbar == null) {
            throw new RuntimeException("make sure " + activity.toString() + "'s layout file has included base_toolbar layout");
        }

        toolbar.setTitle("");
        activity.setSupportActionBar(toolbar);

        if (activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onBackPressed();
            }
        });
    }
}
