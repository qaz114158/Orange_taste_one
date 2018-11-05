package net.sourceforge.manager;

import android.net.Uri;
import android.text.TextUtils;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import net.sourceforge.application.AppApplication;
import net.sourceforge.utils.AppUtils;

/**
 * Created by Terry on 1/16/17.
 */

public class AppImageLoader {
    private static final AppImageLoader ourInstance = new AppImageLoader();

    public static AppImageLoader getInstance() {
        return ourInstance;
    }

    private AppImageLoader() {
    }

    public void displayImage(String url, SimpleDraweeView draweeView) {
        if (TextUtils.isEmpty(url)) {
            url = "";
        }
        Uri uri = Uri.parse(url);
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(AppUtils.dp2px(AppApplication.getInstance(),140), AppUtils.dp2px(AppApplication.getInstance(),90)))
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setControllerListener(new BaseControllerListener<ImageInfo>())
                .setOldController(draweeView.getController())
                .setImageRequest(request)
                .setUri(uri)
                .build();
        draweeView.setController(controller);
    }

    public void displayADImage(String url, SimpleDraweeView draweeView) {
        if (TextUtils.isEmpty(url)) {
            url = "";
        }
        Uri uri = Uri.parse(url);
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(AppUtils.dp2px(AppApplication.getInstance(),720), AppUtils.dp2px(AppApplication.getInstance(),1080)))
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setControllerListener(new BaseControllerListener<ImageInfo>())
                .setOldController(draweeView.getController())
                .setImageRequest(request)
                .setUri(uri)
                .build();
        draweeView.setController(controller);
    }

    public void displayLocalSDCardImage(String url, SimpleDraweeView draweeView) {
        if (TextUtils.isEmpty(url)) {
            url = "";
        }
        Uri uri = Uri.parse("file://" + url);

        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(AppUtils.dp2px(AppApplication.getInstance(),140), AppUtils.dp2px(AppApplication.getInstance(),90)))
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setControllerListener(new BaseControllerListener<ImageInfo>())
                .setOldController(draweeView.getController())
                .setImageRequest(request)
                .setUri(uri)
                .build();
        draweeView.setController(controller);
    }

}
