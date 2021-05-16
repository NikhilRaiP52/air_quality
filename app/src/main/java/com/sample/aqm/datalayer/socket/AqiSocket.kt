package com.sample.aqm.datalayer.socket

import android.util.Log
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI

class AqiSocket (val AQUri: URI, val listener: SocketUpdateListener){
    private val webSocketClient: WebSocketClient

    init {
        webSocketClient = object : WebSocketClient(AQUri) {
            override fun onOpen(handshakedata: ServerHandshake?) {
                Log.d(TAG, "onOpen() Called")
            }
            override fun onMessage(message: String?) {
                Log.i(TAG, "onMessage: $message")
                listener.onUpdateSocket(message)
            }
            override fun onClose(code: Int, reason: String?, remote: Boolean) {
                Log.d(TAG, "onClose: code -> $code, reason -> $reason, remote -> $remote")
            }
            override fun onError(ex: Exception?) {
                Log.e(TAG, "onError: ${ex?.message}")
            }
        }
    }

    /**
     * this functionality help to establish a connection with socket
     *
     */
    fun connect() {
            webSocketClient.connect()
    }

    /**
     * this functionality help to disconnect the connection with socket
     *
     */
    fun disconnect() {
            webSocketClient.close()
    }


    companion object {
        private val TAG = AqiSocket::class.java.canonicalName
        const val SOCKET_URL = "ws://city-ws.herokuapp.com/"
    }

}