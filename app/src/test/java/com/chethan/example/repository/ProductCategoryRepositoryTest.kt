package com.chethan.example.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.chethan.data.api.NetWorkApi
import com.chethan.data.db.AppDatabase
import com.chethan.data.db.ProductCategoryDao
import com.chethan.data.model.ProductCategory
import com.chethan.example.util.InstantAppExecutors
import com.chethan.example.util.mock
import com.chethan.mericari.util.ApiUtil.successCall
import com.chethan.tfl.util.TestUtil
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*

/**
 * Created by Chethan on 10/12/2021.
 */

@RunWith(JUnit4::class)
class ProductCategoryRepositoryTest {
    private lateinit var repository: CategoryListRepository
    private val dao = mock(ProductCategoryDao::class.java)
    private val service = mock(NetWorkApi::class.java)

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()
    val item =
        TestUtil.getCategoryItem()

    @Before
    fun init() {
        val db = mock(AppDatabase::class.java)
        `when`(db.productCategoryDao()).thenReturn(dao)
        `when`(db.runInTransaction(ArgumentMatchers.any())).thenCallRealMethod()
        repository = CategoryListRepository(InstantAppExecutors(), dao, service)
    }

    @Test
    fun loadCategoryListResponse() {
        val dbData = MutableLiveData<List<ProductCategory>>()
        `when`(dao.loadAllTheProductCategory()).thenReturn(dbData)

        val productCategoryArrayList = TestUtil.createProductCategoryArrayList(item)
        val call = successCall(productCategoryArrayList)

        `when`(service.getMasterJson()).thenReturn(call)

        val data = repository.getProductCategotyJson()
        verify(dao).loadAllTheProductCategory()
        verifyNoMoreInteractions(service)

        val observer = mock<Observer<Resource<List<ProductCategory>>>>()
        data.observeForever(observer)
        verifyNoMoreInteractions(service)
        verify(observer).onChanged(Resource.loading(null))

        val updatedDbData = MutableLiveData<List<ProductCategory>>()
        `when`(dao.loadAllTheProductCategory()).thenReturn(updatedDbData)

        dbData.postValue(null)
        verify(service).getMasterJson()

        verify(dao).insertProductCategories(productCategoryArrayList)
    }
}
