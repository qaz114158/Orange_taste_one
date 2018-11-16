package net.sourceforge.UI.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chain.wallet.spd.R;

/**
 * Created by terry.c on 12/03/2018.
 */

public class MnemonicAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public MnemonicAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_text, item);
        helper.addOnClickListener(R.id.tv_text);
    }


}
