package net.sourceforge.UI.adapter;


import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chain.wallet.spd.R;

import net.sourceforge.http.model.UserMenu;

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
    }


}
