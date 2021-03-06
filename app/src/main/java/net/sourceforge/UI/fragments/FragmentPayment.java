package net.sourceforge.UI.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chain.wallet.spd.R;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import net.sourceforge.base.FragmentBase;
import net.sourceforge.commons.log.SWLog;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by terry.c on 06/03/2018.
 */

public class FragmentPayment extends FragmentBase {

    public static final String TAG = FragmentPayment.class.getSimpleName();

    private View curView = null;

    private Unbinder unbinder;

    @BindView(R.id.iv_qr_image)
    public ImageView iv_qr_image;

    public static FragmentPayment newInstance() {
        FragmentPayment f = new FragmentPayment();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (null != curView && curView.getParent()!=null) {
            ((ViewGroup) curView.getParent()).removeView(curView);
            return curView;
        }
        curView = inflater.inflate(R.layout.layout_payment, null);
        unbinder = ButterKnife.bind(this, curView);
        initRes();
        return curView;
    }

    private void initRes() {
        Bitmap mBitmap = CodeUtils.createImage("fukuan:1000", 400, 400, null);
        iv_qr_image.setImageBitmap(mBitmap);
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


}
