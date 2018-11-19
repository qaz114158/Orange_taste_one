package net.sourceforge.UI.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.chain.wallet.spd.R;

import net.sourceforge.UI.activity.ActivityDetail;
import net.sourceforge.UI.view.InputAmountDialog;
import net.sourceforge.UI.view.TransferDialog1;
import net.sourceforge.UI.view.TransferDialog2;
import net.sourceforge.base.FragmentBase;
import net.sourceforge.commons.log.SWLog;
import net.sourceforge.http.model.AddressModel;
import net.sourceforge.manager.JumpMethod;
import net.sourceforge.utils.DMG;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

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
        return curView;
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
        String address = tv_address.getText().toString();
        String money = tv_money.getText().toString();
        String remark = tv_remark.getText().toString();

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
                    showDialog2();
                }
            }
        });
        transferDialog1.setData(address, money, remark);
        transferDialog1.show();
    }

    private void showDialog2() {
        transferDialog2 = new TransferDialog2(getActivity(), new TransferDialog2.IOnProtocolDialogClickListener() {
            @Override
            public void onClickBtn(boolean isConform) {
                transferDialog2.dismiss();
                if (isConform) {
                    DMG.showNomalShortToast("转账成功");
                    getActivity().finish();
                }
            }
        });
        transferDialog2.show();
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
