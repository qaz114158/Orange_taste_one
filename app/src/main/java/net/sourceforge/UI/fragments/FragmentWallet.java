package net.sourceforge.UI.fragments;

import android.Manifest;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chain.wallet.spd.R;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import net.sourceforge.UI.activity.ActivityDetail;
import net.sourceforge.UI.activity.ActivityMain;
import net.sourceforge.UI.activity.CaptureQRActivity;
import net.sourceforge.UI.adapter.HomeAssertsAdapter;
import net.sourceforge.UI.adapter.HomeFeatureAdapter;
import net.sourceforge.UI.model.WalletViewModel;
import net.sourceforge.UI.view.InputWalletPasswordDialog;
import net.sourceforge.base.FragmentBase;
import net.sourceforge.commons.log.SWLog;
import net.sourceforge.constants.Constants;
import net.sourceforge.external.eventbus.events.EventAction;
import net.sourceforge.external.risenumber.RiseNumberTextView;
import net.sourceforge.http.engine.RetrofitClient;
import net.sourceforge.http.model.BaseResponse;
import net.sourceforge.http.model.HomeAssertModel;
import net.sourceforge.http.model.HomeFeatureModel;
import net.sourceforge.http.model.NodeModel;
import net.sourceforge.http.model.WalletBalanceModel;
import net.sourceforge.http.model.WalletModel;
import net.sourceforge.manager.JumpMethod;
import net.sourceforge.manager.WalletManager;
import net.sourceforge.utils.AppUtils;
import net.sourceforge.utils.DMG;
import net.sourceforge.utils.GsonUtil;
import net.sourceforge.utils.PreferenceHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;
import okhttp3.RequestBody;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by terry.c on 06/03/2018.
 */

public class FragmentWallet extends FragmentBase {

    public static final String TAG = FragmentWallet.class.getSimpleName();

    private View curView = null;

    private Unbinder unbinder;

    private WalletViewModel walletViewModel;

    @BindView(R.id.rl_home_features)
    public RecyclerView rl_home_features;

    private HomeFeatureAdapter homeFeatureAdapter;

    @BindView(R.id.rl_home_asserts)
    public RecyclerView rl_home_asserts;

    @BindView(R.id.tv_username)
    public TextView tv_username;

    @BindView(R.id.tv_address)
    public TextView tv_address;

    @BindView(R.id.tv_count)
    public RiseNumberTextView tv_count;

    @BindView(R.id.tv_count_cny)
    public TextView tv_count_cny;

    @BindView(R.id.tv_coin_type)
    public TextView tv_coin_type;

    @BindView(R.id.ll_assert)
    public LinearLayout ll_assert;

    private HomeAssertsAdapter homeAssertsAdapter;

    private boolean isShow = true;

    public static final int REQ_CODE_PERMISSION = 0x1111;

    public static final int REQUEST_CODE = 0x1112;

    private InputWalletPasswordDialog inputWalletPasswordDialog;

