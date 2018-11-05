package net.sourceforge.UI.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import net.sourceforge.http.model.BaseResponse;

public class LoginViewModel extends AndroidViewModel {

    private MutableLiveData<BaseResponse> loginResponse = new MutableLiveData<>();

    public LiveData<BaseResponse> getLoginResponse() {
        return loginResponse;
    }

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public void login(String username, String password) {

    }


}
