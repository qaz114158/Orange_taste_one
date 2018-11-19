package net.sourceforge.UI.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chain.wallet.spd.R;

import net.sourceforge.http.model.BussnessModel;

/**
 * Created by terry.c on 12/03/2018.
 */

public class BussnessSellAdapter extends BaseQuickAdapter<BussnessModel, BaseViewHolder> {

    public BussnessSellAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, BussnessModel item) {
        helper.addOnClickListener(R.id.bt_sell);

        helper.setText(R.id.tv_text1, "单号 " + item.orderNum);
        helper.setText(R.id.tv_text2, "成交量：" + item.dealNum);
        helper.setText(R.id.tv_text3, item.tranTime);
        helper.setText(R.id.tv_text4, item.count + "CNY");
        helper.setText(R.id.tv_text5, "最小交易量："+item.min +" SPDT");
        helper.setText(R.id.tv_text6, "已成交量："+item.balance+" SPDT");
    }


}
