package com.example.board

import android.app.Activity
import okhttp3.*
import org.json.JSONObject
import java.io.IOException


private var OkHttpClient = okhttp3.OkHttpClient().newBuilder().build()

fun login_my_account(activity: Activity,name:String,password:String,pref:SharedPreference_login,viewModel:ViewModel_Login){

     val url = "https://2c0f8055.ngrok.io/api/login"
    val body = FormBody.Builder().add("name",name).add("password",password).build()
     val request = Request.Builder().url(url).post(body) .build()
    val call = OkHttpClient .newCall(request)
     call.enqueue(object: Callback {
     override fun onFailure(call: Call, e: IOException) {

                       }

     override fun onResponse(call: Call, response: Response) {

       val code = response.code()

         if (code == 200){

             val myjson = response.body()?.string()!!.trim()

             val jasonObject = JSONObject(myjson)

             val my_id = jasonObject.getInt("id").toString()

             val my_name  = jasonObject.getString("name")

             val my_token = jasonObject.getString("api_token")

             activity.runOnUiThread {

                 pref.save_user_id(my_id)

                 pref.save_name(my_name)

                 pref.save_apitoken(my_token)

                 viewModel.set_login_code("200")
             }
         }

     }
   })

}