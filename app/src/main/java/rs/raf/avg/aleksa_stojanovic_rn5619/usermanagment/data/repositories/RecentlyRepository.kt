package rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.repositories

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.models.Employee
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.models.Recently

interface RecentlyRepository {
    fun getAll(): Observable<List<Recently>>
    fun getAllByName(name: String): Observable<List<Recently>>
    fun insert(recently: Recently): Completable
    fun getLast2Minutes(): Observable<List<Recently>>
}