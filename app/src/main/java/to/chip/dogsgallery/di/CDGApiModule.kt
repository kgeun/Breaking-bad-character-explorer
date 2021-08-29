package to.chip.dogsgallery.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import to.chip.dogsgallery.network.CDGDogsService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CDGApiModule {

    @Singleton
    @Provides
    fun provideRetrofitService(): CDGDogsService = Retrofit.Builder()
        .baseUrl(CDGDogsService.DOGS_GALLERY_API_URL)
        .addConverterFactory(
            MoshiConverterFactory.create(
                Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            )
        )
        .build()
        .create(CDGDogsService::class.java)
}