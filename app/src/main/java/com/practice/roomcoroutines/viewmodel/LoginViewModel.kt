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

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val db by lazy {
        UserDatabase(getApplication()).userDao()
    }

    val loginComplete = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    fun login(username: String, password: String) {
        coroutineScope.launch {
            val user = db.getUser(username)
            if (user.passwordHash == password.hashCode()) {
                LoginState.isLoggedIn = true
                LoginState.user = User(user.userName, user.passwordHash, user.info)
                withContext(Dispatchers.Main) {
                    loginComplete.value = true
                }
            } else {
                withContext(Dispatchers.Main) {
                    error.value = "Wrong Password."
                }
            }
        }
    }
}