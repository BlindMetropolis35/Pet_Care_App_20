package com.example.petcareapp20.mainhome.ui.personal.chatsupport.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petcareapp20.R
import com.example.petcareapp20.mainhome.ui.personal.chatsupport.data.Message
import com.example.petcareapp20.mainhome.ui.personal.chatsupport.utils.BotResponse
import com.example.petcareapp20.mainhome.ui.personal.chatsupport.utils.Constants.OPEN_GOOGLE
import com.example.petcareapp20.mainhome.ui.personal.chatsupport.utils.Constants.OPEN_SEARCH
import com.example.petcareapp20.mainhome.ui.personal.chatsupport.utils.Constants.RECEIVE_ID
import com.example.petcareapp20.mainhome.ui.personal.chatsupport.utils.Constants.SEND_ID
import com.example.petcareapp20.mainhome.ui.personal.chatsupport.utils.Time
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChatBotActivity : AppCompatActivity() {

    private val TAG = "ChatBotActivity"
    var messagesList = mutableListOf<Message>()
    private lateinit var adapter: MessagingAdapter
    private val botList = listOf("Peter", "Francesca", "Luigi", "Igor")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_chat_bot)

        recyclerView()

        clickEvents()

        val random = (0..3).random()
        customBotMessage("Hello! Today you're speaking with ${botList[random]}, how may I help?")
    }

    private fun clickEvents() {

        //Send a message
        val btn_send=findViewById<MaterialButton>(R.id.btn_send)
        btn_send.setOnClickListener {
            sendMessage()
        }

        //Scroll back to correct position when user clicks on text view
        val et_message=findViewById<EditText>(R.id.et_message)
        et_message.setOnClickListener {
            GlobalScope.launch {
                delay(100)
                withContext(Dispatchers.Main) {
                    val rv_messages=findViewById<RecyclerView>(R.id.rv_messages)
                    rv_messages.scrollToPosition(adapter.itemCount - 1)

                }
            }
        }
    }

    private fun recyclerView() {
        adapter = MessagingAdapter(this)
        val rv_messages=findViewById<RecyclerView>(R.id.rv_messages)
        rv_messages.adapter = adapter
        rv_messages.layoutManager = LinearLayoutManager(this)

    }

    override fun onStart() {
        super.onStart()
        //In case there are messages, scroll to bottom when re-opening app
        GlobalScope.launch {
            delay(100)
            withContext(Dispatchers.Main) {
                val rv_messages=findViewById<RecyclerView>(R.id.rv_messages)
                rv_messages.scrollToPosition(adapter.itemCount - 1)
            }
        }
    }

    private fun sendMessage() {
        val et_message=findViewById<EditText>(R.id.et_message)
        val message = et_message.text.toString()
        val timeStamp = Time.timeStamp()

        if (message.isNotEmpty()) {
            //Adds it to our local list
            messagesList.add(Message(message, SEND_ID, timeStamp))
            et_message.setText("")

            adapter.insertMessage(Message(message, SEND_ID, timeStamp))
            val rv_messages=findViewById<RecyclerView>(R.id.rv_messages)
            rv_messages.scrollToPosition(adapter.itemCount - 1)

            botResponse(message)
        }
    }

    private fun botResponse(message: String) {
        val timeStamp = Time.timeStamp()

        GlobalScope.launch {
            //Fake response delay
            delay(1000)

            withContext(Dispatchers.Main) {
                //Gets the response
                val response = BotResponse.basicResponses(message)

                //Adds it to our local list
                messagesList.add(Message(response, RECEIVE_ID, timeStamp))

                //Inserts our message into the adapter
                adapter.insertMessage(Message(response, RECEIVE_ID, timeStamp))

                //Scrolls us to the position of the latest message
                val rv_messages=findViewById<RecyclerView>(R.id.rv_messages)
                rv_messages.scrollToPosition(adapter.itemCount - 1)

                //Starts Google
                when (response) {
                    OPEN_GOOGLE -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        site.data = Uri.parse("https://www.google.com/")
                        startActivity(site)
                    }
                    OPEN_SEARCH -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        val searchTerm: String? = message.substringAfterLast("search")
                        site.data = Uri.parse("https://www.google.com/search?&q=$searchTerm")
                        startActivity(site)
                    }

                }
            }
        }
    }

    private fun customBotMessage(message: String) {

        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main) {
                val timeStamp = Time.timeStamp()
                messagesList.add(Message(message, RECEIVE_ID, timeStamp))
                adapter.insertMessage(Message(message, RECEIVE_ID, timeStamp))

                val rv_messages=findViewById<RecyclerView>(R.id.rv_messages)
                rv_messages.scrollToPosition(adapter.itemCount - 1)
            }
        }
    }
}
