package net.sourceforge.UI.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chain.wallet.spd.R;

import net.sourceforge.http.model.WalletModel;

/**
 * Created by terry.c on 12/03/2018.
 */

public class SwitchWalletAdapter extends BaseQuickAdapter<WalletModel, BaseViewHolder> {

    public SwitchWalletAdapter() {
        super(R.layout.item_swich_wallet);
    }

    @Override
    protected void convert(BaseViewHolder helper, WalletModel item) {
        switch (item.walletType) {
            case ETH:
            {
                helper.setImageResource(R.id.iv_image, R.drawable.ic_app_81);
                helper.setText(R.id.tv_name, "ETH");
            }
                break;
            case FBC:
            {
                helper.setImageResource(R.id.iv_image, R.drawable.ic_app_80);
                helper.setText(R.id.tv_name, "SPDT");
            }
                break;
        }
        helper.setText(R.id.tv_address, item.address);

    }


}
