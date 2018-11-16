package net.sourceforge.UI.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chain.wallet.spd.R;

import net.sourceforge.http.model.BussnessModel;

/**
 * Created by terry.c on 12/03/2018.
 */

public class BussnessOrderAdapter extends BaseQuickAdapter<BussnessModel, BaseViewHolder> {

    public BussnessOrderAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, BussnessModel item) {
        helper.addOnClickListener(R.id.bt_pay);
    }


}
