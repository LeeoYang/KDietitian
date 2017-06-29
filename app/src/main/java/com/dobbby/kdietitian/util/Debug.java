package com.dobbby.kdietitian.util;

import android.view.View;
import com.dobbby.kdietitian.KDApplication;

/**
 * Created by Dobbby on 2017/6/29.
 */
public final class Debug {
    /**
     * This variable shows if the current program is on debug mode.
     */
    public static boolean DEBUG = false;

    /**
     * Simply set {@code ALLOW_DEBUG} to false to fully disable
     * the debug controller provided by this class.
     */
    private static final boolean ALLOW_DEBUG = true;

    /**
     * Count so that the controller may know when to enable/disable
     * debug mode.
     */
    private static int enableDebugClickCount = 0;
    private static int disableDebugClickCount = 0;

    /**
     * Click a view 6 times to enable/disable debug mode.
     */
    private static final int DEBUG_CLICK_COUNT = 6;

    private static boolean enableDebug() {
        if (!ALLOW_DEBUG) {
            return false;
        }

        if (++enableDebugClickCount >= DEBUG_CLICK_COUNT) {
            enableDebugClickCount = 0;
            DEBUG = true;
            WbToastFactory.create(KDApplication.getInstance(), "DEBUG MODE ON").show();
            return true;
        }

        return false;
    }

    private static boolean disableDebug() {
        if (!ALLOW_DEBUG) {
            return false;
        }

        if (++disableDebugClickCount >= DEBUG_CLICK_COUNT) {
            disableDebugClickCount = 0;
            DEBUG = false;
            WbToastFactory.create(KDApplication.getInstance(), "DEBUG MODE OFF").show();
            return true;
        }

        return false;
    }

    /**
     * The method will add a controller to a view, so that simple
     * clicking on the view can set off debug features.
     *
     * @param view the view that holds control.
     */
    public static void attachDebugController(final View view) {
        if (view == null) {
            return;
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DEBUG) {
                    disableDebug();
                } else {
                    enableDebug();
                }
            }
        });
    }
}
