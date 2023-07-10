package rs.raf.nutritiontracker.presentation.view.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.nutritiontracker.R
import rs.raf.nutritiontracker.data.models.User
import rs.raf.nutritiontracker.databinding.FragmentLoginBinding
import rs.raf.nutritiontracker.presentation.contract.UserContract
import rs.raf.nutritiontracker.presentation.view.states.UserState
import rs.raf.nutritiontracker.presentation.viewmodel.UserViewModel
import timber.log.Timber

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val userViewModel: UserContract.ViewModel by sharedViewModel<UserViewModel>()
    private val sharedPreferences: SharedPreferences by inject()
    private val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    private var _binding: FragmentLoginBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
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

        binding.loginBtn.setOnClickListener {
            val username = binding.usernameET.text.toString()
            val password = binding.passwordET.text.toString()
            println("ZOVE ZA IME " + username)
            userViewModel.getUserByUsername(username)
            userViewModel.userState.observe(viewLifecycleOwner, Observer {
                when(it) {
                    is UserState.Success -> {
                        if(it.users.size == 0) {
                            Toast.makeText(context, "Ne postoji user sa unetim username-om", Toast.LENGTH_SHORT).show()
                            return@Observer
                        } else {
                            val user = it.users.get(0)
                            if(user.password.equals(password) && user.password.length > 4) {
                                sharedPreferences.edit().putString("USER", moshi.adapter(User::class.java).toJson(user)).apply()

                            } else {
                                Toast.makeText(context, "Lozinka nije dobra", Toast.LENGTH_SHORT).show()
                                return@Observer
                            }
                        }
                        val transaction = parentFragmentManager.beginTransaction()
                        transaction.replace(R.id.container, MainFragment())
                        transaction.commit()
                    }
                    is UserState.Error -> {
                        println("LOSSS")
                        return@Observer
                    }
                }

            })
        }
    }

    private fun initObservers() {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}