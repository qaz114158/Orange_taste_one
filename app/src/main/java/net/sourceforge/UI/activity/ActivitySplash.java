package net.sourceforge.UI.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.chain.wallet.spd.R;

import net.sourceforge.base.ActivityBase;
import net.sourceforge.commons.log.SWLog;

/**
 * Created by terry.c on 06/03/2018.
 */

public class ActivitySplash extends ActivityBase {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                enterMain();
            }
        }, 2000);

    }

    public void enterMain() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(mContext, ActivityMain.class);
                startActivity(intent);
                finish();
                overridePendingTransition(android.R.anim.fade_in,
                        android.R.anim.fade_out);
            }
        });
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        SWLog.d("ActivitySplash", "call onDestroy()");
    }
}
