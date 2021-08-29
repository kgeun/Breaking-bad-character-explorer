package to.chip.dogsgallery.di

import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import to.chip.dogsgallery.data.persistance.CDGAppDatabase
import to.chip.dogsgallery.data.persistance.CDGMainDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CDGDatabaseModule {
    @Provides
    @Singleton
    fun provideMainDao(appDatabase: CDGAppDatabase): CDGMainDao = appDatabase.CDGMainDao()

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): CDGAppDatabase {
        return Room.databaseBuilder(
            appContext,
            CDGAppDatabase::class.java,
            "dogsgallery"
        ).build()
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }
}