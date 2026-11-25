package com.example.servicioskotlin

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class ServicioOperacion : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val numero = intent?.getIntExtra("numero", 0) ?: 0
        Log.d("ServicioOperacion", "Calculando factorial para número: $numero")

        Thread {
            val resultado = calcularFactorial(numero)
            Log.d("ServicioOperacion", "Factorial de $numero = $resultado")
        }.start()

        return START_NOT_STICKY
    }

    private fun calcularFactorial(n: Int): Long {
        if (n < 0) return -1
        var factorial: Long = 1
        for (i in 1..n) {
            factorial *= i
        }
        return factorial
    }

    override fun onDestroy() {
        Log.d("ServicioOperacion", "ServicioOperacion destruido")
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}