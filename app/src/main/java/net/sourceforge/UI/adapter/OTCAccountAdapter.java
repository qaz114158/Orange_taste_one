package net.sourceforge.UI.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.sourceforge.http.model.OTCAccountModel;

/**
 * Created by terry.c on 12/03/2018.
 */

public class OTCAccountAdapter extends BaseQuickAdapter<OTCAccountModel, BaseViewHolder> {

    public OTCAccountAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, OTCAccountModel item) {
//        helper.addOnClickListener(R.id.bt_buy);
    }


}
