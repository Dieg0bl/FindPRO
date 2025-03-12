package dbl.findpro.features.mapservice.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dbl.findpro.core.domain.repository.IGeocodingRepository
import dbl.findpro.core.util.RemoteConfigManager
import dbl.findpro.features.mapservice.data.repository.GeocodingRepositoryImpl
import dbl.findpro.features.mapservice.data.services.IMapboxGeocodingService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MapboxModule {

    private const val MAPBOX_BASE_URL = "https://api.mapbox.com/"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(MAPBOX_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideMapboxGeocodingService(retrofit: Retrofit): IMapboxGeocodingService =
        retrofit.create(IMapboxGeocodingService::class.java)

    @Provides
    @Singleton
    fun provideGeocodingRepository(
        IMapboxGeocodingService: IMapboxGeocodingService,
        remoteConfigManager: RemoteConfigManager
    ): IGeocodingRepository {
        return GeocodingRepositoryImpl(IMapboxGeocodingService, remoteConfigManager)
    }
}
