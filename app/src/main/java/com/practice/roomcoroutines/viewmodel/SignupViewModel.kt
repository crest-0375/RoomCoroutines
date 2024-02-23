package com.practice.roomcoroutines.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.practice.roomcoroutines.db.LoginState
import com.practice.roomcoroutines.db.UserDatabase
import com.practice.roomcoroutines.models.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignupViewModel(application: Application) : AndroidViewModel(application) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val db by lazy {
        UserDatabase(getApplication()).userDao()
    }
    val signupComplete = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    fun signup(username: String, password: String, info: String) {
        coroutineScope.launch {
            val user = db.getUser(username)
            if (user != null) {
                withContext(Dispatchers.Main) {
                    error.value = "User already exists."
                }
            } else {
                val mUser = User(username, password.hashCode(), info)
                val userId: Long = db.insertUser(mUser)
                mUser.id = userId
                LoginState.login(mUser)
                withContext(Dispatchers.Main){
                    signupComplete.value = true
                }
            }
        }
    }

}