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
import net.sourceforge.utils.DMG;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by terry.c on 06/03/2018.
 */

public class FragmentSetPayPassword extends FragmentBase {

    public static final String TAG = FragmentSetPayPassword.class.getSimpleName();

    private View curView = null;

    private Unbinder unbinder;

    @BindView(R.id.et_password)
    public EditText et_password;

    @BindView(R.id.et_re_password)
    public EditText et_re_password;

    public static FragmentSetPayPassword newInstance() {
        FragmentSetPayPassword f = new FragmentSetPayPassword();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (null != curView && curView.getParent()!=null) {
            ((ViewGroup) curView.getParent()).removeView(curView);
            return curView;
        }
        curView = inflater.inflate(R.layout.layout_set_pay_password, null);
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
        String password = et_password.getText().toString();
        String rePassword = et_re_password.getText().toString();
        if (TextUtils.isEmpty(password)) {
            DMG.showNomalLongToast("请输入密码");
            return;
        }
        if (!password.equals(rePassword)) {
            DMG.showNomalLongToast("两次输入的密码不一致");
            return;
        }
        DMG.showNomalLongToast("设置成功");
        getActivity().finish();
    }

}
