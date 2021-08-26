package zaloznaya.olga.app.gifviewer.di

import com.google.gson.GsonBuilder
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import zaloznaya.olga.app.gifviewer.BuildConfig
import zaloznaya.olga.app.gifviewer.data.remote.retrofit.GiphyApiService
import java.util.concurrent.TimeUnit

val NetworkModule = module {
    single {
        val interceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                var request = chain.request()
                val url: HttpUrl = request.url.newBuilder()
                    .addQueryParameter("api_key", BuildConfig.API_KEY)
                    .build()
                request = request.newBuilder().url(url).build()
                return@addInterceptor chain.proceed(request)
            }
            .addInterceptor(interceptor)
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()

        Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }
    single {
        get<Retrofit>().create(GiphyApiService::class.java)
    }
}