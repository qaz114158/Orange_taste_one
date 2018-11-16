package net.sourceforge.UI.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chain.wallet.spd.R;

import net.sourceforge.base.FragmentBase;

public class FragmentWelcome extends FragmentBase {

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
    	
//    	if (showIn) {
//    		imageView.setVisibility(View.GONE);
//    	} else {
//    		imageView.setVisibility(View.VISIBLE);
//    	}
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
    
//    private void enterMain() {
//		if (checkExistWallet()) {
//			Intent intent = new Intent(mContext, ActivityMain.class);
//			startActivity(intent);
//			getActivity().finish();
//			getActivity().overridePendingTransition(android.R.anim.fade_in,
//					android.R.anim.fade_out);
//		} else {
//			Intent intent = new Intent(mContext, ActivityPreCreateAccount.class);
//			startActivity(intent);
//			getActivity().finish();
//			getActivity().overridePendingTransition(android.R.anim.fade_in,
//					android.R.anim.fade_out);
//		}
//	}
//
//	private boolean checkExistWallet() {
//		List<WalletModel> walletModels = PreferenceHelper.getInstance().getObject(PreferenceHelper.PreferenceKey.KEY_WALLET_LIST, List.class);
//		if (walletModels == null || walletModels.size() == 0) {
////            WalletModel walletModel = new WalletModel();
////            walletModel.address = "0x2323821787382183621863";
////            walletModel.bcuId = "";
////            walletModel.keystoreJson = "";
////            walletModel.mnemonicStr = "";
////            walletModel.pubKey = "wiuqheiuqwheiuhqw98129";
////            WalletManager.getInstance().addWallet(walletModel);
//			return false;
//		}
//		return true;
//	}
    
}

