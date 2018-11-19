package net.sourceforge.UI.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.chain.wallet.spd.R;

import net.sourceforge.base.FragmentBase;
import net.sourceforge.commons.log.SWLog;
import net.sourceforge.http.model.WalletModel;
import net.sourceforge.manager.WalletManager;
import net.sourceforge.utils.DMG;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by terry.c on 06/03/2018.
 */

public class FragmentAuthBase extends FragmentBase {

    public static final String TAG = FragmentAuthBase.class.getSimpleName();

    private View curView = null;

    private Unbinder unbinder;

    @BindView(R.id.et_field1)
    public EditText et_field1;

    @BindView(R.id.et_field2)
    public EditText et_field2;

    @BindView(R.id.et_field3)
    public EditText et_field3;

    public static FragmentAuthBase newInstance() {
        FragmentAuthBase f = new FragmentAuthBase();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (null != curView && curView.getParent()!=null) {
            ((ViewGroup) curView.getParent()).removeView(curView);
            return curView;
        }
        curView = inflater.inflate(R.layout.layout_auth_base, null);
        unbinder = ButterKnife.bind(this, curView);
        initRes();
        return curView;
    }

    private void initRes() {

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
        String username = et_field1.getText().toString();
        String phone = et_field1.getText().toString();
        String cardid = et_field1.getText().toString();
        if (TextUtils.isEmpty(username)) {
            DMG.showNomalShortToast("请输入姓名");
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            DMG.showNomalShortToast("请输入手机号码");
            return;
        }
        if (TextUtils.isEmpty(cardid)) {
            DMG.showNomalShortToast("请输入身份证号码");
            return;
        }
        DMG.showNomalShortToast("已提交认证申请");

        WalletModel walletModel = WalletManager.getInstance().getCurrentWallet();
        walletModel.auth = 1;
        WalletManager.getInstance().updateWalletAuth(walletModel);
        getActivity().finish();
    }


}
