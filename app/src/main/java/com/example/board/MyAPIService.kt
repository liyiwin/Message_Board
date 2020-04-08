package com.example.board

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MyAPIService {

    @GET("api/board")

    fun get_All_data (): retrofit2.Call<MutableList<Article_fundation_data>>

    @POST("api/allLike")

    fun get_like_list (@Body body:Update_my_like_body): retrofit2.Call<MutableList<Update_my_like_result>>

    @POST("api/allReply")

    fun get_reply_list (@Body body:Update_my_reply_list_body): retrofit2.Call<MutableList<Reply_fundation_data>>

    @POST("api/allComment")

    fun get_message_list (@Body body:Update_my_message_list_body): retrofit2.Call<MutableList<Message_fundation_data>>

    @GET("api/allPost")

    fun get_article_list (): retrofit2.Call<MutableList<Article_fundation_data>>

    @POST("api/storePost")

    fun add_article (@Body body:Add_article_body ): retrofit2.Call<Add_article_result>

    @POST("api/storeComment")

    fun add_message (@Body body:Add_message_body): retrofit2.Call<Add_message_result>

    @POST("api/storeReply")

    fun add_reply (@Body body:Add_reply_body): retrofit2.Call<Add_reply_result>

    @POST("api/storeLike")

    fun add_like (@Body body:Post_like_body): retrofit2.Call<Post_like_result>

    @POST("api/dislike")

    fun cancell_like(@Body body:Cancell_like_body): retrofit2.Call<Cancell_like_result>


}