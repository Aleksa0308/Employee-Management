package rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.view.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.R
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.contract.EmployeeContract
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.view.states.EmployeeDetailState
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.viewmodel.EmployeeViewModel
import timber.log.Timber

class UpdateActivity : AppCompatActivity() {

    private val employeeViewModel: EmployeeContract.ViewModel by viewModel<EmployeeViewModel>()
    private lateinit var editName: EditText
    private lateinit var editSalary: EditText
    private lateinit var editAge: EditText
    private lateinit var update: Button
    private lateinit var imageEmp: ImageView
    private lateinit var progresBar: ProgressBar
    private var x = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
        init()
    }

    private fun init(){
        initUi()
        initObservers()
    }


    private fun initObservers() {
        employeeViewModel.employeeLocal.observe(this, Observer {
            Timber.e(it.toString())
            renderState(it)
        })
        val idExtra = intent.getStringExtra("id")
        if (idExtra != null) {
            x = idExtra
            employeeViewModel.getEmployeeLocal(idExtra)
            //employeeViewModel.getEmployeeLocal()
        }
    }

    private fun initUi(){
        editName = findViewById(R.id.editName)
        editSalary = findViewById(R.id.editSalary)
        editAge = findViewById(R.id.editAge)
        imageEmp = findViewById(R.id.imageView5)
        update = findViewById(R.id.updateBtn)
        progresBar = findViewById(R.id.loadingPb3)

        update.setOnClickListener {
            println("HMMMM: " + x)
            //Locale
            employeeViewModel.updateEmployee(x, editName.text.toString(), editSalary.text.toString(),editAge.text.toString())
            //Remote
            employeeViewModel.updateEmployeeRemote(x, editName.text.toString(), editSalary.text.toString(),editAge.text.toString())
            finish()
        }
    }

    @SuppressLint("SetTextI18n", "ResourceAsColor")
    private fun renderState(state: EmployeeDetailState) {
        when (state) {
            is EmployeeDetailState.Success -> {
                showLoadingState(false)
                val id  = employeeViewModel.employeeLocal.value.toString().split("id=","employee_name=","employee_salary=","employee_age=")
                //println(id[0].dropLast(1))
                println(id[1].dropLast(2))
                println(id[2].dropLast(2))
                println(id[3].dropLast(2))
                println(id[4].dropLast(2))
                editName.setText(id[2].dropLast(2))
                editSalary.setText(id[3].dropLast(2))
                editAge.setText(id[4].dropLast(2))

                Toast.makeText(this, "Employee data fetched from the db", Toast.LENGTH_LONG).show()
            }
            is EmployeeDetailState.Error -> {
                showLoadingState(false)
                Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
            }
            is EmployeeDetailState.DataFetched -> {
                showLoadingState(false)
                Toast.makeText(this, "Employee data fetched from the db", Toast.LENGTH_LONG).show()
            }
            is EmployeeDetailState.Loading -> {
                showLoadingState(true)
            }
        }
    }

    private fun showLoadingState(loading: Boolean) {
        editName.isVisible = !loading
        editSalary.isVisible = !loading
        editAge.isVisible = !loading
        imageEmp.isVisible = !loading
        update.isVisible = !loading
        progresBar.isVisible = loading
    }
}