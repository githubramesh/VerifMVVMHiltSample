package com.verif.sample.ui.dashboard

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.verif.sample.data.repository.FakeDashboardRepository
import com.verif.sample.domain.usecase.DashboardUseCase
import com.verif.sample.ui.dashboard.DashboardUiState
import com.verif.sample.ui.dashboard.DashboardViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.*

@OptIn(ExperimentalCoroutinesApi::class)
class DashboardViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: DashboardViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        val fakeUseCase = object : DashboardUseCase(FakeDashboardRepository()) {}
        viewModel = DashboardViewModel(fakeUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test success state emission`() = runTest {
        viewModel.loadDashboard()
        advanceUntilIdle() // Wait for coroutines

        val state = viewModel.uiState.value
        assert(state is DashboardUiState.Success)
        assert((state as DashboardUiState.Success).data.profile.name == "Ramesh Kumar")
    }
}