package hu.bme.aut.android.reportapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import hu.bme.aut.android.reportapp.data.HelloResponse
import hu.bme.aut.android.reportapp.databinding.ActivityMainBinding
import hu.bme.aut.android.reportapp.network.NetworkManager
import hu.bme.aut.android.reportapp.network.LoginService
import hu.bme.aut.android.reportapp.data.User
import hu.bme.aut.android.reportapp.network.BasicAuthClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            loginCall()
        }

        binding.btnHello.setOnClickListener {
            sayHello()
        }
    }

    private fun sayHello() {
        NetworkManager.getHello().enqueue(object : Callback<HelloResponse>{
            override fun onResponse(
                call: Call<HelloResponse>,
                response: Response<HelloResponse>
            ) {
                Log.d("login call response", response.code().toString())
                if (response.isSuccessful) {
                    Toast.makeText(this@MainActivity, response.body().toString(), Toast.LENGTH_LONG).show()
                } else {
                    Log.d("response in login call", response.body().toString())
                    Toast.makeText(this@MainActivity, resources.getText(R.string.login_not_success), Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<HelloResponse>, t: Throwable) {
                t.printStackTrace()
                Log.d("Error", t.message.toString())
            }

        })
    }


    private fun loginCall(){
        val call = BasicAuthClient<LoginService>(binding.etEmail.text.toString(), binding.etPassword.text.toString()).create(LoginService::class.java).basicLogin()

        call!!.enqueue(object : Callback<User?> {
            override fun onResponse(call: Call<User?>?, response: Response<User?>) {
                Log.d("login call response", response.code().toString())
                if (response.isSuccessful) {
                    val reportIntent = Intent(this@MainActivity, ReportActivity::class.java)
                    reportIntent.putExtra("username", binding.etEmail.text.toString())
                    reportIntent.putExtra("password", binding.etPassword.text.toString())
                    startActivity(reportIntent)
                } else {
                    Log.d("response in login call", response.body().toString())
                    Toast.makeText(this@MainActivity, resources.getText(R.string.login_not_success), Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<User?>?, t: Throwable) {
                t.printStackTrace()
                Log.d("Error", t.message.toString())
            }
        })
    }
}