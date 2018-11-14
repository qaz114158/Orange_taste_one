package net.sourceforge.UI.adapter;


import android.graphics.Color;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chain.wallet.spd.R;

import net.sourceforge.http.model.TransRecordModel;

/**
 * Created by terry.c on 12/03/2018.
 */

public class TransRecordAdapter extends BaseQuickAdapter<TransRecordModel, BaseViewHolder> {

    public TransRecordAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, TransRecordModel item) {
        helper.setText(R.id.tv_address, item.address);
        helper.setText(R.id.tv_count, item.count);
        helper.getView(R.id.ll_right).setVisibility(View.GONE);
        helper.getView(R.id.tv_count).setVisibility(View.VISIBLE);

        helper.setTextColor(R.id.tv_count, Color.parseColor("#eca703"));
        switch (item.type) {
            case 1:
            {
                //转账
                switch (item.statu) {
                    case 0:
                    {
                        helper.setImageResource(R.id.iv_image, R.drawable.ic_app_12);
                    }
                    break;
                    case 1:
                    {
                        helper.setImageResource(R.id.iv_image, R.drawable.ic_app_11);
                        helper.getView(R.id.ll_right).setVisibility(View.VISIBLE);
                        helper.getView(R.id.tv_count).setVisibility(View.GONE);

                        helper.setTextColor(R.id.tv_statu, Color.parseColor("#4a90e2"));

                        helper.setText(R.id.tv_statu, "等待中");
                    }
                    break;
                    case 2:
                    {
                        helper.setImageResource(R.id.iv_image, R.drawable.ic_app_16);
                        helper.getView(R.id.ll_right).setVisibility(View.VISIBLE);
                        helper.getView(R.id.tv_count).setVisibility(View.GONE);

                        helper.setTextColor(R.id.tv_statu, Color.parseColor("#d7390d"));
                        helper.setText(R.id.tv_statu, "转账失败");
                    }
                    break;
                }
            }
                break;
            case 2:
            {
                //收款
                helper.setImageResource(R.id.iv_image, R.drawable.ic_app_14);
                helper.setTextColor(R.id.tv_count, Color.parseColor("#00b858"));
            }
                break;
        }
    }


}
