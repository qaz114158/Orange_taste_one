package net.sourceforge.UI.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;


import com.chain.wallet.spd.R;

import net.sourceforge.base.ActivityBase;
import net.sourceforge.external.eventbus.events.EventAction;
import net.sourceforge.http.model.WalletModel;
import net.sourceforge.manager.JumpMethod;
import net.sourceforge.manager.WalletManager;
import net.sourceforge.utils.AppUtils;
import net.sourceforge.utils.DMG;
import net.sourceforge.utils.KeyStoreUtils;
import net.sourceforge.utils.MnemonicUtils;
import net.sourceforge.utils.RandomUtil;

import org.brewchain.ecrypto.impl.EncInstance;
import org.fc.brewchain.bcapi.KeyPairs;
import org.fc.brewchain.bcapi.KeyStoreFile;
import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityCreateAccount extends ActivityBase {

    @BindView(R.id.et_username)
    public EditText et_username;

    @BindView(R.id.et_password)
    public EditText et_password;

    @BindView(R.id.re_password)
    public EditText re_password;


    @BindView(R.id.bt_create)
    public Button bt_create;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_create_account);
        ButterKnife.bind(this);
        setLeftBtnBackPrecious();

        et_username.addTextChangedListener(ediTextChangeListener);
        et_password.addTextChangedListener(ediTextChangeListener);
        re_password.addTextChangedListener(ediTextChangeListener);
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
        String username = et_username.getText().toString();
        String password = et_password.getText().toString();
        String rePassword = re_password.getText().toString();
        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(rePassword)) {
            bt_create.setEnabled(true);
        } else {
            bt_create.setEnabled(false);
        }
    }

    @OnClick(R.id.bt_create)
    public void onClickCreate() {
        final String walletId = et_username.getText().toString();
        final String pwd = et_password.getText().toString();
        final String rePwd = re_password.getText().toString();

//        JumpMethod.jumpToCreateAccountSuccess(this);

        if (TextUtils.isEmpty(walletId)) {
            DMG.showNomalShortToast("请输入钱包ID");
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            DMG.showNomalShortToast("请输入钱包密码");
            return;
        }
        if (pwd.length() <8 || pwd.length() >16) {
            DMG.showNomalShortToast("钱包密码长度应该为8-16位");
            return;
        }
        if (TextUtils.isEmpty(rePwd)) {
            DMG.showNomalShortToast("请确认钱包密码");
            return;
        }
        if (!pwd.equals(rePwd)) {
            DMG.showNomalShortToast("两次输入的钱包密码不一致");
            return;
        }
//        if (!cb_option.isChecked()) {
//            DMG.showNomalShortToast("请先阅读并同意条款");
//            return;
//        }

//        if (WalletManager.getInstance().isExsitWalletId(walletId)) {
//            DMG.showNomalShortToast("钱包ID已存在，请重新输入");
//            return;
//        }

        showProgressDialog("");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                WalletModel walletModel = new WalletModel();
                walletModel.walletId = walletId;
                walletModel.walletPassowrd = pwd;

                String mnemonicStr = MnemonicUtils.generateMnemonic(RandomUtil.generateByteStr(16));
                EncInstance encAPI = new EncInstance();
                encAPI.startup();

                KeyPairs keyPairs = encAPI.genKeys(mnemonicStr);
                walletModel.address = keyPairs.getAddress();
                walletModel.privateKey = keyPairs.getPrikey();
                walletModel.pubKey = keyPairs.getPubkey();
                walletModel.bcuId = keyPairs.getBcuid();
                walletModel.mnemonicStr = mnemonicStr;
                walletModel.balance = 10000;

                KeyStoreUtils keyStoreHelper = new KeyStoreUtils(encAPI);
                KeyStoreFile oKeyStoreFile = keyStoreHelper.generate(keyPairs, pwd);
                String keyStoreFileStr = keyStoreHelper.parseToJsonStr(oKeyStoreFile);
                walletModel.keystoreJson = keyStoreFileStr;

                //test
//                EncAPI apiB = new EncInstance();
//                KeyStoreUtils keyStoreHelperB = new KeyStoreUtils(apiB);
//                Oentity.KeyStoreValue oKeyStoreValue = keyStoreHelperB.getKeyStore(keyStoreFileStr, pwd);

                WalletManager.getInstance().addWallet(walletModel);
                EventBus.getDefault().post(new EventAction(null, EventAction.EventKey.KEY_WALLET_CREATE_SUCESS));
                createSuccess(walletModel);
            }
        }, 3000);

    }

    @OnClick(R.id.root_container)
    public void onClickRootView() {
        AppUtils.hideSoftInput(this);
    }

    public void createSuccess(final WalletModel walletModel) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                hideProgressDialog();
//                JumpMethod.jumpToCreateWalletSuccess(ActivityCreateWallet.this, walletModel, isFromSet);
                JumpMethod.jumpToMain(mContext);
                finish();
                overridePendingTransition(R.anim.fragment_slide_right_enter,
                        R.anim.fragment_slide_left_exit);
            }
        });
    }

}
