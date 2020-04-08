package com.example.board

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_article.view.*

class Total_article_Adapter(var context: Context,
                            var outer_list:MutableList<Article_fundation_data>,
                            var user_id:Int):RecyclerView.Adapter<Total_article_Adapter.ViewHolder>() {



   var inner_list = mutableListOf<Article_fundation_data>()




    init {

        inner_list = outer_list


    }


    var this_my_add_reply_article:Add_reply_article? = null


    interface Add_reply_article{


        fun add_reply_article(comment_id:Int,content:String,adapter:Total_reply_Adapter){



        }

    }

    fun connect_add_reply_article (my_add_reply_article:Add_reply_article){


        this_my_add_reply_article = my_add_reply_article

    }




     var this_add_message:Add_message? =null


     interface Add_message{


         fun add_message(content:String,post_id:Int,adapter: Total_message_Adapter,textView: TextView){



         }

     }


    fun connect_add_message(my_add_message:Add_message){

        this_add_message = my_add_message

    }



    var this_post_good: Post_good ?= null



    interface Post_good {

        fun post_good (post_id:Int,textView: TextView){

        }

    }

    fun post_good_connect(my_post_good:Post_good){

        this_post_good = my_post_good


    }

    //
    var this_cancell_good: Cancell_good?= null



    interface Cancell_good {

        fun cancell_good (like_id:Int,textView: TextView,post_id: Int){

        }

    }

    fun cancell_good_connect(my_cancell_good:Cancell_good){

        this_cancell_good = my_cancell_good

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.item_article,parent,false)

        return ViewHolder(view)

    }

    override fun getItemCount(): Int = inner_list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var b = 0

        var is_like = false




        val this_article_message_list = if (inner_list[position].comments != null) inner_list[position].comments else mutableListOf<Message_fundation_data>()

        // 留言 Adapter

        val my_message_adapter = Total_message_Adapter(context,this_article_message_list)

        // 按讚名單與所有留言

        val this_article_like_list = if (inner_list[position].likes != null) inner_list[position].likes else mutableListOf<like_bean>()

        // 我有無在按讚列表中

        val my_like_data = this_article_like_list.filter { it.user_id == user_id }

        var like_id = 0

         // 1 代表 我有按讚  0 則沒有

         if (my_like_data.size == 0 ){

             is_like = false

             holder.my_like_button.setImageResource(R.drawable.thumb_up)

         }

        else{

             is_like = true

             like_id = my_like_data[0].id

             holder.my_like_button.setImageResource(R.drawable.thumb_up_fill)

         }

        // 這篇文章id

        val post_id = inner_list[position].id

        // like_btn

        holder.my_like_button.setOnClickListener{

            if (is_like == false){

                is_like = true

                holder.my_like_button.setImageResource(R.drawable.thumb_up_fill)

                this_post_good?.post_good(post_id,holder.my_total_good_number)

            }

            else{

                holder.my_like_button.setImageResource(R.drawable.thumb_up)

                is_like = false

                this_cancell_good?.cancell_good(like_id,holder.my_total_good_number,post_id)

            }

        }

        my_message_adapter.connect_add_reply_message(object:Total_message_Adapter.Add_reply_message{

            override fun add_reply_message(
                comment_id: Int,
                content: String,
                adapter: Total_reply_Adapter
            ) {


                this_my_add_reply_article!!.add_reply_article(comment_id,content, adapter)

            }
        })

        //基本文字

        holder.my_article_user_name.text = inner_list[position].user.name

        holder.my_article_date.text = inner_list[position].created_at

        holder.my_article_content.text =  inner_list[position].content

        holder.my_total_good_number.text ="${ inner_list[position].likes_count.toString()}" + "個讚"

        holder.my_total_messege_number.text = "${inner_list[position].comments_count.toString()}" + "個留言"

        // 輸入留言

        holder.my_message_input_button.setOnClickListener {

            val content = holder.my_message_input.text.toString()

            this_add_message!!.add_message(content,post_id,my_message_adapter,holder.my_total_messege_number)

        }

       // message_recyclerview

        holder.total_message_recyclerview.apply{

           layoutManager = LinearLayoutManager(context)

            adapter = my_message_adapter

        }

    }

    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view){

        val my_article_user_name = view.my_article_user_name

        val my_article_date =  view. my_article_date

        val my_article_content = view.my_article_content

        val my_total_good_number = view.my_total_good_number

        val my_total_messege_number = view.my_total_messege_number

        val my_like_button = view.my_like_button

        val my_message_button = view.my_message_button

        val my_message_input_button = view.my_message_input_button

        val my_message_input = view.my_message_input

        val total_message_recyclerview = view.total_message_recyclerview

    }

    fun update_article_list (article_list: MutableList<Article_fundation_data>){

        inner_list = article_list

        notifyDataSetChanged()

    }

}