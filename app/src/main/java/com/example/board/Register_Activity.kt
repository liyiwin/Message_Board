package com.example.board

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_register_.*

class Register_Activity : AppCompatActivity() {

        private val  viewModel_register by lazy {

            ViewModelProviders.of(this).get(ViewModel_Register::class.java)

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_)



        Registered_Button.setOnClickListener {

            val my_registered_name = registered_name.text.toString()

            val my_registered_password = registered_password.text.toString()

            register_my_account(this, my_registered_name, my_registered_password,viewModel_register)

        }

        val code_Observer =  Observer<String>{

            if (it ==  "200"){

                Toast.makeText(this,"註冊成功",Toast.LENGTH_SHORT).show()

                val intent = Intent(this,MainActivity::class.java)

                startActivity(intent)

            }

        }

        viewModel_register.get_register_code().observe(this,code_Observer)

    }
}
