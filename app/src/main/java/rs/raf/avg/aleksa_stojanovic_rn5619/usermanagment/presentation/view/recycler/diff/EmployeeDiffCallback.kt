package rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.models.Employee

class EmployeeDiffCallback : DiffUtil.ItemCallback<Employee>() {
    override fun areItemsTheSame(oldItem: Employee, newItem: Employee): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Employee, newItem: Employee): Boolean {
        return oldItem.employee_name == newItem.employee_name
    }
}