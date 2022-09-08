package rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.dataResources.remote


import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.models.LoginUser

interface LoginUserService {

@Headers("Content-Type: application/json")
@POST("/auth/login")
fun LogUser(@Body userData: LoginUser): Call<LoginUser>

}