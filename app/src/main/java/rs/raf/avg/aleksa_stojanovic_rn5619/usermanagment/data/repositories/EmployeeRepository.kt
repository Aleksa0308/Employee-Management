package rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.repositories

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.models.Employee
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.models.EmployeeSingleResponse
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.models.Resource

interface EmployeeRepository {
    fun fetchAll(): Observable<Resource<Unit>>
    fun getAll(): Observable<List<Employee>>
    fun getEmployeeLocal(id: String): Observable<Employee>
    fun getAllByName(name: String): Observable<List<Employee>>
    fun insert(employee: Employee): Completable
    fun deleteEmployee(id: String): Completable
    fun updateEmplLocal(id: String, emp_name: String, emp_salary: String, emp_age: String): Completable
    fun getEmployee(id: String) : Observable<Employee>
    fun deleteEmployeeLocal(id: String): Completable
    fun updateEmpRemote(id: String, emp_name: String, emp_salary: String, emp_age: String): Observable<EmployeeSingleResponse>
    fun postEmployee(id: String, emp_name: String, emp_salary: String, emp_age: String, prof_img: String): Observable<EmployeeSingleResponse>
//    fun addToRecently(id: String, emp_name: String, emp_salary: String, emp_age: String): Completable
}