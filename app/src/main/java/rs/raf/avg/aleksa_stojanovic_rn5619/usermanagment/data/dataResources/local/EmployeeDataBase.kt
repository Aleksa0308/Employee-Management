package rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.dataResources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.models.EmployeeEntity

@Database(
    entities = [EmployeeEntity::class],
    version = 1,
    exportSchema = false)
@TypeConverters()
abstract class EmployeeDataBase : RoomDatabase(){
    abstract fun getEmployeeDao(): EmployeeDao
}
