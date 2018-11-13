package net.sourceforge.UI.adapter;


import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chain.wallet.spd.R;

import net.sourceforge.http.model.HomeAssertModel;

/**
 * Created by terry.c on 12/03/2018.
 */

public class HomeAssertsAdapter extends BaseQuickAdapter<HomeAssertModel, BaseViewHolder> {

    public HomeAssertsAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeAssertModel item) {
        helper.setText(R.id.tv_name, item.title);
        helper.setText(R.id.tv_count, item.count);
        helper.setText(R.id.tv_countCNY, item.countCNY);

        if (TextUtils.isEmpty(item.title)) {
            helper.getView(R.id.ll_content).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.ll_content).setVisibility(View.VISIBLE);
        }

    }


}
