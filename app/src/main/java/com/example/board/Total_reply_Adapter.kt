package com.example.board

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_reply.view.*

class Total_reply_Adapter(var context: Context, var outer_list:MutableList<Reply_fundation_data>):
    RecyclerView.Adapter<Total_reply_Adapter.ViewHolder>() {

    var inner_list = mutableListOf<Reply_fundation_data>()

    init {

        inner_list = outer_list

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Total_reply_Adapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_reply,parent,false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = inner_list.size

    override fun onBindViewHolder(holder: Total_reply_Adapter.ViewHolder, position: Int) {
        holder.reply_user_name.text = inner_list[position].user.name
        holder.my_reply_content.text = inner_list[position].content
        holder.my_reply_date.text = inner_list[position].created_at
    }

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){

        val reply_user_name =view.reply_user_name
        val my_reply_content = view.my_reply_content
        val my_reply_date = view.my_reply_date

    }

    fun update_reply_list(reply_list:MutableList<Reply_fundation_data>){

        inner_list = reply_list

        notifyDataSetChanged()

    }
}