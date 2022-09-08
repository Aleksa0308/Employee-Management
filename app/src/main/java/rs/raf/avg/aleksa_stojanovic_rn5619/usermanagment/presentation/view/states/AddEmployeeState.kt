package rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.view.states

sealed class AddEmployeeState{
    object Success: AddEmployeeState()
    data class Error(val message: String): AddEmployeeState()
}
