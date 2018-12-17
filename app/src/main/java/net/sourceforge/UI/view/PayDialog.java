package net.sourceforge.UI.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.chain.wallet.spd.R;
import com.uuzuche.lib_zxing.activity.CodeUtils;

public class PayDialog extends Dialog implements View.OnClickListener{

    public IOnProtocolDialogClickListener iOnProtocolDialogClickListener;

    private CheckBox cb_aggrement;

    private Button bt_continue;

    private TextView tv_name;

    public PayDialog(Context context, IOnProtocolDialogClickListener iOnProtocolDialogClickListener) {
        super(context, com.terry.tcdialoglibrary.R.style.CommonDialog);
        this.iOnProtocolDialogClickListener = iOnProtocolDialogClickListener;
        initDialogAttrs(context);
    }

    public PayDialog(Context context, int themeResId) {
        super(context, themeResId);
        initDialogAttrs(context);
    }

    protected PayDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initDialogAttrs(context);
    }

    protected void initDialogAttrs(Context paramContext)
    {
        setCanceledOnTouchOutside(false);
        getWindow().getAttributes().width = ViewGroup.LayoutParams.WRAP_CONTENT;
        getWindow().getAttributes().height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getWindow().getAttributes().y = 0;
        getWindow().setGravity(Gravity.CENTER_VERTICAL);
        getWindow().setAttributes(getWindow().getAttributes());
        if ((paramContext instanceof Activity)) {
            setOwnerActivity((Activity)paramContext);
        }
        setContentView(R.layout.layout_dialog_pay);

        Button bt_confirm = findViewById(R.id.bt_confirm);
        bt_confirm.setOnClickListener(this);

        tv_name = findViewById(R.id.tv_name);

        ImageView iv_image = findViewById(R.id.iv_image);

        Bitmap mBitmap = CodeUtils.createImage("shoukuan:0", 400, 400, null);
        iv_image.setImageBitmap(mBitmap);
    }

    public void setName(String name) {
        tv_name.setText(name);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_confirm:
            {
                if (iOnProtocolDialogClickListener != null) {
                    iOnProtocolDialogClickListener.onClickBtn(true);
                }
            }
            break;

        }
    }

    public interface IOnProtocolDialogClickListener {

        public void onClickBtn(boolean isConform);

    }

}
