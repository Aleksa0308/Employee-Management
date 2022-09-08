package rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.view.recycler.viewholder

import androidx.recyclerview.widget.RecyclerView
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.models.Employee
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.databinding.LayoutItemEmployeeBinding

class EmployeeViewHolder(private val itemEmployeeBinding: LayoutItemEmployeeBinding, val openDetailed: (position: Int) -> Unit) : RecyclerView.ViewHolder(itemEmployeeBinding.root) {

    init {
        itemEmployeeBinding.root.setOnClickListener {
            openDetailed(layoutPosition)
        }
    }

    fun bind(employee: Employee){
            itemEmployeeBinding.employeeTv.text = employee.employee_name
        }
}