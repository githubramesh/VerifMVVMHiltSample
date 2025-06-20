package com.verif.sample.data.repository

import com.verif.sample.network.DashboardApi
import javax.inject.Inject

class DashboardRepositoryImpl @Inject constructor(
private val api: DashboardApi
) : DashboardRepository {
    override suspend fun fetchProfile() = api.getProfile()
    override suspend fun fetchTransactions(profileId: String) = api.getTransactions(profileId)
    override suspend fun fetchConfig() = api.getConfig()
}