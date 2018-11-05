package net.sourceforge.utils;

import android.widget.Toast;

import net.sourceforge.application.AppApplication;


/**
 * Created by terry on 11/11/16.
 */

public class DMG{

    public static void showNomalShortToast(String message) {
        Toast.makeText(AppApplication.getInstance(), message, Toast.LENGTH_SHORT).show();
    }

    public static void showNomalLongToast(String message) {
        Toast.makeText(AppApplication.getInstance(), message, Toast.LENGTH_LONG).show();
    }

}
