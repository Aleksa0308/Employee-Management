package rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.models.Employee
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.models.Resource
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.repositories.EmployeeRepository
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.contract.EmployeeContract
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.view.states.*
import timber.log.Timber
import java.util.concurrent.TimeUnit


class EmployeeViewModel(
    private val employeeRepository: EmployeeRepository,

) : ViewModel(), EmployeeContract.ViewModel {

    private val subscriptions = CompositeDisposable()
    override val employeesState: MutableLiveData<EmployeesState> = MutableLiveData()
    override val addDone: MutableLiveData<AddEmployeeState> = MutableLiveData()
    override val employee: MutableLiveData<EmployeeDetailState> = MutableLiveData()
    override val employeeLocal: MutableLiveData<EmployeeDetailState> = MutableLiveData()
    override val deleteUser: MutableLiveData<DeleteState> = MutableLiveData()
    override val updateUser: LiveData<UpdateState> = MutableLiveData()
    override val postUser: LiveData<PostState> = MutableLiveData()
    override val detailEmployee: LiveData<EmployeeDetailState> = MutableLiveData()
    private val publishSubject: PublishSubject<String> = PublishSubject.create()


    init {
        val subscription = publishSubject
            .debounce(200, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMap {
                employeeRepository
                    .getAllByName(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError {
                        Timber.e("Error in publish subject")
                        Timber.e(it)
                    }
            }
            .subscribe(
                {
                    employeesState.value = EmployeesState.Success(it)
                },
                {
                    employeesState.value = EmployeesState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun fetchAllEmployees() {
        val subscription = employeeRepository
            .fetchAll()
            .startWith(Resource.Loading()) //Kada se pokrene fetch hocemo da postavimo stanje na Loading
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when(it) {
                        is Resource.Loading -> employeesState.value = EmployeesState.Loading
                        is Resource.Success -> employeesState.value = EmployeesState.DataFetched
                        is Resource.Error -> employeesState.value = EmployeesState.Error("Error happened while fetching data from the server")
                    }
                },
                {
                    employeesState.value = EmployeesState.Error("Error happened while fetching data from the server")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getAllEmployees() {
        val subscription = employeeRepository
            .getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    employeesState.value = EmployeesState.Success(it)
                },
                {
                    employeesState.value = EmployeesState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getEmployeeLocal(id: String) {
        val subscription = employeeRepository
            .getEmployeeLocal(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    employeeLocal.value = EmployeeDetailState.Success(it)

                },
                {
                    employeeLocal.value = EmployeeDetailState.Error(it.toString())
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getEmployeesByName(name: String) {
        publishSubject.onNext(name)
    }

    override fun addEmployee(employee: Employee) {
        val subscription = employeeRepository
            .insert(employee)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    addDone.value = AddEmployeeState.Success
                },
                {
                    addDone.value = AddEmployeeState.Error("Error happened while adding employee")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun deleteEmployee(id: String) {
        val subscription = employeeRepository
            .deleteEmployee(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    deleteUser.value = DeleteState.Success
                },
                {
                    Timber.e(it)
                    deleteUser.value = DeleteState.Error(it.toString())
                }
            )
        subscriptions.add(subscription)

    }

    override fun deleteEmployeeLocal(id: String) {
        val subscription = employeeRepository
            .deleteEmployeeLocal(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    deleteUser.value = DeleteState.Success
                },
                {
                    Timber.e(it)
                    deleteUser.value = DeleteState.Error(it.toString())
                }
            )
        subscriptions.add(subscription)

    }

    override fun getEmployee(id: String) {
        val subscription = employeeRepository
            .getEmployee(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    employee.value = EmployeeDetailState.Success(it)

                },
                {
                    employee.value = EmployeeDetailState.Error(it.toString())
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun updateEmployee(id: String, emp_name: String, emp_salary: String, emp_age: String) {
        val subscription = employeeRepository
            .updateEmplLocal(id, emp_name, emp_salary, emp_age)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Timber.e("UPDATED LOCAL!")
                },
                {
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun updateEmployeeRemote(
        id: String,
        emp_name: String,
        emp_salary: String,
        emp_age: String
    ) {
        val subscription = employeeRepository
            .updateEmpRemote(id, emp_name, emp_salary, emp_age)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Timber.e(it.message)
                },
                {
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun postEmployee(id: String, emp_name: String, emp_salary: String, emp_age: String, prof_img: String) {
        val subscription = employeeRepository
            .postEmployee(id, emp_name, emp_salary, emp_age, prof_img)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Timber.e(it.message)
                },
                {
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

//    override fun addToRecently(id: String, emp_name: String, emp_salary: String, emp_age: String) {
//        val subscription = employeeRepository
//            .addToRecently(id, emp_name, emp_salary, emp_age)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                {
//                    Timber.e("ADDED TO RECENTLY!")
//                },
//                {
//                    Timber.e(it)
//                }
//            )
//        subscriptions.add(subscription)
//    }


    override fun onCleared() {
        super.onCleared()
        subscriptions.dispose()
    }
}