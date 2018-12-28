package net.sourceforge.UI.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.annotation.NonNull;

import net.sourceforge.constants.Constants;
import net.sourceforge.http.engine.RetrofitClient;
import net.sourceforge.http.model.NodeModel;
import net.sourceforge.manager.WalletManager;
import net.sourceforge.utils.GsonUtil;
import net.sourceforge.utils.PreferenceHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletViewModel extends AndroidViewModel implements LifecycleObserver {

    private MutableLiveData<List<NodeModel>> nodeModelResponse = new MutableLiveData<>();
    private MutableLiveData<List<NodeModel>> nodeFBCModelResponse = new MutableLiveData<>();
    private MutableLiveData<List<NodeModel>> nodeETHModelResponse = new MutableLiveData<>();

    public LiveData<List<NodeModel>> getNodeModelResponse() {
        return nodeModelResponse;
    }

    public LiveData<List<NodeModel>> getETHNodeModelResponse() {
        return nodeETHModelResponse;
    }

    public LiveData<List<NodeModel>> getFBCNodeModelResponse() {
        return nodeFBCModelResponse;
    }

    public WalletViewModel(@NonNull Application application) {
        super(application);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {

    }

    public List<NodeModel> getNodeList() {
        if (nodeModelResponse.getValue() == null || nodeModelResponse.getValue().size() ==0) {
            requestNodeList("ETH", "dev");
        }
        return null;
    }

    public List<NodeModel> getFBCNodeList() {
        if (nodeFBCModelResponse.getValue() == null || nodeFBCModelResponse.getValue().size() ==0) {
            requestNodeList("FBC", "dev");
        }
        return null;
    }

    public List<NodeModel> getETHNodeList() {
        if (nodeETHModelResponse.getValue() == null || nodeETHModelResponse.getValue().size() ==0) {
            requestNodeList("ETH", "dev");
        }
        return null;
    }

    public void requestNodeList(String chainType, String nodeNet) {
        if (chainType.equalsIgnoreCase("ETH")) {
            RetrofitClient.ETHNodeListService apiService = RetrofitClient.getInstance().createRetrofit().create(RetrofitClient.ETHNodeListService.class);
            Map<String, String> params = new HashMap<>();

            params.put("dapp_id", Constants.DAPP_ID);
            params.put("node_net", nodeNet);
            String json = GsonUtil.toJson(params);
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=utf-8"), json);
            retrofit2.Call<NodeModel.NodeModelResponse> call = apiService.requestNodeList(body);
            call.enqueue(new Callback<NodeModel.NodeModelResponse>() {
                @Override
                public void onResponse(retrofit2.Call<NodeModel.NodeModelResponse> call, Response<NodeModel.NodeModelResponse> response) {
                    if (net.sourceforge.utils.TextUtils.isResponseSuccess(response.body())) {
                        nodeETHModelResponse.postValue(response.body().dev_net);
//                        PreferenceHelper.getInstance().setObject(PreferenceHelper.PreferenceKey.KEY_ETH_NODE_LIST, response.body().dev_net);
                    } else {
                        nodeETHModelResponse.postValue(null);
                    }
                }

                @Override
                public void onFailure(retrofit2.Call<NodeModel.NodeModelResponse> call, Throwable t) {
                    nodeETHModelResponse.postValue(null);
                }
            });
        } else if (chainType.equalsIgnoreCase("FBC")) {
            RetrofitClient.APIService apiService = RetrofitClient.getInstance().createRetrofit().create(RetrofitClient.APIService.class);
            Map<String, String> params = new HashMap<>();

            params.put("dapp_id", Constants.DAPP_ID);
            params.put("node_net", nodeNet);
            String json = GsonUtil.toJson(params);
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=utf-8"), json);
            retrofit2.Call<NodeModel.NodeModelResponse> call = apiService.requestNodeList(body);
            call.enqueue(new Callback<NodeModel.NodeModelResponse>() {
                @Override
                public void onResponse(retrofit2.Call<NodeModel.NodeModelResponse> call, Response<NodeModel.NodeModelResponse> response) {
                    if (net.sourceforge.utils.TextUtils.isResponseSuccess(response.body())) {
                        nodeFBCModelResponse.postValue(response.body().dev_net);
//                        PreferenceHelper.getInstance().setObject(PreferenceHelper.PreferenceKey.KEY_FBC_NODE_LIST, response.body().dev_net);
                    } else {
                        nodeFBCModelResponse.postValue(null);
                    }
                }

                @Override
                public void onFailure(retrofit2.Call<NodeModel.NodeModelResponse> call, Throwable t) {
                    nodeFBCModelResponse.postValue(null);
                }
            });
        }
    }


}
