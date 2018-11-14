package net.sourceforge.UI.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;

import com.chain.wallet.spd.R;

import net.sourceforge.UI.fragments.FragmentBussiness;
import net.sourceforge.UI.fragments.FragmentFinancialManagement;
import net.sourceforge.UI.fragments.FragmentMe;
import net.sourceforge.UI.fragments.FragmentMore;
import net.sourceforge.UI.fragments.FragmentPayment;
import net.sourceforge.UI.fragments.FragmentTransfer;
import net.sourceforge.base.ActivityBase;
import net.sourceforge.http.model.WalletModel;

import butterknife.ButterKnife;

/**
 * Created by terry.c on 06/03/2018.
 */

public class ActivityDetail extends ActivityBase {

    public static final String PARAM_TYPE_TITLE = "PARAM_TYPE_TITLE";

    public static final String PARAM_TYPE_CONTENT = "PARAM_TYPE_CONTENT";

    public static final int PAGE_TRANSFER = 1;//转账

    public static final int PAGE_PAY = 2;//付款

    public static final int PAGE_BUSSINESS = 3;//买卖

    public static final int PAGE_LICAI = 4;//理财

    public static final int PAGE_MORE = 5;//更多

    private AppBarLayout appbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_detail);
        ButterKnife.bind(this);
        initTitle();
        initViews();
    }

    private void initTitle() {
        String title = getIntent().getStringExtra(PARAM_TYPE_TITLE);
        if (!TextUtils.isEmpty(title)) {
            setTitle(title);
        }
        setLeftBtnBackPrecious();
        appbar = findViewById(R.id.appbar);
    }

    private void initViews() {
        int pageType = getIntent().getIntExtra(PARAM_TYPE_CONTENT, 0);
        switch (pageType) {
            case PAGE_TRANSFER:
//                appbar.setTargetElevation(0.f);
                navigateTo(FragmentTransfer.newInstance());
                break;
            case PAGE_PAY:
                navigateTo(FragmentPayment.newInstance());
                break;
            case PAGE_BUSSINESS:
                navigateTo(FragmentBussiness.newInstance());
                break;
            case PAGE_LICAI:
                navigateTo(FragmentFinancialManagement.newInstance());
                break;
            case PAGE_MORE:
            {
                setTitleBarBackgroundColor(getResources().getColor(R.color.bg_base2));
                navigateTo(FragmentMore.newInstance());
            }

                break;
        }
    }

    private void navigateTo(Fragment newFragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.rl_content, newFragment).commit();
    }

}
