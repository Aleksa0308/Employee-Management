package rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.view.states

sealed class AddRecentlyState{
    object Success: AddRecentlyState()
    data class Error(val message: String): AddRecentlyState()
}
