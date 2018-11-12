package net.sourceforge.UI.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chain.wallet.spd.R;

import net.sourceforge.UI.adapter.BannerPageAdapter;

import java.util.List;

public class WalletADPageView extends LinearLayout implements ViewPager.OnPageChangeListener {

    private Context mContext;

    private ViewPager viewPager;

    private ImageView[] points;

    private BannerPageAdapter pagerAdapter;

    private LinearLayout ll_points;

    private FragmentManager fragmentManager;

    // 自动轮播启用开关
    private boolean isAutoPlay = false;
    /** 当前页数 */
    private int currentPage;

    private int screenWidth;

    private List<ImageView> views;

    public WalletADPageView(Context context) {
        this(context, null);
    }

    public WalletADPageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WalletADPageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initScreenInfo();
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_show_header, this, true);

        viewPager = (ViewPager) findViewById(R.id.pager);
//        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, screenWidth/3);
//        viewPager.setLayoutParams(lp);
//        viewPager.setPageMargin(10);
        viewPager.addOnPageChangeListener(this);
        ll_points = (LinearLayout) findViewById(R.id.ll_points);
        viewPager.setAdapter(new BannerPageAdapter(context, null));

        addPoints(3);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        currentPage = position;
        if (null != points) {
            int posi = position % points.length;
            setImageBackground(posi);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void setImageBackground(int selectItems) {
        if (points != null) {
            for (int i = 0; i < points.length; i++) {
                if (i == selectItems) {
                    points[i].setBackgroundResource(R.drawable.page_dot_focused);
                } else {
                    points[i].setBackgroundResource(R.drawable.page_dot_un_focused);
                }
            }
        }
    }

    private void addPoints(int size) {
        ll_points.removeAllViews();
        points = new ImageView[size];
        for (int i = 0; i < size; i++) {
            points[i] = new ImageView(mContext);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            layoutParams.leftMargin = 5;
            layoutParams.rightMargin = 5;
            if (i == 0) {
                points[i].setBackgroundResource(R.drawable.page_dot_focused);
            } else {
                points[i].setBackgroundResource(R.drawable.page_dot_un_focused);
            }
            ll_points.addView(points[i], layoutParams);
        }
    }

    private void initScreenInfo() {
        WindowManager wm = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        screenWidth = outMetrics.widthPixels;
    }
}
