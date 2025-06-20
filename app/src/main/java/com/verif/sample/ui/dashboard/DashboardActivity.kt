package com.verif.sample.ui.dashboard

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.verif.sample.R
import com.verif.sample.databinding.ActivityDashboardBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DashboardActivity : ComponentActivity() {
    private lateinit var binding: ActivityDashboardBinding
    private val viewModel: DashboardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard);

        lifecycleScope.launch {
            viewModel.uiState.collectLatest { state ->
                when (state) {
                    is DashboardUiState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.errorLayout.visibility = View.GONE
                        binding.textView.text = ""
                    }

                    is DashboardUiState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.errorLayout.visibility = View.GONE
                        val data = state.data
                        binding.textView.text = """
                            Profile: ${data.profile.name}
                            Transactions: ${data.transactions.size}
                            Theme: ${data.config.theme}
                        """.trimIndent()
                    }

                    is DashboardUiState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.errorLayout.visibility = View.VISIBLE
                        binding.textView.text = ""
                    }
                }
            }
        }

        binding.retryButton.setOnClickListener {
            viewModel.loadDashboard()
        }
    }
}