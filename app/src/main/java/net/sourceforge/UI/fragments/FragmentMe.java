package net.sourceforge.UI.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chain.wallet.spd.R;

import net.sourceforge.UI.activity.ActivityDetail;
import net.sourceforge.UI.adapter.UserMenuAdapter;
import net.sourceforge.UI.adapter.UserMenuAdapter2;
import net.sourceforge.UI.view.ChooseLanuageDialog;
import net.sourceforge.UI.view.InputWalletPasswordDialog;
import net.sourceforge.base.FragmentBase;
import net.sourceforge.commons.log.SWLog;
import net.sourceforge.http.model.UserMenu;
import net.sourceforge.http.model.WalletModel;
import net.sourceforge.manager.JumpMethod;
import net.sourceforge.manager.UserManager;
import net.sourceforge.manager.WalletManager;
import net.sourceforge.utils.DMG;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by terry.c on 06/03/2018.
 */

public class FragmentMe extends FragmentBase {

    public static final String TAG = FragmentMe.class.getSimpleName();

    private View curView = null;

    private Unbinder unbinder;

    @BindView(R.id.rl_menu1)
    public RecyclerView rl_menu1;

    private UserMenuAdapter userMenuAdapter1;

    private List<UserMenu> userMenus1;

    @BindView(R.id.rl_menu2)
    public RecyclerView rl_menu2;

    private UserMenuAdapter2 userMenuAdapter2;

    private List<UserMenu> userMenus2;

    private ChooseLanuageDialog chooseLanuageDialog;

    @BindView(R.id.tv_username)
    public TextView tv_username;

    @BindView(R.id.tv_address)
    public TextView tv_address;

