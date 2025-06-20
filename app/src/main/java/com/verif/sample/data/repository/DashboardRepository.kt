package com.verif.sample.data.repository

import com.verif.sample.domain.model.Config
import com.verif.sample.domain.model.Profile
import com.verif.sample.domain.model.Transaction

interface DashboardRepository {
    suspend fun fetchProfile(): Profile
    suspend fun fetchTransactions(profileId: String): List<Transaction>
    suspend fun fetchConfig(): Config
}