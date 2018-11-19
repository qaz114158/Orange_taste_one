package net.sourceforge.UI.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

import com.chain.wallet.spd.R;

import net.sourceforge.UI.fragments.FragmentAboutUS;
import net.sourceforge.UI.fragments.FragmentAddressBook;
import net.sourceforge.UI.fragments.FragmentAuth;
import net.sourceforge.UI.fragments.FragmentAuthBase;
import net.sourceforge.UI.fragments.FragmentAuthHigh;
import net.sourceforge.UI.fragments.FragmentAuthMiddle;
import net.sourceforge.UI.fragments.FragmentBussiness;
import net.sourceforge.UI.fragments.FragmentFinancialManagement;
import net.sourceforge.UI.fragments.FragmentMore;
import net.sourceforge.UI.fragments.FragmentTransRecord;
import net.sourceforge.UI.fragments.FragmentPayment;
import net.sourceforge.UI.fragments.FragmentSetLoginPassword;
import net.sourceforge.UI.fragments.FragmentSetOTC;
import net.sourceforge.UI.fragments.FragmentSetPayPassword;
import net.sourceforge.UI.fragments.FragmentTransRecordDetail;
import net.sourceforge.UI.fragments.FragmentTransfer;
import net.sourceforge.base.ActivityBase;

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

    public static final int PAGE_SET_PAY_PASSWORD = 6;//设置支付密码

    public static final int PAGE_SET_LOGIN_PASSWORD = 7;//重置登录密码

    public static final int PAGE_AUTH = 8;//实名认证

    public static final int PAGE_AUTH_BASE = 9;//基础认证

    public static final int PAGE_AUTH_MIDDLE = 10;//中级认证

    public static final int PAGE_AUTH_HIGH = 11;//高级认证

    public static final int PAGE_SET_OTC = 12;//设置OTC

    public static final int PAGE_ABOUT_US = 13;//关于我们

    public static final int PAGE_TRANS_RECORD = 14;//交易记录

    public static final int PAGE_ADDRESS_BOOK = 15;//地址本

    public static final int PAGE_ADDRESS_BOOK_CHOOSE = 16;//选择地址本

    public static final int PAGE_TRANS_RECORD_DETAIL = 17;//交易记录详情

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
                setTitleBarBackgroundColor(getResources().getColor(R.color.bg_base2));
                navigateTo(FragmentBussiness.newInstance());
                break;
            case PAGE_LICAI:
                navigateTo(FragmentFinancialManagement.newInstance());
                break;
            case PAGE_MORE:
            {
//                setTitleBarBackgroundColor(getResources().getColor(R.color.bg_base2));
//                navigateTo(FragmentTransRecord.newInstance());
                navigateTo(FragmentMore.newInstance());
            }
                break;
            case PAGE_SET_PAY_PASSWORD:
            {
                navigateTo(FragmentSetPayPassword.newInstance());
            }
                break;
            case PAGE_SET_LOGIN_PASSWORD:
            {
                navigateTo(FragmentSetLoginPassword.newInstance());
            }
                break;
            case PAGE_AUTH:
            {
                navigateTo(FragmentAuth.newInstance());
            }
                break;
            case PAGE_AUTH_BASE:
            {
                navigateTo(FragmentAuthBase.newInstance());
            }
                break;
            case PAGE_AUTH_MIDDLE:
            {
                navigateTo(FragmentAuthMiddle.newInstance());
            }
            break;
            case PAGE_AUTH_HIGH:
            {
                navigateTo(FragmentAuthHigh.newInstance());
            }
            break;
            case PAGE_SET_OTC:
            {
                navigateTo(FragmentSetOTC.newInstance());
            }
            break;
            case PAGE_ABOUT_US:
            {
                navigateTo(FragmentAboutUS.newInstance());
            }
                break;

            case PAGE_TRANS_RECORD:
            {
                setTitleBarBackgroundColor(getResources().getColor(R.color.bg_base2));
                navigateTo(FragmentTransRecord.newInstance());
            }
                break;
            case PAGE_ADDRESS_BOOK:
            {
                navigateTo(FragmentAddressBook.newInstance(false));
            }
                break;

            case PAGE_ADDRESS_BOOK_CHOOSE:
            {
                navigateTo(FragmentAddressBook.newInstance(true));
            }
                break;
            case PAGE_TRANS_RECORD_DETAIL:
            {
                setTitleBarBackgroundColor(getResources().getColor(R.color.bg_base2));
                navigateTo(FragmentTransRecordDetail.newInstance());
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
