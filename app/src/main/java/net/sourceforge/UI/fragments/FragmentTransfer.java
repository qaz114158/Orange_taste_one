package net.sourceforge.UI.fragments;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.chain.wallet.spd.R;

import net.sourceforge.UI.activity.ActivityDetail;
import net.sourceforge.UI.model.WalletViewModel;
import net.sourceforge.UI.view.InputAmountDialog;
import net.sourceforge.UI.view.TransferDialog1;
import net.sourceforge.UI.view.TransferDialog2;
import net.sourceforge.base.FragmentBase;
import net.sourceforge.commons.log.SWLog;
import net.sourceforge.constants.Constants;
import net.sourceforge.http.engine.RetrofitClient;
import net.sourceforge.http.model.AddressModel;
import net.sourceforge.http.model.NodeModel;
import net.sourceforge.http.model.WalletBalanceModel;
import net.sourceforge.http.model.WalletModel;
import net.sourceforge.http.model.WalletNonceModel;
import net.sourceforge.http.model.WalletTransferModel;
import net.sourceforge.manager.JumpMethod;
import net.sourceforge.manager.WalletManager;
import net.sourceforge.utils.DMG;
import net.sourceforge.utils.GsonUtil;
import net.sourceforge.utils.PreferenceHelper;

import org.brewchain.bcapi.gens.Oentity;
import org.fc.fbc.sdk.FbcSDK;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.RequestBody;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by terry.c on 06/03/2018.
 */

public class FragmentTransfer extends FragmentBase {

    public static final String TAG = FragmentTransfer.class.getSimpleName();

    private View curView = null;

    private Unbinder unbinder;

    private TransferDialog1 transferDialog1;

    private TransferDialog2 transferDialog2;

    @BindView(R.id.tv_address)
    public EditText tv_address;

    @BindView(R.id.tv_money)
    public EditText tv_money;

    @BindView(R.id.tv_remark)
    public EditText tv_remark;
    private WalletViewModel walletViewModel;
    private WalletModel currentWallet;

    public static FragmentTransfer newInstance() {
        FragmentTransfer f = new FragmentTransfer();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (null != curView && curView.getParent()!=null) {
            ((ViewGroup) curView.getParent()).removeView(curView);
            return curView;
        }
        curView = inflater.inflate(R.layout.layout_transfer, null);
        unbinder = ButterKnife.bind(this, curView);
        walletViewModel = ViewModelProviders.of(getActivity()).get(WalletViewModel.class);
        getLifecycle().addObserver(walletViewModel);
        initDataObserver();
        return curView;
    }

    private void initDataObserver() {
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
        currentWallet = WalletManager.getInstance().getCurrentWallet();
        switch (currentWallet.walletType) {
            case FBC:
            {

                List fbcNodeLists = PreferenceHelper.getInstance().getObject(PreferenceHelper.PreferenceKey.KEY_FBC_NODE_LIST, List.class);
                NodeModel nodeModelResponse = (NodeModel) fbcNodeLists.get(0);
//                requestBalance("FBC", Constants.DAPP_ID,nodeModelResponse.node_url, currentWallet.address, " ");
            }
            break;
            case ETH:
            {
                List fbcNodeLists = PreferenceHelper.getInstance().getObject(PreferenceHelper.PreferenceKey.KEY_ETH_NODE_LIST, List.class);
                NodeModel nodeModelResponse = (NodeModel) fbcNodeLists.get(0);
//                requestBalance("ETH", Constants.DAPP_ID,nodeModelResponse.node_url, currentWallet.address, " ");
            }
            break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
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

        }
    }

    @OnClick(R.id.bt_next)
    public void onClickNext() {
        final String address = tv_address.getText().toString().trim();
        final String money = tv_money.getText().toString().trim();
        String remark = tv_remark.getText().toString().trim();

        if (TextUtils.isEmpty(address)) {
            DMG.showNomalShortToast("请输入转账地址");
            return;
        }
        if (TextUtils.isEmpty(money)) {
            DMG.showNomalShortToast("请输入转账金额");
            return;
        }

        transferDialog1 = new TransferDialog1(getActivity(), new TransferDialog1.IOnProtocolDialogClickListener() {
            @Override
            public void onClickBtn(boolean isConform) {
                transferDialog1.dismiss();
                if (isConform) {
                    showDialog2(address, money);
                }
            }
        });
        transferDialog1.setData(address, money, remark);
        transferDialog1.show();
    }

