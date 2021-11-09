package com.chethan.example.repository

import androidx.lifecycle.LiveData
import com.chethan.common.AppExecutors
import com.chethan.common.RateLimiter
import com.chethan.data.db.ProductsDao
import com.chethan.data.model.ProductOverview
import com.chethan.data.api.ApiSuccessResponse
import com.chethan.data.api.NetWorkApi
import testing.OpenForTesting
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@OpenForTesting
class ProductsRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val productsDao: ProductsDao,
    private val netWorkApi: NetWorkApi
) {

    private val repoListRateLimit = RateLimiter<String>(10, TimeUnit.MINUTES)
    private val categoryAll = "All"
    private val specialCharacter = "m"
    /**
     * Expecting Master json response to deliver list of product category
     */
    fun getProductsList(categoryName: String, categoryUrl: String): LiveData<Resource<List<ProductOverview>>> {
        return object : NetworkBoundResource<List<ProductOverview>, List<ProductOverview>>(appExecutors) {

            // inserting into data base
            override fun saveCallResult(item: List<ProductOverview>) {
                productsDao.insertProducts(item)
            }

            override fun shouldFetch(data: List<ProductOverview>?) = repoListRateLimit.shouldFetch(categoryUrl)

            override fun loadFromDb(): LiveData<List<ProductOverview>> {
                if (categoryName == categoryAll) return productsDao.loadAllTheProducts()
                else
                    return productsDao.loadProducts(
                        specialCharacter + categoryName.toLowerCase(
                            Locale.ROOT
                        )
                    )
            }

            override fun createCall() = netWorkApi.getProducts(categoryUrl)

            override fun processResponse(response: ApiSuccessResponse<List<ProductOverview>>):
                List<ProductOverview> {
                    val body = response.body
                    return body
                }
        }.asLiveData()
    }
}
