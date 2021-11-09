package com.chethan.example.view.productCategory

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.chethan.data.model.ProductCategory
import com.chethan.example.repository.CategoryListRepository
import com.chethan.example.repository.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import testing.OpenForTesting
import javax.inject.Inject

/**
 * Created by Chethan on 10/12/2021.
 */
@OpenForTesting
@HiltViewModel
class ProductCategoryViewModel @Inject constructor(categoryListRepository: CategoryListRepository) :
    ViewModel() {

    val productCategories: LiveData<Resource<List<ProductCategory>>> =
        categoryListRepository.getProductCategotyJson()
}
