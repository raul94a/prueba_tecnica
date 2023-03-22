package raulalbin.prueba.tecnica.di

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import raulalbin.prueba.tecnica.data.api.RickAndMortyAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Provides
    @Singleton
    fun provideRetrofit(@ApplicationContext context: Context): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideOkHttpClient(context))
            .build()
    }

    @Provides
    @Singleton
    fun provideRickAndMortyService(retrofit: Retrofit): RickAndMortyAPI {
        return retrofit.create(RickAndMortyAPI::class.java)
    }



    private fun provideOkHttpClient(context: Context) :OkHttpClient {
        val cacheSize = (5 * 1024 * 1024 * 2).toLong()
        val cache = Cache(context.cacheDir,cacheSize )
        val client = OkHttpClient.Builder()
            .cache(cache)
            .addNetworkInterceptor(provideCacheInterceptor())
            .addInterceptor { chain ->

                var request = chain.request()

                request = if (hasNetwork(context)!!)

                    request.newBuilder().header("Cache-Control", "public, max-age=" + 120).build()
                else
                    request.newBuilder().header(
                        "Cache-Control",
                        "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7
                    ).build()
                chain.proceed(request)
            }
            .build()
        return client
    }

    private fun provideCacheInterceptor(): Interceptor {
        return Interceptor { chain ->
            var request: Request = chain.request()
            val originalResponse: Response = chain.proceed(request)
            val cacheControl: String? = originalResponse.header("Cache-Control")
            if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains("no-cache") ||
                cacheControl.contains("must-revalidate") || cacheControl.contains("max-stale=0")
            ) {
                val cc = CacheControl.Builder()
                    .maxStale(1, TimeUnit.DAYS)
                    .build()
                request = request.newBuilder()
                    .removeHeader("Pragma")
                    .cacheControl(cc)
                    .build()
                chain.proceed(request)
            } else {
                originalResponse

            }
        }
    }




    private fun hasNetwork(context: Context): Boolean? {
        var isConnected: Boolean? = false // Initial Value
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnected)
            isConnected = true
        return isConnected
    }
}