package raulalbin.prueba.tecnica.di


import android.content.Context

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import raulalbin.prueba.tecnica.data.database.AppDatabase
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun database(@ApplicationContext context: Context) : AppDatabase {
        return AppDatabase.getInstance(context)
    }
//    @Provides
//    fun characterDao(appDatabase: AppDatabase) = appDatabase.characterDao()
//    @Provides
//    fun episodeDao(appDatabase: AppDatabase) = appDatabase.episodeDao()
//    @Provides
//    fun locationDao(appDatabase: AppDatabase) = appDatabase.locationDao()
}