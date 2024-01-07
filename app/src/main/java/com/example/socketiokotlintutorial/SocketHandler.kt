import android.util.Log
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException

object SocketHandler {

    lateinit var mSocket: Socket


    @Synchronized
    fun setSocket() {
        try {
            mSocket = IO.socket("http://10.0.0.208:3000")
        } catch (e: URISyntaxException) {
            Log.e("SocketHandler", "URISyntaxException: ${e.message}")
        }
    }


    @Synchronized
    fun getSocket(): Socket {
        return mSocket
    }

    @Synchronized
    fun establishConnection() {
        mSocket.connect()
        Log.d("SocketHandler", "Socket connection established")
    }


    @Synchronized
    fun closeConnection() {
        mSocket.disconnect()
    }
}