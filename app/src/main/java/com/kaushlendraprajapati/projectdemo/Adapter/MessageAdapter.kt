package com.kaushlendraprajapati.projectdemo.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.firebase.auth.FirebaseAuth
import com.kaushlendraprajapati.projectdemo.Modal.Message
import com.kaushlendraprajapati.projectdemo.R

class MessageAdapter(val context: Context, val messageList: ArrayList<Message>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        val ITEM_SENT = 2
        val ITEM_RECEIVE = 1


    class SentViewHolder(itemView: View) : ViewHolder(itemView)
    {
        val sentMessage = itemView.findViewById<TextView>(R.id.txt_sent_message)
    }
    class ReceiveViewHolder(itemView: View) : ViewHolder(itemView)
    {
        val receiveMessage = itemView.findViewById<TextView>(R.id.tex_receive_message)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        if (viewType == 1)
        {
            // inflate receive
            val view: View= LayoutInflater.from(context).inflate(R.layout.recieve,parent,false)
            return ReceiveViewHolder(view)

        }
        else{
            // inflate sent
            val view: View= LayoutInflater.from(context).inflate(R.layout.sent,parent,false)
            return SentViewHolder(view)
        }

    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentMessage=messageList[position]

        if (holder.javaClass == SentViewHolder::class.java)
        {
            //do stuff sent vieholder...

            val viewHolder = holder as SentViewHolder
            holder.sentMessage.text=currentMessage.message

        }
        else
        {
            // do stuff receive viewholder..
            val viewHolder = holder as ReceiveViewHolder
            holder.receiveMessage.text=currentMessage.message
        }
    }

    override fun getItemViewType(position: Int): Int {

        val currentMessage =messageList[position]
        if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId))
        {
            return ITEM_SENT
        }
        else{
            return ITEM_RECEIVE
        }
    }
}