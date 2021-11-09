package com.chethan.example.api

import com.chethan.data.api.ApiErrorResponse
import com.chethan.data.api.ApiResponse
import com.chethan.data.api.ApiSuccessResponse
import okhttp3.Headers.Companion.headersOf
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response

/**
 * Created by Chethan on 10/12/2021.
 */

@RunWith(JUnit4::class)
class ApiResponseTest {

    @Test
    fun exception() {
        val exception = Exception("rotterdam")
        val (errorMessage) = ApiResponse.create<String>(exception)
        assertThat<String>(errorMessage, `is`("rotterdam"))
    }

    @Test
    fun success() {
        val apiResponse: ApiSuccessResponse<String> = ApiResponse
            .create<String>(Response.success("rotterdam")) as ApiSuccessResponse<String>
        assertThat<String>(apiResponse.body, `is`("rotterdam"))
        assertThat<Int>(apiResponse.nextPage, `is`(nullValue()))
    }

    @Test
    fun link() {
        val link =
            "https://s3-ap-northeast-1.amazonaws.com/m-et/Android/json/all.json"
        val headers = headersOf("link", link)
        val response = ApiResponse.create<String>(Response.success("items", headers))
        assertThat<String>((response as ApiSuccessResponse).body, `is`("items"))
    }

    @Test
    fun badLinkHeader() {
        val link = "https://s3-ap-northeast-1.amazonaws.com/m-et/Android/json/all.json"
        val headers = headersOf("link", link)
        val response = ApiResponse.create<String>(Response.success("items", headers))
        assertThat<Int>((response as ApiSuccessResponse).nextPage, nullValue())
    }

    @Test
    fun error() {
        val errorResponse = Response.error<String>(
            400,
            "blah".toResponseBody("application/txt".toMediaTypeOrNull())
        )
        val (errorMessage) = ApiResponse.create<String>(errorResponse) as ApiErrorResponse<String>
        assertThat<String>(errorMessage, `is`("blah"))
    }
}