    private void showDialog2(final String address, final String money) {
        transferDialog2 = new TransferDialog2(getActivity(), new TransferDialog2.IOnProtocolDialogClickListener() {
            @Override
            public void onClickBtn(boolean isConform) {
                transferDialog2.dismiss();
                if (isConform) {
                    showProgressDialog("转账中，请稍等");
                    transferAccountsPreprocessing(address, money);
                }
            }
        });
        transferDialog2.show();
    }

    /**
     * 转账预处理（请求地址最新nonce）
     * @param chain_type
     * @param to_address 收款方地址
     * @param money 转账金额
     */
    private void requestProcessing(String chain_type, String to_address, String money) {
        if (chain_type.equalsIgnoreCase("FBC")) {
            List fbcNodeLists = PreferenceHelper.getInstance().getObject(PreferenceHelper.PreferenceKey.KEY_FBC_NODE_LIST, List.class);
            NodeModel nodeModelResponse = (NodeModel) fbcNodeLists.get(0);
            requestNonce("FBC", Constants.DAPP_ID,nodeModelResponse.node_url, currentWallet.address, to_address, money);
        } else if (chain_type.equalsIgnoreCase("ETH")) {
            List fbcNodeLists = PreferenceHelper.getInstance().getObject(PreferenceHelper.PreferenceKey.KEY_ETH_NODE_LIST, List.class);
            NodeModel nodeModelResponse = (NodeModel) fbcNodeLists.get(0);
            requestNonce("ETH", Constants.DAPP_ID,nodeModelResponse.node_url, currentWallet.address, to_address, money);
        }
    }

    /**
     * @param to_address 收款方地址
     * @param money 转账金额
     * @param chain_type
     * @param dappId
     * @param node_url
     * @param address
     */
    private void requestNonce(final String chain_type, final String dappId, final String node_url, final String address, final String to_address, final String money) {
        if (chain_type.equalsIgnoreCase("FBC")) {
            RetrofitClient.FBCNonceService apiService = RetrofitClient.getInstance().createRetrofit().create(RetrofitClient.FBCNonceService.class);
            Map<String, String> params = new HashMap<>();

            params.put("dapp_id", dappId);
            params.put("node_url", node_url);
            params.put("address", address);
            String json = GsonUtil.toJson(params);
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=utf-8"), json);
            retrofit2.Call<WalletNonceModel> call = apiService.requestFBCNonce(body);
            call.enqueue(new Callback<WalletNonceModel>() {
                @Override
                public void onResponse(retrofit2.Call<WalletNonceModel> call, Response<WalletNonceModel> response) {
                    if (net.sourceforge.utils.TextUtils.isResponseSuccess(response.body())) {
                        transfer(chain_type, dappId, node_url, address, "8c7e3bf67471eb4e8b0e2a9818aa02077ddd741f", money, response.body().nonce);
//                        transfer(chain_type, dappId, node_url, address, to_address, money, response.body().nonce);
                    } else {
                        // TODO
                    }
                }

                @Override
                public void onFailure(retrofit2.Call<WalletNonceModel> call, Throwable t) {

                }
            });
        } else if (chain_type.equalsIgnoreCase("ETH")) {
            RetrofitClient.ETHNonceService apiService = RetrofitClient.getInstance().createRetrofit().create(RetrofitClient.ETHNonceService.class);
            Map<String, String> params = new HashMap<>();

            params.put("dapp_id", dappId);
            params.put("node_url", node_url);
            params.put("address", address);
            String json = GsonUtil.toJson(params);
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=utf-8"), json);
            retrofit2.Call<WalletNonceModel> call = apiService.requestETHNonce(body);
            call.enqueue(new Callback<WalletNonceModel>() {
                @Override
                public void onResponse(retrofit2.Call<WalletNonceModel> call, Response<WalletNonceModel> response) {
                    if (net.sourceforge.utils.TextUtils.isResponseSuccess(response.body())) {

                    } else {
                        // TODO
                    }
                }

                @Override
                public void onFailure(retrofit2.Call<WalletNonceModel> call, Throwable t) {

                }
            });
        }
    }

