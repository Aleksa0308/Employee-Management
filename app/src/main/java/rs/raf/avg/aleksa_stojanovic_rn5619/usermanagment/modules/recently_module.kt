package rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.dataResources.local.EmployeeDataBase
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.dataResources.local.RecentlyDataBase
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.dataResources.remote.EmployeeService
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.repositories.EmployeeRepository
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.repositories.EmployeeRepositoryImpl
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.repositories.RecentlyRepository
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.repositories.RecentlyRepositoryImpl
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.viewmodel.EmployeeViewModel
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.viewmodel.RecentlyViewModel

val recentlyModule = module {

    viewModel { RecentlyViewModel(recentlyRepository = get()) }

    single<RecentlyRepository> { RecentlyRepositoryImpl(localDataSource = get()) }

    single { get<RecentlyDataBase>().getRecentlyDao() }


}