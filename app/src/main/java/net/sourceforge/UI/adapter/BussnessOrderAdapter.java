package net.sourceforge.UI.adapter;


import android.graphics.Color;

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
//        helper.addOnClickListener(R.id.bt_pay);
        helper.setText(R.id.tv_text1, "单号 " +  item.orderNum);
        helper.setText(R.id.tv_text2, "剩余交易时间：" +  item.tranTime);
        helper.setText(R.id.tv_text3, "买入量："+ item.min+ " SPDT（1 SPDT/0.78 CNY）");
        helper.setText(R.id.tv_text4, "总额："+item.totalCount+" CNY" );

        switch (item.coinType) {
            case 1:
            {
                helper.setText(R.id.bt_pay, "去支付");
                helper.setTextColor(R.id.bt_pay, Color.parseColor("#04ac54"));
            }
                break;
            case 2:
            {
                helper.setText(R.id.bt_pay, "去转账");
                helper.setTextColor(R.id.bt_pay, Color.parseColor("#eca703"));
            }
            break;
            case 3:
            {
                helper.setText(R.id.bt_pay, "去申诉");
                helper.setTextColor(R.id.bt_pay, Color.parseColor("#fc0e0e"));
            }
            break;
            case 4:
            {
                helper.setText(R.id.bt_pay, "已支付，交易处理中");
                helper.setTextColor(R.id.bt_pay, Color.parseColor("#03a9fb"));
            }
            break;
        }
        helper.addOnClickListener(R.id.bt_pay);

    }


}
