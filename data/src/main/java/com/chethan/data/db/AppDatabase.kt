package com.chethan.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chethan.data.model.ProductCategory
import com.chethan.data.model.ProductOverview

/**
 * Created by Chethan on 10/12/2021.
 */

@Database(
    entities = [
        ProductCategory::class,
        ProductOverview::class
    ],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun productCategoryDao(): ProductCategoryDao
    abstract fun productsDao(): ProductsDao
}
