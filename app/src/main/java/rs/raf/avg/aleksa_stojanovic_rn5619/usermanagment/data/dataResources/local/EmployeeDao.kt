package rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.dataResources.local

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.models.EmployeeEntity

@Dao
abstract class EmployeeDao {
    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insert(entity: EmployeeEntity): Completable

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insertAll(entities: List<EmployeeEntity>): Completable

    @Query("SELECT * FROM employees")
    abstract fun getAll(): Observable<List<EmployeeEntity>>

    @Query("DELETE FROM employees")
    abstract fun deleteAll()


    @Query("DELETE FROM employees WHERE id == :id")
    abstract fun deleteEmployee(id: String): Completable

    @Delete
    abstract fun delete(employeeEntity: EmployeeEntity)

    @Transaction
    open fun deleteAndInsertAll(entities: List<EmployeeEntity>) {
        deleteAll()
        insertAll(entities).blockingAwait()
    }

    @Query("UPDATE employees SET employee_name = :name, employee_salary = :salary, employee_age = :age WHERE id == :id")
    abstract fun updateEmpById(id: Int, name: String, salary: Int, age: Int): Completable

    @Query("SELECT * FROM employees WHERE employee_name LIKE :name || '%'")
    abstract fun getByName(name: String): Observable<List<EmployeeEntity>>

    @Query("SELECT * FROM employees WHERE id == :id")
    abstract fun getEmployeeById(id: String): Observable<EmployeeEntity>
//    @Query("INSERT INTO added (id, employee_name, employee_salary, employee_age, time) values(:id, :emp_name,:emp_salary, :emp_age, current_timestamp)")
//    abstract fun addToRecently(id: String, emp_name: String, emp_salary: String, emp_age: String): Completable
//

}