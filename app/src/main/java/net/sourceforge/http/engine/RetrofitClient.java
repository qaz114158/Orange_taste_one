package net.sourceforge.http.engine;

import net.sourceforge.commons.log.SWLog;
import net.sourceforge.http.model.BaseResponse;
import net.sourceforge.http.model.NodeModel;
import net.sourceforge.http.model.UserInfo;
import net.sourceforge.http.url.URLBuilder;
import net.sourceforge.utils.TextUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by terry.c on 06/03/2018.
 */

public class RetrofitClient {

    private static final RetrofitClient ourInstance = new RetrofitClient();

    public static RetrofitClient getInstance() {
        return ourInstance;
    }

    private RetrofitClient() {
    }

    private Retrofit mRetrofit;

    private static final int mTimeOut = 20;

    public Retrofit createRetrofit() {
        if (mRetrofit == null) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    //打印retrofit日志
                    try {
                        SWLog.d("RetrofitClient retrofitBack = ", TextUtils.unicodeToUtf8(message));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
//                    .cache(cache)
                    .addInterceptor(loggingInterceptor)
                    .connectTimeout(mTimeOut, TimeUnit.SECONDS)
                    .readTimeout(mTimeOut, TimeUnit.SECONDS)
                    .writeTimeout(mTimeOut, TimeUnit.SECONDS)
                    .build();


            mRetrofit = new Retrofit.Builder()
                    .baseUrl(URLBuilder.ins().getHostUrl())
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return mRetrofit;
    }

    public interface APIService {

        /**
         * 获取节点列表
         * @param map
         * @return
         */
        @Headers({"Content-Type:application/x-www-form-urlencoded"})
        @POST("spdt/pbqnl.do")
        Call<NodeModel.NodeModelResponse> requestNodeList(@Body RequestBody map);

       

    }

}
