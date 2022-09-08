package rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.models.Recently
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.databinding.LayoutItemRecentlyBinding
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.view.recycler.diff.RecentlyDiffCallback
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.view.recycler.viewholder.RecentlyViewHolder

class RecentlyAdapter: ListAdapter<Recently, RecentlyViewHolder>(RecentlyDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentlyViewHolder {
        val itemBinding = LayoutItemRecentlyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecentlyViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RecentlyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}