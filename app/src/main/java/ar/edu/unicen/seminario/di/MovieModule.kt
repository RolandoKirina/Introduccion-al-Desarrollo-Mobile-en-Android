package ar.edu.unicen.seminario.di

import android.content.Context
import ar.edu.unicen.seminario.data.MovieApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

    @Module
    @InstallIn(SingletonComponent::class)
    class MovieModule {

        @Provides
        @Singleton
        fun providesRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create()) // paso de json a un objeto movie
                .build();
        }

        @Provides
        @Singleton
        fun provideTokenKey(): String {
            return "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2ZTUyNGU2MjFhZGFiOTRkOWE3NzVjZTU1ZjU2NTQwNiIsIm5iZiI6MTcyODY3MjQ5Ni41OTkzMDgsInN1YiI6IjY3MDk2OTUxYjBjNGY3YTUzNjFhMjk1YyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.c6FG0IaLbpu5aZZhz3r2dSg08FN-O-RCzwEbTC-h98Y"
        }

        @Provides
        @Singleton
        fun providesMovieApi(retrofit: Retrofit): MovieApi {
            return retrofit.create(MovieApi::class.java)
        }

        @Provides
        @Singleton
        fun provideApplicationContext(@ApplicationContext context: Context): Context {
            return context
        }

    }
