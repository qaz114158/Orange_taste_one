package net.sourceforge.base;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chain.wallet.spd.R;

import net.sourceforge.UI.view.WheelDialog;
import net.sourceforge.commons.log.SWLog;
import net.sourceforge.manager.AppActivityManager;
import net.sourceforge.utils.StatusBarUtil;

/**
 * Created by terry.c on 05/03/2018.
 */

public class ActivityBase extends AppCompatActivity {

    protected Context mContext;

    private RelativeLayout _naviBar;

    private ImageView _leftImageBtn;

    protected TextView _titleTextView;

    private ImageView _rightImageBtn;

    private WheelDialog mWheelDialog;

    private TextView _tvRightTextView;

    private TextView iv_right_text_wrap_content;

    private ImageView iv_right_image2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        AppActivityManager.getInstance().pushActivity(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        initTitleBar();
        StatusBarUtil.setStatuBarAndFontColor(this, getResources().getColor(R.color.colorPrimary), false);
    }

    private void initTitleBar() {
        _naviBar = findViewById(R.id.rl_nav);
        _leftImageBtn = findViewById(R.id.iv_left_image);
        _titleTextView = findViewById(R.id.toolbar_title);
        _rightImageBtn = findViewById(R.id.iv_right_image);
        _tvRightTextView = findViewById(R.id.iv_right_text);
    }

    protected void setTitle(String title) {
        if (_titleTextView != null) {
            _titleTextView.setVisibility(View.VISIBLE);
            _titleTextView.setText(title);
        }
    }

    protected void setLeftBtnBackPrecious() {
        if (_leftImageBtn != null) {
            _leftImageBtn.setVisibility(View.VISIBLE);
            _leftImageBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
    }

    protected void setLeftSelector(View.OnClickListener clickListener) {
        if (_leftImageBtn != null) {
            _leftImageBtn.setVisibility(View.VISIBLE);
            _leftImageBtn.setOnClickListener(clickListener);
        }
    }

    protected void setRightImageAndSelector(int imgResourceId, View.OnClickListener onClickListener) {
        if (_rightImageBtn != null) {
            _rightImageBtn.setVisibility(View.VISIBLE);
            _rightImageBtn.setImageResource(imgResourceId);
            _rightImageBtn.setOnClickListener(onClickListener);
        }
    }

    protected void setRightImage2AndSelector(int imgResourceId, View.OnClickListener onClickListener) {
        if (iv_right_image2 == null) {
            iv_right_image2 = findViewById(R.id.iv_right_image2);
        }
        if (iv_right_image2 != null) {
            iv_right_image2.setVisibility(View.VISIBLE);
            iv_right_image2.setImageResource(imgResourceId);
            iv_right_image2.setOnClickListener(onClickListener);
        }
    }

    protected void setRightTextAndSelector(String text, int textColor, View.OnClickListener onClickListener) {
        if (_tvRightTextView != null) {
            _tvRightTextView.setVisibility(View.VISIBLE);
            _tvRightTextView.setTextColor(textColor);
            _tvRightTextView.setText(text);
            _tvRightTextView.setOnClickListener(onClickListener);
        }
    }

    protected void setRightTextAndColor(String text, int textColor) {
        if (_tvRightTextView != null) {
            _tvRightTextView.setVisibility(View.VISIBLE);
            _tvRightTextView.setTextColor(textColor);
            _tvRightTextView.setText(text);
        }
    }

    protected void setLeftImageAndSelector(int imageResource, View.OnClickListener onClickListener) {
        if (_leftImageBtn != null) {
            _leftImageBtn.setVisibility(View.VISIBLE);
            _leftImageBtn.setImageResource(imageResource);
            _leftImageBtn.setOnClickListener(onClickListener);
        }
    }

    protected void setRightTextBackGroundAndSelector(String text, int backgroundDrawableId, int textColor, View.OnClickListener onClickListener) {
        if (iv_right_text_wrap_content == null) {
            iv_right_text_wrap_content = findViewById(R.id.iv_right_text_wrap_content);
        }
        if (iv_right_text_wrap_content != null) {
            iv_right_text_wrap_content.setVisibility(View.VISIBLE);
            iv_right_text_wrap_content.setText(text);
            iv_right_text_wrap_content.setBackgroundResource(backgroundDrawableId);
            iv_right_text_wrap_content.setTextColor(textColor);
            iv_right_text_wrap_content.setOnClickListener(onClickListener);
        }
    }


    protected void setSysteStatuBarColor(int statusColor) {
        Window window = getWindow();
        //取消状态栏透明
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //添加Flag把状态栏设为可绘制模式
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(statusColor);
        }
        //设置系统状态栏处于可见状态
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        //让view不根据系统窗口来调整自己的布局
        ViewGroup mContentView = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            ViewCompat.setFitsSystemWindows(mChildView, false);
            ViewCompat.requestApplyInsets(mChildView);
        }
    }

    @Override
    public void finish() {
        super.finish();
        AppActivityManager.getInstance().popActivity(this);
        overridePendingTransition(R.anim.fragment_slide_left_enter,
                R.anim.fragment_slide_right_exit);// default animation for
        // finish
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SWLog.d(TAG(), "call onDestroy()");
    }

    protected String TAG() {
        return this.getClass().getSimpleName();
    }

    protected void showProgressDialog(String message) {
        if (mWheelDialog == null) {
            mWheelDialog = new WheelDialog(mContext, R.style.WheelDialog);
        }
        if (mWheelDialog.isShowing()) {
            return;
        }
        if (TextUtils.isEmpty(message)) {
            mWheelDialog.setMessage("请稍后..");
        } else {
            mWheelDialog.setMessage(message);
        }
        mWheelDialog.show();
    }

    protected void hideProgressDialog() {
        if (mWheelDialog != null && mWheelDialog.isShowing()) {
            mWheelDialog.dismiss();
        }
    }

    protected void enterActivityWithoutFinish(Intent intent) {
        startActivity(intent);
        overridePendingTransition(R.anim.fragment_slide_right_enter,
                R.anim.fragment_slide_left_exit);
    }

    protected void jumpToActivity(Context context, Class cls) {
        Intent intent = new Intent(context, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
