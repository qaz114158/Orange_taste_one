package net.sourceforge.UI.view;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.chain.wallet.spd.R;

import net.lucode.hackware.magicindicator.NavigatorHelper;
import net.lucode.hackware.magicindicator.abs.IPagerNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IMeasurablePagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.model.PositionData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by terry.c on 24/03/2018.
 */

public class CustomCommonNavigator extends FrameLayout implements IPagerNavigator, NavigatorHelper.OnNavigatorScrollListener {
    private HorizontalScrollView mScrollView;
    private LinearLayout mTitleContainer;
    private LinearLayout mIndicatorContainer;
    private IPagerIndicator mIndicator;
    private CommonNavigatorAdapter mAdapter;
    private NavigatorHelper mNavigatorHelper = new NavigatorHelper();
    private boolean mAdjustMode;
    private boolean mEnablePivotScroll;
    private float mScrollPivotX = 0.5F;
    private boolean mSmoothScroll = true;
    private boolean mFollowTouch = true;
    private int mRightPadding;
    private int mLeftPadding;
    private boolean mIndicatorOnTop;
    private boolean mSkimOver;
    private boolean mReselectWhenLayout = true;
    private List<PositionData> mPositionDataList = new ArrayList();
    private DataSetObserver mObserver = new DataSetObserver() {
        public void onChanged() {
            CustomCommonNavigator.this.mNavigatorHelper.setTotalCount(CustomCommonNavigator.this.mAdapter.getCount());
            CustomCommonNavigator.this.init();
        }

        public void onInvalidated() {
        }
    };

    public CustomCommonNavigator(Context context) {
        super(context);
        this.mNavigatorHelper.setNavigatorScrollListener(this);
    }

    public void notifyDataSetChanged() {
        if(this.mAdapter != null) {
            this.mAdapter.notifyDataSetChanged();
        }

    }

    public boolean isAdjustMode() {
        return this.mAdjustMode;
    }

    public void setAdjustMode(boolean is) {
        this.mAdjustMode = is;
    }

    public CommonNavigatorAdapter getAdapter() {
        return this.mAdapter;
    }

    public void setAdapter(CommonNavigatorAdapter adapter) {
        if(this.mAdapter != adapter) {
            if(this.mAdapter != null) {
                this.mAdapter.unregisterDataSetObserver(this.mObserver);
            }

            this.mAdapter = adapter;
            if(this.mAdapter != null) {
                this.mAdapter.registerDataSetObserver(this.mObserver);
                this.mNavigatorHelper.setTotalCount(this.mAdapter.getCount());
                if(this.mTitleContainer != null) {
                    this.mAdapter.notifyDataSetChanged();
                }
            } else {
                this.mNavigatorHelper.setTotalCount(0);
                this.init();
            }

        }
    }

    private void init() {
        this.removeAllViews();
        View root;
        if(this.mAdjustMode) {
            root = LayoutInflater.from(this.getContext()).inflate(net.lucode.hackware.magicindicator.R.layout.pager_navigator_layout_no_scroll, this);
        } else {
            root = LayoutInflater.from(this.getContext()).inflate(R.layout.pager_navigator_layout, this);
        }

        this.mScrollView = (HorizontalScrollView)root.findViewById(net.lucode.hackware.magicindicator.R.id.scroll_view);
        this.mScrollView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        this.mTitleContainer = (LinearLayout)root.findViewById(net.lucode.hackware.magicindicator.R.id.title_container);
        this.mTitleContainer.setPadding(this.mLeftPadding, 0, this.mRightPadding, 0);
        this.mIndicatorContainer = (LinearLayout)root.findViewById(net.lucode.hackware.magicindicator.R.id.indicator_container);
        if(this.mIndicatorOnTop) {
            this.mIndicatorContainer.getParent().bringChildToFront(this.mIndicatorContainer);
        }

        this.initTitlesAndIndicator();
    }

