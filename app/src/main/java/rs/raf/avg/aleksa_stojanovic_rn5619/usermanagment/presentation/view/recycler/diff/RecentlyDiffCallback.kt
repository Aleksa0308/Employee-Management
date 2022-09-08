package rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.models.Employee
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.models.Recently

class RecentlyDiffCallback : DiffUtil.ItemCallback<Recently>() {
    override fun areItemsTheSame(oldItem: Recently, newItem: Recently): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Recently, newItem: Recently): Boolean {
        return oldItem.employee_name == newItem.employee_name
    }

}