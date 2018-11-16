package net.sourceforge.UI.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chain.wallet.spd.R;

import net.sourceforge.http.model.BussnessModel;
import net.sourceforge.http.model.FinanncialItemModel;

/**
 * Created by terry.c on 12/03/2018.
 */

public class FinanncialItemAdapter extends BaseQuickAdapter<FinanncialItemModel, BaseViewHolder> {

    public FinanncialItemAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, FinanncialItemModel item) {
//        helper.addOnClickListener(R.id.bt_buy);
    }


}
