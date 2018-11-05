package net.sourceforge.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;


import com.chain.wallet.fashion.R;

import net.sourceforge.UI.view.WheelDialog;

/**
 * Created by terry.c on 06/03/2018.
 */

public class FragmentBase extends Fragment {

    protected String TAG() {
        return this.getClass().getSimpleName();
    }

    protected Context mContext;

    private WheelDialog mWheelDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    protected void showProgressDialog(String message) {
        if (mWheelDialog == null) {
            mWheelDialog = new WheelDialog(getActivity(), R.style.WheelDialog);
        }
        if (mWheelDialog.isShowing()) {
            return;
        }
        mWheelDialog.setMessage(message);
        mWheelDialog.show();
    }

    protected void hideProgressDialog() {
        if (mWheelDialog != null && mWheelDialog.isShowing()) {
            mWheelDialog.dismiss();
        }
    }

    protected void enterActivityWithoutFinish(Intent intent) {
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.fragment_slide_right_enter,
                R.anim.fragment_slide_left_exit);
    }

    protected void jumpToActivity(Context context, Class cls) {
        Intent intent = new Intent(context, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
