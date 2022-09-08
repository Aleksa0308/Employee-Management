package rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.contract

import androidx.lifecycle.LiveData
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.models.Employee
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.view.states.*

interface EmployeeContract {
    interface ViewModel {

        val employeesState: LiveData<EmployeesState>
        val addDone: LiveData<AddEmployeeState>
        val employee : LiveData<EmployeeDetailState>
        val employeeLocal: LiveData<EmployeeDetailState>
        val deleteUser: LiveData<DeleteState>
        val updateUser: LiveData<UpdateState>
        val postUser: LiveData<PostState>
        val detailEmployee: LiveData<EmployeeDetailState>

        fun fetchAllEmployees()
        fun getAllEmployees()
        fun getEmployeeLocal(id:String)
        fun getEmployeesByName(name: String)
        fun addEmployee(employee: Employee)
        fun deleteEmployee(id: String)
        fun deleteEmployeeLocal(id: String)
        fun getEmployee(id: String)
        fun updateEmployee(id: String, emp_name: String, emp_salary: String, emp_age: String)
        fun updateEmployeeRemote(id: String, emp_name: String, emp_salary: String, emp_age: String)
        fun postEmployee(id: String, emp_name: String, emp_salary: String, emp_age: String, prof_img: String)
        //fun addToRecently(id: String, emp_name: String, emp_salary: String, emp_age: String)
    }
}