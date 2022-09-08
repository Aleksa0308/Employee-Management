package rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.view.states

import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.models.Employee

sealed class EmployeeDetailState {
    object Loading: EmployeeDetailState()
    object DataFetched: EmployeeDetailState()
    data class Success(val employee: Employee): EmployeeDetailState()
    data class Error(val message: String): EmployeeDetailState()
}