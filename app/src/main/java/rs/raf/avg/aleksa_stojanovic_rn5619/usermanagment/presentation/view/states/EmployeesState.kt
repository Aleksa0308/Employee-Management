package rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.view.states

import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.models.Employee

sealed class EmployeesState{
    object Loading: EmployeesState()
    object DataFetched: EmployeesState()
    data class Success(val employees: List<Employee>): EmployeesState()
    data class Error(val message: String): EmployeesState()
}
