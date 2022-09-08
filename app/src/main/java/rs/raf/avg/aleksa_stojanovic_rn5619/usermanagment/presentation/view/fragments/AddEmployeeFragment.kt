package rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.view.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.models.Employee
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.models.Recently
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.databinding.FragmentAddemployeeBinding
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.contract.EmployeeContract
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.contract.RecentlyContract
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.view.states.AddEmployeeState
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.view.states.PostState
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.viewmodel.EmployeeViewModel
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.viewmodel.RecentlyViewModel
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

class AddEmployeeFragment : Fragment(){


    // Koristimo by sharedViewModel jer sada view modele instanciramo kroz koin
    private val mainViewModel: EmployeeContract.ViewModel by sharedViewModel<EmployeeViewModel>()
    private val recentlyViewModel: RecentlyContract.ViewModel by sharedViewModel<RecentlyViewModel>()

    private var _binding: FragmentAddemployeeBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddemployeeBinding.inflate(inflater, container, false)
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

    @SuppressLint("SimpleDateFormat")
    private fun initUi() {
        binding.addBtn.setOnClickListener {
            val nameInput = binding.nameEt.text.toString()
            val salaryInput = binding.salaryEt.text.toString()
            val ageInput = binding.ageEt.text.toString()
            if (nameInput.isBlank() || salaryInput.isBlank() || ageInput.isBlank()) {
                Toast.makeText(context, "Input cannot be empty!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            binding.nameEt.text.clear()
            binding.salaryEt.text.clear()
            binding.ageEt.text.clear()
            val temp: UUID = UUID.randomUUID()

            val employeeToAdd = Employee(
                id = java.lang.Long.toHexString(temp.mostSignificantBits)
                        + java.lang.Long.toHexString(temp.leastSignificantBits),
                employee_name = nameInput,
                employee_salary = salaryInput,
                employee_age = ageInput,
            )
            //Locale
            mainViewModel.addEmployee(employeeToAdd)
            val x = "130"
            //Remote
            mainViewModel.postEmployee(x,nameInput, salaryInput, ageInput, "slika.jpg")
            val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
            val currentDate = sdf.format(Date())
            val recentlyEmp = Recently(recentlyViewModel.xId.toString(), nameInput,currentDate)
            recentlyViewModel.addRecently(recentlyEmp)
            //mainViewModel.addToRecently("0", nameInput, salaryInput, ageInput)
        }
    }

    private fun initObservers() {
        mainViewModel.addDone.observe(viewLifecycleOwner, Observer {
            renderState(it)
        })
        mainViewModel.postUser.observe(viewLifecycleOwner, Observer {
            renderPostState(it)
        })
    }

    private fun renderState(state: AddEmployeeState) {
        when(state) {
            is AddEmployeeState.Success -> Toast.makeText(context, "Employee added", Toast.LENGTH_SHORT)
                .show()
            is AddEmployeeState.Error -> Toast.makeText(context, "Error happened", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun renderPostState(state: PostState) {
        when(state) {
            is PostState.Success -> Toast.makeText(context, "Employee successfully added![REMOTE]", Toast.LENGTH_LONG)
                .show()
            is PostState.Error -> Toast.makeText(context, "Error happened", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}