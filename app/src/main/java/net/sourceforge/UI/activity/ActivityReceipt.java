package net.sourceforge.UI.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chain.wallet.spd.R;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import net.sourceforge.UI.view.InputAmountDialog;
import net.sourceforge.base.ActivityBase;
import net.sourceforge.http.model.WalletModel;
import net.sourceforge.manager.WalletManager;
import net.sourceforge.utils.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by terry.c on 06/03/2018.
 */

public class ActivityReceipt extends ActivityBase {

    @BindView(R.id.iv_qr_image)
    public ImageView iv_qr_image;

    @BindView(R.id.tv_address)
    public TextView tv_address;

    @BindView(R.id.tv_has_copy)
    public TextView tv_has_copy;

    private WalletModel walletModel;

    private InputAmountDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_receipt);
        ButterKnife.bind(this);
        walletModel = WalletManager.getInstance().getCurrentWallet();
        initTitle();
        initViews();
    }

    private void initTitle() {
        setTitle("收款");
        setLeftBtnBackPrecious();
    }

    private void initViews() {
        tv_address.setText(walletModel.address);
        Bitmap mBitmap = CodeUtils.createImage(walletModel.address, 400, 400, null);
        iv_qr_image.setImageBitmap(mBitmap);

    }

    @OnClick(R.id.tv_address)
    public void onClickAddress() {
        tv_has_copy.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.bt_input_amount)
    public void onCLickInputAmount() {
        dialog = new InputAmountDialog(this, new InputAmountDialog.IOnProtocolDialogClickListener() {
            @Override
            public void onClickBtn(boolean isConform) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}
