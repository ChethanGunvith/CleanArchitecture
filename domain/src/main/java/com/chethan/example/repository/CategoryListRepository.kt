package com.chethan.example.repository

import androidx.lifecycle.LiveData
import com.chethan.common.AppExecutors
import com.chethan.common.RateLimiter
import com.chethan.data.db.ProductCategoryDao
import com.chethan.data.model.ProductCategory
import com.chethan.data.api.ApiSuccessResponse
import com.chethan.data.api.NetWorkApi
import testing.OpenForTesting
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@OpenForTesting
class CategoryListRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val productCategoryDao: ProductCategoryDao,
    private val netWorkApi: NetWorkApi
) {

    private val repoListRateLimit = RateLimiter<String>(10, TimeUnit.MINUTES)

    /**
     * Expecting Master json response to deliver list of product category
     */
    fun getProductCategotyJson(): LiveData<Resource<List<ProductCategory>>> {
        return object :
            NetworkBoundResource<List<ProductCategory>, List<ProductCategory>>(appExecutors) {

            // inserting into data base
            override fun saveCallResult(item: List<ProductCategory>) {
                productCategoryDao.insertProductCategories(item)
            }

            override fun shouldFetch(data: List<ProductCategory>?) =
                repoListRateLimit.shouldFetch("master.json")

            override fun loadFromDb(): LiveData<List<ProductCategory>> {
                return productCategoryDao.loadAllTheProductCategory()
            }

            override fun createCall() = netWorkApi.getMasterJson()

            override fun processResponse(response: ApiSuccessResponse<List<ProductCategory>>):
                    List<ProductCategory> {
                val body = response.body
                return body
            }
        }.asLiveData()
    }
}
