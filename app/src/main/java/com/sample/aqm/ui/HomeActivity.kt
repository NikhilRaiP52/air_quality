package com.sample.aqm.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.sample.aqm.R
import com.sample.aqm.datalayer.socket.AqiSocket
import com.sample.aqm.datalayer.socket.SocketUpdateListener
import java.net.URI

class HomeActivity : AppCompatActivity() {
    val viewModel: HomeViewModel by viewModels()
    private lateinit var mSocket : AqiSocket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }

    override fun onResume() {
        super.onResume()
        initSocketConnection()
        mSocket.connect()
    }

    override fun onPause() {
        super.onPause()
        mSocket.disconnect()
    }

    /**
     * initialize the web-socket
     * It will update the data to view model with help of callback (SocketUpdateListener)
     *
     */
    private fun initSocketConnection() {
        val uri : URI = URI(AqiSocket.SOCKET_URL)
        mSocket = AqiSocket(uri, object : SocketUpdateListener {
            override fun onUpdateSocket(message: String?) {
                message?.let {
                    runOnUiThread {
                        viewModel.updateAQI(it)
                    }
                }
            }
        })
    }
}