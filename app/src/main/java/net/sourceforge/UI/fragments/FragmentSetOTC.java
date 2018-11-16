package net.sourceforge.UI.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chain.wallet.spd.R;

import net.sourceforge.UI.adapter.OTCAccountAdapter;
import net.sourceforge.UI.view.AddOtcAccountDialog;
import net.sourceforge.UI.view.BussnessBuyDialog;
import net.sourceforge.UI.view.ChooseAddOtcDialog;
import net.sourceforge.base.FragmentBase;
import net.sourceforge.commons.log.SWLog;
import net.sourceforge.http.model.OTCAccountModel;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by terry.c on 06/03/2018.
 */

public class FragmentSetOTC extends FragmentBase {

    public static final String TAG = FragmentSetOTC.class.getSimpleName();

    private View curView = null;

    private Unbinder unbinder;

    @BindView(R.id.rl_recycler)
    public RecyclerView rl_recycler;

    private OTCAccountAdapter adapter;

    private ChooseAddOtcDialog dialog;

    private List<OTCAccountModel> models;

    private AddOtcAccountDialog addDialog;

    public static FragmentSetOTC newInstance() {
        FragmentSetOTC f = new FragmentSetOTC();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (null != curView && curView.getParent()!=null) {
            ((ViewGroup) curView.getParent()).removeView(curView);
            return curView;
        }
        curView = inflater.inflate(R.layout.layout_set_otc, null);
        unbinder = ButterKnife.bind(this, curView);
        initRes();
        return curView;
    }

    private void initRes() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rl_recycler.setLayoutManager(layoutManager);
        rl_recycler.setAdapter(adapter = new OTCAccountAdapter(R.layout.item_otc_account));

//        List<BussnessModel> models = new ArrayList<>();
//        models.add(new BussnessModel());
//        models.add(new BussnessModel());
//        models.add(new BussnessModel());
//        models.add(new TransRecordModel("0x0543b4e1186…4d9f72",0, "-59.12SPDT", 1));
//        models.add(new TransRecordModel("0x0543b4e1186…4d9f72",0, "-390.00SPDT", 1));
//        models.add(new TransRecordModel("0x0543b4e1186…4d9f72",0, "+12000SPDT", 2));
//        models.add(new TransRecordModel("0x0543b4e1186…4d9f72",1, "-6700SPDT", 1));
//        models.add(new TransRecordModel("0x0543b4e1186…4d9f72",2, "-369.00SPDT", 1));

//        adapter.setNewData(models);
        models = new ArrayList<>();
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

    @OnClick(R.id.ib_add)
    public void onClickAdd() {
        showDialog();
    }

    private void showDialog() {
        dialog = new ChooseAddOtcDialog(getActivity(), new ChooseAddOtcDialog.IOnProtocolDialogClickListener() {
            @Override
            public void onClickBtn(boolean isConform) {
                dialog.dismiss();
                showAddDialog();
            }
        });
        dialog.show();
    }

    private void showAddDialog() {
        addDialog = new AddOtcAccountDialog(getActivity(), new AddOtcAccountDialog.IOnProtocolDialogClickListener() {
            @Override
            public void onClickBtn(boolean isConform) {
                if (isConform) {
                    addDialog.dismiss();
                    models.add(new OTCAccountModel());
                    adapter.setNewData(models);
                    adapter.notifyDataSetChanged();
                }
            }
        });
        addDialog.show();
    }
}
