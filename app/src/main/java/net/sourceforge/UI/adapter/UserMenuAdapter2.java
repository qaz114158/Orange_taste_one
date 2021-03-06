package net.sourceforge.UI.adapter;


import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chain.wallet.spd.R;

import net.sourceforge.http.model.UserMenu;

/**
 * Created by terry.c on 12/03/2018.
 */

public class UserMenuAdapter2 extends BaseQuickAdapter<UserMenu, BaseViewHolder> {

    public UserMenuAdapter2(int layoutResId) {
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

        if (helper.getLayoutPosition() == 0) {
            helper.getView(R.id.tv_notice1).setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_notice1, "跟随系统");
        } else {
            helper.getView(R.id.tv_notice1).setVisibility(View.GONE);
        }
    }


}
