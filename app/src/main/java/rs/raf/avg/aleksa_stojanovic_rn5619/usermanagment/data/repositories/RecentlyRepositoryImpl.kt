package rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.repositories

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.dataResources.local.RecentlyDao
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.models.Recently
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.models.RecentlyEntity
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

class RecentlyRepositoryImpl(
    private val localDataSource: RecentlyDao,
): RecentlyRepository {
    override fun getAll(): Observable<List<Recently>> {
        return localDataSource
            .getAll()
            .map {
                it.map {
                    Recently(it.id, it.employee_name,it.time)
                }
            }
    }

    override fun getAllByName(name: String): Observable<List<Recently>> {
        return localDataSource
            .getByName(name)
            .map {
                it.map {
                    Recently(it.id, it.employee_name,it.time)
                }
            }
    }

    override fun insert(recently: Recently): Completable {
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        val currentDate = sdf.format(Date())
        val recentlyEntity = RecentlyEntity(recently.id, recently.employee_name, currentDate)
        return localDataSource
            .insert(recentlyEntity)
    }

    override fun getLast2Minutes(): Observable<List<Recently>> {
        return localDataSource
            .getLast2Minutes()
            .map {
                it.map {
                    Recently(it.id, it.employee_name,it.time)
                }
            }
    }

}