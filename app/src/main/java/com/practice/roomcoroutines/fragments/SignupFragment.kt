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
import com.practice.roomcoroutines.databinding.FragmentSignupBinding
import com.practice.roomcoroutines.viewmodel.SignupViewModel

class SignupFragment : Fragment() {
    private lateinit var binding: FragmentSignupBinding
    private lateinit var viewModel: SignupViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignupBinding.inflate(LayoutInflater.from(activity))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signupBtn.setOnClickListener { onSignup() }
        binding.gotoLoginBtn.setOnClickListener { onGotoLogin() }

        viewModel = ViewModelProvider(this)[SignupViewModel::class.java]
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.signupComplete.observe(viewLifecycleOwner, Observer { isComplete ->
            if (isComplete) {
                Toast.makeText(activity, "SignUp complete!", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.signupToMain)
                viewModel.signupComplete.value = false
            }
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { error ->
            if (error.isNotEmpty()) {
                Toast.makeText(activity, "Error : $error", Toast.LENGTH_SHORT).show()
                viewModel.error.value = ""
            }
        })
    }

    private fun onSignup() {
        val userName: String = binding.signupUsername.text.toString()
        val password: String = binding.signupPassword.text.toString()
        val userInfo: String = binding.otherInfo.text.toString()
        if (userName.isNotBlank() && password.isNotBlank() && userInfo.isNotBlank())
            viewModel.signup(userName, password, userInfo)
        else
            Toast.makeText(activity, "Please enter all the details", Toast.LENGTH_SHORT).show()
    }

    private fun onGotoLogin() {
        findNavController().navigate(R.id.signupToLogin)
    }
}
