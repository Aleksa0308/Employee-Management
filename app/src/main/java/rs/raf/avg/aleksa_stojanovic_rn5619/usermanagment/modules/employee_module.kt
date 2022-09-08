package rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.dataResources.local.EmployeeDataBase
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.dataResources.remote.EmployeeService
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.repositories.EmployeeRepository
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.repositories.EmployeeRepositoryImpl
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.viewmodel.EmployeeViewModel

val employeeModule = module {

    viewModel { EmployeeViewModel(employeeRepository = get()) }

    single<EmployeeRepository> { EmployeeRepositoryImpl(localDataSource = get(), remoteDataSource = get()) }

    single { get<EmployeeDataBase>().getEmployeeDao() }

    single<EmployeeService> { create(retrofit = get()) }

}