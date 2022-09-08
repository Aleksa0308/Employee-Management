package rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.view.states

sealed class DeleteState{
    object Success: DeleteState()
    data class Error(val message: String): DeleteState()
}
