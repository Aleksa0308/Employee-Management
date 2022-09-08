package rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.view.activities.ui.home;

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.R
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.dataResources.remote.EmployeeService
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.dataResources.remote.LoginUserService
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.models.Employee
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.databinding.FragmentListBinding
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.contract.EmployeeContract
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.view.activities.DrawerNavigationActivity
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.view.activities.EmployeeDetailActivity
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.view.activities.UpdateActivity
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.view.recycler.adapter.EmployeeAdapter
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.view.states.DeleteState
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.view.states.EmployeesState
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.view.states.PostState
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.view.states.UpdateState
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.viewmodel.EmployeeViewModel
import timber.log.Timber
import org.koin.androidx.scope.scopeActivity as scopeActivity1


class HomeFragment : Fragment(R.layout.fragment_list) {

    private val employeeViewModel: EmployeeContract.ViewModel by sharedViewModel<EmployeeViewModel>()

    private var _binding: FragmentListBinding? = null

    private val binding get() = _binding!!

    private lateinit var adapter: EmployeeAdapter
    private var i = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        i++
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initUi()
        initObservers()
    }

    private fun initUi() {
        initRecycler()
        initListeners()
    }

    private fun initRecycler() {
        binding.listRv.layoutManager = LinearLayoutManager(context)
        adapter = EmployeeAdapter(::openDetailed)
        binding.listRv.adapter = adapter
    }

    private fun openDetailed(singleEmployee: Employee){
        val items = arrayOf("Delete employee", "Update employee", "Employee Details")
        var selectedItem: String = items[0]
        var selectedItemIndex: Int = 0
        val alert = AlertDialog.Builder(context)


        alert.setTitle(singleEmployee.employee_name)
        alert.setSingleChoiceItems(items, selectedItemIndex) { dialog_, which ->
            selectedItemIndex = which
            selectedItem = items[which]
        }
            .setPositiveButton("Ok") { dialog, which ->
                //Toast.makeText(context, "$selectedItem Selected", Toast.LENGTH_SHORT).show()
                if(selectedItem.equals(items[0])){
                    val deleteAlert = AlertDialog.Builder(context)
                    deleteAlert.setTitle("Are you sure you want to delete: " + singleEmployee.employee_name + "?")
                    deleteAlert.setPositiveButton("Yes") { dialog, which ->
                        //remote poziv
                        employeeViewModel.deleteEmployee(singleEmployee.id)
                        //local poziv
                        employeeViewModel.deleteEmployeeLocal(singleEmployee.id)
                    }
                    deleteAlert.setNegativeButton("Cancel") { dialog, which ->
                        dialog.dismiss()
                    }
                    deleteAlert.show()
                }else if(selectedItem.equals(items[1])){
                        val intent = Intent (activity, UpdateActivity::class.java)
                        intent.putExtra("id", singleEmployee.id)
                        startActivity(intent)
                }else if (selectedItem.equals(items[2])){
                    (activity as? DrawerNavigationActivity)?.let{
                        val intent = Intent (it, EmployeeDetailActivity::class.java)
                        intent.putExtra("id", singleEmployee.id)
                        it.startActivity(intent)
                    }
                }
            }
            .setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()
            }
            .show()

        //Toast.makeText(context,singleEmployee.employee_name,Toast.LENGTH_SHORT).show()
    }


    private fun initListeners() {
        binding.inputEt.doAfterTextChanged {
            val filter = it.toString()
            employeeViewModel.getEmployeesByName(filter)
        }
    }

    private fun initObservers() {
        employeeViewModel.employeesState.observe(viewLifecycleOwner, Observer {
            Timber.e(it.toString())
            renderState(it)
        })
        employeeViewModel.deleteUser.observe(viewLifecycleOwner, Observer {
            Timber.e(it.toString())
            renderDeleteState(it)
        })
        employeeViewModel.updateUser.observe(viewLifecycleOwner, Observer {
            Timber.e(it.toString())
            renderUpdateState(it)
        })
        employeeViewModel.postUser.observe(viewLifecycleOwner, Observer {
            Timber.e(it.toString())
            renderPostState(it)
        })
        // Pravimo subscription kad observablu koji je vezan za getAll iz baze
        // Na svaku promenu tabele, obserrvable ce emitovati na onNext sve elemente
        // koji zadovoljavaju query
        employeeViewModel.getAllEmployees()
        // Pokrecemo operaciju dovlacenja podataka sa servera, kada podaci stignu,
        // bice sacuvani u bazi, tada ce se triggerovati observable na koji smo se pretplatili
        // preko metode getAllMovies()

        //PROVERA DODATA: Kako nebi prilikom promene activity stalno fethcovao nove podatke s neta
        // da bi mogla da se proveri funkcionalnost dodavanja employee-a
        if(i==1){
            employeeViewModel.fetchAllEmployees()
            i++
        }
    }

    private fun renderState(state: EmployeesState) {
        when (state) {
            is EmployeesState.Success -> {
                showLoadingState(false)
                adapter.submitList(state.employees)
            }
            is EmployeesState.Error -> {
                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is EmployeesState.DataFetched -> {
                showLoadingState(false)
                Toast.makeText(context, "Fresh data fetched from the server", Toast.LENGTH_LONG).show()
            }
            is EmployeesState.Loading -> {
                showLoadingState(true)
            }
        }
    }

    private fun renderDeleteState(state: DeleteState) {
        when (state) {
            is DeleteState.Success -> {
                Toast.makeText(context, "Employee successfully deleted!", Toast.LENGTH_SHORT).show()
            }
            is DeleteState.Error -> {
                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun renderPostState(state: PostState) {
        when (state) {
            is PostState.Success -> {
                Toast.makeText(context, "Employee successfully posted!", Toast.LENGTH_SHORT).show()
            }
            is PostState.Error -> {
                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun renderUpdateState(state: UpdateState) {
        when (state) {
            is UpdateState.Success -> {
                Toast.makeText(context, employeeViewModel.updateUser.value.toString(), Toast.LENGTH_SHORT).show()
            }
            is UpdateState.Error -> {
                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showLoadingState(loading: Boolean) {
        binding.inputEt.isVisible = !loading
        binding.listRv.isVisible = !loading
        binding.loadingPb.isVisible = loading
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

