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
import android.widget.LinearLayout;

import com.chain.wallet.spd.R;

public class ChooseLanuageDialog extends Dialog implements View.OnClickListener{

    public IOnProtocolDialogClickListener iOnProtocolDialogClickListener;

    private CheckBox cb_aggrement;

    private Button bt_continue;



    public ChooseLanuageDialog(Context context, IOnProtocolDialogClickListener iOnProtocolDialogClickListener) {
        super(context, com.terry.tcdialoglibrary.R.style.CommonDialog);
        this.iOnProtocolDialogClickListener = iOnProtocolDialogClickListener;
        initDialogAttrs(context);
    }

    public ChooseLanuageDialog(Context context, int themeResId) {
        super(context, themeResId);
        initDialogAttrs(context);
    }

    protected ChooseLanuageDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
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
        setContentView(R.layout.layout_dialog_choose_language);

        LinearLayout ll_container1 = findViewById(R.id.ll_container1);
        ll_container1.setOnClickListener(this);
        LinearLayout ll_container2 = findViewById(R.id.ll_container2);
        ll_container2.setOnClickListener(this);
        LinearLayout ll_container3 = findViewById(R.id.ll_container3);
        ll_container3.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_container1:
            {
                if (iOnProtocolDialogClickListener != null) {
                    iOnProtocolDialogClickListener.onClickBtn(true);
                }
            }
                break;
            case R.id.ll_container2:
            {
                if (iOnProtocolDialogClickListener != null) {
                    iOnProtocolDialogClickListener.onClickBtn(true);
                }
            }
            break;
            case R.id.ll_container3:
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
