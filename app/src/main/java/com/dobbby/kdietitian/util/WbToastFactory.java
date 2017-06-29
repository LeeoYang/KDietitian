package com.dobbby.kdietitian.util;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.dobbby.kdietitian.R;

/**
 * Created by Dobbby on 2017/6/20.
 */
public class WbToastFactory {

    public static Toast create(Context context, CharSequence text) {
        Context c = context.getApplicationContext();

        Toast toast = new Toast(c);

        View v = LayoutInflater.from(c).inflate(R.layout.toast_layout, null);
        ((TextView) v.findViewById(R.id.toast_text)).setText(text);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            v.setElevation(6);
        }

        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(v);

        return toast;
    }
}
