package net.sourceforge.UI.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;

import com.chain.wallet.spd.R;

public class ChooseAddOtcDialog extends Dialog implements View.OnClickListener{

    public IOnProtocolDialogClickListener iOnProtocolDialogClickListener;

    private CheckBox cb_aggrement;

    private Button bt_continue;

    public ChooseAddOtcDialog(Context context, IOnProtocolDialogClickListener iOnProtocolDialogClickListener) {
        super(context, com.terry.tcdialoglibrary.R.style.CommonDialog);
        this.iOnProtocolDialogClickListener = iOnProtocolDialogClickListener;
        initDialogAttrs(context);
    }

    public ChooseAddOtcDialog(Context context, int themeResId) {
        super(context, themeResId);
        initDialogAttrs(context);
    }

    protected ChooseAddOtcDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
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
        setContentView(R.layout.layout_dialog_choose_otc);

        ImageButton ib_paypal = findViewById(R.id.ib_paypal);
        ib_paypal.setOnClickListener(this);
        ImageButton ib_wechat = findViewById(R.id.ib_wechat);
        ib_wechat.setOnClickListener(this);
        ImageButton ib_alipay = findViewById(R.id.ib_alipay);
        ib_alipay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_paypal:
            case R.id.ib_alipay:
            case R.id.ib_wechat:
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