    /**
     * 真实请求转账处理
     * @param chain_type
     * @param dappId
     * @param node_url
     * @param address
     * @param to_address
     * @param money
     */
    private void transfer(String chain_type, String dappId, String node_url, String address, String to_address, String money, String nonce) {
        Calendar cal = Calendar.getInstance();
        long timestamp = cal.getTimeInMillis() / 1000;
        WalletModel currentWallet = WalletManager.getInstance().getCurrentWallet();
        try {
            Oentity.KeyStoreValue from = FbcSDK.readKeyStoreContent(currentWallet.walletPassowrd, currentWallet.keystoreJson);
            String sign = FbcSDK.genSignedMessage(new BigInteger(nonce), from, to_address, new BigInteger(money), timestamp);
            if (chain_type.equalsIgnoreCase("FBC")) {
                RetrofitClient.FBCTransferService apiService = RetrofitClient.getInstance().createRetrofit().create(RetrofitClient.FBCTransferService.class);
                Map<String, String> params = new HashMap<>();

                params.put("dapp_id", dappId);
                params.put("node_url", node_url);
                params.put("nonce", nonce);
                params.put("from_addr", address);
                params.put("to_addr", to_address);
                params.put("value", money);
                params.put("signed_message", sign);
                params.put("gas_price", "");
                params.put("gas_limit", "");
                params.put("timestamp", timestamp + "");
                String json = GsonUtil.toJson(params);
                RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=utf-8"), json);
                retrofit2.Call<WalletTransferModel> call = apiService.requestFBCTransfer(body);
                call.enqueue(new Callback<WalletTransferModel>() {
                    @Override
                    public void onResponse(retrofit2.Call<WalletTransferModel> call, Response<WalletTransferModel> response) {
                        if (net.sourceforge.utils.TextUtils.isResponseSuccess(response.body())) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    DMG.showNomalShortToast("转账成功");
                                    hideProgressDialog();
                                    getActivity().finish();
                                }
                            }, 1000);
                        } else {
                            hideProgressDialog();
                            DMG.showNomalShortToast("转账失败，请重试");
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<WalletTransferModel> call, Throwable t) {

                    }
                });
            } else if (chain_type.equalsIgnoreCase("ETH")) {
                RetrofitClient.ETHTransferService apiService = RetrofitClient.getInstance().createRetrofit().create(RetrofitClient.ETHTransferService.class);
                Map<String, String> params = new HashMap<>();

                params.put("dapp_id", dappId);
                params.put("node_url", node_url);
                params.put("nonce", nonce);
                params.put("from_addr", address);
                params.put("to_addr", to_address);
                params.put("value", money);
                params.put("signed_message", "");
                params.put("gas_price", "");
                params.put("gas_limit", "");
                params.put("timestamp", timestamp + "");
                String json = GsonUtil.toJson(params);
                RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=utf-8"), json);
                retrofit2.Call<WalletTransferModel> call = apiService.requestETHTransfer(body);
                call.enqueue(new Callback<WalletTransferModel>() {
                    @Override
                    public void onResponse(retrofit2.Call<WalletTransferModel> call, Response<WalletTransferModel> response) {
                        if (net.sourceforge.utils.TextUtils.isResponseSuccess(response.body())) {
                            if (net.sourceforge.utils.TextUtils.isResponseSuccess(response.body())) {
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        DMG.showNomalShortToast("转账成功");
                                        hideProgressDialog();
                                        getActivity().finish();
                                    }
                                }, 1000);
                            } else {
                                hideProgressDialog();
                                DMG.showNomalShortToast("转账失败，请重试");
                            }
                        } else {
                            hideProgressDialog();
                            DMG.showNomalShortToast("转账失败，请重试");
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<WalletTransferModel> call, Throwable t) {
                        hideProgressDialog();
                        DMG.showNomalShortToast("转账失败，请重试");
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    /**
     * 转账预处理
     * @param to_address 收款方地址
     * @param money 转账金额
     */
    private void transferAccountsPreprocessing(String to_address, String money) {
        requestProcessing("FBC", to_address, money);
    }

    @OnClick(R.id.iv_choose)
    public void onClickChooseAddress() {
        Intent intent = new Intent(getActivity(), ActivityDetail.class);
        intent.putExtra(ActivityDetail.PARAM_TYPE_TITLE,"选择地址");
        intent.putExtra(ActivityDetail.PARAM_TYPE_CONTENT,ActivityDetail.PAGE_ADDRESS_BOOK_CHOOSE);
        startActivityForResult(intent, 1);
        ((Activity)mContext).overridePendingTransition(R.anim.fragment_slide_right_enter,
                R.anim.fragment_slide_left_exit);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {
            AddressModel addressModel = (AddressModel) data.getSerializableExtra("model");
            if (addressModel != null) {
                tv_address.setText(addressModel.address);
            }
        }
    }
}
