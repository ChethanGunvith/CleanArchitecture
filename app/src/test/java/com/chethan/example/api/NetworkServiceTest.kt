package com.chethan.example.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.chethan.common.LiveDataCallAdapterFactory
import com.chethan.data.api.ApiSuccessResponse
import com.chethan.data.api.NetWorkApi
import com.chethan.data.model.ProductCategory
import com.chethan.example.util.LiveDataTestUtil.getValue
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.core.IsNull.notNullValue
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Chethan on 10/12/2021.
 */

@RunWith(JUnit4::class)
class NetworkServiceTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: NetWorkApi

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
            .create(NetWorkApi::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun getValidSearchResponse() {
        enqueueResponse("master.json")
        val response = (getValue(service.getMasterJson()) as ApiSuccessResponse).body

        val request = mockWebServer.takeRequest()
        assertThat(request.path, `is`("/m-et/Android/json/master.json"))

        assertThat<List<ProductCategory>>(response, notNullValue())
        val productCategory = response[0]
        assertThat(
            productCategory.data,
            `is`("https://s3-ap-northeast-1.amazonaws.com/m-et/Android/json/all.json")
        )
        assertThat(productCategory.name, `is`("All"))
    }

    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader
            .getResourceAsStream("api-response/$fileName")
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(
            mockResponse
                .setBody(source.readString(Charsets.UTF_8))
        )
    }
}
