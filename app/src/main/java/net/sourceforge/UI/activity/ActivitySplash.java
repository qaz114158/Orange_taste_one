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
import net.sourceforge.constants.Constants;
import net.sourceforge.http.engine.RetrofitClient;
import net.sourceforge.http.model.NodeModel;
import net.sourceforge.http.model.WalletModel;
import net.sourceforge.utils.GsonUtil;
import net.sourceforge.utils.PreferenceHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Callback;
import retrofit2.Response;

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
        requestNodeList("ETH","dev");
        requestNodeList("FBC","dev");
    }

    public void requestNodeList(String chainType, String nodeNet) {
        if (chainType.equalsIgnoreCase("ETH")) {
            RetrofitClient.ETHNodeListService apiService = RetrofitClient.getInstance().createRetrofit().create(RetrofitClient.ETHNodeListService.class);
            Map<String, String> params = new HashMap<>();

            params.put("dapp_id", Constants.DAPP_ID);
            params.put("node_net", nodeNet);
            String json = GsonUtil.toJson(params);
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=utf-8"), json);
            retrofit2.Call<NodeModel.NodeModelResponse> call = apiService.requestNodeList(body);
            call.enqueue(new Callback<NodeModel.NodeModelResponse>() {
                @Override
                public void onResponse(retrofit2.Call<NodeModel.NodeModelResponse> call, Response<NodeModel.NodeModelResponse> response) {
                    if (net.sourceforge.utils.TextUtils.isResponseSuccess(response.body())) {
                        PreferenceHelper.getInstance().setObject(PreferenceHelper.PreferenceKey.KEY_ETH_NODE_LIST, response.body().dev_net);
                    } else {
                    }
                }

                @Override
                public void onFailure(retrofit2.Call<NodeModel.NodeModelResponse> call, Throwable t) {

                }
            });
        } else if (chainType.equalsIgnoreCase("FBC")) {
            RetrofitClient.APIService apiService = RetrofitClient.getInstance().createRetrofit().create(RetrofitClient.APIService.class);
            Map<String, String> params = new HashMap<>();

            params.put("dapp_id", Constants.DAPP_ID);
            params.put("node_net", nodeNet);
            String json = GsonUtil.toJson(params);
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=utf-8"), json);
            retrofit2.Call<NodeModel.NodeModelResponse> call = apiService.requestNodeList(body);
            call.enqueue(new Callback<NodeModel.NodeModelResponse>() {
                @Override
                public void onResponse(retrofit2.Call<NodeModel.NodeModelResponse> call, Response<NodeModel.NodeModelResponse> response) {
                    if (net.sourceforge.utils.TextUtils.isResponseSuccess(response.body())) {
                        PreferenceHelper.getInstance().setObject(PreferenceHelper.PreferenceKey.KEY_FBC_NODE_LIST, response.body().dev_net);
                    } else {

                    }
                }

                @Override
                public void onFailure(retrofit2.Call<NodeModel.NodeModelResponse> call, Throwable t) {

                }
            });
        }
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
