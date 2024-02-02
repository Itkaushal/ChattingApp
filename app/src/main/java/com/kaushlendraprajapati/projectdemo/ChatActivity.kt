package com.kaushlendraprajapati.projectdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kaushlendraprajapati.projectdemo.Adapter.MessageAdapter
import com.kaushlendraprajapati.projectdemo.databinding.ActivityChatBinding

class ChatActivity : AppCompatActivity() {

    var chatBinding:ActivityChatBinding?=null
    private lateinit var recyclerView: RecyclerView
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var mDbRef: DatabaseReference
    private lateinit var messageList: ArrayList<com.kaushlendraprajapati.projectdemo.Modal.Message>

    var receiverRoom: String? = null
    var senderRoom: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chatBinding=ActivityChatBinding.inflate(layoutInflater)
        setContentView(chatBinding!!.root)

        mDbRef=FirebaseDatabase.getInstance().getReference()
        recyclerView=chatBinding!!.recylerviewChat
        messageList = ArrayList()
        messageAdapter= MessageAdapter(this,messageList)
        recyclerView.layoutManager=LinearLayoutManager(this)
        recyclerView.adapter=messageAdapter

        val name= intent.getStringExtra("name")
        val receiverUid= intent.getStringExtra("uid")
        val senderUid = FirebaseAuth.getInstance().uid
        supportActionBar?.title=name
        supportActionBar?.show()

        if(senderUid!=null  && receiverUid!=null) {
            senderRoom = receiverUid + senderUid
            receiverRoom = senderUid + receiverUid
        }
        // logic for adding data to recycler view
            mDbRef.child("chats").child(senderRoom!!).child("messages")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {

                        messageList.clear()
                        for (postSnapshot in snapshot.children) {
                            val message =
                                postSnapshot.getValue(com.kaushlendraprajapati.projectdemo.Modal.Message::class.java)

                            messageList.add(message!!)
                        }
                        messageAdapter.notifyDataSetChanged()
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })

        chatBinding!!.sendButton.setOnClickListener {

            val message = chatBinding!!.messageBox.text.toString()
            val messageObject = com.kaushlendraprajapati.projectdemo.Modal.Message(message,senderUid)

            mDbRef.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    mDbRef.child("chats").child(receiverRoom!!).child("messages").push()
                        .setValue(messageObject)
                }
            chatBinding!!.messageBox.setText("")
        }
    }
}