package com.verif.sample.data.repository

import com.verif.sample.data.repository.DashboardRepository
import com.verif.sample.domain.model.Config
import com.verif.sample.domain.model.Profile
import com.verif.sample.domain.model.Transaction

class FakeDashboardRepository : DashboardRepository {
    override suspend fun fetchProfile(): Profile {
        return Profile("123", "Ramesh Kumar", "ramesh.kumar@example.com")
    }

    override suspend fun fetchTransactions(profileId: String): List<Transaction> {
        return listOf(Transaction("txn1", 100.0, "2025-06-17"))
    }

    override suspend fun fetchConfig(): Config {
        return Config("dark", isFeatureXEnabled = true)
    }
}