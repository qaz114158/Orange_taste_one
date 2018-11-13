package net.sourceforge.UI.fragments;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chain.wallet.spd.R;

import net.sourceforge.UI.adapter.HomeAssertsAdapter;
import net.sourceforge.UI.adapter.HomeFeatureAdapter;
import net.sourceforge.base.FragmentBase;
import net.sourceforge.commons.log.SWLog;
import net.sourceforge.http.model.HomeAssertModel;
import net.sourceforge.http.model.HomeFeatureModel;
import net.sourceforge.manager.JumpMethod;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by terry.c on 06/03/2018.
 */

public class FragmentWallet extends FragmentBase {

    public static final String TAG = FragmentWallet.class.getSimpleName();

    private View curView = null;

    private Unbinder unbinder;

    @BindView(R.id.rl_home_features)
    public RecyclerView rl_home_features;

    private HomeFeatureAdapter homeFeatureAdapter;

    @BindView(R.id.rl_home_asserts)
    public RecyclerView rl_home_asserts;

    private HomeAssertsAdapter homeAssertsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (null != curView && curView.getParent()!=null) {
            ((ViewGroup) curView.getParent()).removeView(curView);
            return curView;
        }
        curView = inflater.inflate(R.layout.layout_wallet, null);
        unbinder = ButterKnife.bind(this, curView);
        initResource();
        return curView;
    }

    private void initResource() {
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
        homeFeatureAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
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
                    }
                    break;
                    case 2:
                    {
                        //付款
                    }
                    break;
                    case 3:
                    {
                        //买卖
                    }
                    break;
                    case 4:
                    {
                        //理财
                    }
                    break;
                    case 5:
                    {
                        //更多
                    }
                    break;
                }
            }
        });

        List<HomeAssertModel> homeAssertModels = new ArrayList<>();
        homeAssertModels.add(new HomeAssertModel("ETH", R.drawable.ic_home_3,"100000", "2000000"));
        homeAssertModels.add(new HomeAssertModel("BTC", R.drawable.ic_home_3,"200000", "3000000"));
        homeAssertModels.add(new HomeAssertModel("USDT", R.drawable.ic_home_3,"300000", "4000000"));
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


}
