package net.sourceforge.UI.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chain.wallet.spd.R;

import net.sourceforge.UI.adapter.BussnessBuyAdapter;
import net.sourceforge.UI.adapter.TransRecordAdapter;
import net.sourceforge.UI.view.BussnessBuyDialog;
import net.sourceforge.UI.view.InputAmountDialog;
import net.sourceforge.base.FragmentBase;
import net.sourceforge.commons.log.SWLog;
import net.sourceforge.http.engine.RetrofitClient;
import net.sourceforge.http.engine.RetrofitClientOTC;
import net.sourceforge.http.model.BussnessModel;
import net.sourceforge.http.model.NodeModel;
import net.sourceforge.http.model.TransRecordModel;
import net.sourceforge.utils.DMG;
import net.sourceforge.utils.GsonUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.RequestBody;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by terry.c on 06/03/2018.
 */

public class FragmentBussnessBuy extends FragmentBase {

    public static final String TAG = FragmentBussnessBuy.class.getSimpleName();

    private View curView = null;

    private Unbinder unbinder;

    @BindView(R.id.rl_recycler)
    public RecyclerView rl_recycler;

    private BussnessBuyAdapter adapter;

    private BussnessBuyDialog dialog;

    public static FragmentBussnessBuy newInstance() {
        FragmentBussnessBuy f = new FragmentBussnessBuy();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (null != curView && curView.getParent()!=null) {
            ((ViewGroup) curView.getParent()).removeView(curView);
            return curView;
        }
        curView = inflater.inflate(R.layout.layout_bussness_buy, null);
        unbinder = ButterKnife.bind(this, curView);
        initRes();
        return curView;
    }

    private void initRes() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rl_recycler.setLayoutManager(layoutManager);
        rl_recycler.setAdapter(adapter = new BussnessBuyAdapter(R.layout.item_bussness_buy));

        List<BussnessModel> models = new ArrayList<>();
        models.add(new BussnessModel(1, "NO10061", "0", "5/11/2019", "0.88", "20000.0000", "20000.0000", "20000.0000"));
        models.add(new BussnessModel(1, "NO10062", "10", "7/8/2019", "0.91", "10000.0000", "30000.0000", "30000.0000"));
        models.add(new BussnessModel(1, "NO10063", "231", "10/15/2019", "1.88", "20000.0000", "70000.0000", "100000.0000"));
//        models.add(new TransRecordModel("0x0543b4e1186…4d9f72",0, "-59.12SPDT", 1));
//        models.add(new TransRecordModel("0x0543b4e1186…4d9f72",0, "-390.00SPDT", 1));
//        models.add(new TransRecordModel("0x0543b4e1186…4d9f72",0, "+12000SPDT", 2));
//        models.add(new TransRecordModel("0x0543b4e1186…4d9f72",1, "-6700SPDT", 1));
//        models.add(new TransRecordModel("0x0543b4e1186…4d9f72",2, "-369.00SPDT", 1));

        adapter.setNewData(models);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.bt_buy:
                    {
                        showDialog();
                    }
                        break;
                }
            }
        });
        loadData();
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

    private void showDialog() {
        dialog = new BussnessBuyDialog(getActivity(), new BussnessBuyDialog.IOnProtocolDialogClickListener() {
            @Override
            public void onClickBtn(boolean isConform) {
                dialog.dismiss();
                if (isConform) {
                    DMG.showNomalShortToast("已提交申请，请去交易订单中完成支付");
                }

            }
        });
        dialog.show();
    }

    public void loadData() {
        RetrofitClientOTC.APIService apiService = RetrofitClientOTC.getInstance().createRetrofit().create(RetrofitClientOTC.APIService.class);
        Map<String, String> params = new HashMap<>();

        params.put("pageNum", "1");
        params.put("pageSize", "10");
        String json = GsonUtil.toJson(params);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=utf-8"), json);
        retrofit2.Call<NodeModel.NodeModelResponse> call = apiService.requestBuyList(body);
        call.enqueue(new Callback<NodeModel.NodeModelResponse>() {
            @Override
            public void onResponse(retrofit2.Call<NodeModel.NodeModelResponse> call, Response<NodeModel.NodeModelResponse> response) {
                SWLog.d(TAG(), "onResponse()");
            }

            @Override
            public void onFailure(retrofit2.Call<NodeModel.NodeModelResponse> call, Throwable t) {

            }
        });
    }

}
