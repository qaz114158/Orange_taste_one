package net.sourceforge.UI.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chain.wallet.spd.R;

import net.sourceforge.http.model.AddressModel;

/**
 * Created by terry.c on 12/03/2018.
 */

public class AddressBookAdapter extends BaseQuickAdapter<AddressModel, BaseViewHolder> {

    public AddressBookAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, AddressModel item) {
        helper.setText(R.id.tv_name, item.name);
        helper.setText(R.id.tv_address, item.address);
        helper.addOnClickListener(R.id.ll_content);
    }


}
