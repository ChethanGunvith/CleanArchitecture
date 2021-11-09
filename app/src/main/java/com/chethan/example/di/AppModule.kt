package com.chethan.example.di

import android.app.Application
import androidx.room.Room
import com.chethan.common.LiveDataCallAdapterFactory
import com.chethan.data.api.NetWorkApi
import com.chethan.data.db.AppDatabase
import com.chethan.data.db.ProductCategoryDao
import com.chethan.data.db.ProductsDao
import com.chethan.example.API_REST_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {
    @Singleton
    @Provides
    fun provideGithubService(): NetWorkApi {
        val retrofit =
            Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .baseUrl(API_REST_URL)
                .build()
        return retrofit.create(NetWorkApi::class.java)
    }

    @Singleton
    @Provides
    fun provideDb(app: Application): AppDatabase {
        return Room.databaseBuilder(app, AppDatabase::class.java, "Shopping.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideProductsDao(db: AppDatabase): ProductsDao {
        return db.productsDao()
    }

    @Singleton
    @Provides
    fun provideProductCategoryDao(db: AppDatabase): ProductCategoryDao {
        return db.productCategoryDao()
    }
}
