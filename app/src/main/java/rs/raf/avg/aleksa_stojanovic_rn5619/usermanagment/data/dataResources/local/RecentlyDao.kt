package rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.dataResources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.models.EmployeeEntity
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.models.RecentlyEntity

@Dao
abstract class RecentlyDao {

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insert(entity: RecentlyEntity): Completable

    @Query("SELECT * FROM recently")
    abstract fun getAll(): Observable<List<RecentlyEntity>>

    @Query("SELECT * FROM recently WHERE employee_name LIKE :name || '%'")
    abstract fun getByName(name: String): Observable<List<RecentlyEntity>>

    @Query("SELECT * FROM recently WHERE substr(time,15, 2) + 0 >= substr(time('now', 'localtime'),4,2) -2")
    abstract fun getLast2Minutes(): Observable<List<RecentlyEntity>>

}