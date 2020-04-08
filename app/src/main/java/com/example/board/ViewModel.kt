package com.example.board

import android.util.Log
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModel :ViewModel() {

    val manage = RetrofitManager()

    val all_Data_list = MutableLiveData<MutableList<Article_fundation_data>>()

    val add_article_code = MutableLiveData<Int>()

    fun get_all_Data (){

       val call = manage.request.get_All_data()

        call.enqueue(object :retrofit2.Callback<MutableList<Article_fundation_data>>{
            override fun onFailure(
                call: Call<MutableList<Article_fundation_data>>?,
                t: Throwable?
            ) {

            }

            override fun onResponse(
                call: Call<MutableList<Article_fundation_data>>?,
                response: Response<MutableList<Article_fundation_data>>?

            ) {

                val list = response!!.body()

                val code = response.code()

                if (code == 200) {

                    all_Data_list.value = list

            }
          }
        }
      )
    }


    fun update_likelist (post_id:Int){

        val my_request_body = Update_my_like_body(post_id )

        val call = manage.request.get_like_list(my_request_body)

        call.enqueue(object:retrofit2.Callback<MutableList<Update_my_like_result>>{
            override fun onFailure(call: Call<MutableList<Update_my_like_result>>?, t: Throwable?) {

            }

            override fun onResponse(
                call: Call<MutableList<Update_my_like_result>>?,
                response: Response<MutableList<Update_my_like_result>>?
            ) {

            }

        })

    }


    fun update_my_reply (comment_id:Int,adapter:Total_reply_Adapter){

        val my_request_body = Update_my_reply_list_body(comment_id)

        val call = manage.request.get_reply_list(my_request_body)

        call .enqueue(object : retrofit2.Callback<MutableList<Reply_fundation_data>>{
            override fun onFailure(call: Call<MutableList<Reply_fundation_data>>?, t: Throwable?) {
                  }

            override fun onResponse(
                call: Call<MutableList<Reply_fundation_data>>?,
                response: Response<MutableList<Reply_fundation_data>>?

            ) {
                val code = response!!.code()

                if (code == 200){

                    // 新回覆

                  val my_new_reply_list = response.body()

                     my_new_reply_list.reverse()

                    //更新回覆列表

                    adapter.update_reply_list(my_new_reply_list)


                }
            }
        })

    }


    fun update_my_message (post_id: Int,adapter: Total_message_Adapter,textView: TextView){

        val my_request_body = Update_my_message_list_body(post_id)

        val call = manage.request.get_message_list(my_request_body)

        call.enqueue(object : retrofit2.Callback<MutableList<Message_fundation_data>>{
            override fun onFailure(
                call: Call<MutableList<Message_fundation_data>>?,
                t: Throwable?
            ) {
                    }

            override fun onResponse(
                call: Call<MutableList<Message_fundation_data>>?,
                response: Response<MutableList<Message_fundation_data>>?
            ) {

                   val code = response!!.code()

                   if (code == 200){

                       // 新留言

                    val   my_new_message_list  = response.body()

                          my_new_message_list.reverse()

                       // 留言總數文字  = 新文字數量

                          textView.text = my_new_message_list.size.toString()

                       // 更新留言列表

                           adapter.update_message_list(my_new_message_list)


                   }

                 }

        })
    }

    fun update_my_article(textView: TextView,post_id: Int){

        val call = manage.request.get_article_list()

        call.enqueue(object : retrofit2.Callback<MutableList<Article_fundation_data>>{
            override fun onFailure(
                call: Call<MutableList<Article_fundation_data>>?,
                t: Throwable?
            ) {
                 }

            override fun onResponse(
                call: Call<MutableList<Article_fundation_data>>?,
                response: Response<MutableList<Article_fundation_data>>?

            ) {

                val code = response!!.code()

                if (code == 200){

                            val my_Article_list = response.body()

                            val  this_article =   my_Article_list.filter { it.id ==post_id }

                            val  this_article_good_number = this_article[0].likes_count

                            textView.text = this_article_good_number.toString()



                }
            
             }

        })

    }

    fun add_my_article (token:String,user_id:Int,content:String){
        
        manage.add_token_client(token)
        
        val my_request_body = Add_article_body(user_id,content)
        
        val call = manage.request.add_article(my_request_body)

        call.enqueue(object :retrofit2.Callback<Add_article_result>{
            override fun onFailure(call: Call<Add_article_result>?, t: Throwable?) {

            }

            override fun onResponse(
                call: Call<Add_article_result>?,
                response: Response<Add_article_result>?
            ) {

                add_article_code.value = response!!.code()

            }


        })
        
        
    }


    fun add_my_message(token:String,user_id:Int,post_id:Int,content:String,adapter: Total_message_Adapter,textView: TextView){

        manage.add_token_client(token)

        val my_request_body = Add_message_body(user_id,post_id,content)

        val call = manage.request.add_message(my_request_body)

        call.enqueue(object :retrofit2.Callback<Add_message_result>{
            override fun onFailure(call: Call<Add_message_result>?, t: Throwable?) {

            }

            override fun onResponse(
                call: Call<Add_message_result>?,
                response: Response<Add_message_result>?
            ) {

                val code = response!!.code()

                if (code == 200){

                    // 取得新留言

                    update_my_message (post_id,adapter,textView)

                }

            }


        })
    }


    fun add_my_reply(token:String,user_id:Int, comment_id:Int,content:String,adapter: Total_reply_Adapter){

        manage.add_token_client(token)

        val my_request_body = Add_reply_body(user_id, comment_id,content)

        val call = manage.request.add_reply(my_request_body)

        call.enqueue(object :retrofit2.Callback<Add_reply_result>{
            override fun onFailure(call: Call<Add_reply_result>?, t: Throwable?) {

            }

            override fun onResponse(
                call: Call<Add_reply_result>?,
                response: Response<Add_reply_result>?
            ) {
                val code = response!!.code()
                if (code == 200){

                    // 取得新回覆

                    update_my_reply(comment_id,adapter)

                }


            }


        })
    }


    fun add_like(token:String,user_id:Int,post_id:Int,textView: TextView){

        manage.add_token_client(token)

        val my_request_body = Post_like_body(post_id)

        val call = manage.request.add_like(my_request_body)

        call.enqueue(object :Callback<Post_like_result>{
            override fun onFailure(call: Call<Post_like_result>?, t: Throwable?) {

            }

            override fun onResponse(
                call: Call<Post_like_result>?,
                response: Response<Post_like_result>?

            ) {

                val code = response!!.code()

                if (code == 200) {

                    get_all_Data ()

                }

            }

        })

    }

    fun cancell_like(token:String,like_id:Int,textView: TextView,post_id: Int){

        manage.add_token_client(token)

        val my_request_body = Cancell_like_body(like_id)

        val call = manage.request.cancell_like(my_request_body)

        call.enqueue(object :retrofit2.Callback<Cancell_like_result>{
            override fun onFailure(call: Call<Cancell_like_result>?, t: Throwable?) {

                Log.e("this_code","fail")

            }

            override fun onResponse(
                call: Call<Cancell_like_result>?,
                response: Response<Cancell_like_result>?
            ) {

                val code = response!!.code()

                Log.e("this_code",code.toString())

                if (code ==200) {

                    get_all_Data ()

                }
            }

        })

    }

}