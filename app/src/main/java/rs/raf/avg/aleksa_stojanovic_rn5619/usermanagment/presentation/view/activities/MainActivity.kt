package rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.R
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.dataResources.remote.LoginUserService
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.data.models.LoginUser

class MainActivity : AppCompatActivity() {

    private lateinit var usernameInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var rememberMeButton: CheckBox
    private lateinit var loginButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {


        val splashScreen: SplashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition() {
            val preferences = getSharedPreferences("checkbox", MODE_PRIVATE)
            val valid = getSharedPreferences("valid", MODE_PRIVATE)
            val checkbox = preferences.getString("remember", "")
            val log = valid.getString("login", "")
            if (checkbox.equals("true") && log.equals("y")) {
                val intent = Intent(this, DrawerNavigationActivity::class.java)
                startActivity(intent)
                finish()
                false
            } else if (checkbox.equals("false") && log.equals("n")) {
                Toast.makeText(this, "Please login.", Toast.LENGTH_SHORT).show()
            }
            false
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initListeners()

    }

    private fun initView(){
        usernameInput = findViewById(R.id.editTextTextPersonName)
        passwordInput = findViewById(R.id.editTextTextPassword)
        rememberMeButton = findViewById(R.id.checkBox)
        loginButton = findViewById(R.id.button)
    }

    private fun initListeners(){
        loginButton.setOnClickListener {
            userLogin()
        }
        val valid =  getSharedPreferences("valid", MODE_PRIVATE)

        val log = valid.getString("login","")
        rememberMeButton.setOnCheckedChangeListener { compoundButton, _ ->
            if(compoundButton.isChecked && log.equals("y")){
                val preferences = getSharedPreferences("checkbox", MODE_PRIVATE)
                val editor = preferences.edit()
                editor.putString("remember","true")
                editor.apply()
                val valid = getSharedPreferences("valid", MODE_PRIVATE)
                val editor2 = valid.edit()
                editor2.putString("login","y")
                editor2.apply()
            }else if(!compoundButton.isChecked){
                val preferences = getSharedPreferences("checkbox", MODE_PRIVATE)
                val editor = preferences.edit()
                editor.putString("remember","false")
                editor.apply()
                val valid = getSharedPreferences("valid", MODE_PRIVATE)
                val editor2 = valid.edit()
                editor2.putString("login","n")
                editor2.apply()
            }
        }
    }

    private fun userLogin(){
        val retrofit = Retrofit.Builder().baseUrl("https://dummyjson.com").addConverterFactory(GsonConverterFactory.create()).build()
        val loginUserService = retrofit.create(LoginUserService::class.java)
        val loginUser = LoginUser(usernameInput.text.toString(), passwordInput.text.toString())

        loginUserService.LogUser(loginUser).enqueue(
            object : Callback<LoginUser>{
                override fun onFailure(call: Call<LoginUser>, t: Throwable) {

                    Toast.makeText(this@MainActivity,"Login Failed",Toast.LENGTH_SHORT).show()
                }
                override fun onResponse( call: Call<LoginUser>, response: Response<LoginUser>) {
                    val ans = response.body()
                    println("RESPONSE:" + ans)
                    if (ans != null) {Toast.makeText(this@MainActivity,"Welcome!",Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@MainActivity, DrawerNavigationActivity::class.java)
                        val valid = getSharedPreferences("valid", MODE_PRIVATE)
                        val editor2 = valid.edit()
                        editor2.putString("login","y")
                        editor2.apply()
                            startActivity(intent)
                            finish()
                        }else{
                        val valid = getSharedPreferences("valid", MODE_PRIVATE)
                        val editor2 = valid.edit()
                        editor2.putString("login","n")
                        editor2.apply()
                        Toast.makeText(this@MainActivity,"Wrong Credentials!",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        )
    }
}