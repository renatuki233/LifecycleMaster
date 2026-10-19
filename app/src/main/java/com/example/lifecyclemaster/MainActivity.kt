package com.example.lifecyclemaster

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import android.app.ActivityManager
import android.app.ApplicationStartInfo
import android.os.Build


class MainActivity : ComponentActivity() {

    private val TAG = "LIFECYCLE_DEBUG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "onCreate: La Activity se está creando")
        if (Build.VERSION.SDK_INT >= 35) {

            val activityManager =
                getSystemService(ActivityManager::class.java)

            val startInfo =
                activityManager
                    .getHistoricalProcessStartReasons(1)
                    .firstOrNull()

            val tipoInicio = when (startInfo?.startType) {
                ApplicationStartInfo.START_TYPE_COLD ->
                    "Fría (Cold Start)"

                ApplicationStartInfo.START_TYPE_WARM ->
                    "Cálida (Warm Start)"

                ApplicationStartInfo.START_TYPE_HOT ->
                    "Caliente (Hot Start)"

                else ->
                    "Tipo de inicio no determinado"
            }

            Log.d("START_INFO", "Tipo de inicio: $tipoInicio")
        }

        setContent {

            val viewModel: CounterViewModel = viewModel()

            val count by viewModel.count.collectAsState()

            MaterialTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Text(
                        text = "Contador Seguro: $count",
                        style = MaterialTheme.typography.headlineLarge
                    )

                    Spacer(
                        modifier = Modifier.height(20.dp)
                    )

                    Button(
                        onClick = {
                            viewModel.increment()
                        }
                    ) {
                        Text("Incrementar")
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: La Activity es visible")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: La Activity tiene el foco (interactuable)")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: La Activity pierde el foco")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: La Activity ya no es visible")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: La Activity va a ser destruida")
    }
}