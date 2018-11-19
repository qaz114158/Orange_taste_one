package net.sourceforge.UI.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chain.wallet.spd.R;
import com.facebook.drawee.view.SimpleDraweeView;

import net.sourceforge.base.FragmentBase;
import net.sourceforge.commons.log.SWLog;
import net.sourceforge.http.model.WalletModel;
import net.sourceforge.manager.AppImageLoader;
import net.sourceforge.manager.WalletManager;
import net.sourceforge.utils.DMG;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.finalteam.rxgalleryfinal.RxGalleryFinal;
import cn.finalteam.rxgalleryfinal.bean.MediaBean;
import cn.finalteam.rxgalleryfinal.imageloader.ImageLoaderType;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultDisposable;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;

/**
 * Created by terry.c on 06/03/2018.
 */

public class FragmentAuthMiddle extends FragmentBase {

    public static final String TAG = FragmentAuthMiddle.class.getSimpleName();

    private View curView = null;

    private Unbinder unbinder;

    @BindView(R.id.iv_image)
    public SimpleDraweeView iv_image;

    public static FragmentAuthMiddle newInstance() {
        FragmentAuthMiddle f = new FragmentAuthMiddle();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (null != curView && curView.getParent()!=null) {
            ((ViewGroup) curView.getParent()).removeView(curView);
            return curView;
        }
        curView = inflater.inflate(R.layout.layout_auth_middle, null);
        unbinder = ButterKnife.bind(this, curView);
        initRes();
        return curView;
    }

    private void initRes() {

    }

    @Override
    public void onResume() {
        super.onResume();
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

    @OnClick(R.id.bt_next)
    public void onClickNext() {
        DMG.showNomalShortToast("已提交认证申请");
        WalletModel walletModel = WalletManager.getInstance().getCurrentWallet();
        walletModel.auth = 1;
        WalletManager.getInstance().updateWalletAuth(walletModel);
        getActivity().finish();
    }

    @OnClick(R.id.rl_choose_image)
    public void onClickChooseImage() {
        RxGalleryFinal rxGalleryFinal = RxGalleryFinal
                .with(mContext)
                .hideCamera()
                .image().radio();
        rxGalleryFinal
                .imageLoader(ImageLoaderType.FRESCO)
                .subscribe(new RxBusResultDisposable<ImageRadioResultEvent>() {

                    @Override
                    protected void onEvent(ImageRadioResultEvent imageMultipleResultEvent) throws Exception {
                        List<MediaBean> mediaBeans = new ArrayList<>(1);
                        MediaBean mediaBean = new MediaBean();
                        mediaBean.setOriginalPath(imageMultipleResultEvent.getResult().getOriginalPath());
                        mediaBeans.add(mediaBean);
                        dealWithTakePicFromGallery(mediaBeans);
//                        list = imageMultipleResultEvent.getResult();
//                        SWLog.d(TAG(), "list.size():" + list.size());
//                        Toast.makeText(getBaseContext(), "已选择" + imageMultipleResultEvent.getResult().size() + "张图片", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
//                        Toast.makeText(getBaseContext(), "OVER", Toast.LENGTH_SHORT).show();
                    }
                })
                .openGallery();
    }


    public void dealWithTakePicFromGallery(List<MediaBean> list) {
        if (list != null && list.size() >0) {
            AppImageLoader.getInstance().displayLocalSDCardImage(list.get(0).getOriginalPath(), iv_image);
        }

    }
}
