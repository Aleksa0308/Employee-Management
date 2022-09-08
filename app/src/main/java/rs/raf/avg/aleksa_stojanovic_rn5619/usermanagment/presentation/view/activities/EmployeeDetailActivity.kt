package rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.view.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.map
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.R
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.models.Employee
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.contract.EmployeeContract
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.view.states.EmployeeDetailState
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.view.states.EmployeesState
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.viewmodel.EmployeeViewModel
import timber.log.Timber

class EmployeeDetailActivity : AppCompatActivity() {


    private val employeeViewModel: EmployeeContract.ViewModel by viewModel<EmployeeViewModel>()

    private lateinit var employeeName: TextView
    private lateinit var employeeSalary: TextView
    private lateinit var employeeAge: TextView
    private lateinit var employeeImage: ImageView
    private lateinit var progress: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_detail)
        init()
    }

    private fun init(){
        initUi()
        initObservers()
    }
    private fun initObservers() {
        employeeViewModel.employee.observe(this, Observer {
            Timber.e(it.toString())
            renderState(it)
        })
        val idExtra = intent.getStringExtra("id")
        if (idExtra != null) {
            employeeViewModel.getEmployee(idExtra)
        }


    }

    private fun initUi(){
        employeeName = findViewById(R.id.employeeName)
        employeeSalary = findViewById(R.id.employeeSalary)
        employeeAge = findViewById(R.id.employeeAge)
        employeeImage = findViewById(R.id.imageView4)
        progress = findViewById(R.id.loadingPb2)
    }

    @SuppressLint("SetTextI18n", "ResourceAsColor")
    private fun renderState(state: EmployeeDetailState) {
        when (state) {
            is EmployeeDetailState.Success -> {
                showLoadingState(false)
                val id  = employeeViewModel.employee.value.toString().split("id=","employee_name=","employee_salary=","employee_age=")
                //println(id[0].dropLast(1))
                println(id[1].dropLast(2))
                println(id[2].dropLast(2))
                println(id[3].dropLast(2))
                println(id[4].dropLast(2))
                employeeName.text = id[2].dropLast(2)

                employeeSalary.text = ("Salary: " + id[3].dropLast(2)+ " $/year")
                if(id[3].dropLast(2).toInt()<100000){
                    //employeeSalary.setTextColor(R.color.red)
                    employeeSalary.setTextColor(ContextCompat.getColor(employeeSalary.context, R.color.red))
                }else{
                    //employeeSalary.setTextColor(R.color.green)
                    employeeSalary.setTextColor(ContextCompat.getColor(employeeSalary.context, R.color.green))
                }

                employeeAge.text = ("Age: " + id[4].dropLast(2))
                Toast.makeText(this, "Employee data fetched from the server", Toast.LENGTH_LONG).show()
            }
            is EmployeeDetailState.Error -> {
                showLoadingState(false)
                Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
            }
            is EmployeeDetailState.DataFetched -> {
                showLoadingState(false)
                Toast.makeText(this, "Employee data fetched from the server", Toast.LENGTH_LONG).show()
            }
            is EmployeeDetailState.Loading -> {
                showLoadingState(true)
            }
        }
    }

    private fun showLoadingState(loading: Boolean) {
        employeeName.isVisible = !loading
        employeeSalary.isVisible = !loading
        employeeAge.isVisible = !loading
        employeeImage.isVisible = !loading
        progress.isVisible = loading
    }
}


