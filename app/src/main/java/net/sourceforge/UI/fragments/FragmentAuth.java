package net.sourceforge.UI.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chain.wallet.spd.R;

import net.sourceforge.UI.activity.ActivityDetail;
import net.sourceforge.base.FragmentBase;
import net.sourceforge.commons.log.SWLog;
import net.sourceforge.manager.JumpMethod;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by terry.c on 06/03/2018.
 */

public class FragmentAuth extends FragmentBase {

    public static final String TAG = FragmentAuth.class.getSimpleName();

    private View curView = null;

    private Unbinder unbinder;

    public static FragmentAuth newInstance() {
        FragmentAuth f = new FragmentAuth();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (null != curView && curView.getParent()!=null) {
            ((ViewGroup) curView.getParent()).removeView(curView);
            return curView;
        }
        curView = inflater.inflate(R.layout.layout_auth, null);
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


    @OnClick(R.id.tv_auth_base)
    public void onClickAuthBase() {
        JumpMethod.jumpToDetail(mContext, "基础认证", ActivityDetail.PAGE_AUTH_BASE);
    }

    @OnClick(R.id.tv_auth_middle)
    public void onClickAuthMiddle() {
        JumpMethod.jumpToDetail(mContext, "中级认证", ActivityDetail.PAGE_AUTH_MIDDLE);
    }

    @OnClick(R.id.tv_auth_high)
    public void onClickAuthHigh() {
        JumpMethod.jumpToDetail(mContext, "高级认证", ActivityDetail.PAGE_AUTH_HIGH);
    }

}