    private WalletModel currentWallet;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (null != curView && curView.getParent()!=null) {
            ((ViewGroup) curView.getParent()).removeView(curView);
            return curView;
        }
        curView = inflater.inflate(R.layout.layout_wallet, null);
        unbinder = ButterKnife.bind(this, curView);
        EventBus.getDefault().register(this);
        walletViewModel = ViewModelProviders.of(getActivity()).get(WalletViewModel.class);
        getLifecycle().addObserver(walletViewModel);
        initResource();
//        setData();
        initDataObserver();
        return curView;
    }

    public void requestBalance(String chain_type, String dapp_id, String node_url, String address, String contract_addr) {
        if (chain_type.equalsIgnoreCase("ETH")) {
            RetrofitClient.ETHBalanceService apiService = RetrofitClient.getInstance().createRetrofit().create(RetrofitClient.ETHBalanceService.class);
            Map<String, String> params = new HashMap<>();

            params.put("dapp_id", dapp_id);
            params.put("node_url", node_url);
            params.put("address", address);
            if (TextUtils.isEmpty(contract_addr) && !contract_addr.equalsIgnoreCase(" ")) {
                params.put("contract_addr", contract_addr);
            }
            String json = GsonUtil.toJson(params);
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=utf-8"), json);
            retrofit2.Call<WalletBalanceModel> call = apiService.requestETHBalance(body);
            call.enqueue(new Callback<WalletBalanceModel>() {
                @Override
                public void onResponse(retrofit2.Call<WalletBalanceModel> call, Response<WalletBalanceModel> response) {
                    if (net.sourceforge.utils.TextUtils.isResponseSuccess(response.body())) {
                        if (response.body().balance != null) {
                            tv_count.setText(response.body().balance);
                            tv_count_cny.setText("≈" + String.format("%.3f", currentWallet.balance_cny) + " CNY");
                        }
                    } else {
                        // TODO
                    }
                }

                @Override
                public void onFailure(retrofit2.Call<WalletBalanceModel> call, Throwable t) {

                }
            });
        } else if (chain_type.equalsIgnoreCase("FBC")) {
            RetrofitClient.FBCBalanceService apiService = RetrofitClient.getInstance().createRetrofit().create(RetrofitClient.FBCBalanceService.class);
            Map<String, String> params = new HashMap<>();

            params.put("dapp_id", dapp_id);
            params.put("node_url", node_url);
            params.put("address", address);
            if (TextUtils.isEmpty(contract_addr) && !contract_addr.equalsIgnoreCase(" ")) {
                params.put("contract_addr", contract_addr);
            }
            String json = GsonUtil.toJson(params);
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=utf-8"), json);
            retrofit2.Call<WalletBalanceModel> call = apiService.requestFBCBalance(body);
            call.enqueue(new Callback<WalletBalanceModel>() {
                @Override
                public void onResponse(retrofit2.Call<WalletBalanceModel> call, Response<WalletBalanceModel> response) {
                    if (net.sourceforge.utils.TextUtils.isResponseSuccess(response.body())) {
                        if (response.body().balance != null) {
                            tv_count.setText(response.body().balance);
                            tv_count_cny.setText("≈" + String.format("%.3f", currentWallet.balance_cny) + " CNY");
                        }
                    } else {
                        // TODO
                    }
                }

                @Override
                public void onFailure(retrofit2.Call<WalletBalanceModel> call, Throwable t) {

                }
            });
        }


    }

    private void setData() {
        currentWallet = WalletManager.getInstance().getCurrentWallet();
        switch (currentWallet.walletType) {
            case FBC:
            {
                tv_coin_type.setText("Total：SPDT");
                rl_home_features.setVisibility(View.VISIBLE);
                rl_home_asserts.setVisibility(View.GONE);
                ll_assert.setVisibility(View.GONE);

                List fbcNodeLists = PreferenceHelper.getInstance().getObject(PreferenceHelper.PreferenceKey.KEY_FBC_NODE_LIST, List.class);
                NodeModel nodeModelResponse = (NodeModel) fbcNodeLists.get(0);
                requestBalance("FBC", Constants.DAPP_ID,nodeModelResponse.node_url, currentWallet.address, " ");
            }
            break;
            case ETH:
            {
                tv_coin_type.setText("Total：ETH");
                rl_home_features.setVisibility(View.GONE);
                rl_home_asserts.setVisibility(View.VISIBLE);
                ll_assert.setVisibility(View.VISIBLE);
                List fbcNodeLists = PreferenceHelper.getInstance().getObject(PreferenceHelper.PreferenceKey.KEY_ETH_NODE_LIST, List.class);
                NodeModel nodeModelResponse = (NodeModel) fbcNodeLists.get(0);
                requestBalance("ETH", Constants.DAPP_ID,nodeModelResponse.node_url, currentWallet.address, " ");
            }
            break;
        }
        tv_username.setText(currentWallet.walletId);
        tv_address.setText(currentWallet.address);
/*        currentWallet = WalletManager.getInstance().getCurrentWallet();
        if (currentWallet != null) {
            switch (currentWallet.walletType) {
                case FBC:
                {
                    tv_coin_type.setText("Total：SPDT");
                    rl_home_features.setVisibility(View.VISIBLE);
                    rl_home_asserts.setVisibility(View.GONE);
                    ll_assert.setVisibility(View.GONE);
                }
                    break;
                case ETH:
                {
                    tv_coin_type.setText("Total：ETH");
                    rl_home_features.setVisibility(View.GONE);
                    rl_home_asserts.setVisibility(View.VISIBLE);
                    ll_assert.setVisibility(View.VISIBLE);
                }
                    break;
            }
            tv_username.setText(currentWallet.walletId);
            tv_address.setText(currentWallet.address);
            tv_count.withNumber(currentWallet.balance).start();
//            tv_count.setText(String.valueOf(walletModel.balance));
            tv_count_cny.setText("≈" + String.format("%.3f", currentWallet.balance_cny) + " CNY");
        }*/
    }

    private void initResource() {
//        Drawable left=getResources().getDrawable(R.drawable.ic_home_4);
//        left.setBounds(0,0,128,66);
//        tv_count.setCompoundDrawables(null,null ,left,null);

        GridLayoutManager layoutManage = new GridLayoutManager(getContext(), 3);
        rl_home_features.setLayoutManager(layoutManage);
        rl_home_features.setAdapter(homeFeatureAdapter = new HomeFeatureAdapter(R.layout.item_home_feature));

        List<HomeFeatureModel> models = new ArrayList<>();
        models.add(new HomeFeatureModel("收款", R.drawable.ic_home_1));
        models.add(new HomeFeatureModel("转账", R.drawable.ic_home_10));
        models.add(new HomeFeatureModel("付款", R.drawable.ic_home_7));
        models.add(new HomeFeatureModel("买卖", R.drawable.ic_home_6));
        models.add(new HomeFeatureModel("理财", R.drawable.ic_home_5));
        models.add(new HomeFeatureModel("更多", R.drawable.ic_home_2));
        homeFeatureAdapter.setNewData(models);
        homeFeatureAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position) {
                    case 0:
                    {
                        //收款
                        JumpMethod.jumpToReceipt(mContext);
                    }
                    break;
                    case 1:
                    {
                        //转账
                        JumpMethod.jumpToDetail(mContext, "转账", ActivityDetail.PAGE_TRANSFER);
                    }
                    break;
                    case 2:
                    {
                        //付款
                        getPasswordDialog().setOnProtocolDialogClickListener(new InputWalletPasswordDialog.IOnProtocolDialogClickListener() {
                            @Override
                            public void onClickBtn(boolean isConform, String password) {
                                getPasswordDialog().dismiss();
                                WalletModel walletModel = WalletManager.getInstance().getCurrentWallet();
                                if (isConform) {
                                    if (walletModel.walletPassowrd.equals(password)) {
                                        JumpMethod.jumpToDetail(mContext, "付款", ActivityDetail.PAGE_PAY);
                                    } else {
                                        DMG.showNomalShortToast("密码错误，请重新输入");
                                    }
                                }
                            }
                        });
                        getPasswordDialog().show();

                    }
                    break;
                    case 3:
                    {
                        //买卖
                        JumpMethod.jumpToDetail(mContext, "买卖", ActivityDetail.PAGE_BUSSINESS);
                    }
                    break;
                    case 4:
                    {
                        //理财
//                        DMG.showNomalShortToast("暂未开通");
                        JumpMethod.jumpToDetail(mContext, "理财", ActivityDetail.PAGE_LICAI);
                    }
                    break;
                    case 5:
                    {
                        //更多
                        JumpMethod.jumpToDetail(mContext, "", ActivityDetail.PAGE_MORE);
                    }
                    break;
                }
            }
        });

        List<HomeAssertModel> homeAssertModels = new ArrayList<>();
        homeAssertModels.add(new HomeAssertModel("ETH", R.drawable.ic_home_3,"460.0000", "681490.00"));
        homeAssertModels.add(new HomeAssertModel("BTC", R.drawable.ic_home_3,"1.0162", "42680.40"));
        homeAssertModels.add(new HomeAssertModel("USDT", R.drawable.ic_home_3,"325.0000", "1950.00"));
        homeAssertModels.add(new HomeAssertModel("", R.drawable.ic_home_3,"300000", "4000000"));
