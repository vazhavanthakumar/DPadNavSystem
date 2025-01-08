package com.ev.dpadnavsystem.di

import android.content.Context
import com.ev.dpadnavsystem.data.repo.ChargersRepository
import com.ev.dpadnavsystem.data.repo.FavoritesRepository
import com.ev.dpadnavsystem.helper.DPSpeechRecognizer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideFavoritesRepository() = FavoritesRepository()


    @Provides
    @Singleton
    fun provideChargersRepository() = ChargersRepository()

    @Provides
    @Singleton
    fun provideSpeechRecognizer(@ApplicationContext context: Context) = DPSpeechRecognizer(context)
}