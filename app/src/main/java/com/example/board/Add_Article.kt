package com.example.board

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_add__article.*

class Add_Article : AppCompatActivity() {

    val viewmodel by lazy {

        ViewModelProviders.of(this).get(ViewModel::class.java)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add__article)

        // 基本資料

        val pref = SharedPreference_login(this)

        val token = pref.get_apitoken()

        val user_id =  pref.get_user_id()!!.toInt()

        // 觀察文章是否有被成功送出 成功則返回所有文章頁面

        val add_article_code_Observer = Observer<Int> {

            if (it == 200){

                val intent = Intent(this,BoardActivity::class.java)

                startActivity(intent)

                finish()

            }


        }

    viewmodel.add_article_code.observe(this,add_article_code_Observer)


        // 送出文章

    send_article_btn.setOnClickListener {

     val my_content = my_article_content_edit.text.toString()

     viewmodel.add_my_article(token!!,user_id,my_content)



        }


    }
}
