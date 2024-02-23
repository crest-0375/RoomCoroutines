package com.practice.roomcoroutines.fragments


import android.app.AlertDialog
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
import com.practice.roomcoroutines.databinding.FragmentMainBinding
import com.practice.roomcoroutines.db.LoginState
import com.practice.roomcoroutines.viewmodel.MainViewModel

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(LayoutInflater.from(activity))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.usernameTV.text = LoginState.user?.userName
        binding.signoutBtn.setOnClickListener { onSignOut() }
        binding.deleteUserBtn.setOnClickListener { onDelete() }

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.signOut.observe(viewLifecycleOwner, Observer {
            if (it) {
                Toast.makeText(activity, "Signed out", Toast.LENGTH_SHORT).show()
                goToSignUpScreen()
            }
        })
        viewModel.userDeleted.observe(viewLifecycleOwner, Observer {
            if (it) {
                Toast.makeText(activity, "User deleted", Toast.LENGTH_SHORT).show()
                goToSignUpScreen()
            }
        })
    }

    private fun onSignOut() {
        viewModel.onSignOut()
    }

    private fun goToSignUpScreen() {
        findNavController().navigateUp()
    }

    private fun onDelete() {
        activity.let {
            AlertDialog.Builder(it)
                .setTitle("Delete user")
                .setMessage("Are you sure you want to delete the user?")
                .setPositiveButton("Yes") { _, _ -> viewModel.onDeleteUser() }
                .setNegativeButton("Cancel", null)
                .create()
                .show()
        }
    }

}
