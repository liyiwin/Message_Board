package com.example.board

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_board.*

class BoardActivity : AppCompatActivity() {

    var handler =Handler()

    var runnable = Runnable {  }

    var my_article_list = mutableListOf<Article_fundation_data>()

    var my_article_adapter = Total_article_Adapter(this,my_article_list,0)

    val viewmodel by lazy {

        ViewModelProviders.of(this).get(ViewModel::class.java)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)

        // 30 s 刷新一次

//       runnable = object :Runnable{
//            override fun run() {
//
//                handler.postDelayed(this,30000)
//
//                viewmodel.get_all_Data()
//
//
//            }
//
//        }
//
//        handler.postDelayed(runnable,30000)

        // 基本資料

        val pref = SharedPreference_login(this)

        val token = pref.get_apitoken()

        val user_id =  pref.get_user_id()!!.toInt()

        // 初始化recylerview


       my_article_adapter =  Total_article_Adapter(this,my_article_list,user_id)

        total_article_recyclerview.apply {

            setNestedScrollingEnabled(false)

            val  mLayoutManager = LinearLayoutManager(this@BoardActivity)

            mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL)

            layoutManager = mLayoutManager

            adapter = my_article_adapter

        }


         //本頁文章輸入框 直接跳到新增文章頁面

        essay_editText.setOnClickListener {

            val intent = Intent(this,Add_Article::class.java)

            startActivity(intent)

            finish()


        }

       // 右上角user 圖片按上去 會登出

        my_user_image.setOnClickListener {


            val mBuilder = AlertDialog.Builder(this)
                                     .setTitle("確定要登出？")
                                     .setNegativeButton("取消"){dialog, which -> }
                                     .setPositiveButton("確定") {dialog, which ->
                                         pref.delete()
                                         val intent = Intent(this,MainActivity::class.java)
                                         startActivity(intent)
                                     }
                                     .show()

        }

        // oncreate 載入所有文章

        viewmodel.get_all_Data()

        val article_list_Observer = Observer<MutableList<Article_fundation_data>>{

            // 資料是相反的

            it.reverse()

            my_article_list  = it

            my_article_adapter.update_article_list(my_article_list)

        }

        viewmodel.all_Data_list.observe(this,article_list_Observer)


        // interface 送出回覆
      my_article_adapter.connect_add_reply_article(

         object:Total_article_Adapter.Add_reply_article{

             override fun add_reply_article(
                 comment_id: Int,
                 content: String,
                 adapter: Total_reply_Adapter
             ) {

                 viewmodel.add_my_reply(token!!,user_id,comment_id, content,adapter)


             }
         })


        // interface 送出留言


     my_article_adapter.connect_add_message(object :Total_article_Adapter.Add_message{

         override fun add_message(content: String, post_id: Int,adapter: Total_message_Adapter,textView: TextView) {

             viewmodel.add_my_message(token!!,user_id,post_id,content,adapter,textView)


         }
     })
        // interface 按讚

     my_article_adapter.post_good_connect(object :Total_article_Adapter.Post_good{

         override fun post_good(post_id: Int,textView: TextView) {

             viewmodel.add_like(token!!,user_id,post_id,textView)
         }
     })

        // interface 取消讚

     my_article_adapter.cancell_good_connect(object :Total_article_Adapter.Cancell_good{

         override fun cancell_good(like_id: Int,textView: TextView,post_id: Int) {

             viewmodel.cancell_like(token!!,like_id,textView,post_id)

         }
     })


  }

    override fun onDestroy() {
        super.onDestroy()

        handler.removeCallbacks(runnable)
    }

}

