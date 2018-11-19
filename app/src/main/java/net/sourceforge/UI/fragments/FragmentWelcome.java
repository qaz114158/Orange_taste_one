package net.sourceforge.UI.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.chain.wallet.spd.R;

import net.sourceforge.UI.activity.ActivityMain;
import net.sourceforge.UI.activity.ActivityPreCreateAccount;
import net.sourceforge.base.FragmentBase;
import net.sourceforge.http.model.WalletModel;
import net.sourceforge.manager.WalletManager;
import net.sourceforge.utils.PreferenceHelper;

import java.util.List;

public class FragmentWelcome extends FragmentBase implements View.OnClickListener {

	private int resourceId;
	
	private boolean showIn;
	
	public static FragmentWelcome newInstance(int resourceId, boolean showIn) {
		FragmentWelcome fragment = new FragmentWelcome();
		fragment.resourceId = resourceId;
		fragment.showIn = showIn;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.item_welcome,
				container, false);
    	ImageView imageView = (ImageView) view.findViewById(R.id.iv_icon);
    	imageView.setImageResource(resourceId);

        ImageButton ib_enter = view.findViewById(R.id.ib_enter);
        ib_enter.setOnClickListener(this);

    	if (resourceId == R.drawable.ic_app_76) {
            ib_enter.setVisibility(View.VISIBLE);
    	} else {
            ib_enter.setVisibility(View.GONE);
    	}
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
    	super.onResume();
//    	if (showIn) {
//    		enterMain();
//    	}
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_enter:
            {
                enterMain();
            }
                break;
        }
    }

    private void enterMain() {
		if (checkExistWallet()) {
			Intent intent = new Intent(mContext, ActivityMain.class);
			startActivity(intent);
			getActivity().finish();
			getActivity().overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);
		} else {
			Intent intent = new Intent(mContext, ActivityPreCreateAccount.class);
			startActivity(intent);
			getActivity().finish();
			getActivity().overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);
		}
	}

	private boolean checkExistWallet() {
		WalletModel walletModel = WalletManager.getInstance().getCurrentWallet();
		if (walletModel == null) {
//            WalletModel walletModel = new WalletModel();
//            walletModel.address = "0x2323821787382183621863";
//            walletModel.bcuId = "";
//            walletModel.keystoreJson = "";
//            walletModel.mnemonicStr = "";
//            walletModel.pubKey = "wiuqheiuqwheiuhqw98129";
//            WalletManager.getInstance().addWallet(walletModel);
			return false;
		}
		return true;
	}
    
}

