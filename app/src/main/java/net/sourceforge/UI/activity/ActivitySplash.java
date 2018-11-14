package net.sourceforge.UI.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.chain.wallet.spd.R;

import net.sourceforge.base.ActivityBase;
import net.sourceforge.commons.log.SWLog;
import net.sourceforge.http.model.WalletModel;
import net.sourceforge.manager.WalletManager;
import net.sourceforge.utils.PreferenceHelper;

import java.util.List;

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
                if (checkExistWallet()) {
                    Intent intent = new Intent(mContext, ActivityMain.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(android.R.anim.fade_in,
                            android.R.anim.fade_out);
                } else {
                    Intent intent = new Intent(mContext, ActivityPreCreateAccount.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(android.R.anim.fade_in,
                            android.R.anim.fade_out);
                }
            }
        });
    }

    private boolean checkExistWallet() {
        List<WalletModel> walletModels = PreferenceHelper.getInstance().getObject(PreferenceHelper.PreferenceKey.KEY_WALLET_LIST, List.class);
        if (walletModels == null || walletModels.size() == 0) {
            WalletModel walletModel = new WalletModel();
            walletModel.address = "0x2323821787382183621863";
            walletModel.bcuId = "";
            walletModel.keystoreJson = "";
            walletModel.mnemonicStr = "";
            walletModel.pubKey = "wiuqheiuqwheiuhqw98129";
            WalletManager.getInstance().addWallet(walletModel);
            return true;
        }
        return true;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        SWLog.d("ActivitySplash", "call onDestroy()");
    }
}
