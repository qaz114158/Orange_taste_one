package net.sourceforge.UI.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.chain.wallet.spd.R;
import com.viewpagerindicator.CirclePageIndicator;

import net.sourceforge.UI.fragments.FragmentWelcome;
import net.sourceforge.base.ActivityBase;
import net.sourceforge.commons.log.SWLog;
import net.sourceforge.http.model.WalletModel;
import net.sourceforge.utils.PreferenceHelper;

import java.util.List;

/**
 * Created by terry.c on 06/03/2018.
 */

public class ActivitySplash extends ActivityBase {

    protected int[] resourceIds = new int[] { R.drawable.ic_app_74,
            R.drawable.ic_app_76};

    private WelcomeFragmentAdapter mAdapter;
    private ViewPager mPager;
    private CirclePageIndicator mIndicator;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_splash);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                enterMain();
//            }
//        }, 2000);
        mAdapter = new WelcomeFragmentAdapter(getSupportFragmentManager());
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
        mPager.setAdapter(mAdapter);
        mPager.setPageMargin(10);

        mIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
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
//            WalletModel walletModel = new WalletModel();
//            walletModel.address = "0x2323821787382183621863";
//            walletModel.bcuId = "";
//            walletModel.keystoreJson = "";
//            walletModel.mnemonicStr = "";
//            walletModel.pubKey = "wiuqheiuqwheiuhqw98129";
//            WalletManager.getInstance().addWallet(walletModel);
            return false;
        }
        return true;
    }

    private class WelcomeFragmentAdapter extends FragmentPagerAdapter {

        public WelcomeFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            boolean isShowIn = (position == resourceIds.length-1?true:false);
            return FragmentWelcome.newInstance(resourceIds[position
                    % resourceIds.length], isShowIn);
        }

        @Override
        public int getCount() {
            return resourceIds.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "";
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SWLog.d("ActivitySplash", "call onDestroy()");
    }
}
