package net.sourceforge.UI.fragments;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chain.wallet.spd.R;

import net.sourceforge.UI.activity.ActivityDetail;
import net.sourceforge.UI.adapter.HomeFeatureAdapter;
import net.sourceforge.UI.view.InputWalletPasswordDialog;
import net.sourceforge.base.FragmentBase;
import net.sourceforge.commons.log.SWLog;
import net.sourceforge.http.model.HomeFeatureModel;
import net.sourceforge.http.model.WalletModel;
import net.sourceforge.manager.JumpMethod;
import net.sourceforge.manager.WalletManager;
import net.sourceforge.utils.DMG;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by terry.c on 06/03/2018.
 */

public class FragmentMore extends FragmentBase {

    public static final String TAG = FragmentMore.class.getSimpleName();

    private View curView = null;

    private Unbinder unbinder;

    @BindView(R.id.rl_home_features)
    public RecyclerView rl_home_features;

    private HomeFeatureAdapter homeFeatureAdapter;

    private InputWalletPasswordDialog inputWalletPasswordDialog;

    public static FragmentMore newInstance() {
        FragmentMore f = new FragmentMore();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (null != curView && curView.getParent()!=null) {
            ((ViewGroup) curView.getParent()).removeView(curView);
            return curView;
        }
        curView = inflater.inflate(R.layout.layout_more, null);
        unbinder = ButterKnife.bind(this, curView);
        initRes();
        return curView;
    }

    private void initRes() {
        GridLayoutManager layoutManage = new GridLayoutManager(getContext(), 3);
        rl_home_features.setLayoutManager(layoutManage);
        rl_home_features.setAdapter(homeFeatureAdapter = new HomeFeatureAdapter(R.layout.item_home_feature1));

        List<HomeFeatureModel> models = new ArrayList<>();
        models.add(new HomeFeatureModel("收款", R.drawable.ic_home_1));
        models.add(new HomeFeatureModel("转账", R.drawable.ic_home_10));
        models.add(new HomeFeatureModel("付款", R.drawable.ic_home_7));
        models.add(new HomeFeatureModel("买卖", R.drawable.ic_home_6));
        models.add(new HomeFeatureModel("理财", R.drawable.ic_home_5));
        models.add(new HomeFeatureModel("交易记录", R.drawable.ic_home_2));
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
//                        JumpMethod.jumpToDetail(mContext, "付款", ActivityDetail.PAGE_PAY);

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
                        JumpMethod.jumpToDetail(mContext, "交易记录", ActivityDetail.PAGE_TRANS_RECORD);
                    }
                    break;
                }
            }
        });
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

}
