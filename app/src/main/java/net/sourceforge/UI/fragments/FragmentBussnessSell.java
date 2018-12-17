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
import net.sourceforge.UI.adapter.BussnessSellAdapter;
import net.sourceforge.UI.view.BussnessBuyDialog;
import net.sourceforge.UI.view.BussnessSellDialog;
import net.sourceforge.base.FragmentBase;
import net.sourceforge.commons.log.SWLog;
import net.sourceforge.http.model.BussnessModel;
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

public class FragmentBussnessSell extends FragmentBase {

    public static final String TAG = FragmentBussnessSell.class.getSimpleName();

    private View curView = null;

    private Unbinder unbinder;

    @BindView(R.id.rl_recycler)
    public RecyclerView rl_recycler;

    private BussnessSellAdapter adapter;

    private BussnessSellDialog dialog;

    public static FragmentBussnessSell newInstance() {
        FragmentBussnessSell f = new FragmentBussnessSell();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (null != curView && curView.getParent()!=null) {
            ((ViewGroup) curView.getParent()).removeView(curView);
            return curView;
        }
        curView = inflater.inflate(R.layout.layout_bussness_sell, null);
        unbinder = ButterKnife.bind(this, curView);
        initRes();
        return curView;
    }

    private void initRes() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rl_recycler.setLayoutManager(layoutManager);
        rl_recycler.setAdapter(adapter = new BussnessSellAdapter(R.layout.item_bussness_sell));

        List<BussnessModel> models = new ArrayList<>();
        models.add(new BussnessModel(1, "NO10001", "5", "10/27/2019", "0.78", "2000.0000", "0.0000", "20000.0000"));
        models.add(new BussnessModel(1, "NO10002", "20", "5/11/2019", "0.88", "1000.0000", "5000.0000", "20000.0000"));
//        models.add(new BussnessModel());
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
                    case R.id.bt_sell:
                    {
                        showDialog();
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

    private void showDialog() {
        dialog = new BussnessSellDialog(getActivity(), new BussnessSellDialog.IOnProtocolDialogClickListener() {
            @Override
            public void onClickBtn(boolean isConform) {
                dialog.dismiss();
                if (isConform) {
                    DMG.showNomalShortToast("已提交申请");
                }
            }
        });
        dialog.show();
    }

}
