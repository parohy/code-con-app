package sk.parohy.codecon.activity

import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import sk.parohy.codecon.api.NetworkResult
import sk.parohy.codecon.api.isLoading
import sk.parohy.codecon.api.model.God
import sk.parohy.codecon.databinding.ActivityHomeBinding
import sk.parohy.codecon.viewmodel.HomeViewModel

class HomeActivity: VBActivity<ActivityHomeBinding>(ActivityHomeBinding::inflate) {
    override fun ActivityHomeBinding.onBind() {
        val model by viewModels<HomeViewModel>()

        swipeRefresh.setOnRefreshListener { model.refresh() }

        lifecycleScope.launchWhenResumed {
            model.state.collect { state ->
                when (state) {
                    is NetworkResult.Failure -> Log.d("Home", state.ex.toString())
                    is NetworkResult.Success -> showResults(state.value)
                    null -> model.refresh()
                }

                swipeRefresh.isRefreshing = state.isLoading
            }
        }
    }

    private fun ActivityHomeBinding.showResults(gods: List<God>) {
        Log.d("Home", gods.toString())
    }
}