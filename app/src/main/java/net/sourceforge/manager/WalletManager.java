package net.sourceforge.manager;

import android.text.TextUtils;

import net.sourceforge.http.model.WalletModel;
import net.sourceforge.utils.PreferenceHelper;

import java.util.ArrayList;
import java.util.List;

public class WalletManager {
    private static final WalletManager ourInstance = new WalletManager();

    public static WalletManager getInstance() {
        return ourInstance;
    }

    private WalletManager() {
    }

    public void addWallet(WalletModel walletModel) {
        List<WalletModel> walletModels =  PreferenceHelper.getInstance().getObject(PreferenceHelper.PreferenceKey.KEY_WALLET_LIST, List.class);
        if (walletModels == null) {
            walletModels = new ArrayList<>();
        }
        walletModels.add(walletModel);
        PreferenceHelper.getInstance().setObject(PreferenceHelper.PreferenceKey.KEY_WALLET_LIST, walletModels);
//        setCurrentWallet(walletModel.pubKey);
    }

    public void deleteWallet(WalletModel walletModel) {
        List<WalletModel> walletModels =  PreferenceHelper.getInstance().getObject(PreferenceHelper.PreferenceKey.KEY_WALLET_LIST, List.class);
        if (walletModels != null && walletModels.size() >0) {
            int dIndex = -1;
            for (int i=0; i<walletModels.size(); i++) {
                if (walletModel.pubKey.equals(walletModels.get(i).pubKey)) {
                    dIndex = i;
                    break;
                }
            }
            if (dIndex != -1) {
                walletModels.remove(dIndex);
                PreferenceHelper.getInstance().setObject(PreferenceHelper.PreferenceKey.KEY_WALLET_LIST, walletModels);
            }
        }
    }

    public List<WalletModel> getAllWallets() {
        return PreferenceHelper.getInstance().getObject(PreferenceHelper.PreferenceKey.KEY_WALLET_LIST, List.class);
    }

    public WalletModel getCurrentWallet() {
        String currentPubkey = PreferenceHelper.getInstance().getStringShareData(PreferenceHelper.PreferenceKey.KEY_WALLET_CURRENT_PUBKEY, "");
        List<WalletModel> allModels = getAllWallets();
        WalletModel cw = null;
        for (int i=0; i<getAllWallets().size(); i++) {
            if (allModels.get(i).pubKey.equals(currentPubkey)) {
                cw =  allModels.get(i);
            }
        }
        if (cw == null && getAllWallets() != null && getAllWallets().size() >0) {
            cw = getAllWallets().get(0);
        }
        return cw;
    }

    public void setCurrentWallet(String pubkey) {
        PreferenceHelper.getInstance().storeStringShareData(PreferenceHelper.PreferenceKey.KEY_WALLET_CURRENT_PUBKEY, pubkey);
    }


    public boolean isMnemonicValid(String mnemonicText) {
        if (TextUtils.isEmpty(mnemonicText)) {
            return false;
        }
        String[] texts = mnemonicText.split(" ");
        if (texts == null || texts.length == 0) {
            return false;
        }
        if (texts.length == 12) {
            return true;
        }
        return false;
    }

    public boolean isWalletExist(WalletModel walletModel) {
        List<WalletModel> walletModels = getAllWallets();
        if (walletModels != null && walletModels.size() >0) {
            for (int i=0; i<walletModels.size(); i++) {
                if (walletModel.address.equals(walletModels.get(i).address)) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    public void updateWalletPassowrd(WalletModel walletModel) {
        List<WalletModel> walletModels =  PreferenceHelper.getInstance().getObject(PreferenceHelper.PreferenceKey.KEY_WALLET_LIST, List.class);
        if (walletModels != null && walletModels.size() >0) {
            for (int i=0; i<walletModels.size(); i++) {
                if (walletModel.pubKey.equals(walletModels.get(i).pubKey)) {
                    walletModels.get(i).walletPassowrd = walletModel.walletPassowrd;
                    break;
                }
            }
            PreferenceHelper.getInstance().setObject(PreferenceHelper.PreferenceKey.KEY_WALLET_LIST, walletModels);
        }
    }

    public WalletModel getNewstWallet(WalletModel walletModel) {
        List<WalletModel> walletModels =  PreferenceHelper.getInstance().getObject(PreferenceHelper.PreferenceKey.KEY_WALLET_LIST, List.class);
        if (walletModels != null && walletModels.size() >0) {
            for (int i=0; i<walletModels.size(); i++) {
                if (walletModel.pubKey.equals(walletModels.get(i).pubKey)) {
                    return walletModels.get(i);
                }
            }
        }
        return walletModel;
    }

    public boolean isExsitWalletId(String walletId) {
        List<WalletModel> walletModels = getAllWallets();
        if (walletModels != null && walletModels.size() >=0) {
            for (int i=0; i<walletModels.size(); i++) {
                if (walletId.equals(walletModels.get(i).walletId)) {
                    return true;
                }
            }
        }
        return false;
    }

//    public void addTransByWallet(String walletAddress, TransModel transModel) {
//        Map<String, List<TransModel>> trans = (Map<String, List<TransModel>>) PreferenceHelper.getInstance().getObject(PreferenceHelper.PreferenceKey.KEY_WALLET_TRANS, Map.class);
//        if (trans == null) {
//            trans = new HashMap<>();
//        }
//        List<TransModel> transModels = trans.get(walletAddress);
//        if (transModels == null) {
//            transModels = new ArrayList<>();
//        }
//        transModels.add(transModel);
//        trans.put(TextUtils.isEmpty(walletAddress)?"CWV":walletAddress, transModels);
//        PreferenceHelper.getInstance().setObject(PreferenceHelper.PreferenceKey.KEY_WALLET_TRANS, trans);
//    }
//
//    public List<TransModel> getTransList(String token) {
//        Map<String, List<TransModel>> trans = (Map<String, List<TransModel>>) PreferenceHelper.getInstance().getObject(PreferenceHelper.PreferenceKey.KEY_WALLET_TRANS, Map.class);
//        if (trans == null) {
//            return null;
//        }
//        return trans.get(TextUtils.isEmpty(token)?"CWV":token);
//    }
//
//    public String transBalance(String balance) {
//        if (TextUtils.isEmpty(balance)) {
//            return "0";
//        } else {
//            String balances = CalculateUtils.div2(balance, String.valueOf(Math.pow(10,18)), 4);
//            if ("0.0000".equals(balances)) {
//                return "0";
//            } else {
//                return balances;
//            }
//        }
//    }


}
