package com.innoji.todolistapp.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.innoji.todolistapp.R
import com.innoji.todolistapp.data.model.UserModel
import com.innoji.todolistapp.databinding.ActivityLoginBinding
import com.innoji.todolistapp.preference.UserPreference
import com.innoji.todolistapp.ui.home.HomeActivity
import com.innoji.todolistapp.ui.register.RegisterActivity
import com.innoji.todolistapp.util.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModelSetup()
        buttonListener()
    }

    private fun viewModelSetup(){
        viewModel = ViewModelProvider(this, ViewModelFactory(UserPreference.getInstance(dataStore), this))[LoginViewModel::class.java]

        viewModel.isLoading.observe(this){
            showLoading(it)
        }
    }

    private fun buttonListener(){
        binding.btnSingin.setOnClickListener {
            val username = binding.edtUsername.text.toString()
            val password = binding.edPasword.text.toString()

            viewModel.loginUser(username, password)
            viewModel.logindata.observe(this){
                if(it != null){
                    viewModel.login(
                        UserModel(
                            username = username,
                            isLogin = true,
                            it.data.token,
                        )
                    )
                }

                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
            viewModel.snackbarText.observe(this){
                it.getContentIfNotHandled()?.let { snackBarText ->
                    showToast(snackBarText)
                }
            }
        }


        binding.tvDaftar.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}