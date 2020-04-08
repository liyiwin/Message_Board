package com.example.board

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private  val viewModel_Login by lazy {

        ViewModelProviders.of(this).get(ViewModel_Login::class.java)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pref = SharedPreference_login(this)

          // 註冊

        Registered_Intent_Button.setOnClickListener {

            val intent = Intent(this,Register_Activity::class.java)

            startActivity(intent)

            finish()

        }

        // 登入

        Login_Button.setOnClickListener {

            val my_name = login_name.text.toString()

            val my_password  = login_password.text.toString()

            login_my_account(this,my_name,my_password,pref,viewModel_Login)

        }

        val code_Observer = Observer<String>{

            if (it ==   "200"){

                Toast.makeText(this,"登入成功", Toast.LENGTH_SHORT).show()

                val intent = Intent(this,BoardActivity::class.java)

                startActivity(intent)

                finish()

            }

        }

        viewModel_Login.get_login_code().observe(this,code_Observer)

    }
}
