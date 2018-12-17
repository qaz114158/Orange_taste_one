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

public class ChoosePayTypeDialog extends Dialog implements View.OnClickListener{

    public IOnProtocolDialogClickListener iOnProtocolDialogClickListener;

    private CheckBox cb_aggrement;

    private Button bt_continue;

    public ChoosePayTypeDialog(Context context, IOnProtocolDialogClickListener iOnProtocolDialogClickListener) {
        super(context, com.terry.tcdialoglibrary.R.style.CommonDialog);
        this.iOnProtocolDialogClickListener = iOnProtocolDialogClickListener;
        initDialogAttrs(context);
    }

    public ChoosePayTypeDialog(Context context, int themeResId) {
        super(context, themeResId);
        initDialogAttrs(context);
    }

    protected ChoosePayTypeDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
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
        setContentView(R.layout.layout_dialog_choose_pay_type);

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
            {
                if (iOnProtocolDialogClickListener != null) {
                    iOnProtocolDialogClickListener.onClickBtn(1);
                }
            }
                break;
            case R.id.ib_wechat:
            {
                if (iOnProtocolDialogClickListener != null) {
                    iOnProtocolDialogClickListener.onClickBtn(2);
                }
            }
                break;
            case R.id.ib_alipay:
            {
                if (iOnProtocolDialogClickListener != null) {
                    iOnProtocolDialogClickListener.onClickBtn(3);
                }
            }
            break;

        }
    }

    public interface IOnProtocolDialogClickListener {

        public void onClickBtn(int payType);

    }

}
