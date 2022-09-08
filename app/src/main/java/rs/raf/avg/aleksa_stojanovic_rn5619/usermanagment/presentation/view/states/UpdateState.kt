package rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.view.states

sealed class UpdateState{
    object Success: UpdateState()
    data class Error(val message: String): UpdateState()
}
