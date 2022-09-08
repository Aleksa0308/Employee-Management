package rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employees")
data class EmployeeEntity(
    @PrimaryKey
    val id: String,
    val employee_name: String,
    val employee_salary: String,
    val employee_age: String
)

