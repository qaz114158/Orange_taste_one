package net.sourceforge.UI.adapter;


import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chain.wallet.spd.R;

import net.sourceforge.application.AppApplication;
import net.sourceforge.http.model.UserMenu;
import net.sourceforge.http.model.WalletModel;
import net.sourceforge.manager.WalletManager;

/**
 * Created by terry.c on 12/03/2018.
 */

public class UserMenuAdapter extends BaseQuickAdapter<UserMenu, BaseViewHolder> {

    public UserMenuAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserMenu item) {
//        helper.addOnClickListener(R.id.bt_buy);
        helper.setImageResource(R.id.iv_image, item.imageResource);
        helper.setText(R.id.tv_text1, item.menuName);

        if (helper.getLayoutPosition() == getData().size()-1) {
            helper.getView(R.id.v_divider).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.v_divider).setVisibility(View.VISIBLE);
        }
        TextView tv_notice1 = helper.getView(R.id.tv_notice1);
        tv_notice1.setVisibility(View.GONE);
        switch (helper.getLayoutPosition()) {
            case 0:
            {
                WalletModel walletModel = WalletManager.getInstance().getCurrentWallet();
                if (TextUtils.isEmpty(walletModel.payPassword)) {
                    Drawable left=AppApplication.getInstance().getResources().getDrawable(R.drawable.ic_app_52);
                    left.setBounds(0,0,50,50);
                    tv_notice1.setVisibility(View.VISIBLE);
                    tv_notice1.setCompoundDrawables(left,null ,null,null);
                    tv_notice1.setText("尚未设置");
                }
            }
                break;
            case 2:
            {
                if (WalletManager.getInstance().getCurrentWallet().auth == 0) {
                    Drawable left=AppApplication.getInstance().getResources().getDrawable(R.drawable.ic_app_52);
                    left.setBounds(0,0,50,50);
                    tv_notice1.setVisibility(View.VISIBLE);
                    tv_notice1.setCompoundDrawables(left,null ,null,null);
                    tv_notice1.setText("尚未设置");
                }
            }
                break;
        }

    }


}
