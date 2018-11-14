package net.sourceforge.manager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.chain.wallet.spd.R;

import net.sourceforge.UI.activity.ActivityDetail;
import net.sourceforge.UI.activity.ActivityReceipt;

/**
 * Created by terry.c on 26/03/2018.
 */

public class JumpMethod {

    public static void jumpToReceipt(Context context) {
        Intent intent = new Intent(context, ActivityReceipt.class);
        context.startActivity(intent);
        ((Activity)context).overridePendingTransition(R.anim.fragment_slide_right_enter,
                R.anim.fragment_slide_left_exit);
    }


    public static void jumpToDetail(Context context, String title, int type) {
        Intent intent = new Intent(context, ActivityDetail.class);
        intent.putExtra(ActivityDetail.PARAM_TYPE_TITLE,title);
        intent.putExtra(ActivityDetail.PARAM_TYPE_CONTENT,type);
        context.startActivity(intent);
        ((Activity)context).overridePendingTransition(R.anim.fragment_slide_right_enter,
                R.anim.fragment_slide_left_exit);
    }


//
//    public static void jumpToUserEditDetail(Context context) {
//        Intent intent = new Intent(context, ActivityUserEdit.class);
//        context.startActivity(intent);
//        ((Activity)context).overridePendingTransition(R.anim.fragment_slide_right_enter,
//                R.anim.fragment_slide_left_exit);
//    }
//
//
//    public static void jumpToUserOperateDetail(Context context, String title, int type) {
//        Intent intent = new Intent(context, ActivityUserDetail.class);
//        intent.putExtra(ActivityUserDetail.PARAM_TYPE_TITLE,title);
//        intent.putExtra(ActivityUserDetail.PARAM_TYPE_CONTENT,type);
//        context.startActivity(intent);
//        ((Activity)context).overridePendingTransition(R.anim.fragment_slide_right_enter,
//                R.anim.fragment_slide_left_exit);
//    }
//
//    public static void jumpToUserMessageDetail(Context context, String title, int type, String plid, String toid) {
//        Intent intent = new Intent(context, ActivityUserDetail.class);
//        intent.putExtra(ActivityUserDetail.PARAM_TYPE_TITLE,title);
//        intent.putExtra(ActivityUserDetail.PARAM_TYPE_CONTENT,type);
//        intent.putExtra("plid",plid);
//        intent.putExtra("toid",toid);
//        context.startActivity(intent);
//        ((Activity)context).overridePendingTransition(R.anim.fragment_slide_right_enter,
//                R.anim.fragment_slide_left_exit);
//    }
//
//    public static void jumpToLogin(Context context) {
//        Intent intent = new Intent(context, ActivityLogin.class);
//        context.startActivity(intent);
//        ((Activity)context).overridePendingTransition(R.anim.fragment_slide_right_enter,
//                R.anim.fragment_slide_left_exit);
//    }
//
//    public static void jumpToRegister(Context context) {
//        Intent intent = new Intent(context, ActivityRegister.class);
//        context.startActivity(intent);
//        ((Activity)context).overridePendingTransition(R.anim.fragment_slide_right_enter,
//                R.anim.fragment_slide_left_exit);
//    }
//
//    public static void jumpToFindPassword(Context context) {
//        Intent intent = new Intent(context, ActivityFindPassword.class);
//        context.startActivity(intent);
//        ((Activity)context).overridePendingTransition(R.anim.fragment_slide_right_enter,
//                R.anim.fragment_slide_left_exit);
//    }
//
//    public static void jumpToForumTypeAllDetail(Context context, int index) {
//        Intent intent = new Intent(context, ActivityForumAllTypeDetail.class);
//        intent.putExtra(ActivityForumAllTypeDetail.FORUM_PAGE, index);
//        context.startActivity(intent);
//        ((Activity)context).overridePendingTransition(R.anim.fragment_slide_right_enter,
//                R.anim.fragment_slide_left_exit);
//    }
//
//    public static void jumpToForumTypeDetail(Context context, ForumTypeModel.ForumTypeItem model) {
//        Intent intent = new Intent(context, ActivityForumTypeDetail.class);
//        intent.putExtra(ActivityForumTypeDetail.PARAM_FORUM_TYPE, model);
//        context.startActivity(intent);
//        ((Activity)context).overridePendingTransition(R.anim.fragment_slide_right_enter,
//                R.anim.fragment_slide_left_exit);
//    }
//
//    public static void jumpToPublishPage(Context context) {
//        Intent intent = new Intent(context, ActivityPublishPage.class);
//        context.startActivity(intent);
//        ((Activity)context).overridePendingTransition(R.anim.publish_slide_bottom_enter,
//                R.anim.fragment_slide_none);
//    }
//
//    public static void jumpToUserHomePage(Context context, String userId) {
//        Intent intent = new Intent(context, ActivityUserHomePage.class);
//        intent.putExtra(ActivityUserHomePage.PARAM_ID, userId);
//        context.startActivity(intent);
//        ((Activity)context).overridePendingTransition(R.anim.fragment_slide_right_enter,
//                R.anim.fragment_slide_left_exit);
//    }
//
//    public static void jumpToForumDetail(Context context, String tid) {
//        Intent intent = new Intent(context, ActivityForumDetail.class);
//        intent.putExtra(ActivityForumDetail.PARAM_NEWS_ID, tid);
//        context.startActivity(intent);
//        ((Activity)context).overridePendingTransition(R.anim.fragment_slide_right_enter,
//                R.anim.fragment_slide_left_exit);
//    }
//
//    public static void jumpToForumSearch(Context context) {
//        Intent intent = new Intent(context, ActivityForumSearch.class);
//        context.startActivity(intent);
//        ((Activity)context).overridePendingTransition(R.anim.fragment_slide_right_enter,
//                R.anim.fragment_slide_left_exit);
//    }

}
