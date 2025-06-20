package com.verif.sample.domain.model

data class DashboardData(
    val profile: Profile,
    val transactions: List<Transaction>,
    val config: Config
)
