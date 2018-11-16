package net.sourceforge.UI.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.chain.wallet.spd.R;

import net.sourceforge.base.ActivityBase;
import net.sourceforge.external.eventbus.events.EventAction;
import net.sourceforge.manager.JumpMethod;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityPreCreateAccount extends ActivityBase {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pre_create_account);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @OnClick(R.id.bt_create)
    public void onClickCreateAcc() {
        JumpMethod.jumpToCreateAccount(this);
    }

    @OnClick(R.id.bt_import)
    public void onClickImportAcc() {
        JumpMethod.jumpToImportAccount(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventAction messageEvent){
        String tag = messageEvent.getMessageTag();
        if (tag.equalsIgnoreCase(EventAction.EventKey.KEY_WALLET_CREATE_SUCESS) || tag.equalsIgnoreCase(EventAction.EventKey.KEY_WALLET_IMPORT_SUCESS) ) {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