    private void initTitlesAndIndicator() {
        int i = 0;

        for(int j = this.mNavigatorHelper.getTotalCount(); i < j; ++i) {
            IPagerTitleView v = this.mAdapter.getTitleView(this.getContext(), i);
            if(v instanceof View) {
                View view = (View)v;
                LinearLayout.LayoutParams lp;
                if(this.mAdjustMode) {
                    lp = new LinearLayout.LayoutParams(0, -1);
                    lp.weight = this.mAdapter.getTitleWeight(this.getContext(), i);
                } else {
                    lp = new LinearLayout.LayoutParams(-2, -1);
                }

                this.mTitleContainer.addView(view, lp);
            }
        }

        if(this.mAdapter != null) {
            this.mIndicator = this.mAdapter.getIndicator(this.getContext());
            if(this.mIndicator instanceof View) {
                android.widget.FrameLayout.LayoutParams lp = new android.widget.FrameLayout.LayoutParams(-1, -1);
                this.mIndicatorContainer.addView((View)this.mIndicator, lp);
            }
        }

    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if(this.mAdapter != null) {
            this.preparePositionData();
            if(this.mIndicator != null) {
                this.mIndicator.onPositionDataProvide(this.mPositionDataList);
            }

            if(this.mReselectWhenLayout && this.mNavigatorHelper.getScrollState() == 0) {
                this.onPageSelected(this.mNavigatorHelper.getCurrentIndex());
                this.onPageScrolled(this.mNavigatorHelper.getCurrentIndex(), 0.0F, 0);
            }
        }

    }

