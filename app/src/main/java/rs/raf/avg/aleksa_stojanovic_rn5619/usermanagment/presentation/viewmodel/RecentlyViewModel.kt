package rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.models.Employee
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.models.Recently
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.repositories.EmployeeRepository
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.repositories.RecentlyRepository
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.contract.EmployeeContract
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.contract.RecentlyContract
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.view.states.AddEmployeeState
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.view.states.AddRecentlyState
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.view.states.EmployeesState
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.view.states.RecentlyState
import timber.log.Timber
import java.util.concurrent.TimeUnit

class RecentlyViewModel(
    private val recentlyRepository: RecentlyRepository,



    ) : ViewModel(), RecentlyContract.ViewModel {
    override var xId = 0
    private val subscriptions = CompositeDisposable()
    override val recentlyState: MutableLiveData<RecentlyState> = MutableLiveData()
    private val publishSubject: PublishSubject<String> = PublishSubject.create()
    override val addDone: MutableLiveData<AddRecentlyState> = MutableLiveData()

    init {
        val subscription = publishSubject
            .debounce(200, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMap {
                recentlyRepository
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
                    recentlyState.value = RecentlyState.Success(it)
                },
                {
                    recentlyState.value = RecentlyState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }


    override fun getAllRecently() {
        val subscription = recentlyRepository
            .getLast2Minutes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    recentlyState.value = RecentlyState.Success(it)
                },
                {
                    recentlyState.value = RecentlyState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getLast2Minutes() {
        val subscription = recentlyRepository
            .getLast2Minutes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    recentlyState.value = RecentlyState.Success(it)
                },
                {
                    recentlyState.value = RecentlyState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getRecentlyByName(name: String) {
        publishSubject.onNext(name)
    }

    override fun addRecently(recently: Recently) {
        val subscription = recentlyRepository
            .insert(recently)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    addDone.value = AddRecentlyState.Success
                    xId++
                },
                {
                    addDone.value = AddRecentlyState.Error("Error happened while adding employee")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

}