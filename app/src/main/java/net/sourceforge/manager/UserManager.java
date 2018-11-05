package net.sourceforge.manager;

import net.sourceforge.external.eventbus.events.EventAction;
import net.sourceforge.http.model.UserInfo;
import net.sourceforge.utils.PreferenceHelper;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by terry.c on 15/03/2018.
 */

public class UserManager {
    private static final UserManager ourInstance = new UserManager();

    public static UserManager getInstance() {
        return ourInstance;
    }

    private UserManager() {
    }

    private UserInfo mUserInfo;

    public UserInfo getUserInfo() {
        if (mUserInfo == null) {
            mUserInfo = PreferenceHelper.getInstance().getObject(PreferenceHelper.PreferenceKey.KEY_USER_INFO,UserInfo.class);
        }
        return mUserInfo;
    }

    public void updateUserInfo(UserInfo userInfo) {
        mUserInfo = userInfo;
        PreferenceHelper.getInstance().setObject(PreferenceHelper.PreferenceKey.KEY_USER_INFO, userInfo);
//        if (null == mUserInfo || TextUtils.isEmpty(mUserInfo.id)) {
//            UmengMsg.removeUmengPushAlias(AppApplication.appContext);
//        } else {
//            UmengMsg.setUmengPushAlias(AppApplication.appContext, mUserInfo.id);
//        }
    }


    public void deleteUserInfo() {
        mUserInfo = null;
        PreferenceHelper.getInstance().setObject(PreferenceHelper.PreferenceKey.KEY_USER_INFO, null);
//        UmengMsg.removeUmengPushAlias(AppApplication.appContext);
    }

    public boolean isUserAuth() {
        if (getUserInfo() == null) {
            return false;
        }
        return true;
    }

    public void updateUserBindPhone(String newPhone) {
        mUserInfo = getUserInfo();
        mUserInfo.regmobile = newPhone;
        updateUserInfo(mUserInfo);
    }

    public void logoutUser() {
        EventBus.getDefault().post(new EventAction(null, EventAction.EventKey.KEY_USER_LOGOUT));
        deleteUserInfo();
    }

    public String getToken() {
        if (getUserInfo() == null) {
            return "";
        }
        return getUserInfo().token;
    }



}
