package com.verif.sample.network

import com.verif.sample.domain.model.Config
import com.verif.sample.domain.model.Profile
import com.verif.sample.domain.model.Transaction
import retrofit2.http.GET
import retrofit2.http.Path

interface DashboardApi {

    @GET("profile")
    suspend fun getProfile(): Profile

    @GET("profile/{id}/transactions")
    suspend fun getTransactions(@Path("id") profileId: String): List<Transaction>

    @GET("config")
    suspend fun getConfig(): Config
}