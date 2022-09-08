package rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.view.states

import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.models.Employee
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.models.Recently

sealed class RecentlyState{
    object Loading: RecentlyState()
    object DataFetched: RecentlyState()
    data class Success(val recentlys: List<Recently>): RecentlyState()
    data class Error(val message: String): RecentlyState()
}
