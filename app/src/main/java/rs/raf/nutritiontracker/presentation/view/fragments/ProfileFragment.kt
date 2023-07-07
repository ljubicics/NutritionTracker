package rs.raf.nutritiontracker.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.nutritiontracker.R
import rs.raf.nutritiontracker.data.models.User
import rs.raf.nutritiontracker.databinding.FragmentCategoriesBinding
import rs.raf.nutritiontracker.databinding.FragmentProfileBinding
import rs.raf.nutritiontracker.presentation.contract.CategoryContract
import rs.raf.nutritiontracker.presentation.contract.UserContract
import rs.raf.nutritiontracker.presentation.view.states.CategoriesState
import rs.raf.nutritiontracker.presentation.view.states.UserState
import rs.raf.nutritiontracker.presentation.viewmodel.CategoryViewModel
import rs.raf.nutritiontracker.presentation.viewmodel.UserViewModel
import timber.log.Timber

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private var _binding: FragmentProfileBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val userViewModel: UserContract.ViewModel by sharedViewModel<UserViewModel>()
    private lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        val userState = userViewModel.userState.value
        if (userState != null) {
            getUser(userState)
        }
        initUi()
    }

    private fun initUi() {
//        initRecycler()
        initListeners()
    }

    private fun initListeners() {
        binding.savedMealsButton.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.add(R.id.mainFragmentFcv, UserSavedMealsFragment(user.username)).addToBackStack(null)
            transaction.commit()
        }
    }

    private fun initObservers() {

    }

    private fun getUser(state: UserState) {
        when (state) {
            is UserState.Success -> {
//                showLoadingState(false)
                user = state.users[0]
            }
            is UserState.Error -> {
//                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is UserState.DataFetched -> {
//                showLoadingState(false)
                Toast.makeText(context, "Fresh data fetched from the server", Toast.LENGTH_LONG).show()
            }
            is UserState.Loading -> {
//                showLoadingState(true)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}