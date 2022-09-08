package rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.view.states

sealed class PostState{
    object Success: PostState()
    data class Error(val message: String): PostState()
}
