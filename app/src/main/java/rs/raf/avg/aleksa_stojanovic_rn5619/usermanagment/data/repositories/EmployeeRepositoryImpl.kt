package rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.repositories

import android.annotation.SuppressLint
import io.reactivex.Completable
import io.reactivex.Observable
import org.w3c.dom.Entity
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.dataResources.local.EmployeeDao
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.dataResources.remote.EmployeeService
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.models.*
import timber.log.Timber

class EmployeeRepositoryImpl(
    private val localDataSource: EmployeeDao,
    private val remoteDataSource: EmployeeService
    ): EmployeeRepository {
    override fun fetchAll(): Observable<Resource<Unit>> {
        return remoteDataSource
            .getAll()
            .doOnNext {
                Timber.e("Upis u bazu")
                val entities = it.data.map {
                    EmployeeEntity(
                        it.id,
                        it.employee_name,
                        it.employee_salary,
                        it.employee_age,

                    )
                }
                localDataSource.deleteAndInsertAll(entities)
            }
            .map {
                Resource.Success(Unit)
            }
    }


    override fun getAll(): Observable<List<Employee>> {
        return localDataSource
            .getAll()
            .map {
                it.map {
                    Employee(it.id, it.employee_name,it.employee_salary,it.employee_age)
                }
            }
    }

    override fun getEmployeeLocal(id: String): Observable<Employee> {
        return localDataSource.getEmployeeById(id).map {
            Employee(it.id, it.employee_name, it.employee_salary, it.employee_age) }
    }

    override fun getAllByName(name: String): Observable<List<Employee>> {
        return localDataSource
            .getByName(name)
            .map {
                it.map {
                    Employee(it.id, it.employee_name,it.employee_salary,it.employee_age)
                }
            }
    }

    override fun insert(employee: Employee): Completable {
        val employeeEntity = EmployeeEntity(employee.id, employee.employee_name, employee.employee_salary, employee.employee_age)
        return localDataSource
            .insert(employeeEntity)
    }




    override fun deleteEmployee(id: String): Completable = remoteDataSource.deleteUser(id)
    override fun updateEmplLocal(
        id: String,
        emp_name: String,
        emp_salary: String,
        emp_age: String
    ): Completable {
        return localDataSource.updateEmpById(id.toInt(), emp_name, emp_salary.toInt(), emp_age.toInt())
    }


    //override fun deleteEmployeeLocal(id: String): Completable = localDataSource.deleteEmployee(id)


    @SuppressLint("CheckResult")
    override fun getEmployee(id: String): Observable<Employee> {
        return remoteDataSource.getEmployee(id).map {
            Employee(it.data.id, it.data.employee_name, it.data.employee_salary, it.data.employee_age)
        }
    }

    override fun deleteEmployeeLocal(id: String): Completable = localDataSource.deleteEmployee(id)
    override fun updateEmpRemote(
        id: String,
        emp_name: String,
        emp_salary: String,
        emp_age: String
    ): Observable<EmployeeSingleResponse> {
        return remoteDataSource.putEmployee(
            id = id,
            body = EmployeeRequestBody(
                id, emp_name, emp_salary, emp_age
            )
        ).map {
            EmployeeSingleResponse(
                it.status,it.data,it.message
            )
         }
    }

    override fun postEmployee(
        id: String,
        emp_name: String,
        emp_salary: String,
        emp_age: String,
        prof_img:String
    ): Observable<EmployeeSingleResponse> {
        return remoteDataSource.postEmployee(
            body = EmployeeRequestBodyPost(
                id, emp_name, emp_salary, emp_age, prof_img
            )
        ).map {
            EmployeeSingleResponse(
                it.status,it.data,it.message
            )
        }
    }

//    override fun addToRecently(id: String, emp_name: String, emp_salary: String, emp_age: String): Completable =
//        localDataSource.addToRecently(id,emp_name,emp_salary,emp_age)




}