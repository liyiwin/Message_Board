package com.example.board

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_board.view.*
import kotlinx.android.synthetic.main.item_message.view.*
import kotlinx.android.synthetic.main.item_reply_input.view.*

class Total_message_Adapter(var context: Context,
                            var outer_list:MutableList<Message_fundation_data>
                             ):RecyclerView.Adapter<Total_message_Adapter.ViewHolder>() {



    var inner_list= mutableListOf<Message_fundation_data>()



    init {
        inner_list = outer_list

    }


    var this_my_add_reply_message:Add_reply_message? = null


    interface Add_reply_message{


        fun add_reply_message(comment_id:Int,content:String,adapter:Total_reply_Adapter){



        }

    }

    fun connect_add_reply_message (my_add_reply_message:Add_reply_message){


        this_my_add_reply_message = my_add_reply_message

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Total_message_Adapter.ViewHolder {

                val view = LayoutInflater.from(context).inflate(R.layout.item_message,parent,false)

                return ViewHolder(view)


        }


    override fun getItemCount(): Int  = inner_list.size

    override fun onBindViewHolder(holder:Total_message_Adapter.ViewHolder, position: Int) {

        //  這則留言的所有回覆

        val this_message_reply_list = if (inner_list[position].replies != null) inner_list[position].replies else mutableListOf<Reply_fundation_data>()


        // 回覆Adapter

        val my_reply_adapter =  Total_reply_Adapter(context,this_message_reply_list)

        //這篇文章id

        val comment_id = inner_list[position].id

        //回覆recyclerview

        holder.total_reply_recyclerview.apply {

            layoutManager = LinearLayoutManager(context)

            adapter = my_reply_adapter

        }

        // 基本文字

        holder.my_message_content.text = inner_list[position].content

        holder.my_message_date.text = inner_list[position].created_at

        holder.my_message_user_name.text = inner_list[position].user.name


        // 送出回覆 btn

        holder.my_reply_button.setOnClickListener {

                   // initial Alerdialog

                val mDialogView = LayoutInflater.from(context).inflate(R.layout.item_reply_input,null)

                 val mBuilder = AlertDialog.Builder(context)
                .setView(mDialogView)
                .create()
                   mBuilder.show()

                // Alerdialog 回覆送出

                mDialogView.inpute_send_btn.setOnClickListener {

                    //我的回覆

                    val content = mDialogView.reply_inpute_edittext.text.toString()

                    this_my_add_reply_message!!.add_reply_message(comment_id,content,my_reply_adapter)

                  mBuilder.dismiss()

                }

               // Alerdialog 關掉

                mDialogView.inpute_return_btn.setOnClickListener {

                    mBuilder.dismiss()
                }



        }

    }

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){

        val my_message_user_name =  view.my_message_name

        val my_message_content =  view.my_message_content

        val  my_reply_button = view. my_reply_button

        val my_message_date = view. my_message_date

        val total_reply_recyclerview = view.total_reply_recyclerview



    }

     fun update_message_list(message_list:MutableList<Message_fundation_data>){

         inner_list = message_list

         notifyDataSetChanged()

     }



}