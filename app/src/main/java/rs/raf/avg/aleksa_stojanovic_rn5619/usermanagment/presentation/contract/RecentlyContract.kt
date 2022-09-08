package rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.contract

import androidx.lifecycle.LiveData
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.models.Employee
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.models.Recently
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.view.states.AddEmployeeState
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.view.states.AddRecentlyState
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.view.states.EmployeesState
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.view.states.RecentlyState

interface RecentlyContract {

    interface ViewModel {

        val recentlyState: LiveData<RecentlyState>
        val addDone: LiveData<AddRecentlyState>
        val xId: Int
        fun getAllRecently()
        fun getLast2Minutes()
        fun getRecentlyByName(name: String)
        fun addRecently(recently: Recently)

    }
}