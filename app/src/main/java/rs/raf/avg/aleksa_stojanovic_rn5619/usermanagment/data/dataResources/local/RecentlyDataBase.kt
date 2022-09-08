package rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.dataResources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.models.RecentlyEntity


@Database(
    entities = [RecentlyEntity::class],
    version = 4,
    exportSchema = false)
    abstract class RecentlyDataBase : RoomDatabase(){
        abstract fun getRecentlyDao(): RecentlyDao
}