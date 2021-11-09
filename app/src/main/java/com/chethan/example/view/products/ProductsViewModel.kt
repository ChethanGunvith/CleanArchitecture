package com.chethan.example.view.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.chethan.data.model.ProductOverview
import com.chethan.example.repository.ProductsRepository
import com.chethan.example.repository.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import testing.OpenForTesting
import javax.inject.Inject

@OpenForTesting
@HiltViewModel
class ProductsViewModel @Inject constructor(productsRepository: ProductsRepository) : ViewModel() {

    private val _categoryName = MutableLiveData<String>()
    private lateinit var _categoryUrl: String
    val products: LiveData<Resource<List<ProductOverview>>> = Transformations
        .switchMap(_categoryName) { input ->
            productsRepository.getProductsList(input, _categoryUrl)
        }

    fun getData(categoryUrl: String, categoryName: String) {
        if (_categoryName.value == categoryName) {
            return
        }

        _categoryUrl = categoryUrl
        _categoryName.value = categoryName
    }
}
