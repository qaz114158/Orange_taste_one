package net.sourceforge.UI.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chain.wallet.spd.R;

import net.sourceforge.UI.adapter.MnemonicAdapter;
import net.sourceforge.base.ActivityBase;
import net.sourceforge.external.eventbus.events.EventAction;
import net.sourceforge.http.model.WalletModel;
import net.sourceforge.utils.DMG;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityBackUpMnemonicStepTwo extends ActivityBase {

    private MnemonicAdapter adapter;

    private List<String> mDatas;

    @BindView(R.id.tv_mnemonic)
    public TextView tv_mnemonic;

    private WalletModel walletModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_backup_mnemonic_step_two);
        ButterKnife.bind(this);
        walletModel = (WalletModel) getIntent().getSerializableExtra("walletModel");
        initTitle();
        initViews();
    }

    private void initViews() {
        RecyclerView recyclerview = findViewById(R.id.recyclerview);
        GridLayoutManager layoutManage = new GridLayoutManager(mContext, 3);
//        layoutManage.setAutoMeasureEnabled(true);
        layoutManage.setOrientation(OrientationHelper.VERTICAL);
        recyclerview.setLayoutManager(layoutManage);
        recyclerview.setAdapter(adapter = new MnemonicAdapter(R.layout.item_mnemonic));

        String[] strings = walletModel.mnemonicStr.split(" ");
        mDatas = new ArrayList<>(Arrays.asList(strings));
        Collections.shuffle(mDatas);
        adapter.setNewData(mDatas);

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.tv_text:
                        String text = adapter.getItem(position);
                        appendMon(text);
                        adapter.remove(position);
                        break;
                }
            }
        });
    }

    public void appendMon(String text) {
        String monText = tv_mnemonic.getText().toString();
        tv_mnemonic.setText(monText + " " + text);
    }

    private void initTitle() {
        setLeftBtnBackPrecious();
    }

    @OnClick(R.id.bt_confirm)
    public void onClickNext() {
        String monText = tv_mnemonic.getText().toString().trim();
        if (walletModel.mnemonicStr.equals(monText)) {
            DMG.showNomalShortToast("备份成功");
            Intent intent = new Intent(mContext, ActivityMain.class);
            startActivity(intent);
            finish();
            overridePendingTransition(android.R.anim.fade_in,
                    android.R.anim.fade_out);
            EventBus.getDefault().post(new EventAction(null, EventAction.EventKey.KEY_WALLET_CREATE_SUCESS));
        } else {
            DMG.showNomalShortToast("助记词不正确，请返回重新输入");
        }
    }

}
