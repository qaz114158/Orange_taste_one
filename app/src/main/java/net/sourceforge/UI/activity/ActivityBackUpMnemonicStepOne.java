package net.sourceforge.UI.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chain.wallet.spd.R;

import net.sourceforge.base.ActivityBase;
import net.sourceforge.http.model.WalletModel;
import net.sourceforge.manager.JumpMethod;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityBackUpMnemonicStepOne extends ActivityBase {

    @BindView(R.id.tv_mnemonic)
    public TextView tv_mnemonic;


    private WalletModel walletModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_backup_mnemonic_step_one);
        ButterKnife.bind(this);
        walletModel = (WalletModel) getIntent().getSerializableExtra("walletModel");
        setLeftBtnBackPrecious();
        tv_mnemonic.setText(walletModel.mnemonicStr);
    }

    @OnClick(R.id.bt_confirm)
    public void onClickConfirm() {
        JumpMethod.jumpToBackupMnemonicStepTwo(mContext, walletModel);
        finish();
        overridePendingTransition(R.anim.fragment_slide_right_enter,
                R.anim.fragment_slide_left_exit);
    }

}
