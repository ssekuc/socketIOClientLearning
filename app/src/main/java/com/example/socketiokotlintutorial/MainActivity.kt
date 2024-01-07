package com.example.socketiokotlintutorial

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.socketiokotlintutorial.ui.theme.SocketIOKotlinTutorialTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SocketHandler.setSocket()
        SocketHandler.establishConnection()

        setContent {
            SocketIOKotlinTutorialTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    CounterButton()
                }
            }
        }
    }
}

@Composable
fun CounterButton(modifier: Modifier = Modifier) {
    val counter = remember { mutableStateOf(0) }
    val mSocket = SocketHandler.getSocket()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = mSocket) {
        mSocket.on("counter") { args ->
            if (args[0] != null) {
                coroutineScope.launch(Dispatchers.Main) {
                    try {
                        counter.value = args[0] as Int
                        Log.d("CounterUpdate", "Counter updated to ${counter.value}")
                    } catch (e: Exception) {
                        Log.e("CounterUpdate", "Error updating counter: ${e.message}")
                    }
                }
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "${counter.value}", fontSize = 30.sp)
        Button(onClick = { mSocket.emit("counter") }) {
            Text("Counter")
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SocketIOKotlinTutorialTheme {
        CounterButton()
    }
}