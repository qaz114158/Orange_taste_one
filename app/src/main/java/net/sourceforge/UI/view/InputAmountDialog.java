package net.sourceforge.UI.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.chain.wallet.spd.R;

public class InputAmountDialog extends Dialog implements View.OnClickListener {

    public IOnProtocolDialogClickListener iOnProtocolDialogClickListener;

    private CheckBox cb_aggrement;

    private Button bt_continue;

    private EditText et_count;

    public InputAmountDialog(Context context, IOnProtocolDialogClickListener iOnProtocolDialogClickListener) {
        super(context, com.terry.tcdialoglibrary.R.style.CommonDialog);
        this.iOnProtocolDialogClickListener = iOnProtocolDialogClickListener;
        initDialogAttrs(context);
    }

    public InputAmountDialog(Context context, int themeResId) {
        super(context, themeResId);
        initDialogAttrs(context);
    }

    protected InputAmountDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initDialogAttrs(context);
    }

    protected void initDialogAttrs(Context paramContext) {
        setCanceledOnTouchOutside(false);
        getWindow().getAttributes().width = ViewGroup.LayoutParams.WRAP_CONTENT;
        getWindow().getAttributes().height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getWindow().getAttributes().y = 0;
        getWindow().setGravity(Gravity.CENTER_VERTICAL);
        getWindow().setAttributes(getWindow().getAttributes());
        if ((paramContext instanceof Activity)) {
            setOwnerActivity((Activity) paramContext);
        }
        setContentView(R.layout.layout_dialog_input_amount);

        Button btn_cancel = findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(this);
        Button btn_confirm = findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(this);

        et_count = findViewById(R.id.et_count);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm: {

                if (iOnProtocolDialogClickListener != null) {
                    String count = et_count.getText().toString();
                    iOnProtocolDialogClickListener.onClickBtn(true, count);
                }

            }
            break;
            case R.id.btn_cancel: {
                if (iOnProtocolDialogClickListener != null) {
                    String count = et_count.getText().toString();
                    iOnProtocolDialogClickListener.onClickBtn(false, count);
                }
            }
            break;
        }
    }

    public interface IOnProtocolDialogClickListener {

        public void onClickBtn(boolean isConform, String count);

    }

}
