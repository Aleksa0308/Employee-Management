package rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.dataResources.remote

import io.reactivex.Completable
import io.reactivex.Observable
import retrofit2.http.*
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.models.EmployeeSingleResponse
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.models.EmployeeObjectResponse
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.models.EmployeeRequestBody
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.models.EmployeeRequestBodyPost

interface EmployeeService {

    @GET("employees")
    fun getAll(): Observable<EmployeeObjectResponse>
    @DELETE("delete/{id}")
    fun deleteUser(@Path("id") id: String) : Completable
    @GET("employee/{id}")
    fun getEmployee(@Path("id") id: String) : Observable<EmployeeSingleResponse>
    @PUT("update/{id}")
    fun putEmployee(@Path("id") id: String, @Body body: EmployeeRequestBody): Observable<EmployeeSingleResponse>
    @POST("create")
    fun postEmployee(@Body body: EmployeeRequestBodyPost): Observable<EmployeeSingleResponse>
}