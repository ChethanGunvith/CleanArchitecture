package com.chethan.example.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.chethan.data.api.ApiResponse
import com.chethan.data.api.NetWorkApi
import com.chethan.data.db.AppDatabase
import com.chethan.data.db.ProductsDao
import com.chethan.data.model.ProductOverview
import com.chethan.example.util.InstantAppExecutors
import com.chethan.example.util.mock
import com.chethan.tfl.util.TestUtil
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*
import retrofit2.Response

/**
 * Created by Chethan on 10/12/2021.
 */

@RunWith(JUnit4::class)
class ProductsRepositoryTest {
    private lateinit var repository: ProductsRepository
    private val dao = mock(ProductsDao::class.java)
    private val service = mock(NetWorkApi::class.java)

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()
    val item =
        TestUtil.getProductsItem()

    @Before
    fun init() {
        val db = mock(AppDatabase::class.java)
        `when`(db.productsDao()).thenReturn(dao)
        `when`(db.runInTransaction(ArgumentMatchers.any())).thenCallRealMethod()
        repository = ProductsRepository(InstantAppExecutors(), dao, service)
    }

    @Test
    fun load_fromServer() {
        val listOfProduct = TestUtil.createProductOverviewArrayList(item)
        val observer = mock<Observer<Resource<List<ProductOverview>>>>()
        val dbSearchResult = MutableLiveData<List<ProductOverview>>()
        val repositories = MutableLiveData<List<ProductOverview>>()

        // mock the network
        val callLiveData = MutableLiveData<ApiResponse<List<ProductOverview>>>()
        `when`(service.getProducts("https://s3-ap-northeast-1.amazonaws.com/m-et/Android/json/all.json")).thenReturn(
            callLiveData
        )

        // mock the db
        `when`(dao.loadAllTheProducts()).thenReturn(dbSearchResult)

        // Simulate network call
        repository.getProductsList(
            "All",
            "https://s3-ap-northeast-1.amazonaws.com/m-et/Android/json/all.json"
        )
            .observeForever(observer)
        // hold network response
        verify(observer).onChanged(Resource.loading(null))
        verifyNoMoreInteractions(service)
        reset(observer)

        // hold db data
        `when`(dao.loadProducts("All")).thenReturn(repositories)
        dbSearchResult.postValue(null)
        verify(dao, never()).loadProducts("All")

        // Listen for db updates in response to network
        verify(service).getProducts("https://s3-ap-northeast-1.amazonaws.com/m-et/Android/json/all.json")
        val updatedResult = MutableLiveData<List<ProductOverview>>()
        `when`(dao.loadAllTheProducts()).thenReturn(updatedResult)
        updatedResult.postValue(listOfProduct)

        // post response and the insert the data
        callLiveData.postValue(ApiResponse.create(Response.success(listOfProduct)))
        verify(dao).insertProducts(listOfProduct)
        repositories.postValue(listOfProduct)
        verify(observer).onChanged(Resource.success(listOfProduct))
        verifyNoMoreInteractions(service)
    }
}
