package com.verif.sample.domain.usecase

import com.verif.sample.data.repository.DashboardRepository
import com.verif.sample.domain.model.DashboardData
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

open class DashboardUseCase @Inject constructor(
    private val repository: DashboardRepository
) {
    suspend fun execute(): DashboardData = coroutineScope {
        try {
            val profile = repository.fetchProfile()
            val transactions = repository.fetchTransactions(profile.id)
            val config = repository.fetchConfig()
            DashboardData(profile, transactions, config)
        } catch (e: Exception) {
            // Structured concurrency ensures child jobs are cancelled automatically
            throw e
        }
    }
}