package net.sourceforge.UI.activity;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;

import com.chain.wallet.spd.R;

import net.sourceforge.UI.view.NaviTabButton;
import net.sourceforge.application.AppApplication;
import net.sourceforge.base.ActivityBase;
import net.sourceforge.external.eventbus.events.EventAction;
import net.sourceforge.utils.DMG;
import net.sourceforge.utils.PreferenceHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

/**
 * Created by terry.c on 06/03/2018.
 */

public class ActivityMain extends ActivityBase {

    private Fragment[] mFragments;
    private NaviTabButton[] mTabButtons;
    private int currentFragmentIndex;
    private long mExitUtcMs = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        initTab();
        initFragment();
        setFragmentIndicator(0);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
//        checkPermission();
    }

    private void initFragment() {
        currentFragmentIndex = 0;
        mFragments = new Fragment[2];
        mFragments[0] = getSupportFragmentManager().findFragmentById(R.id.fragment_1);
        mFragments[1] = getSupportFragmentManager().findFragmentById(R.id.fragment_2);
//        mFragments[2] = getSupportFragmentManager().findFragmentById(R.id.fragment_3);
//        mFragments[3] = getSupportFragmentManager().findFragmentById(R.id.fragment_4);
//        mFragments[4] = getSupportFragmentManager().findFragmentById(R.id.fragment_5);
    }

    private void initTab() {
        mTabButtons = new NaviTabButton[4];

        mTabButtons[0] =  findViewById(R.id.tabbutton_1);
        mTabButtons[1] =  findViewById(R.id.tabbutton_2);
//        mTabButtons[2] =  findViewById(R.id.tabbutton_3);
//        mTabButtons[3] =  findViewById(R.id.tabbutton_4);
//        mTabButtons[4] =  findViewById(R.id.tabbutton_5);

        mTabButtons[0].setTitle(getString(R.string.st_text1));
        mTabButtons[0].setIndex(0);
        mTabButtons[0].setSelectedImage(getResources().getDrawable(R.drawable.ic_tab_home_selected));
        mTabButtons[0].setUnselectedImage(getResources().getDrawable(R.drawable.ic_tab_home_nomal));

        mTabButtons[1].setTitle(getString(R.string.st_text3));
        mTabButtons[1].setIndex(1);
        mTabButtons[1].setSelectedImage(getResources().getDrawable(R.drawable.ic_tab_me_selected));
        mTabButtons[1].setUnselectedImage(getResources().getDrawable(R.drawable.ic_tab_me_nomal));

//        mTabButtons[2].setTitle(getString(R.string.st_text3));
//        mTabButtons[2].setIndex(2);
//        mTabButtons[2].setSelectedImage(getResources().getDrawable(R.drawable.icon_tab_ser_sel));
//        mTabButtons[2].setUnselectedImage(getResources().getDrawable(R.drawable.tt_tab_internal_nor));

//        mTabButtons[2].setTitle(getString(R.string.st_text3));
//        mTabButtons[2].setIndex(2);
//        mTabButtons[2].setSelectedImage(getResources().getDrawable(R.drawable.icon_tab_ser_sel));
//        mTabButtons[2].setUnselectedImage(getResources().getDrawable(R.drawable.icon_tab_ser));

//        mTabButtons[3].setTitle(getString(R.string.st_text5));
//        mTabButtons[3].setIndex(3);
//        mTabButtons[3].setSelectedImage(getResources().getDrawable(R.drawable.icon_tab_my_sel));
//        mTabButtons[3].setUnselectedImage(getResources().getDrawable(R.drawable.icon_tab_my));
    }

    public void setFragmentIndicator(int which) {
        currentFragmentIndex = which;
        getSupportFragmentManager().beginTransaction().hide(mFragments[0]).hide(mFragments[1]).show(mFragments[which]).commitAllowingStateLoss();

        mTabButtons[0].setSelectedButton(false);
        mTabButtons[1].setSelectedButton(false);
//        mTabButtons[2].setSelectedButton(false);
//        mTabButtons[3].setSelectedButton(false);
//        mTabButtons[4].setSelectedButton(false);

        mTabButtons[which].setSelectedButton(true);
//        if (which == 3) {
//            StatusBarUtil.setStatuBarAndFontColor(this, getResources().getColor(R.color.c_background_me), false);
//        } else {
//            StatusBarUtil.setStatuBarAndFontColor(this, getResources().getColor(R.color.white), true);
//        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventAction messageEvent){
        String tag = messageEvent.getMessageTag();
        if (tag.equalsIgnoreCase(EventAction.EventKey.KEY_USER_LOGOUT)) {
//            setFragmentIndicator(0);
        } else if (tag.equalsIgnoreCase(EventAction.EventKey.KEY_USER_NEED_LOGIN)) {

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - mExitUtcMs > 2000) {
            mExitUtcMs = System.currentTimeMillis();
            DMG.showNomalShortToast("再按一次退出应用");
        } else {
            PreferenceHelper.getInstance().clearObjectForKey(PreferenceHelper.PreferenceKey.KEY_NEWS_SELECT_LIST);
            AppApplication.getInstance().exit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void checkPermission() {
        PermissionGen.with(ActivityMain.this)
                .addRequestCode(100)
                .permissions(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .request();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @PermissionSuccess(requestCode = 100)
    public void onPermissionSuccess(){
//        Toast.makeText(this, "Contact permission is granted", Toast.LENGTH_SHORT).show();
    }

}
