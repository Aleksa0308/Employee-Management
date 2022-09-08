package rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.view.recycler.viewholder

import androidx.recyclerview.widget.RecyclerView
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.models.Recently
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.databinding.LayoutItemRecentlyBinding

class RecentlyViewHolder(private val itemBinding: LayoutItemRecentlyBinding) : RecyclerView.ViewHolder(itemBinding.root){
    fun bind(recently: Recently) {
        itemBinding.recentlyTv.text = recently.employee_name
        itemBinding.dateTv.text = recently.time
    }
}