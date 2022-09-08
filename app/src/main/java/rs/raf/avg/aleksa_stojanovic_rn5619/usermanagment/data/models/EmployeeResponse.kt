package rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EmployeeResponse(
    val id: String,
    val employee_name : String,
    val employee_salary: String,
    val employee_age: String,
    val profile_image: String
    )
