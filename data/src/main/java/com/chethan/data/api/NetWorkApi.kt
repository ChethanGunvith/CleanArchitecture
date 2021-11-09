package com.chethan.data.api

import androidx.lifecycle.LiveData
import com.chethan.data.model.ProductCategory
import com.chethan.data.model.ProductOverview
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * Created by Chethan on 10/12/2021.
 * This interface contains the definition list of all the network endpoints used by the App.
 * Ref: Retrofit
 */
interface NetWorkApi {

    @GET("m-et/Android/json/master.json")
    fun getMasterJson(): LiveData<ApiResponse<List<ProductCategory>>>

    @GET
    fun getProducts(@Url url: String): LiveData<ApiResponse<List<ProductOverview>>>
}
