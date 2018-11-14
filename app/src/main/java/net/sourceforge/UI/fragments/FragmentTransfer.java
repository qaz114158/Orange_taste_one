package net.sourceforge.UI.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chain.wallet.spd.R;

import net.sourceforge.UI.view.InputAmountDialog;
import net.sourceforge.UI.view.TransferDialog1;
import net.sourceforge.UI.view.TransferDialog2;
import net.sourceforge.base.FragmentBase;
import net.sourceforge.commons.log.SWLog;

import org.greenrobot.eventbus.EventBus;

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
        transferDialog1 = new TransferDialog1(getActivity(), new TransferDialog1.IOnProtocolDialogClickListener() {
            @Override
            public void onClickBtn(boolean isConform) {
                transferDialog1.dismiss();
                if (isConform) {
                    showDialog2();
                }
            }
        });
        transferDialog1.show();
    }

    private void showDialog2() {
        transferDialog2 = new TransferDialog2(getActivity(), new TransferDialog2.IOnProtocolDialogClickListener() {
            @Override
            public void onClickBtn(boolean isConform) {
                transferDialog2.dismiss();
                if (isConform) {

                }
            }
        });
        transferDialog2.show();
    }


}
