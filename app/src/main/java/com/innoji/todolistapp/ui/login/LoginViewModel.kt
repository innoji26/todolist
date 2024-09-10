package com.innoji.todolistapp.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.innoji.todolistapp.data.network.ApiConfig
import com.innoji.todolistapp.data.model.UserModel
import com.innoji.todolistapp.data.request.LoginRequest
import com.innoji.todolistapp.data.response.LoginResponse
import com.innoji.todolistapp.preference.UserPreference
import com.innoji.todolistapp.util.Event
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel (private val pref: UserPreference): ViewModel() {

    private val _loginData = MutableLiveData<LoginResponse>()
    val logindata: LiveData<LoginResponse> = _loginData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText

    fun login(user: UserModel){
        viewModelScope.launch {
            pref.login(user)
        }
    }

    fun loginUser(username: String, password: String){
        _isLoading.value = true
        val service = ApiConfig.getApiService().login(LoginRequest(username, password))
        service.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                _isLoading.value = false
                if(response.code() == 404){
                    _snackbarText.value = Event(response.body()?.message.toString())

                }else if(response.code() == 403){
                    _snackbarText.value = Event(response.body()?.message.toString())

                }else if(response.code() == 401){
                    _snackbarText.value = Event(response.body()?.message.toString())

                }else{
                    if(response.isSuccessful){
                        val responseBody = response.body()
                        if(responseBody != null){
                            _loginData.value = response.body()
                            _snackbarText.value = Event(response.body()?.message.toString())
                        }else{
                            _snackbarText.value = Event(response.body()?.message.toString())
                        }
                    }
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLoading.value = false
                _snackbarText.value = Event(t.message.toString())
                Log.e("LoginViewModel", "onFailure: ${t.message.toString()}")
            }

        })
    }
}