package com.practice.roomcoroutines.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.practice.roomcoroutines.R
import com.practice.roomcoroutines.databinding.FragmentLoginBinding
import com.practice.roomcoroutines.viewmodel.LoginViewModel

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(LayoutInflater.from(activity))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginBtn.setOnClickListener { onLogin() }
        binding.gotoSignupBtn.setOnClickListener { onGotoSignup() }

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        observeViewModel()


    }

    private fun observeViewModel() {
        viewModel.loginComplete.observe(viewLifecycleOwner, Observer { isComplete ->
            if (isComplete) {
                Toast.makeText(activity, "Login completed", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.loginToMain)
                viewModel.loginComplete.value = false
            }
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { error ->
            if (error.isNotEmpty()) {
                Toast.makeText(activity, "Error : $error", Toast.LENGTH_SHORT).show()
                viewModel.error.value = ""
            }
        })
    }

    private fun onLogin() {
        val userName: String = binding.loginUsername.text.toString()
        val password: String = binding.loginPassword.text.toString()
        if (userName.isBlank() && password.isBlank())
            Toast.makeText(activity, "Please enter all the details", Toast.LENGTH_SHORT).show()
        else {
            viewModel.login(userName, password)
        }

    }

    private fun onGotoSignup() {
        findNavController().navigateUp()
    }
}