    private InputWalletPasswordDialog inputWalletPasswordDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (null != curView && curView.getParent()!=null) {
            ((ViewGroup) curView.getParent()).removeView(curView);
            return curView;
        }
        curView = inflater.inflate(R.layout.layout_me, null);
        unbinder = ButterKnife.bind(this, curView);
        initRes();
        setData();
        return curView;
    }

    private void setData() {
        WalletModel walletModel = WalletManager.getInstance().getCurrentWallet();
        if (walletModel != null) {
            tv_username.setText(walletModel.walletId);
            tv_address.setText(walletModel.address);
        }
    }

    private void initRes() {

        //menu1
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rl_menu1.setLayoutManager(layoutManager);

        userMenus1 = new ArrayList<>();
        userMenus1.add(new UserMenu(R.drawable.ic_app_41, "设置交易密码"));
        userMenus1.add(new UserMenu(R.drawable.ic_app_50, "重置登录密码"));
        userMenus1.add(new UserMenu(R.drawable.ic_app_48, "实名认证"));
        userMenus1.add(new UserMenu(R.drawable.ic_app_47, "设置OTC收支账号"));
        userMenus1.add(new UserMenu(R.drawable.ic_app_43, "备份助记词"));


        rl_menu1.setAdapter(userMenuAdapter1 = new UserMenuAdapter(R.layout.item_user_menu));
        userMenuAdapter1.setNewData(userMenus1);

        rl_menu1.setNestedScrollingEnabled(false);
        userMenuAdapter1.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position) {
                    case 0:
                    {
                        //设置交易密码
                        getPasswordDialog().setOnProtocolDialogClickListener(new InputWalletPasswordDialog.IOnProtocolDialogClickListener() {
                            @Override
                            public void onClickBtn(boolean isConform, String password) {
                                getPasswordDialog().dismiss();
                                WalletModel walletModel = WalletManager.getInstance().getCurrentWallet();
                                if (isConform) {
                                    if (walletModel.walletPassowrd.equals(password)) {
                                        JumpMethod.jumpToDetail(mContext, "设置交易密码", ActivityDetail.PAGE_SET_PAY_PASSWORD);
                                    } else {
                                        DMG.showNomalShortToast("密码错误，请重新输入");
                                    }
                                }
                            }
                        });
                        getPasswordDialog().show();
                    }
                        break;
                    case 1:
                    {
                        //重置登录密码
                        JumpMethod.jumpToDetail(mContext, "重置登录密码", ActivityDetail.PAGE_SET_LOGIN_PASSWORD);
                    }
                    break;
                    case 2:
                    {
                        //实名认证
                        JumpMethod.jumpToDetail(mContext, "实名认证", ActivityDetail.PAGE_AUTH);
                    }
                    break;
                    case 3:
                    {
                        //设置OTC收支账号
                        JumpMethod.jumpToDetail(mContext, "设置OTC收支账号", ActivityDetail.PAGE_SET_OTC);
                    }
                    break;
                    case 4:
                    {
                        //备份助记词
                        getPasswordDialog().setOnProtocolDialogClickListener(new InputWalletPasswordDialog.IOnProtocolDialogClickListener() {
                            @Override
                            public void onClickBtn(boolean isConform, String password) {
                                getPasswordDialog().dismiss();
                                WalletModel walletModel = WalletManager.getInstance().getCurrentWallet();
                                if (isConform) {
                                    if (walletModel.walletPassowrd.equals(password)) {
                                        JumpMethod.jumpToBackupMnemonicStepOne(mContext,walletModel);
                                    } else {
                                        DMG.showNomalShortToast("密码错误，请重新输入");
                                    }
                                }
                            }
                        });
                        getPasswordDialog().show();
                    }
                    break;
                }
            }
        });

        //menu2
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity());
        layoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        rl_menu2.setLayoutManager(layoutManager2);

        userMenus2 = new ArrayList<>();
        userMenus2.add(new UserMenu(R.drawable.ic_app_44, "语言切换"));
        userMenus2.add(new UserMenu(R.drawable.ic_app_42, "版本更新"));
        userMenus2.add(new UserMenu(R.drawable.ic_app_46, "关于我们"));


        rl_menu2.setAdapter(userMenuAdapter2 = new UserMenuAdapter2(R.layout.item_user_menu));
        userMenuAdapter2.setNewData(userMenus2);
        userMenuAdapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position) {
                    case 0:
                    {
                        //语言切换
                        showChooseLanguage();
                    }
                        break;
                    case 1:
                    {
                        //版本更新
                        DMG.showNomalShortToast("已是最新版本");
                    }
                    break;
                    case 2:
                    {
                        //关于我们
                        JumpMethod.jumpToDetail(mContext, "关于我们", ActivityDetail.PAGE_ABOUT_US);
                    }
                    break;
                }
            }
        });

        rl_menu2.setNestedScrollingEnabled(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        setData();
        userMenuAdapter1.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            SWLog.d(TAG, "call onHiddenChanged():" + hidden);
            //todo

        }
    }

    private void showChooseLanguage() {
        if (chooseLanuageDialog == null) {
            chooseLanuageDialog = new ChooseLanuageDialog(getActivity(), new ChooseLanuageDialog.IOnProtocolDialogClickListener() {
                @Override
                public void onClickBtn(boolean isConform) {
                    chooseLanuageDialog.dismiss();
                }
            });
        }
        chooseLanuageDialog.show();
    }

    @OnClick(R.id.ib_address_book)
    public void onClickBookAddress() {
        JumpMethod.jumpToDetail(mContext, "地址本", ActivityDetail.PAGE_ADDRESS_BOOK);
    }


    private InputWalletPasswordDialog getPasswordDialog() {
        if (inputWalletPasswordDialog ==null) {
            inputWalletPasswordDialog = new InputWalletPasswordDialog(mContext, new InputWalletPasswordDialog.IOnProtocolDialogClickListener() {
                @Override
                public void onClickBtn(boolean isConform, String password) {

                }
            });
        }
        inputWalletPasswordDialog.resetStatu();
        return inputWalletPasswordDialog;
    }

}
