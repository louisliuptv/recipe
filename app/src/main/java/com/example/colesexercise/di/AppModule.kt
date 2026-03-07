package com.example.colesexercise.di

import android.content.Context
import com.example.colesexercise.data.datasource.RecipeLocalDataSource
import com.example.colesexercise.data.datasource.RecipeLocalDataSourceImpl
import com.example.colesexercise.data.repository.RecipeRepositoryImpl
import com.example.colesexercise.domain.repository.RecipeRepository
import com.example.colesexercise.domain.usecase.SortRecipesByTotalTimeUseCase
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideRecipeDataSource(
        @ApplicationContext context: Context,
        gson: Gson
    ): RecipeLocalDataSource = RecipeLocalDataSourceImpl(context, gson)

    @Provides
    @Singleton
    fun provideRecipeRepository(
        localDataSource:  RecipeLocalDataSource
    ): RecipeRepository = RecipeRepositoryImpl(localDataSource)


    @Provides
    @Singleton
    fun provideSortRecipesByTotalTimeUseCase(): SortRecipesByTotalTimeUseCase =
        SortRecipesByTotalTimeUseCase()
}