//        homeAssertModels.add(new HomeAssertModel("", R.drawable.ic_home_3,"300000", "4000000"));


        rl_home_asserts.setNestedScrollingEnabled(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rl_home_asserts.setLayoutManager(linearLayoutManager);
        rl_home_asserts.setAdapter(homeAssertsAdapter = new HomeAssertsAdapter(R.layout.item_home_assert));
        homeAssertsAdapter.setNewData(homeAssertModels);
    }

    @Override
    public void onResume() {
        super.onResume();
        setData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            SWLog.d(TAG, "call onHiddenChanged():" + hidden);
            //todo
            setData();
        }
    }

    @OnClick(R.id.ib_scan)
    public void onClickScanBtn() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.VIBRATE) != PackageManager.PERMISSION_GRANTED) {
            // Do not have the permission of camera, request it.
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.VIBRATE}, REQ_CODE_PERMISSION);
        } else {
            // Have gotten the permission
            Intent intent = new Intent(getActivity(), CaptureQRActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
//                    Toast.makeText(getActivity(), "解析结果:" + result, Toast.LENGTH_LONG).show();
//                    et_address.setText(result);
                    checkScanResult(result);


                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
//                    Toast.makeText(getActivity(), "解析二维码失败", Toast.LENGTH_LONG).show();
                    DMG.showNomalShortToast("解析二维码失败");
                }
            }
        }
    }

    private void checkScanResult(String scanResult) {
        if (!TextUtils.isEmpty(scanResult)) {
            String[] strings = scanResult.split(":");
            if (strings!=null && strings.length >0) {
                if ("shoukuan".equalsIgnoreCase(strings[0])) {
                    float value = Float.parseFloat(strings[1]);
                    WalletModel walletModel =  WalletManager.getInstance().getCurrentWallet();
                    if (walletModel.balance_cny <value) {
                        DMG.showNomalShortToast("余额不足，付款失败");
                        return;
                    }
                    walletModel.balance = walletModel.balance-value;
                    walletModel.balance_cny = walletModel.balance_cny - value;
                    WalletManager.getInstance().updateWalletBalance(walletModel);
                    setData();
                    DMG.showNomalShortToast("付款成功");
                } else if ("fukuan".equalsIgnoreCase(strings[0])) {
                    float value = Float.parseFloat(strings[1]);
                    WalletModel walletModel =  WalletManager.getInstance().getCurrentWallet();
                    walletModel.balance = walletModel.balance+value;
                    walletModel.balance_cny = walletModel.balance_cny + value;
                    WalletManager.getInstance().updateWalletBalance(walletModel);
                    setData();
                    DMG.showNomalShortToast("收款成功");
                }
            } else {
                DMG.showNomalShortToast("无法识别的二维码");
            }
        } else {
            DMG.showNomalShortToast("无法识别的二维码");
        }
    }

    @OnClick(R.id.tv_address)
    public void onClickAddress() {
        AppUtils.clipboardString(mContext, tv_address.getText().toString());
    }


    @OnClick(R.id.ib_hide_assert)
    public void onCLickShowHideAssert() {
        isShow = !isShow;
        if (isShow) {

            tv_count.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            tv_count_cny.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            tv_count.setTransformationMethod(PasswordTransformationMethod.getInstance());
            tv_count_cny.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    private InputWalletPasswordDialog getPasswordDialog() {
        if (inputWalletPasswordDialog ==null) {
            inputWalletPasswordDialog = new InputWalletPasswordDialog(mContext, new InputWalletPasswordDialog.IOnProtocolDialogClickListener() {
                @Override
                public void onClickBtn(boolean isConform, String password) {

                }
            });
        }
        inputWalletPasswordDialog.resetStatu();
        return inputWalletPasswordDialog;
    }

    private void initDataObserver() {
        walletViewModel.getNodeModelResponse().observe(getActivity(), new Observer<List<NodeModel>>() {
            @Override
            public void onChanged(@Nullable List<NodeModel> models) {
                if (models != null && models.size() >0) {
                    SWLog.d(TAG(), "models in fragment size:" + models.size());

                }
            }
        });
        walletViewModel.getFBCNodeModelResponse().observe(getActivity(), new Observer<List<NodeModel>>() {
            @Override
            public void onChanged(@Nullable List<NodeModel> models) {
                if (models != null && models.size() >0) {
                    SWLog.d(TAG(), "models in fragment size:" + models.size());
                }
            }
        });
        walletViewModel.getETHNodeModelResponse().observe(getActivity(), new Observer<List<NodeModel>>() {
            @Override
            public void onChanged(@Nullable List<NodeModel> models) {
                if (models != null && models.size() >0) {
                    SWLog.d(TAG(), "models in fragment size:" + models.size());
                }
            }
        });
//        SWLog.d(TAG(), "models in fragment getNodelist:" + (walletViewModel.getNodeList() == null?"000":walletViewModel.getNodeList().size()));
        walletViewModel.requestNodeList("ETH", "dev");
        walletViewModel.requestNodeList("FBC", "dev");
    }

    @OnClick(R.id.bt_switch_wallet)
    public void onClickSwitchWallet() {
        JumpMethod.jumpToDetail(getActivity(), "切换钱包", ActivityDetail.PAGE_SWITCH_WALLET);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventAction messageEvent){
        String tag = messageEvent.getMessageTag();
        if (tag.equalsIgnoreCase(EventAction.EventKey.KEY_WALLET_SWICH)) {
            currentWallet = WalletManager.getInstance().getCurrentWallet();
            setData();
        }
    }


}
