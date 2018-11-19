package net.sourceforge.UI.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.chain.wallet.spd.R;

public class TransferDialog1 extends Dialog implements View.OnClickListener{

    public IOnProtocolDialogClickListener iOnProtocolDialogClickListener;

    private CheckBox cb_aggrement;

    private Button bt_continue;

    private TextView tv_money;
    private TextView tv_address;
    private TextView tv_remark;

    public TransferDialog1(Context context, IOnProtocolDialogClickListener iOnProtocolDialogClickListener) {
        super(context, com.terry.tcdialoglibrary.R.style.CommonDialog);
        this.iOnProtocolDialogClickListener = iOnProtocolDialogClickListener;
        initDialogAttrs(context);
    }

    public TransferDialog1(Context context, int themeResId) {
        super(context, themeResId);
        initDialogAttrs(context);
    }

    protected TransferDialog1(Context context, boolean cancelable, OnCancelListener cancelListener) {
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
        setContentView(R.layout.layout_dialog_transfer1);

        Button btn_cancel = findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(this);
        Button btn_confirm = findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(this);

        tv_money = findViewById(R.id.tv_money);
        tv_address = findViewById(R.id.tv_address);
        tv_remark = findViewById(R.id.tv_remark);

    }

    public void setData(String address, String money, String remark) {
        tv_money.setText(money + " SPDT");
        tv_address.setText(address);
        tv_remark.setText(remark);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm:
            {
                {
                    if (iOnProtocolDialogClickListener != null) {
                        iOnProtocolDialogClickListener.onClickBtn(true);
                    }
                }
            }
                break;
            case R.id.btn_cancel:
            {
                if (iOnProtocolDialogClickListener != null) {
                    iOnProtocolDialogClickListener.onClickBtn(false);
                }
            }
                break;
        }
    }

    public interface IOnProtocolDialogClickListener {

        public void onClickBtn(boolean isConform);

    }

}
