package com.example.dogapp.di.module

import com.example.dogapp.di.qualifiers.IoDispatcher
import com.example.dogapp.repository.baseUrl.BaseUrlService
import com.example.dogapp.repository.remote.DogRepositoryImpl
import com.example.dogapp.repository.remote.DogServiceAPI
import com.example.dogapp.repository.usecases.GetAllBreedUseCase
import com.example.dogapp.repository.usecases.GetDogBreedImageUseCase
import com.example.dogapp.repository.usecases.GetDogBreedRandomImageUseCase
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideGson(): Gson = GsonBuilder().setLenient().create()

    @Singleton
    @Provides
    fun provideBaseUrlService(): BaseUrlService = BaseUrlService()

    @Provides
    fun provideServiceAPI(@Named("retrofit1") retrofit: Retrofit): DogServiceAPI = retrofit.create( DogServiceAPI::class.java)

    @Singleton
    @Provides
    @Named("retrofit1")
    fun providesDogServiceAPI(
        gson: Gson, api: BaseUrlService, provideOkHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder().baseUrl(api.baseUrl).client(provideOkHttpClient)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson)).build()

    @Singleton
    @Provides
    fun provideInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Singleton
    @Provides
    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder().addInterceptor(interceptor).build()


    @Singleton
    @Provides
    fun provideChatRepositoryImpl(
        api: DogServiceAPI,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): DogRepositoryImpl = DogRepositoryImpl(ioDispatcher, api)

}