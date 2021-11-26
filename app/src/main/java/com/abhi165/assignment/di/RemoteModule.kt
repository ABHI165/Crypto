package com.abhi165.assignment.di

import android.content.Context
import android.content.pm.PackageManager
import com.abhi165.assignment.App
import com.abhi165.assignment.data.remote.SocketService
import com.abhi165.assignment.data.repository.BitCoinRepoImpl
import com.abhi165.assignment.domain.repository.BitCoinRepo
import com.abhi165.assignment.utill.FlowStreamAdapter
import com.squareup.moshi.Moshi
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.lifecycle.android.AndroidLifecycle
import com.tinder.scarlet.messageadapter.moshi.MoshiMessageAdapter
import com.tinder.scarlet.retry.ExponentialWithJitterBackoffStrategy
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RemoteModule {

    @Singleton
    @Provides
    fun provideBaseUrl(@ApplicationContext context: Context) = context.packageManager
        .getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
        .metaData["base_url"].toString()

    @Singleton
    @Provides
    fun provideOkHttpClient(
        @ApplicationContext context: Context
    ) =  OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .addInterceptor ( HttpLoggingInterceptor().apply {
            level =HttpLoggingInterceptor.Level.BODY
        } )
        .writeTimeout(10, TimeUnit.SECONDS)
        .build()

    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .build()
    }

    @Singleton
    @Provides
    fun providesRetrofit(moshi:Moshi,okHttpClient: OkHttpClient, baseUrl:String,@ApplicationContext context:Context):SocketService {
        val lifecycle = AndroidLifecycle.ofApplicationForeground(context as App)
        val backoffStrategy = ExponentialWithJitterBackoffStrategy(5000, 5000)

      return Scarlet.Builder()
            .webSocketFactory(okHttpClient.newWebSocketFactory(baseUrl))
          .addMessageAdapterFactory(MoshiMessageAdapter.Factory(moshi))
          .addStreamAdapterFactory(FlowStreamAdapter.Factory())
            .backoffStrategy(backoffStrategy)
            .lifecycle(lifecycle)
            .build()
            .create<SocketService>()

    }

   @Singleton
   @Provides
   fun getRepository(remote:SocketService):BitCoinRepo = BitCoinRepoImpl(remote)



}