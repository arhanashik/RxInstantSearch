package com.blackspider.util.helper;
/*
 *  ****************************************************************************
 *  * Created by : Arhan Ashik on 10/17/2018 at 12:36 PM.
 *  * Email : ashik.pstu.cse@gmail.com
 *  *
 *  * Last edited by : Arhan Ashik on 10/17/2018.
 *  *
 *  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>
 *  ****************************************************************************
 */

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;

public class AndroidUtil {
    public static void whiteNotificationBar(Activity activity, View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            activity.getWindow().setStatusBarColor(Color.WHITE);
        }
    }
}
