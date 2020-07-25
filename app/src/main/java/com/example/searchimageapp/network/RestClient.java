package com.example.searchimageapp.network;

import com.example.searchimageapp.ImageSearchApplication;
import com.example.searchimageapp.Utils.Utils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {
    private static final String TAG = "RestClient";
    private static final String BASE_URL = "https://api.imgur.com/";

    private static OkHttpClient httpClient;

    /*static Gson gson = new GsonBuilder()
            .registerTypeAdapter(CustomAttribute.class, new CustomAttribute.DataStateDeserializer())
            .setLenient()
            .create();
*/
    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder().client(httpClient())
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();


    private static APIInterface apiInterface = retrofit.create(APIInterface.class);

    /**
     * Gets api interface.
     *
     * @return the api interface
     */
    public static APIInterface getAPIInterface() {
        return apiInterface;
    }

    /**
     * Http client ok http client.
     *
     * @return the ok http client
     */
    public static OkHttpClient httpClient() {
        if (null == httpClient) createService();
        return httpClient;
    }

    private static void createService() {

        HttpLoggingInterceptor httpLogsInterceptor = new HttpLoggingInterceptor();
        httpLogsInterceptor.level(HttpLoggingInterceptor.Level.BODY);

        httpClient = new OkHttpClient.Builder().
                connectTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(new BearerTokenInterceptor())
                .addInterceptor(httpLogsInterceptor)
                .build();
    }

    /**
     * The type Bearer token interceptor.
     */
    public static class BearerTokenInterceptor implements Interceptor {

        @NotNull
        @Override
        public Response intercept(@NotNull Chain chain) throws IOException {
            if (!Utils.isNetworkAvailable(ImageSearchApplication.getInstance())) {
                throw new NoConnectivityException();
            }

            Request.Builder newRequest = chain.request().newBuilder();
            newRequest.addHeader("Authorization", "Client-ID 137cda6b5008a7c");

            Response response = chain.proceed(newRequest.build());
            return response;
        }
    }

    /**
     * The type No connectivity exception.
     */
    public static class NoConnectivityException extends IOException {
        @Override
        public String getMessage() {
            return "Please check your internet connection.";
        }
    }
}
