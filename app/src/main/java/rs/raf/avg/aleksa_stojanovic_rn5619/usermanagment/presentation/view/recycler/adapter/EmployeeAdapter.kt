package rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.models.Employee
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.databinding.LayoutItemEmployeeBinding
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.view.recycler.diff.EmployeeDiffCallback
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.view.recycler.viewholder.EmployeeViewHolder

class EmployeeAdapter  (val openDetailed: (stock: Employee) -> Unit) : ListAdapter<Employee,  EmployeeViewHolder>(EmployeeDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val itemBinding = LayoutItemEmployeeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EmployeeViewHolder(itemBinding)
        { openDetailed(getItem(it)) }
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }



}