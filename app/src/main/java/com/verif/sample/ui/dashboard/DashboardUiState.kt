package com.verif.sample.ui.dashboard

import com.verif.sample.domain.model.DashboardData

sealed class DashboardUiState {
    object Loading : DashboardUiState()
    data class Success(val data: DashboardData) : DashboardUiState()
    data class Error(val message: String) : DashboardUiState()
}