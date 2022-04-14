package sk.parohy.codecon.activity

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.collect
import sk.parohy.codecon.api.NetworkResult
import sk.parohy.codecon.api.isLoading
import sk.parohy.codecon.api.model.God
import sk.parohy.codecon.databinding.ActivityHomeBinding
import sk.parohy.codecon.databinding.ItemGodBinding
import sk.parohy.codecon.viewmodel.HomeViewModel

class GodViewHolder(private val binding: ItemGodBinding): RecyclerView.ViewHolder(binding.root) {
    fun setData(god: God) {
        binding.tName.text = god.name
        binding.tTitle.text = god.title
        binding.tDesc.text = god.desc
    }
}

class GodAdapter: RecyclerView.Adapter<GodViewHolder>() {
    var data: List<God> = emptyList()
        set(value) {
            field = value
            Log.d("List", field.toString())
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GodViewHolder {
        val binding = ItemGodBinding.inflate(LayoutInflater.from(parent.context))
        return GodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GodViewHolder, position: Int) {
        holder.setData(data[position])
    }

    override fun getItemCount(): Int = data.size

}

class HomeActivity: VBActivity<ActivityHomeBinding>(ActivityHomeBinding::inflate) {
    private val adapter = GodAdapter()

    override fun ActivityHomeBinding.onBind() {
        val model by viewModels<HomeViewModel>()

        swipeRefresh.setOnRefreshListener { model.refresh() }

        rvGods.layoutManager = LinearLayoutManager(this@HomeActivity)
        rvGods.adapter = adapter

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