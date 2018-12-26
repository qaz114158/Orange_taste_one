package net.sourceforge.UI.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import com.chain.wallet.spd.R;
import com.terry.tcdialoglibrary.TCDialogUtils;
import com.terry.tcdialoglibrary.TipDialogOneButton;

import net.sourceforge.base.ActivityBase;
import net.sourceforge.external.eventbus.events.EventAction;
import net.sourceforge.http.model.WalletModel;
import net.sourceforge.manager.JumpMethod;
import net.sourceforge.manager.WalletManager;
import net.sourceforge.utils.AppUtils;
import net.sourceforge.utils.DMG;

import org.brewchain.bcapi.gens.Oentity;
import org.fc.fbc.sdk.FbcSDK;
import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityRestoreAccount extends ActivityBase {

    @BindView(R.id.et_mnemonic)
    public EditText et_mnemonic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_restore_account);
        ButterKnife.bind(this);
        setLeftBtnBackPrecious();

        et_mnemonic.addTextChangedListener(ediTextChangeListener);
    }

    TextWatcher ediTextChangeListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            checkInputStatu();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void checkInputStatu() {
        String mnemonic = et_mnemonic.getText().toString();
    }

    @OnClick(R.id.bt_restore)
    public void onClickCreate() {
        final String mnemonic = et_mnemonic.getText().toString();

        if (TextUtils.isEmpty(mnemonic)) {
            DMG.showNomalShortToast("请输入助记词");
            return;
        }
        if (!WalletManager.getInstance().isMnemonicValid(mnemonic)) {
            DMG.showNomalShortToast("助记词长度不正确");
            return;
        }

        showProgressDialog("");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //默认导入fbc钱包

                try {
                    String keyStoreContent = FbcSDK.genKeyStoreContentBySeed(mnemonic,"123456");
                    Oentity.KeyStoreValue from = FbcSDK.readKeyStoreContent("123456", keyStoreContent);

                    WalletModel walletModel = new WalletModel();
                    walletModel.walletType = WalletModel.WalletType.FBC;
                    walletModel.pubKey = from.getPubkey();
                    walletModel.privateKey = from.getPrikey();
                    walletModel.mnemonicStr = mnemonic;
                    walletModel.address = from.getAddress();
                    walletModel.walletPassowrd = "";
                    walletModel.walletId = AppUtils.genalCharacter(2) + "-" + "新钱包";

                    walletModel.keystoreJson = keyStoreContent;

                    if (WalletManager.getInstance().isWalletExist(walletModel)) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                DMG.showNomalShortToast("需要导入的钱包已存在");
                                hideProgressDialog();
                            }
                        });
                        return;
                    }
                    WalletManager.getInstance().addWallet(walletModel);
                    WalletManager.getInstance().setCurrentWallet(walletModel.pubKey);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DMG.showNomalShortToast("导入成功");
                            hideProgressDialog();
                            EventBus.getDefault().post(new EventAction(null, EventAction.EventKey.KEY_WALLET_IMPORT_SUCESS));
                            finish();
                            JumpMethod.jumpToMain(mContext);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    hideProgressDialog();
                    TCDialogUtils.showTipDialogOneButtonNoTitle(mContext, "导入失败", "确定", new TipDialogOneButton.OnConfirmListener() {
                        @Override
                        public void onConfirm(Dialog paramDialog) {

                        }
                    });
                }
            }
        }, 1000);


    }

    @OnClick(R.id.root_container)
    public void onClickRootView() {
        AppUtils.hideSoftInput(this);
    }

}
