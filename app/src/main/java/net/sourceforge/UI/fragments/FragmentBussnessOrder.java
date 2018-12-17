package net.sourceforge.UI.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chain.wallet.spd.R;
import com.terry.tcdialoglibrary.TCDialogUtils;
import com.terry.tcdialoglibrary.TipDialog;

import net.sourceforge.UI.activity.ActivityDetail;
import net.sourceforge.UI.adapter.BussnessOrderAdapter;
import net.sourceforge.UI.adapter.BussnessSellAdapter;
import net.sourceforge.UI.view.BussnessSellDialog;
import net.sourceforge.UI.view.ChoosePayTypeDialog;
import net.sourceforge.UI.view.PayDialog;
import net.sourceforge.base.FragmentBase;
import net.sourceforge.commons.log.SWLog;
import net.sourceforge.http.model.BussnessModel;
import net.sourceforge.manager.JumpMethod;
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

public class FragmentBussnessOrder extends FragmentBase {

    public static final String TAG = FragmentBussnessOrder.class.getSimpleName();

    private View curView = null;

    private Unbinder unbinder;

    @BindView(R.id.rl_recycler)
    public RecyclerView rl_recycler;

    private BussnessOrderAdapter adapter;

    private BussnessSellDialog dialog;

    private ChoosePayTypeDialog choosePayTypeDialog;

    private PayDialog payDialog;

    public static FragmentBussnessOrder newInstance() {
        FragmentBussnessOrder f = new FragmentBussnessOrder();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (null != curView && curView.getParent()!=null) {
            ((ViewGroup) curView.getParent()).removeView(curView);
            return curView;
        }
        curView = inflater.inflate(R.layout.layout_bussness_order, null);
        unbinder = ButterKnife.bind(this, curView);
        initRes();
        return curView;
    }

    private void initRes() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rl_recycler.setLayoutManager(layoutManager);
        rl_recycler.setAdapter(adapter = new BussnessOrderAdapter(R.layout.item_bussness_order));

        List<BussnessModel> models = new ArrayList<>();
        models.add(new BussnessModel(1, "NO10061", "10", "00:15:00", "0.88", "2000.0000", "20000.0000", "1760.00"));
        models.add(new BussnessModel(2, "NO10001", "-10", "00:03:00", "0.88", "5000.0000", "20000.0000", "3900.00"));
        models.add(new BussnessModel(3, "NO10024", "-10", "00:00:00", "0.88", "5000.0000", "20000.0000", "4000.00"));
        models.add(new BussnessModel(4, "NO10020", "10", "00:10:00", "0.88", "2000.0000", "20000.0000", "1740.00"));
//        models.add(new BussnessModel());
//        models.add(new BussnessModel());
//        models.add(new TransRecordModel("0x0543b4e1186…4d9f72",0, "-59.12SPDT", 1));
//        models.add(new TransRecordModel("0x0543b4e1186…4d9f72",0, "-390.00SPDT", 1));
//        models.add(new TransRecordModel("0x0543b4e1186…4d9f72",0, "+12000SPDT", 2));
//        models.add(new TransRecordModel("0x0543b4e1186…4d9f72",1, "-6700SPDT", 1));
//        models.add(new TransRecordModel("0x0543b4e1186…4d9f72",2, "-369.00SPDT", 1));

        adapter.setNewData(models);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                BussnessModel model = adapter.getItem(position);
                switch (view.getId()) {
                    case R.id.bt_pay:

                        switch (model.coinType) {
                            case 1:
                            {
                                showPayTypeDialog();
                            }
                                break;
                            case 2:
                            {
                                JumpMethod.jumpToDetail(mContext, "转账", ActivityDetail.PAGE_TRANSFER);
                            }
                            break;
                            case 3:
                            {
                                JumpMethod.jumpToDetail(mContext, "我要申诉", ActivityDetail.PAGE_TRANS_SS);
                            }
                            break;
                            case 4:
                            {

                            }
                            break;
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
            }
        });
        dialog.show();
    }


    private void showPayTypeDialog() {
        if (choosePayTypeDialog == null) {
            choosePayTypeDialog = new ChoosePayTypeDialog(getActivity(), new ChoosePayTypeDialog.IOnProtocolDialogClickListener() {
                @Override
                public void onClickBtn(final int payType) {
                    choosePayTypeDialog.dismiss();
                    switch (payType) {
                        case 1:
                            TCDialogUtils.showTipDialogNoTitle(mContext, "是否确认银行卡支付?","取消","确认", new TipDialog.OnDialogBtnClickListener() {
                                @Override
                                public void onLeftBtnClicked(TipDialog paramTipDialog) {

                                }

                                @Override
                                public void onRightBtnClicked(TipDialog paramTipDialog) {
                                    showPayDialog(payType);
                                }
                            });

                            break;
                        case 2:
                            TCDialogUtils.showTipDialogNoTitle(mContext, "是否确认微信支付?","取消","确认", new TipDialog.OnDialogBtnClickListener() {
                                @Override
                                public void onLeftBtnClicked(TipDialog paramTipDialog) {

                                }

                                @Override
                                public void onRightBtnClicked(TipDialog paramTipDialog) {
                                    showPayDialog(payType);
                                }
                            });
                            break;
                        case 3:
                            TCDialogUtils.showTipDialogNoTitle(mContext, "是否确认支付宝支付?","取消","确认", new TipDialog.OnDialogBtnClickListener() {
                                @Override
                                public void onLeftBtnClicked(TipDialog paramTipDialog) {

                                }

                                @Override
                                public void onRightBtnClicked(TipDialog paramTipDialog) {
                                    showPayDialog(payType);
                                }
                            });
                            break;

                    }
                }
            });
        }
        choosePayTypeDialog.show();
    }

    private void showPayDialog(int type) {
        if (payDialog ==null) {
            payDialog = new PayDialog(mContext, new PayDialog.IOnProtocolDialogClickListener() {
                @Override
                public void onClickBtn(boolean isConform) {
                    payDialog.dismiss();
                    DMG.showNomalShortToast("支付成功");
                }
            });
        }
        String text = "";
        switch (type) {
            case 1:
                text = "银行卡扫码支付";
                break;
            case 2:
                text = "微信扫码支付";
                break;
            case 3:
                text = "支付宝扫码支付";
                break;
        }
        payDialog.setName(text);
        payDialog.show();
    }

}
