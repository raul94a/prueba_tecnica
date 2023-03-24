package raulalbin.prueba.tecnica.di

import androidx.navigation.NavHostController
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NavigationModule {
    public lateinit var controller: NavHostController

    @Singleton
    fun setNav(nav: NavHostController){
        controller = nav
    }
    @Singleton
    fun getNav():NavHostController {
        return controller;
    }


//    @Provides
//    fun characterDao(appDatabase: AppDatabase) = appDatabase.characterDao()
//    @Provides
//    fun episodeDao(appDatabase: AppDatabase) = appDatabase.episodeDao()
//    @Provides
//    fun locationDao(appDatabase: AppDatabase) = appDatabase.locationDao()
}