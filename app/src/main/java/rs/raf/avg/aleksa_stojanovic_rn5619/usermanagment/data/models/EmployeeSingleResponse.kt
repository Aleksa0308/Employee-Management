package rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.models

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class EmployeeSingleResponse(
    val status: String,
    val data: EmployeeResponse,
    val message: String)
