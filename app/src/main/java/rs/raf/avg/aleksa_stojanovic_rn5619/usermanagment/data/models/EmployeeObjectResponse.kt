package rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EmployeeObjectResponse(
    val status: String,
    val data: List<EmployeeResponse>,
    val message: String
)