    private void preparePositionData() {
        this.mPositionDataList.clear();
        int i = 0;

        for(int j = this.mNavigatorHelper.getTotalCount(); i < j; ++i) {
            PositionData data = new PositionData();
            View v = this.mTitleContainer.getChildAt(i);
            if(v != null) {
                data.mLeft = v.getLeft();
                data.mTop = v.getTop();
                data.mRight = v.getRight();
                data.mBottom = v.getBottom();
                if(v instanceof IMeasurablePagerTitleView) {
                    IMeasurablePagerTitleView view = (IMeasurablePagerTitleView)v;
                    data.mContentLeft = view.getContentLeft();
                    data.mContentTop = view.getContentTop();
                    data.mContentRight = view.getContentRight();
                    data.mContentBottom = view.getContentBottom();
                } else {
                    data.mContentLeft = data.mLeft;
                    data.mContentTop = data.mTop;
                    data.mContentRight = data.mRight;
                    data.mContentBottom = data.mBottom;
                }
            }

            this.mPositionDataList.add(data);
        }

    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if(this.mAdapter != null) {
            this.mNavigatorHelper.onPageScrolled(position, positionOffset, positionOffsetPixels);
            if(this.mIndicator != null) {
                this.mIndicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            if(this.mScrollView != null && this.mPositionDataList.size() > 0 && position >= 0 && position < this.mPositionDataList.size()) {
                if(this.mFollowTouch) {
                    int currentPosition = Math.min(this.mPositionDataList.size() - 1, position);
                    int nextPosition = Math.min(this.mPositionDataList.size() - 1, position + 1);
                    PositionData current = (PositionData)this.mPositionDataList.get(currentPosition);
                    PositionData next = (PositionData)this.mPositionDataList.get(nextPosition);
                    float scrollTo = (float)current.horizontalCenter() - (float)this.mScrollView.getWidth() * this.mScrollPivotX;
                    float nextScrollTo = (float)next.horizontalCenter() - (float)this.mScrollView.getWidth() * this.mScrollPivotX;
                    this.mScrollView.scrollTo((int)(scrollTo + (nextScrollTo - scrollTo) * positionOffset), 0);
                } else if(!this.mEnablePivotScroll) {
                    ;
                }
            }
        }

    }

    public float getScrollPivotX() {
        return this.mScrollPivotX;
    }

    public void setScrollPivotX(float scrollPivotX) {
        this.mScrollPivotX = scrollPivotX;
    }

    public void onPageSelected(int position) {
        if(this.mAdapter != null) {
            this.mNavigatorHelper.onPageSelected(position);
            if(this.mIndicator != null) {
                this.mIndicator.onPageSelected(position);
            }
        }

    }

    public void onPageScrollStateChanged(int state) {
        if(this.mAdapter != null) {
            this.mNavigatorHelper.onPageScrollStateChanged(state);
            if(this.mIndicator != null) {
                this.mIndicator.onPageScrollStateChanged(state);
            }
        }

    }

    public void onAttachToMagicIndicator() {
        this.init();
    }

    public void onDetachFromMagicIndicator() {
    }

    public IPagerIndicator getPagerIndicator() {
        return this.mIndicator;
    }

    public boolean isEnablePivotScroll() {
        return this.mEnablePivotScroll;
    }

    public void setEnablePivotScroll(boolean is) {
        this.mEnablePivotScroll = is;
    }

    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
        if(this.mTitleContainer != null) {
            View v = this.mTitleContainer.getChildAt(index);
            if(v instanceof IPagerTitleView) {
                ((IPagerTitleView)v).onEnter(index, totalCount, enterPercent, leftToRight);
            }

        }
    }

    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
        if(this.mTitleContainer != null) {
            View v = this.mTitleContainer.getChildAt(index);
            if(v instanceof IPagerTitleView) {
                ((IPagerTitleView)v).onLeave(index, totalCount, leavePercent, leftToRight);
            }

        }
    }

    public boolean isSmoothScroll() {
        return this.mSmoothScroll;
    }

    public void setSmoothScroll(boolean smoothScroll) {
        this.mSmoothScroll = smoothScroll;
    }

    public boolean isFollowTouch() {
        return this.mFollowTouch;
    }

    public void setFollowTouch(boolean followTouch) {
        this.mFollowTouch = followTouch;
    }

    public boolean isSkimOver() {
        return this.mSkimOver;
    }

    public void setSkimOver(boolean skimOver) {
        this.mSkimOver = skimOver;
        this.mNavigatorHelper.setSkimOver(skimOver);
    }

    public void onSelected(int index, int totalCount) {
        if(this.mTitleContainer != null) {
            View v = this.mTitleContainer.getChildAt(index);
            if(v instanceof IPagerTitleView) {
                ((IPagerTitleView)v).onSelected(index, totalCount);
            }

            if(!this.mAdjustMode && !this.mFollowTouch && this.mScrollView != null && this.mPositionDataList.size() > 0) {
                int currentIndex = Math.min(this.mPositionDataList.size() - 1, index);
                PositionData current = (PositionData)this.mPositionDataList.get(currentIndex);
                if(this.mEnablePivotScroll) {
                    float scrollTo = (float)current.horizontalCenter() - (float)this.mScrollView.getWidth() * this.mScrollPivotX;
                    if(this.mSmoothScroll) {
                        this.mScrollView.smoothScrollTo((int)scrollTo, 0);
                    } else {
                        this.mScrollView.scrollTo((int)scrollTo, 0);
                    }
                } else if(this.mScrollView.getScrollX() > current.mLeft) {
                    if(this.mSmoothScroll) {
                        this.mScrollView.smoothScrollTo(current.mLeft, 0);
                    } else {
                        this.mScrollView.scrollTo(current.mLeft, 0);
                    }
                } else if(this.mScrollView.getScrollX() + this.getWidth() < current.mRight) {
                    if(this.mSmoothScroll) {
                        this.mScrollView.smoothScrollTo(current.mRight - this.getWidth(), 0);
                    } else {
                        this.mScrollView.scrollTo(current.mRight - this.getWidth(), 0);
                    }
                }
            }

        }
    }

    public void onDeselected(int index, int totalCount) {
        if(this.mTitleContainer != null) {
            View v = this.mTitleContainer.getChildAt(index);
            if(v instanceof IPagerTitleView) {
                ((IPagerTitleView)v).onDeselected(index, totalCount);
            }

        }
    }

    public IPagerTitleView getPagerTitleView(int index) {
        return this.mTitleContainer == null?null:(IPagerTitleView)this.mTitleContainer.getChildAt(index);
    }

    public LinearLayout getTitleContainer() {
        return this.mTitleContainer;
    }

    public int getRightPadding() {
        return this.mRightPadding;
    }

    public void setRightPadding(int rightPadding) {
        this.mRightPadding = rightPadding;
    }

    public int getLeftPadding() {
        return this.mLeftPadding;
    }

    public void setLeftPadding(int leftPadding) {
        this.mLeftPadding = leftPadding;
    }

    public boolean isIndicatorOnTop() {
        return this.mIndicatorOnTop;
    }

    public void setIndicatorOnTop(boolean indicatorOnTop) {
        this.mIndicatorOnTop = indicatorOnTop;
    }

    public boolean isReselectWhenLayout() {
        return this.mReselectWhenLayout;
    }

    public void setReselectWhenLayout(boolean reselectWhenLayout) {
        this.mReselectWhenLayout = reselectWhenLayout;
    }
}
