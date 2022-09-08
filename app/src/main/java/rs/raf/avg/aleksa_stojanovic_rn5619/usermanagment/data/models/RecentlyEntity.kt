package rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.time.LocalDateTime

@Entity(tableName = "recently")
data class RecentlyEntity(
    @PrimaryKey
    val id: String,
    val employee_name: String,
    val time: String
)
