package com.example.board

import android.app.Activity
import android.util.Log
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*






data class like_bean
(
  val id: Int,
  val user_id: Int,
  val post_id: Int

)

data class last
(

   val hour :Int,
   val min :Int,
   val second:Int

)

data class user
(
  val  id :Int,
  val  name:String

)



data class Reply_fundation_data
(

    val id : Int ,
    val content : String,
    val user_id : Int,
    val comment_id: Int,
    val created_at : String,
    val last :last,
    val user:user


)

data class  Message_fundation_data
(
//文章回覆
    val id : Int ,
    val content : String,
    val user_id : Int,
    val post_id:Int,
    val created_at : String,
    val last :last,
    val user:user,
    val replies:MutableList<Reply_fundation_data>


)

data class Article_fundation_data (

    val id : Int, //文章id
    val content : String, //文章內容
    val user_id : Int, //發文者id
    val created_at : String, //發文時間
    val likes_count : Int, //文章讚數
    var comments_count : Int, //文章評論數
    val last :last,//發表文章距離目前時間
    val user:user, //發文者詳細資料
    var comments:MutableList<Message_fundation_data>,
    val likes : MutableList<like_bean>

)



public  interface Load_all_data_Interface{

    @GET("api/board")

    fun get_call (): retrofit2.Call<MutableList<Article_fundation_data>>

}



fun load_all_data(viewModel:ViewModel_Board,activity: Activity){

        val retrofit = Retrofit.Builder()
            .baseUrl("https://fa2271b1.ngrok.io")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val request =  retrofit.create(Load_all_data_Interface::class.java)

        val call  =  request.get_call()

        call.enqueue(object : retrofit2.Callback<MutableList<Article_fundation_data>>{
            override fun onFailure(
                call: retrofit2.Call<MutableList<Article_fundation_data>>?,
                t: Throwable?
            ) {

            }

            override fun onResponse(
                call: retrofit2.Call<MutableList<Article_fundation_data>>?,
                response: retrofit2.Response<MutableList<Article_fundation_data>>?
            ) {

                val list = response!!.body()

                val code = response.code()

                if (code == 200){

                    activity.runOnUiThread {

                    viewModel.set_essay_list(list)


                }}


            }


        })

}


// 僅顯示按讚回覆

data class  Update_my_like_body

(

val post_id: Int

)




data class Update_my_like_result

(
 val id:Int,

 val user_id: Int,

 val post_id: Int,

 val user:user

)



// 僅顯示回覆

data class  Update_my_reply_list_body

(

val comment_id:Int

)

// 僅顯示留言

data class  Update_my_message_list_body

 (


val post_id:Int


)



// 僅顯示回覆

data class Add_article_result
(

    val user_id: String,
    val content:String,
    val  created_at :String,
    val   id :Int


)

// 新增文章

data class Add_article_body

 (
  val user_id: Int,
  val content:String

)


public  interface add_article_list_Interface{

    //("Authorization:Bearer $token")

    @POST("api/storePost")

    fun get_call (@Body body:Add_article_body, @Header("0")token:String ): retrofit2.Call<Add_article_result>

}






//新增留言



data class  Add_message_result

(


    val user_id: String,
    val post_id : String,
    val content : String,
    val created_at: String,
    val id:  Int


)

data class Add_message_body


(

val uer_id: Int,
val post_id: Int,
val content: String

)





//新增回覆




data class Add_reply_body
(

   val uer_id : Int,
   val comment_id : Int,
   val content : String


)



data class Add_reply_result
(

   val user_id : String,
   val comment_id : String,
   val content : String,
   val created_at : String,
   val id : Int

)


// 按讚

data class Post_like_body

(

 val  post_id :Int


)



data class Post_like_result
(
  val user_id : String,
  val post_id : String,
  val created_at :String,
  val id : Int
)




// 取消讚

data class  Cancell_like_body
(

    val  like_id:Int

)



data class Cancell_like_result
(

    val  status : Boolean

)





