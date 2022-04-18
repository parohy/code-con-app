package sk.parohy.codecon.activity

import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.collect
import sk.parohy.codecon.api.NetworkResult
import sk.parohy.codecon.api.isLoading
import sk.parohy.codecon.databinding.ActivityHomeBinding
import sk.parohy.codecon.viewmodel.HomeViewModel

class HomeActivity: VBActivity<ActivityHomeBinding>(ActivityHomeBinding::inflate) {
    private val adapter = GodAdapter()

    override fun ActivityHomeBinding.onBind() {
        val model by viewModels<HomeViewModel>()

        swipeRefresh.setOnRefreshListener { model.refresh() }

        rvGods.layoutManager = LinearLayoutManager(this@HomeActivity)
        rvGods.adapter = adapter
        rvGods.addItemDecoration(SpacerItemDecoration(toPx(8), toPx(16)))

        bSignout.setOnClickListener {
            model.logOut(PreferenceManager.getDefaultSharedPreferences(this@HomeActivity))
            startLoginActivity()
        }

        lifecycleScope.launchWhenResumed {
            model.state.collect { state ->
                when (state) {
                    is NetworkResult.Failure -> Log.d("Home", state.ex.toString())
                    is NetworkResult.Success -> adapter.data = state.value
                    null -> model.refresh()
                }

                swipeRefresh.isRefreshing = state.isLoading
            }
        }
    }
}
