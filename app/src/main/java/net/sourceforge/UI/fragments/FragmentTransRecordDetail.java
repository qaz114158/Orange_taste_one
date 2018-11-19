package net.sourceforge.UI.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.chain.wallet.spd.R;

import net.sourceforge.UI.view.AddAddressDialog;
import net.sourceforge.base.FragmentBase;
import net.sourceforge.commons.log.SWLog;
import net.sourceforge.utils.DMG;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by terry.c on 06/03/2018.
 */

public class FragmentTransRecordDetail extends FragmentBase {

    public static final String TAG = FragmentTransRecordDetail.class.getSimpleName();

    private View curView = null;

    private Unbinder unbinder;

    private AddAddressDialog addAddressDialog;

    public static FragmentTransRecordDetail newInstance() {
        FragmentTransRecordDetail f = new FragmentTransRecordDetail();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (null != curView && curView.getParent()!=null) {
            ((ViewGroup) curView.getParent()).removeView(curView);
            return curView;
        }
        curView = inflater.inflate(R.layout.layout_trans_detail_record, null);
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

    @OnClick(R.id.ib_add_address)
    public void onClickAddAddress() {
        addAddressDialog = new AddAddressDialog(mContext, new AddAddressDialog.IOnProtocolDialogClickListener() {
            @Override
            public void onClickBtn(boolean isConform) {
                addAddressDialog.dismiss();
                if (isConform) {
                    DMG.showNomalShortToast("添加成功");
                }
            }
        });
        addAddressDialog.show();
    }

}
