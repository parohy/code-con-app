package sk.parohy.codecon.activity

import android.content.Context
import android.graphics.Rect
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import sk.parohy.codecon.api.model.God
import sk.parohy.codecon.databinding.ItemGodBinding

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

class GodViewHolder(private val binding: ItemGodBinding): RecyclerView.ViewHolder(binding.root) {
    fun setData(god: God) {
        binding.tName.text = god.name
        binding.tTitle.text = god.title
        binding.tDesc.text = god.desc
    }
}

fun Context.toPx(dp: Int) = (dp * (resources.displayMetrics.densityDpi / 160f)).toInt()

class SpacerItemDecoration(val topMarginPx: Int, val hMarginPx: Int): RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (parent.getChildLayoutPosition(view) != 0)
            outRect.top = topMarginPx
        else
            outRect.top = 0

        outRect.left = hMarginPx
        outRect.right = hMarginPx
    }
}