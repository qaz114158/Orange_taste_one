package net.sourceforge.UI.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chain.wallet.spd.R;

import net.sourceforge.http.model.HomeFeatureModel;

/**
 * Created by terry.c on 12/03/2018.
 */

public class HomeFeatureAdapter extends BaseQuickAdapter<HomeFeatureModel, BaseViewHolder> {

    public HomeFeatureAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeFeatureModel item) {
        helper.setText(R.id.tv_text, item.title);
        helper.setImageResource(R.id.iv_image, item.imageResource);
    }


}
