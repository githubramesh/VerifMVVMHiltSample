package com.verif.sample.domain.usecase

import com.verif.sample.domain.usecase.DashboardUseCase
import com.verif.sample.data.repository.DashboardRepository
import com.verif.sample.data.repository.DashboardRepositoryImpl
import com.verif.sample.network.DashboardApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@ExperimentalCoroutinesApi
class DashboardUseCaseTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var api: DashboardApi
    private lateinit var repository: DashboardRepository
    private lateinit var useCase: DashboardUseCase

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/")) // localhost URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(DashboardApi::class.java)
        repository = DashboardRepositoryImpl(api)
        useCase = DashboardUseCase(repository)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `test dashboard data success`() = runTest {
        // Enqueue mock responses
        mockWebServer.enqueue(MockResponse().setBody("""
            {
              "id": "1",
              "name": "Ramesh Kumar",
              "email": "ramesh.kumar@example.com"
            }
        """))

        mockWebServer.enqueue(MockResponse().setBody("""
            [
              { "id": "txn1", "amount": 100.0, "date": "2025-06-17" }
            ]
        """))

        mockWebServer.enqueue(MockResponse().setBody("""
            {
              "theme": "dark",
              "isFeatureXEnabled": true
            }
        """))

        val result = useCase.execute()
        Assert.assertEquals("Ramesh Kumar", result.profile.name)
        Assert.assertEquals(1, result.transactions.size)
        Assert.assertEquals("dark", result.config.theme)
    }
}