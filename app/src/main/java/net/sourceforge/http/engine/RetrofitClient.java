package net.sourceforge.http.engine;

import net.sourceforge.commons.log.SWLog;
import net.sourceforge.http.model.BaseResponse;
import net.sourceforge.http.model.NodeModel;
import net.sourceforge.http.model.UserInfo;
import net.sourceforge.http.model.WalletBalanceModel;
import net.sourceforge.http.model.WalletNonceModel;
import net.sourceforge.http.model.WalletTransferModel;
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
        @POST("fbc/pbqnl.do")
        Call<NodeModel.NodeModelResponse> requestNodeList(@Body RequestBody map);


    }

    public interface ETHNodeListService {

        /**
         * 获取ETH节点列表
         * @param map
         * @return
         */
        @Headers({"Content-Type:application/x-www-form-urlencoded"})
        @POST("eth/pbqnl.do")
        Call<NodeModel.NodeModelResponse> requestNodeList(@Body RequestBody map);


    }

    public interface CreateETHAccountService {

        /**
         * 创建ETH链账户
         * @param map
         * @return
         */
        @Headers({"Content-Type:application/x-www-form-urlencoded"})
        @POST("eth/pbhna.do")
        Call<WalletBalanceModel> requestCreateETHAccount(@Body RequestBody map);

    }

    public interface CreateFBCAccountService {

        /**
         * 创建FBC链账户
         * @param map
         * @return
         */
        @Headers({"Content-Type:application/x-www-form-urlencoded"})
        @POST("fbc/pbhna.do")
        Call<WalletBalanceModel> requestCreateFBCAccount(@Body RequestBody map);

    }

    public interface FBCBalanceService {

        /**
         * 获取FBC钱包余额
         * @param map
         * @return
         */
        @Headers({"Content-Type:application/x-www-form-urlencoded"})
        @POST("fbc/pbqbe.do")
        Call<WalletBalanceModel> requestFBCBalance(@Body RequestBody map);

    }

    public interface ETHBalanceService {

        /**
         * 获取ETH钱包余额
         * @param map
         * @return
         */
        @Headers({"Content-Type:application/x-www-form-urlencoded"})
        @POST("eth/pbqbe.do")
        Call<WalletBalanceModel> requestETHBalance(@Body RequestBody map);

    }

    public interface FBCNonceService {

        /**
         * 获取FBC Nonce
         * @param map
         * @return
         */
        @Headers({"Content-Type:application/x-www-form-urlencoded"})
        @POST("fbc/pbqne.do")
        Call<WalletNonceModel> requestFBCNonce(@Body RequestBody map);

    }

    public interface ETHNonceService {

        /**
         * 获取ETH Nonce
         * @param map
         * @return
         */
        @Headers({"Content-Type:application/x-www-form-urlencoded"})
        @POST("eth/pbqne.do")
        Call<WalletNonceModel> requestETHNonce(@Body RequestBody map);

    }

    public interface FBCTransferService {

        /**
         * FBC 冷 主币转账
         * @param map
         * @return
         */
        @Headers({"Content-Type:application/x-www-form-urlencoded"})
        @POST("fbc/pbtxe.do")
        Call<WalletTransferModel> requestFBCTransfer(@Body RequestBody map);

    }

    public interface ETHTransferService {

        /**
         * ETH 冷 主币转账
         * @param map
         * @return
         */
        @Headers({"Content-Type:application/x-www-form-urlencoded"})
        @POST("eth/pbtxe.do")
        Call<WalletTransferModel> requestETHTransfer(@Body RequestBody map);

    }
}
