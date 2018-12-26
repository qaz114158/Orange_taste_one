package net.sourceforge.http.engine;

import net.sourceforge.commons.log.SWLog;
import net.sourceforge.http.model.NodeModel;
import net.sourceforge.http.url.URLBuilder;
import net.sourceforge.utils.TextUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by terry.c on 06/03/2018.
 */

public class RetrofitClientOTC {

    private static final RetrofitClientOTC ourInstance = new RetrofitClientOTC();

    public static RetrofitClientOTC getInstance() {
        return ourInstance;
    }

    private RetrofitClientOTC() {
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
                    .baseUrl("http://39.96.70.16:7085/otc/")
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
        @POST("mer/pbadv.do")
        Call<NodeModel.NodeModelResponse> requestBuyList(@Body RequestBody map);

       

    }

}
