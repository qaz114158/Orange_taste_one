package net.sourceforge.UI.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.chain.wallet.spd.R;

import net.sourceforge.base.FragmentBase;
import net.sourceforge.utils.AppUtils;

/**
 * 
 * 广告栏图片显示和点击
 *
 */
public class ShowImageFragment extends FragmentBase {

	private static final String TAG = ShowImageFragment.class.getSimpleName();

	private String imageUrl;

	private String data;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public static ShowImageFragment newInstance(String imageUrl, String targetUrl) {
		ShowImageFragment f = new ShowImageFragment();
		f.imageUrl = imageUrl;
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//		data = (RecommendModel.RecommendBean) getArguments().getSerializable("BannerModel");
//		imageUrl = data.image1;
//		Log.d(TAG, "onCreateView");
		ImageView imageView = new ImageView(getActivity());
		imageView.setMaxHeight(AppUtils.dp2px(mContext, 100));
		imageView.setMaxWidth(AppUtils.dp2px(mContext, 500));
		imageView.setImageResource(R.drawable.ic_launcher_foreground);
		imageView.setScaleType(ScaleType.CENTER_CROP);
//		AppImageLoader.getInstance().displayImage(this, imageUrl, imageView, Options.getBannerListOption());
		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				click();
			}
		});
		return imageView;
	}

	private void click() {
		if (data != null) {
//			jump(data.type);
		}
	}

	private void jump(int type) {
		switch (type) {
//		case 1:// 跳转到直播
//			skipShowDetail();
//			break;
//		case 2:// 网页
//			if (!TextUtils.isEmpty(data.remark)) {
//				JumpMethod.jumpToChipWebDetail1(mContext, data.remark, data.title, true);
//			}
//			break;
//		}

		}
	}
}
